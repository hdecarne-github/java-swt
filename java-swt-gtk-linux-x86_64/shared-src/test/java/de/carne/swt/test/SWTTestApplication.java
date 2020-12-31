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
package de.carne.swt.test;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.jupiter.api.Assertions;

import de.carne.boot.ApplicationMain;
import de.carne.swt.UserApplication;
import de.carne.swt.graphics.ResourceException;
import de.carne.swt.widgets.ShellUserInterface;
import de.carne.util.cmdline.CmdLineProcessor;
import de.carne.util.logging.Logs;

/**
 * SWT Test Application
 */
public class SWTTestApplication extends UserApplication implements ApplicationMain {

	/**
	 * Root shell title.
	 */
	public final static String ROOT_TITLE = "root";
	/**
	 * Progress dialog title.
	 */
	public final static String PROGRESS_TITLE = "progress";

	/**
	 * Application menu.
	 */
	public final static String MENU_APPLICATION = "Application";
	/**
	 * Application - Quit menu item.
	 */
	public final static String MENU_ITEM_QUIT = "Quit";
	/**
	 * Test menu.
	 */
	public final static String MENU_TEST = "Test";
	/**
	 * Test - About menu item.
	 */
	public final static String MENU_ITEM_ABOUT = "About";
	/**
	 * Test - Logs menu item.
	 */
	public final static String MENU_ITEM_LOGS = "Logs";
	/**
	 * Test - Notification menu item.
	 */
	public final static String MENU_ITEM_NOTIFICATION = "Notification";

	/**
	 * Message tool item.
	 */
	public final static String TOOL_ITEM_MESSAGE = "Message";
	/**
	 * Color tool item.
	 */
	public final static String TOOL_ITEM_COLOR = "Color";
	/**
	 * Directory tool item.
	 */
	public final static String TOOL_ITEM_DIRECTORY = "Directory";
	/**
	 * File tool item.
	 */
	public final static String TOOL_ITEM_FILE = "File";
	/**
	 * Font tool item.
	 */
	public final static String TOOL_ITEM_FONT = "Font";
	/**
	 * Print tool item.
	 */
	public final static String TOOL_ITEM_PRINT = "Print";

	/**
	 * Left button text.
	 */
	public final static String BUTTON_LEFT = "Left button";
	/**
	 * Middle button text.
	 */
	public final static String BUTTON_MIDDLE = "Middle button";
	/**
	 * Right button text.
	 */
	public final static String BUTTON_RIGHT = "Right button";
	/**
	 * Close button text.
	 */
	public final static String BUTTON_CLOSE = "Close";

	/**
	 * Left tab text.
	 */
	public final static String TAB_LEFT = "Left tab";
	/**
	 * Middle tab text.
	 */
	public final static String TAB_MIDDLE = "Middle tab";
	/**
	 * Right tab text.
	 */
	public final static String TAB_RIGHT = "Right tab";

	@Override
	public int run(String[] args) {
		int status = -1;

		try {
			Logs.readConfig(Logs.CONFIG_DEFAULT);

			@SuppressWarnings("null") CmdLineProcessor cmdLine = new CmdLineProcessor(name(), args);

			cmdLine.onUnknownArg(CmdLineProcessor::ignore);
			cmdLine.onUnnamedOption(CmdLineProcessor::ignore);
			status = run(cmdLine);
		} catch (Exception e) {
			Throwable cause = e.getCause();

			if (cause instanceof AssertionError) {
				throw (AssertionError) cause;
			}
			Assertions.fail("Uncaught exception: " + e.getClass().getName(), e);
		}
		return status;
	}

	@Override
	protected Display setupDisplay() throws ResourceException {
		Display.setAppName(name());
		return new Display();
	}

	@Override
	protected ShellUserInterface setupUserInterface(Display display) throws ResourceException {
		return new SWTTestApplicationUI(new Shell(display));
	}

}
