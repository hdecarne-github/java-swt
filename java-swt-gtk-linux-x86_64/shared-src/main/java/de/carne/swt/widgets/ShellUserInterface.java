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

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.carne.util.Exceptions;
import de.carne.util.logging.Log;

/**
 * Base class for {@linkplain Shell} based top-level user interfaces.
 */
public abstract class ShellUserInterface extends UserInterface<Shell> {

	private static final Log LOG = new Log();

	/**
	 * Constructs a new {@linkplain ShellUserInterface} instance.
	 *
	 * @param shell the user interface {@linkplain Shell}.
	 */
	protected ShellUserInterface(Shell shell) {
		super(shell);
	}

	/**
	 * Runs the event dispatching loop on this {@linkplain UserInterface} instance.
	 */
	public void run() {
		Shell shell = root();
		Display display = shell.getDisplay();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Displays a generic unexpected exception error message box.
	 *
	 * @param exception the causing exception.
	 */
	protected void unexpectedException(Throwable exception) {
		LOG.warning(exception, "An unexpected exception occurred while executing ''{0}''", root().getText());

		MessageBoxBuilder.error(root()).withText(MessagesI18N.i18nTextUnexpectedException())
				.withMessage(MessagesI18N.i18nMessageUnexpectedException(Exceptions.toString(exception))).get().open();
	}

}
