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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * {@linkplain MessageBox} builder.
 */
public class MessageBoxBuilder extends DialogBuilder<MessageBox> {

	private MessageBoxBuilder(MessageBox dialog) {
		super(dialog);
	}

	/**
	 * Constructs a new {@linkplain MessageBoxBuilder} instance.
	 *
	 * @param parent the dialog's parent.
	 * @param style the dialog's style.
	 * @return the created builder.
	 */
	public static MessageBoxBuilder build(Shell parent, int style) {
		return new MessageBoxBuilder(new MessageBox(parent, style));
	}

	/**
	 * Convenience function for creating a {@linkplain MessageBoxBuilder} instance for an error message box.
	 *
	 * @param parent the dialog's parent.
	 * @return the created builder.
	 */
	public static MessageBoxBuilder error(Shell parent) {
		return new MessageBoxBuilder(new MessageBox(parent, SWT.ICON_ERROR | SWT.APPLICATION_MODAL));
	}

	/**
	 * Convenience function for creating a {@linkplain MessageBoxBuilder} instance for an warning message box.
	 *
	 * @param parent the dialog's parent.
	 * @return the created builder.
	 */
	public static MessageBoxBuilder warning(Shell parent) {
		return new MessageBoxBuilder(new MessageBox(parent, SWT.ICON_WARNING | SWT.APPLICATION_MODAL));
	}

	/**
	 * Convenience function for creating a {@linkplain MessageBoxBuilder} instance for an info message box.
	 *
	 * @param parent the dialog's parent.
	 * @return the created builder.
	 */
	public static MessageBoxBuilder info(Shell parent) {
		return new MessageBoxBuilder(new MessageBox(parent, SWT.ICON_INFORMATION | SWT.APPLICATION_MODAL));
	}

	/**
	 * Sets the message box dialog's title text.
	 *
	 * @param text the text to set.
	 * @return the updated builder.
	 * @see MessageBox#setText(String)
	 */
	@Override
	public MessageBoxBuilder withText(String text) {
		get().setText(text);
		return this;
	}

	/**
	 * Sets the message box dialog's message.
	 *
	 * @param message the message to set.
	 * @return the updated builder.
	 * @see MessageBox#setMessage(String)
	 */
	public MessageBoxBuilder withMessage(String message) {
		get().setMessage(message);
		return this;
	}

	/**
	 * Opens the message box dialog.
	 *
	 * @return the selected button or {@code SWT#CANCEL} if the selection has been cancelled.
	 * @see MessageBox#open()
	 */
	public int open() {
		return get().open();
	}

}
