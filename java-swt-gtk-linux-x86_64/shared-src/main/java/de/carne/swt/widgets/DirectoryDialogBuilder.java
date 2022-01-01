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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * {@linkplain DirectoryDialog} builder.
 */
public class DirectoryDialogBuilder extends DialogBuilder<DirectoryDialog> {

	/**
	 * Constructs a new {@linkplain DirectoryDialogBuilder} instance.
	 *
	 * @param dialog the dialog to build.
	 */
	public DirectoryDialogBuilder(DirectoryDialog dialog) {
		super(dialog);
	}

	/**
	 * Convenience function for creating a {@linkplain DirectoryDialogBuilder} instance for a standard directory dialog.
	 *
	 * @param parent the dialog's parent.
	 * @return the created builder.
	 */
	public static DirectoryDialogBuilder choose(Shell parent) {
		return new DirectoryDialogBuilder(new DirectoryDialog(parent, SWT.DEFAULT));
	}

	/**
	 * Sets the directory dialog's message text.
	 *
	 * @param message the message text to set.
	 * @return the updated builder.
	 * @see DirectoryDialog#setMessage(String)
	 */
	public DirectoryDialogBuilder withMessage(String message) {
		get().setMessage(message);
		return this;
	}

	/**
	 * Sets the directory dialog's filter path.
	 *
	 * @param path the filter path to set.
	 * @return the updated builder.
	 * @see FileDialog#setFilterPath(String)
	 */
	public DirectoryDialogBuilder withFilterPath(String path) {
		get().setFilterPath(path);
		return this;
	}

	/**
	 * Opens the directory dialog.
	 * 
	 * @return the selected directory or {@code null} if the selection has been cancelled.
	 * @see DirectoryDialog#open()
	 */
	@Nullable
	public String open() {
		return get().open();
	}

}
