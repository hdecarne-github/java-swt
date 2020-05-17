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
package de.carne.swt.test.widgets.logview;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.boot.Application;
import de.carne.swt.test.SWTTestApplication;
import de.carne.test.swt.DisableIfThreadNotSWTCapable;
import de.carne.test.swt.tester.SWTTest;
import de.carne.test.swt.tester.accessor.ShellAccessor;

/**
 * Test {@linkplain de.carne.swt.widgets.logview.LogViewDialog} class.
 */
@DisableIfThreadNotSWTCapable
class LogViewTest extends SWTTest {

	@Test
	void testTestUserApplication() {
		Script script = script(Application::main);

		script.add(this::doOpenLogs, true);
		script.add(this::doWaitLogs, this::doCloseLogs);
		script.add(this::doClose);
		script.execute();
		Assertions.assertTrue(script.passed());
	}

	private void doOpenLogs() {
		accessShell().accessMenuBar().accessItem(SWTTestApplication.MENU_ITEM_LOGS).select();
	}

	private ShellAccessor doWaitLogs() {
		return accessShell("Log");
	}

	private void doCloseLogs(ShellAccessor about) {
		about.close();
	}

	private void doClose() {
		accessShell().close();
	}

}
