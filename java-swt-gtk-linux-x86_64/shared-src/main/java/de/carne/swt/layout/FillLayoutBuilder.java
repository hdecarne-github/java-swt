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

import org.eclipse.swt.layout.FillLayout;

/**
 * {@linkplain FillLayout} builder.
 */
public final class FillLayoutBuilder extends LayoutBuilder<FillLayout> {

	private FillLayoutBuilder(FillLayout layout) {
		super(layout);
	}

	/**
	 * Start layout build.
	 *
	 * @return The created builder.
	 */
	public static FillLayoutBuilder layout() {
		return new FillLayoutBuilder(new FillLayout());
	}

	/**
	 * Start layout build.
	 *
	 * @param type The layout type.
	 * @return The created builder.
	 * @see FillLayout#FillLayout(int)
	 */
	public static FillLayoutBuilder layout(int type) {
		return new FillLayoutBuilder(new FillLayout(type));
	}

	/**
	 * Set the layout's margin attributes.
	 *
	 * @param width The horizontal margin.
	 * @param height The vertical margin.
	 * @return The updated builder.
	 */
	public FillLayoutBuilder margin(int width, int height) {
		FillLayout layout = get();

		layout.marginWidth = width;
		layout.marginHeight = height;
		return this;
	}

	/**
	 * Set the layout's spacing attribute.
	 *
	 * @param spacing The spacing to set.
	 * @return The updated builder.
	 */
	public FillLayoutBuilder spacing(int spacing) {
		get().spacing = spacing;
		return this;
	}

}
