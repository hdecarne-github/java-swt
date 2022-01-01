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

import java.util.function.Supplier;

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Control;

/**
 * {@linkplain FormLayout} builder.
 */
public final class FormLayoutBuilder extends LayoutBuilder<FormLayout> {

	private FormLayoutBuilder(FormLayout layout) {
		super(layout);
	}

	/**
	 * Start layout build.
	 *
	 * @return The created builder.
	 */
	public static FormLayoutBuilder layout() {
		return new FormLayoutBuilder(new FormLayout());
	}

	/**
	 * Set the layout's margin attributes.
	 *
	 * @param width The horizontal margin.
	 * @param height The vertical margin.
	 * @return The updated builder.
	 */
	public FormLayoutBuilder margin(int width, int height) {
		FormLayout layout = get();

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
	public FormLayoutBuilder margin(int left, int top, int right, int bottom) {
		FormLayout layout = get();

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
	public FormLayoutBuilder spacing(int spacing) {
		FormLayout layout = get();

		layout.spacing = spacing;
		return this;
	}

	/**
	 * Begin layout data build.
	 *
	 * @return The created builder.
	 */
	public static DataBuilder data() {
		return new DataBuilder(new FormData());
	}

	/**
	 * {@linkplain FormData} builder.
	 */
	public static class DataBuilder extends LayoutDataBuilder<FormData> {

		DataBuilder(FormData layoutData) {
			super(layoutData);
		}

		/**
		 * Set the layout data's preferred size attributes.
		 *
		 * @param width The preferred width in pixels.
		 * @param height The preferred height in pixels.
		 * @return The updated builder.
		 */
		public DataBuilder preferredSize(int width, int height) {
			FormData layoutData = get();

			layoutData.width = width;
			layoutData.height = height;
			return this;
		}

		/**
		 * Set the layout data's left attachment attribute.
		 *
		 * @param numerator The attachment's numerator value.
		 * @return The updated builder.
		 */
		public DataBuilder left(int numerator) {
			FormData layoutData = get();

			layoutData.left = new FormAttachment(numerator);
			return this;
		}

		/**
		 * Set the layout data's left attachment attribute.
		 *
		 * @param numerator The attachment's numerator value.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder left(int numerator, int offset) {
			FormData layoutData = get();

			layoutData.left = new FormAttachment(numerator, offset);
			return this;
		}

		/**
		 * Set the layout data's left attachment attribute.
		 *
		 * @param numerator The attachment's numerator value.
		 * @param denominator The attachment's denominator value.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder left(int numerator, int denominator, int offset) {
			FormData layoutData = get();

			layoutData.left = new FormAttachment(numerator, denominator, offset);
			return this;
		}

		/**
		 * Set the layout data's left attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @return The updated builder.
		 */
		public DataBuilder left(Control control) {
			FormData layoutData = get();

			layoutData.left = new FormAttachment(control);
			return this;
		}

		/**
		 * Set the layout data's left attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @return The updated builder.
		 */
		public DataBuilder left(Supplier<? extends Control> control) {
			return left(control.get());
		}

		/**
		 * Set the layout data's left attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder left(Control control, int offset) {
			FormData layoutData = get();

			layoutData.left = new FormAttachment(control, offset);
			return this;
		}

		/**
		 * Set the layout data's left attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder left(Supplier<? extends Control> control, int offset) {
			return left(control.get(), offset);
		}

		/**
		 * Set the layout data's left attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @param alignment The attachment's alignment value.
		 * @return The updated builder.
		 */
		public DataBuilder left(Control control, int offset, int alignment) {
			FormData layoutData = get();

			layoutData.left = new FormAttachment(control, offset, alignment);
			return this;
		}

		/**
		 * Set the layout data's left attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @param alignment The attachment's alignment value.
		 * @return The updated builder.
		 */
		public DataBuilder left(Supplier<? extends Control> control, int offset, int alignment) {
			return left(control.get(), offset, alignment);
		}

		/**
		 * Set the layout data's top attachment attribute.
		 *
		 * @param numerator The attachment's numerator value.
		 * @return The updated builder.
		 */
		public DataBuilder top(int numerator) {
			FormData layoutData = get();

			layoutData.top = new FormAttachment(numerator);
			return this;
		}

		/**
		 * Set the layout data's top attachment attribute.
		 *
		 * @param numerator The attachment's numerator value.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder top(int numerator, int offset) {
			FormData layoutData = get();

			layoutData.top = new FormAttachment(numerator, offset);
			return this;
		}

		/**
		 * Set the layout data's top attachment attribute.
		 *
		 * @param numerator The attachment's numerator value.
		 * @param denominator The attachment's denominator value.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder top(int numerator, int denominator, int offset) {
			FormData layoutData = get();

			layoutData.top = new FormAttachment(numerator, denominator, offset);
			return this;
		}

		/**
		 * Set the layout data's top attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @return The updated builder.
		 */
		public DataBuilder top(Control control) {
			FormData layoutData = get();

			layoutData.top = new FormAttachment(control);
			return this;
		}

		/**
		 * Set the layout data's top attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @return The updated builder.
		 */
		public DataBuilder top(Supplier<? extends Control> control) {
			return top(control.get());
		}

		/**
		 * Set the layout data's top attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder top(Control control, int offset) {
			FormData layoutData = get();

			layoutData.top = new FormAttachment(control, offset);
			return this;
		}

		/**
		 * Set the layout data's top attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder top(Supplier<? extends Control> control, int offset) {
			return top(control.get(), offset);
		}

		/**
		 * Set the layout data's top attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @param alignment The attachment's alignment value.
		 * @return The updated builder.
		 */
		public DataBuilder top(Control control, int offset, int alignment) {
			FormData layoutData = get();

			layoutData.top = new FormAttachment(control, offset, alignment);
			return this;
		}

		/**
		 * Set the layout data's top attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @param alignment The attachment's alignment value.
		 * @return The updated builder.
		 */
		public DataBuilder top(Supplier<? extends Control> control, int offset, int alignment) {
			return top(control.get(), offset, alignment);
		}

		/**
		 * Set the layout data's right attachment attribute.
		 *
		 * @param numerator The attachment's numerator value.
		 * @return The updated builder.
		 */
		public DataBuilder right(int numerator) {
			FormData layoutData = get();

			layoutData.right = new FormAttachment(numerator);
			return this;
		}

		/**
		 * Set the layout data's right attachment attribute.
		 *
		 * @param numerator The attachment's numerator value.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder right(int numerator, int offset) {
			FormData layoutData = get();

			layoutData.right = new FormAttachment(numerator, offset);
			return this;
		}

		/**
		 * Set the layout data's right attachment attribute.
		 *
		 * @param numerator The attachment's numerator value.
		 * @param denominator The attachment's denominator value.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder right(int numerator, int denominator, int offset) {
			FormData layoutData = get();

			layoutData.right = new FormAttachment(numerator, denominator, offset);
			return this;
		}

		/**
		 * Set the layout data's right attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @return The updated builder.
		 */
		public DataBuilder right(Control control) {
			FormData layoutData = get();

			layoutData.right = new FormAttachment(control);
			return this;
		}

		/**
		 * Set the layout data's right attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @return The updated builder.
		 */
		public DataBuilder right(Supplier<? extends Control> control) {
			return right(control.get());
		}

		/**
		 * Set the layout data's right attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder right(Control control, int offset) {
			FormData layoutData = get();

			layoutData.right = new FormAttachment(control, offset);
			return this;
		}

		/**
		 * Set the layout data's right attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder right(Supplier<? extends Control> control, int offset) {
			return right(control.get(), offset);
		}

		/**
		 * Set the layout data's right attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @param alignment The attachment's alignment value.
		 * @return The updated builder.
		 */
		public DataBuilder right(Control control, int offset, int alignment) {
			FormData layoutData = get();

			layoutData.right = new FormAttachment(control, offset, alignment);
			return this;
		}

		/**
		 * Set the layout data's right attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @param alignment The attachment's alignment value.
		 * @return The updated builder.
		 */
		public DataBuilder right(Supplier<? extends Control> control, int offset, int alignment) {
			return right(control.get(), offset, alignment);
		}

		/**
		 * Set the layout data's bottom attachment attribute.
		 *
		 * @param numerator The attachment's numerator value.
		 * @return The updated builder.
		 */
		public DataBuilder bottom(int numerator) {
			FormData layoutData = get();

			layoutData.bottom = new FormAttachment(numerator);
			return this;
		}

		/**
		 * Set the layout data's bottom attachment attribute.
		 *
		 * @param numerator The attachment's numerator value.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder bottom(int numerator, int offset) {
			FormData layoutData = get();

			layoutData.bottom = new FormAttachment(numerator, offset);
			return this;
		}

		/**
		 * Set the layout data's bottom attachment attribute.
		 *
		 * @param numerator The attachment's numerator value.
		 * @param denominator The attachment's denominator value.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder bottom(int numerator, int denominator, int offset) {
			FormData layoutData = get();

			layoutData.bottom = new FormAttachment(numerator, denominator, offset);
			return this;
		}

		/**
		 * Set the layout data's bottom attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @return The updated builder.
		 */
		public DataBuilder bottom(Control control) {
			FormData layoutData = get();

			layoutData.bottom = new FormAttachment(control);
			return this;
		}

		/**
		 * Set the layout data's bottom attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @return The updated builder.
		 */
		public DataBuilder bottom(Supplier<? extends Control> control) {
			return bottom(control.get());
		}

		/**
		 * Set the layout data's bottom attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder bottom(Control control, int offset) {
			FormData layoutData = get();

			layoutData.bottom = new FormAttachment(control, offset);
			return this;
		}

		/**
		 * Set the layout data's bottom attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @return The updated builder.
		 */
		public DataBuilder bottom(Supplier<? extends Control> control, int offset) {
			return bottom(control.get(), offset);
		}

		/**
		 * Set the layout data's bottom attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @param alignment The attachment's alignment value.
		 * @return The updated builder.
		 */
		public DataBuilder bottom(Control control, int offset, int alignment) {
			FormData layoutData = get();

			layoutData.bottom = new FormAttachment(control, offset, alignment);
			return this;
		}

		/**
		 * Set the layout data's bottom attachment attribute.
		 *
		 * @param control The control to attach to.
		 * @param offset The attachment's offset value.
		 * @param alignment The attachment's alignment value.
		 * @return The updated builder.
		 */
		public DataBuilder bottom(Supplier<? extends Control> control, int offset, int alignment) {
			return bottom(control.get(), offset, alignment);
		}

	}

}
