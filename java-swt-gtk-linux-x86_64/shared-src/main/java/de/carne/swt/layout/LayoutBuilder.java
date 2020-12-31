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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

/**
 * Base class for all layout builders.
 *
 * @param <T> The actual layout type.
 */
public abstract class LayoutBuilder<T extends Layout> implements Supplier<T> {

	private final T layout;

	/**
	 * Construct {@linkplain LayoutBuilder}.
	 *
	 * @param layout The actual layout object.
	 */
	protected LayoutBuilder(T layout) {
		this.layout = layout;
	}

	/**
	 * Apply the built layout to a {@linkplain Composite}.
	 *
	 * @param composite The {@linkplain Composite} to apply the layout to.
	 * @see Composite#setLayout(Layout)
	 */
	public void apply(Composite composite) {
		composite.setLayout(this.layout);
	}

	/**
	 * Apply the built layout to a {@linkplain Composite}.
	 *
	 * @param composite The {@linkplain Composite} to apply the layout to.
	 * @see Composite#setLayout(Layout)
	 */
	public void apply(Supplier<? extends Composite> composite) {
		apply(composite.get());
	}

	/**
	 * Get the built layout object.
	 *
	 * @return The build layout object.
	 */
	@Override
	public T get() {
		return this.layout;
	}

}
