/*
 * Copyright (c) 2007-2020 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.swt.test.app;

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
 * Test application main class.
 */
public class TestAppMain extends UserApplication implements ApplicationMain {

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
		return new TestAppUI(new Shell(display), getCmdLine().toString());
	}

}
