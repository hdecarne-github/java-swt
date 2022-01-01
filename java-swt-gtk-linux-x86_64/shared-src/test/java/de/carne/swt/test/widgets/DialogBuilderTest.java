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
package de.carne.swt.test.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.ToolBar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.boot.Application;
import de.carne.test.swt.DisableIfThreadNotSWTCapable;
import de.carne.test.swt.tester.SWTTest;
import de.carne.test.swt.tester.accessor.ToolBarAccessor;

/**
 * Test {@linkplain de.carne.swt.widgets.DialogBuilder} class and derived ones.
 */
@DisableIfThreadNotSWTCapable
class DialogBuilderTest extends SWTTest {

	@Test
	void testTestUserApplication() {
		Script script = script(Application::main);

		script.add(this::doMessageBox);
		script.add(this::doColorDialog);
		script.add(this::doDirectoryDialog);
		script.add(this::doFileDialog);
		script.add(this::doFontDialog);
		script.add(this::doPrintDialog);
		script.add(this::doClose);
		script.execute();
		Assertions.assertTrue(script.passed());
	}

	private void doMessageBox() {
		mockMessageBox().offerResult(SWT.CANCEL);
		accessShell().accessChild(ToolBarAccessor::new, ToolBar.class, 0).accessItem(0).select();
	}

	private void doColorDialog() {
		mockColorDialog().offerResult((RGB) null);
		accessShell().accessChild(ToolBarAccessor::new, ToolBar.class, 0).accessItem(1).select();
	}

	private void doDirectoryDialog() {
		mockDirectoryDialog().offerResult((String) null);
		accessShell().accessChild(ToolBarAccessor::new, ToolBar.class, 0).accessItem(2).select();
	}

	private void doFileDialog() {
		mockFileDialog().offerResult((String) null);
		accessShell().accessChild(ToolBarAccessor::new, ToolBar.class, 0).accessItem(3).select();
	}

	private void doFontDialog() {
		mockFontDialog().offerResult((FontData) null);
		accessShell().accessChild(ToolBarAccessor::new, ToolBar.class, 0).accessItem(4).select();
	}

	private void doPrintDialog() {
		mockPrintDialog().offerResult((PrinterData) null);
		accessShell().accessChild(ToolBarAccessor::new, ToolBar.class, 0).accessItem(5).select();
	}

	private void doClose() {
		accessShell().close();
	}

}
