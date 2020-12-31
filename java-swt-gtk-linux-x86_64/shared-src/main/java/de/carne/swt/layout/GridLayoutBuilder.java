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

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

/**
 * {@linkplain GridLayout} builder.
 */
public final class GridLayoutBuilder extends LayoutBuilder<GridLayout> {

	private GridLayoutBuilder(GridLayout layout) {
		super(layout);
	}

	/**
	 * Starts layout build.
	 *
	 * @return the created builder.
	 */
	public static GridLayoutBuilder layout() {
		return new GridLayoutBuilder(new GridLayout());
	}

	/**
	 * Starts layout build.
	 *
	 * @param numColumns the number of grid columns.
	 * @return the created builder.
	 */
	public static GridLayoutBuilder layout(int numColumns) {
		return layout(numColumns, false);
	}

	/**
	 * Starts layout build.
	 *
	 * @param numColumns the number of grid columns.
	 * @param makeColumnsEqualWidth whether to make the grid columns equal width.
	 * @return the created builder.
	 * @see GridLayout#GridLayout(int, boolean)
	 */
	public static GridLayoutBuilder layout(int numColumns, boolean makeColumnsEqualWidth) {
		return new GridLayoutBuilder(new GridLayout(numColumns, makeColumnsEqualWidth));
	}

	/**
	 * Sets the layout's margin attributes.
	 *
	 * @param width the horizontal margin.
	 * @param height the vertical margin.
	 * @return the updated builder.
	 */
	public GridLayoutBuilder margin(int width, int height) {
		GridLayout layout = get();

		layout.marginWidth = width;
		layout.marginHeight = height;
		return this;
	}

	/**
	 * Sets the layout's margin attributes.
	 *
	 * @param left the left margin.
	 * @param top the top margin.
	 * @param right the right margin.
	 * @param bottom the bottom margin.
	 * @return the updated builder.
	 */
	public GridLayoutBuilder margin(int left, int top, int right, int bottom) {
		GridLayout layout = get();

		layout.marginLeft = left;
		layout.marginTop = top;
		layout.marginRight = right;
		layout.marginBottom = bottom;
		return this;
	}

	/**
	 * Sets the layout's spacing attributes.
	 *
	 * @param horizontalSpacing the horizontal spacing.
	 * @param verticalSpacing the vertical spacing.
	 * @return the updated builder.
	 */
	public GridLayoutBuilder spacing(int horizontalSpacing, int verticalSpacing) {
		GridLayout layout = get();

		layout.horizontalSpacing = horizontalSpacing;
		layout.verticalSpacing = verticalSpacing;
		return this;
	}

	/**
	 * Begins layout data build.
	 *
	 * @return the created builder.
	 */
	public static DataBuilder data() {
		return new DataBuilder(new GridData());
	}

	/**
	 * Begins layout data build.
	 *
	 * @param style the data style.
	 * @return the created builder.
	 * @see GridData#GridData(int)
	 */
	public static DataBuilder data(int style) {
		return new DataBuilder(new GridData(style));
	}

	/**
	 * {@linkplain GridData} builder.
	 */
	public static class DataBuilder extends LayoutDataBuilder<GridData> {

		DataBuilder(GridData layoutData) {
			super(layoutData);
		}

		/**
		 * Sets the layout data's alignment attributes.
		 *
		 * @param horizontalAlignment the horizontal alignment.
		 * @param verticalAlignment the vertical alignment.
		 * @return the updated builder.
		 */
		public DataBuilder align(int horizontalAlignment, int verticalAlignment) {
			GridData layoutData = get();

			layoutData.horizontalAlignment = horizontalAlignment;
			layoutData.verticalAlignment = verticalAlignment;
			return this;
		}

		/**
		 * Sets the layout data's preferred size attributes.
		 *
		 * @param widthHint the preferred width in pixels.
		 * @param heightHint the preferred height in pixels.
		 * @return the updated builder.
		 */
		public DataBuilder preferredSize(int widthHint, int heightHint) {
			GridData layoutData = get();

			layoutData.widthHint = widthHint;
			layoutData.heightHint = heightHint;
			return this;
		}

		/**
		 * Sets the layout data's minimum size attributes.
		 *
		 * @param minimumWidth the minimum width in pixels.
		 * @param minimumHeight the minimum height in pixels.
		 * @return the updated builder.
		 */
		public DataBuilder minimumSize(int minimumWidth, int minimumHeight) {
			GridData layoutData = get();

			layoutData.minimumWidth = minimumWidth;
			layoutData.minimumHeight = minimumHeight;
			return this;
		}

		/**
		 * Sets the layout data's indent attributes.
		 *
		 * @param horizontalIndent the horizontal indent.
		 * @param verticalIndent the vertical indent.
		 * @return the updated builder.
		 */
		public DataBuilder indent(int horizontalIndent, int verticalIndent) {
			GridData layoutData = get();

			layoutData.horizontalIndent = horizontalIndent;
			layoutData.verticalIndent = verticalIndent;
			return this;
		}

		/**
		 * Sets the layout data's span attributes.
		 *
		 * @param horizontalSpan the horizontal span.
		 * @param verticalSpan the vertical span.
		 * @return the updated builder.
		 */
		public DataBuilder span(int horizontalSpan, int verticalSpan) {
			GridData layoutData = get();

			layoutData.horizontalSpan = horizontalSpan;
			layoutData.verticalSpan = verticalSpan;
			return this;
		}

		/**
		 * Sets the layout data's grab attributes.
		 *
		 * @param grabExcessHorizontalSpace whether to grap excessive horizontal space.
		 * @param grabExcessVerticalSpace whether to grap excessive vertical space.
		 * @return the updated builder.
		 */
		public DataBuilder grab(boolean grabExcessHorizontalSpace, boolean grabExcessVerticalSpace) {
			GridData layoutData = get();

			layoutData.grabExcessHorizontalSpace = grabExcessHorizontalSpace;
			layoutData.grabExcessVerticalSpace = grabExcessVerticalSpace;
			return this;
		}

		/**
		 * Sets the layout data's exclude attribute.
		 *
		 * @param exclude whether to exclude the control during layout.
		 * @return the updated builder.
		 */
		public DataBuilder exclude(boolean exclude) {
			get().exclude = exclude;
			return this;
		}

	}

}
