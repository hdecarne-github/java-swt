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
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Shell;

/**
 * {@linkplain PrintDialog} builder.
 */
public class PrintDialogBuilder extends DialogBuilder<PrintDialog> {

	/**
	 * Constructs a new {@linkplain PrintDialogBuilder} instance.
	 *
	 * @param dialog the dialog to build.
	 */
	public PrintDialogBuilder(PrintDialog dialog) {
		super(dialog);
	}

	/**
	 * Convenience function for creating a {@linkplain PrintDialogBuilder} instance for a standard printer chooser
	 * dialog.
	 *
	 * @param parent the dialog's parent.
	 * @return the created builder.
	 */
	public static PrintDialogBuilder choose(Shell parent) {
		return new PrintDialogBuilder(new PrintDialog(parent));
	}

	/**
	 * Sets the printer dialog's initial end page option.
	 *
	 * @param endPage the end page option to set.
	 * @return the updated builder.
	 * @see PrintDialog#setEndPage(int)
	 */
	public PrintDialogBuilder withEndPage(int endPage) {
		get().setEndPage(endPage);
		return this;
	}

	/**
	 * Sets the printer dialog's initial printer selection.
	 *
	 * @param data the printer to set.
	 * @return the updated builder.
	 * @see PrintDialog#setPrinterData(PrinterData)
	 */
	public PrintDialogBuilder withPrinterData(PrinterData data) {
		get().setPrinterData(data);
		return this;
	}

	/**
	 * Sets the printer dialog's initial print to file option.
	 *
	 * @param printToFile the print to file option to set.
	 * @return the updated builder.
	 * @see PrintDialog#setPrintToFile(boolean)
	 */
	public PrintDialogBuilder withPrintToFile(boolean printToFile) {
		get().setPrintToFile(printToFile);
		return this;
	}

	/**
	 * Sets the printer dialog's initial scope option.
	 *
	 * @param scope the scope option to set.
	 * @return the updated builder.
	 * @see PrintDialog#setScope(int)
	 */
	public PrintDialogBuilder withScope(int scope) {
		get().setScope(scope);
		return this;
	}

	/**
	 * Sets the printer dialog's initial start page option.
	 *
	 * @param startPage the start page option to set.
	 * @return the updated builder.
	 * @see PrintDialog#setStartPage(int)
	 */
	public PrintDialogBuilder withStartPage(int startPage) {
		get().setStartPage(startPage);
		return this;
	}

	/**
	 * Opens the printer dialog.
	 *
	 * @return the selected print or {@code null} if the selection has been cancelled.
	 * @see PrintDialog#open()
	 */
	@Nullable
	public PrinterData open() {
		return get().open();
	}

}
