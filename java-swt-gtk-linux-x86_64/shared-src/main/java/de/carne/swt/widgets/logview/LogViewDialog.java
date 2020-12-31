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
package de.carne.swt.widgets.logview;

import java.net.URL;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;

import de.carne.swt.graphics.ResourceException;
import de.carne.util.ManifestInfos;
import de.carne.util.logging.Log;

/**
 * Dialog for displaying the application log.
 */
public class LogViewDialog extends Dialog {

	private final Logger logger;
	private final NavigableMap<Integer, URL> logoMap = new TreeMap<>();

	private LogViewDialog(Shell parent, Logger logger) {
		super(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
		this.logger = logger;
	}

	/**
	 * Starts a {@linkplain LogViewDialog} setup.
	 *
	 * @param parent the dialog's parent.
	 * @param log the {@linkplain Log} instance to monitor.
	 * @return the created {@linkplain LogViewDialog} instance for chaining.
	 * @see ManifestInfos
	 */
	public static LogViewDialog build(Shell parent, Log log) {
		return new LogViewDialog(parent, log.logger());
	}

	/**
	 * Starts a {@linkplain LogViewDialog} setup.
	 *
	 * @param parent the dialog's parent.
	 * @param logger the {@linkplain Logger} instance to monitor.
	 * @return the created {@linkplain LogViewDialog} instance for chaining.
	 * @see ManifestInfos
	 */
	public static LogViewDialog build(Shell parent, Logger logger) {
		return new LogViewDialog(parent, logger);
	}

	/**
	 * Sets the logo for a log level.
	 *
	 * @param level the {@linkplain Level} to set the logo for.
	 * @param logo the {@linkplain URL} of the logo to display.
	 * @return the updated {@linkplain LogViewDialog} instance for chaining.
	 */
	public LogViewDialog withLogo(Level level, URL logo) {
		this.logoMap.put(level.intValue(), logo);
		return this;
	}

	/**
	 * Opens and runs the dialog.
	 *
	 * @throws ResourceException if a required resource is not available.
	 */
	public void open() throws ResourceException {
		LogViewUI userInterface = new LogViewUI(new Shell(getParent(), getStyle()), this.logger, this.logoMap);

		userInterface.open();
		userInterface.run();
	}

}
