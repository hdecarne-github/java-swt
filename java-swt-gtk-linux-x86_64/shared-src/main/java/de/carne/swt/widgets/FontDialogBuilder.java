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
package de.carne.swt.widgets;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * {@linkplain FontDialog} builder.
 */
public class FontDialogBuilder extends DialogBuilder<FontDialog> {

	/**
	 * Constructs a new {@linkplain FontDialogBuilder} instance.
	 *
	 * @param dialog the dialog to build.
	 */
	public FontDialogBuilder(FontDialog dialog) {
		super(dialog);
	}

	/**
	 * Convenience function for creating a {@linkplain FontDialogBuilder} instance for a standard font chooser dialog.
	 *
	 * @param parent the dialog's parent.
	 * @return the created builder.
	 */
	public static FontDialogBuilder choose(Shell parent) {
		return new FontDialogBuilder(new FontDialog(parent));
	}

	/**
	 * Sets the font dialog's effect controls visibility.
	 *
	 * @param visible whether to show the effects selection controls or not.
	 * @return the updated builder.
	 * @see FontDialog#setEffectsVisible(boolean)
	 */
	public FontDialogBuilder withEffectsVisible(boolean visible) {
		get().setEffectsVisible(visible);
		return this;
	}

	/**
	 * Sets the font dialog's initial font color.
	 *
	 * @param rgb the color to set.
	 * @return the updated builder.
	 * @see FontDialog#setRGB(RGB)
	 */
	public FontDialogBuilder withRgb(RGB rgb) {
		get().setRGB(rgb);
		return this;
	}

	/**
	 * Sets the font dialog's initial font selection.
	 *
	 * @param fontData the fonts to set.
	 * @return the updated builder.
	 * @see FontDialog#setFontList(FontData[])
	 */
	public FontDialogBuilder withFontList(FontData[] fontData) {
		get().setFontList(fontData);
		return this;
	}

	/**
	 * Opens the font dialog.
	 *
	 * @return the selected font or {@code null} if the selection has been cancelled.
	 * @see FontDialog#open()
	 */
	@Nullable
	public FontData open() {
		return get().open();
	}

}
