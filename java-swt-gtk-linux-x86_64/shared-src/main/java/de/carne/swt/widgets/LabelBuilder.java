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
package de.carne.swt.widgets;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;

/**
 * {@linkplain Label} builder.
 */
public class LabelBuilder extends ControlBuilder<Label> {

	/**
	 * Constructs a new {@linkplain LabelBuilder} instance.
	 *
	 * @param label the label to build.
	 */
	public LabelBuilder(Label label) {
		super(label);
	}

	/**
	 * Sets the {@linkplain Label}'s text.
	 *
	 * @param text The text to set.
	 * @return The updated builder.
	 * @see Label#setText(String)
	 */
	public LabelBuilder withText(String text) {
		get().setText(text);
		return this;
	}

	/**
	 * Sets the {@linkplain Label}'s image.
	 *
	 * @param image The {@linkplain Image} to set.
	 * @return The updated builder.
	 * @see Label#setImage(Image)
	 */
	public LabelBuilder withImage(Image image) {
		get().setImage(image);
		return this;
	}

}
