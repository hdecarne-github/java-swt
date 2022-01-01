/*
 * Copyright (c) 2007-2022 Holger de Carne and contributors, All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.carne.swt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * This class represents an observable property value.
 *
 * @param <T> the actual property type.
 */
public class Property<T> implements Supplier<T> {

	private T value;
	private List<PropertyChangedListener<T>> changedListeners = new ArrayList<>();

	/**
	 * Constructs a new {@linkplain Property} instance.
	 *
	 * @param value the initial property value.
	 */
	public Property(T value) {
		this.value = value;
	}

	/**
	 * Gets the property value.
	 */
	@Override
	public T get() {
		return this.value;
	}

	/**
	 * Sets the property value.
	 *
	 * @param newValue the value to set.
	 * @return the old property value.
	 */
	public T set(T newValue) {
		return set(newValue, false);
	}

	/**
	 * Sets the property value.
	 *
	 * @param newValue the value to set.
	 * @param forceChange whether to always notify registered ({@code true}) or only if value is changed
	 * ({@code false}).
	 * @return the old property value.
	 */
	public synchronized T set(T newValue, boolean forceChange) {
		T oldValue = this.value;

		this.value = newValue;
		if (forceChange || !Objects.equals(this.value, oldValue)) {
			for (PropertyChangedListener<T> changedListener : this.changedListeners) {
				changedListener.changed(this.value, oldValue);
			}
		}
		return oldValue;
	}

	/**
	 * Adds a {@linkplain PropertyChangedListener}.
	 * <p>
	 * The added listener is called every time the property value is changed.
	 *
	 * @param changedListener the listener to add.
	 * @return the updated {@linkplain Property} instance.
	 */
	public synchronized Property<T> addChangedListener(PropertyChangedListener<T> changedListener) {
		this.changedListeners.add(changedListener);
		return this;
	}

	/**
	 * Removes a previously added {@linkplain PropertyChangedListener}.
	 *
	 * @param changedListener the listener to remove.
	 * @return the updated {@linkplain Property} instance.
	 */
	public synchronized Property<T> removeChangedListener(PropertyChangedListener<T> changedListener) {
		this.changedListeners.remove(changedListener);
		return this;
	}

}
