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
package de.carne.swt.layout;

import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;

/**
 * {@linkplain RowLayout} builder.
 */
public final class RowLayoutBuilder extends LayoutBuilder<RowLayout> {

	private RowLayoutBuilder(RowLayout layout) {
		super(layout);
	}

	/**
	 * Start layout build.
	 *
	 * @return The created builder.
	 */
	public static RowLayoutBuilder layout() {
		return new RowLayoutBuilder(new RowLayout());
	}

	/**
	 * Start layout build.
	 *
	 * @param type The layout type.
	 * @return The created builder.
	 * @see RowLayout#RowLayout(int)
	 */
	public static RowLayoutBuilder layout(int type) {
		return new RowLayoutBuilder(new RowLayout(type));
	}

	/**
	 * Set the layout's wrap attribute.
	 *
	 * @param wrap Whether to wrap the controls in case of insufficient space.
	 * @return The updated builder.
	 */
	public RowLayoutBuilder wrap(boolean wrap) {
		get().wrap = wrap;
		return this;
	}

	/**
	 * Set the layout's pack attribute.
	 *
	 * @param pack Whether to pack the controls to their preferred size.
	 * @return The updated builder.
	 */
	public RowLayoutBuilder pack(boolean pack) {
		get().pack = pack;
		return this;
	}

	/**
	 * Set the layout's fill attribute.
	 *
	 * @param fill Whether to fill space to make all controls the same height/width.
	 * @return The updated builder.
	 */
	public RowLayoutBuilder fill(boolean fill) {
		get().fill = fill;
		return this;
	}

	/**
	 * Set the layout's center attribute.
	 *
	 * @param center Whether to center the controls in their space.
	 * @return The updated builder.
	 */
	public RowLayoutBuilder center(boolean center) {
		get().center = center;
		return this;
	}

	/**
	 * Set the layout's justify attribute.
	 *
	 * @param justify Whether to fully justify the controls.
	 * @return The updated builder.
	 */
	public RowLayoutBuilder justify(boolean justify) {
		get().justify = justify;
		return this;
	}

	/**
	 * Set the layout's margin attributes.
	 *
	 * @param width The horizontal margin.
	 * @param height The vertical margin.
	 * @return The updated builder.
	 */
	public RowLayoutBuilder margin(int width, int height) {
		RowLayout layout = get();

		layout.marginWidth = width;
		layout.marginHeight = height;
		return this;
	}

	/**
	 * Set the layout's margin attributes.
	 *
	 * @param left The left margin.
	 * @param top The top margin.
	 * @param right The right margin.
	 * @param bottom The bottom margin.
	 * @return The updated builder.
	 */
	public RowLayoutBuilder margin(int left, int top, int right, int bottom) {
		RowLayout layout = get();

		layout.marginLeft = left;
		layout.marginTop = top;
		layout.marginRight = right;
		layout.marginBottom = bottom;
		return this;
	}

	/**
	 * Set the layout's spacing attribute.
	 *
	 * @param spacing The spacing to set.
	 * @return The updated builder.
	 */
	public RowLayoutBuilder spacing(int spacing) {
		get().spacing = spacing;
		return this;
	}

	/**
	 * Begin layout data build.
	 *
	 * @return The created builder.
	 */
	public static DataBuilder data() {
		return new DataBuilder(new RowData());
	}

	/**
	 * {@linkplain RowData} builder.
	 */
	public static class DataBuilder extends LayoutDataBuilder<RowData> {

		DataBuilder(RowData layoutData) {
			super(layoutData);
		}

		/**
		 * Set the layout data's size attributes.
		 *
		 * @param width The desired width.
		 * @param height The desired height.
		 * @return The updated builder.
		 */
		public DataBuilder size(int width, int height) {
			RowData layoutData = get();

			layoutData.width = width;
			layoutData.height = height;
			return this;
		}

		/**
		 * Set the layout data's exclude attribute.
		 *
		 * @param exclude Whether to exclude the control during layout.
		 * @return The updated builder.
		 */
		public DataBuilder exclude(boolean exclude) {
			get().exclude = exclude;
			return this;
		}

	}

}
