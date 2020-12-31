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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import de.carne.util.Strings;

/**
 * {@linkplain FileDialog} builder.
 */
public class FileDialogBuilder extends DialogBuilder<FileDialog> {

	/**
	 * Constructs a new {@linkplain FileDialogBuilder} instance.
	 *
	 * @param dialog the dialog to build.
	 */
	public FileDialogBuilder(FileDialog dialog) {
		super(dialog);
	}

	/**
	 * Convenience function for creating a {@linkplain FileDialogBuilder} instance for a standard open file dialog.
	 *
	 * @param parent the dialog's parent.
	 * @return the created builder.
	 */
	public static FileDialogBuilder open(Shell parent) {
		return new FileDialogBuilder(new FileDialog(parent, SWT.OPEN));
	}

	/**
	 * Convenience function for creating a {@linkplain FileDialogBuilder} instance for a standard save file dialog.
	 *
	 * @param parent the dialog's parent.
	 * @return the created builder.
	 */
	public static FileDialogBuilder save(Shell parent) {
		return new FileDialogBuilder(new FileDialog(parent, SWT.SAVE)).withOverwrite(true);
	}

	/**
	 * Sets the file dialog's filter (extensions as well as names).
	 * <p>
	 * The submitted filter string must contain one or more pair of filter extensions and names separated by a pipe char
	 * {@code "|"}. As the resulting filter tokens are processed via the {@linkplain Strings#decode(CharSequence)} the
	 * pipe char can be part by the filter by adding it using it's encoded representation {@code "\\u007c"}
	 *
	 * @param filter the filter string to set.
	 * @return the updated builder.
	 * @see FileDialog#setFilterExtensions(String[])
	 * @see FileDialog#setFilterNames(String[])
	 */
	public FileDialogBuilder withFilter(String filter) {
		List<String> extensions = new ArrayList<>();
		List<String> names = new ArrayList<>();
		StringTokenizer filterTokens = new StringTokenizer(filter, "|");
		boolean extensionToken = true;

		while (filterTokens.hasMoreTokens()) {
			String token = Strings.decode(filterTokens.nextToken());

			(extensionToken ? extensions : names).add(token);
			extensionToken = !extensionToken;
		}

		FileDialog fileDialog = get();

		fileDialog.setFilterExtensions(extensions.toArray(new @Nullable String[extensions.size()]));
		fileDialog.setFilterNames(names.toArray(new @Nullable String[names.size()]));
		return this;
	}

	/**
	 * Sets the file dialog's filter path.
	 *
	 * @param path the filter path to set.
	 * @return the updated builder.
	 * @see FileDialog#setFilterPath(String)
	 */
	public FileDialogBuilder withFilterPath(String path) {
		get().setFilterPath(path);
		return this;
	}

	/**
	 * Sets the file dialog's initial filter index.
	 *
	 * @param index the index to set.
	 * @return the updated builder.
	 * @see FileDialog#setFilterIndex(int)
	 */
	public FileDialogBuilder withFilterIndex(int index) {
		get().setFilterIndex(index);
		return this;
	}

	/**
	 * Sets the file dialog's initial file name.
	 *
	 * @param file the file name to set.
	 * @return the updated builder.
	 * @see FileDialog#setFileName(String)
	 */
	public FileDialogBuilder withFileName(String file) {
		int filterPathLength = file.length();

		while (filterPathLength > 0 && "/\\".indexOf(file.charAt(filterPathLength - 1)) < 0) {
			filterPathLength--;
		}

		FileDialog fileDialog = get();

		if (filterPathLength > 0) {
			fileDialog.setFilterPath(file.substring(0, filterPathLength));
			fileDialog.setFileName(file.substring(filterPathLength));
		} else {
			fileDialog.setFileName(file);
		}
		return this;
	}

	/**
	 * Sets the file dialog's overwrite option.
	 *
	 * @param overwrite the overwrite flag to set.
	 * @return the updated builder.
	 * @see FileDialog#setOverwrite(boolean)
	 */
	public FileDialogBuilder withOverwrite(boolean overwrite) {
		get().setOverwrite(overwrite);
		return this;
	}

	/**
	 * Opens the file dialog.
	 *
	 * @return the selected file or {@code null} if the selection has been cancelled.
	 * @see FileDialog#open()
	 */
	@Nullable
	public String open() {
		return get().open();
	}

}
