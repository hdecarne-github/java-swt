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

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * {@linkplain ColorDialog} builder.
 */
public class ColorDialogBuilder extends DialogBuilder<ColorDialog> {

	/**
	 * Constructs a new {@linkplain ColorDialogBuilder} instance.
	 *
	 * @param dialog the dialog to build.
	 */
	public ColorDialogBuilder(ColorDialog dialog) {
		super(dialog);
	}

	/**
	 * Convenience function for creating a {@linkplain ColorDialogBuilder} instance for a standard color chooser dialog.
	 *
	 * @param parent the dialog's parent.
	 * @return the created builder.
	 */
	public static ColorDialogBuilder choose(Shell parent) {
		return new ColorDialogBuilder(new ColorDialog(parent));
	}

	/**
	 * Sets the color dialog's initial color.
	 *
	 * @param rgb the color to set.
	 * @return the updated builder.
	 * @see ColorDialog#setRGB(RGB)
	 */
	public ColorDialogBuilder withRgb(RGB rgb) {
		get().setRGB(rgb);
		return this;
	}

	/**
	 * Sets the color dialog's initial colors.
	 *
	 * @param rgbs the colors to set.
	 * @return the updated builder.
	 * @see ColorDialog#setRGBs(RGB[])
	 */
	public ColorDialogBuilder withRgbs(RGB[] rgbs) {
		get().setRGBs(rgbs);
		return this;
	}

	/**
	 * Opens the color dialog.
	 *
	 * @return the selected color or {@code null} if the selection has been cancelled.
	 * @see ColorDialog#open()
	 */
	@Nullable
	public RGB open() {
		return get().open();
	}

}
