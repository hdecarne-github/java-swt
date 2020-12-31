/*
 * Copyright (c) 2007-2021 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.swt.layout;

import java.util.function.Supplier;

import org.eclipse.swt.widgets.Control;

/**
 * Base class for all layout data builders.
 *
 * @param <T> The actual layout data type.
 */
public abstract class LayoutDataBuilder<T> implements Supplier<T> {

	private final T layoutData;

	/**
	 * Construct {@linkplain LayoutDataBuilder}.
	 *
	 * @param layoutData The actual layout data object.
	 */
	protected LayoutDataBuilder(T layoutData) {
		this.layoutData = layoutData;
	}

	/**
	 * Apply the built layout data to a {@linkplain Control}.
	 *
	 * @param control The {@linkplain Control} to apply the layout to.
	 * @see Control#setLayoutData(Object)
	 */
	public void apply(Control control) {
		control.setLayoutData(this.layoutData);
	}

	/**
	 * Apply the built layout data to a {@linkplain Control}.
	 *
	 * @param control The {@linkplain Control} to apply the layout to.
	 * @see Control#setLayoutData(Object)
	 */
	public void apply(Supplier<? extends Control> control) {
		apply(control.get());
	}

	/**
	 * Get the built layout data object.
	 *
	 * @return The build layout data object.
	 */
	@Override
	public T get() {
		return this.layoutData;
	}

}
