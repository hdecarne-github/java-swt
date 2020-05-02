/*
 * Copyright (c) 2017-2020 Holger de Carne and contributors, All Rights Reserved.
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

import java.nio.file.Paths;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.boot.Application;
import de.carne.swt.test.app.TestAppTest;
import de.carne.swt.widgets.FileDialogBuilder;
import de.carne.swt.widgets.MessageBoxBuilder;
import de.carne.test.swt.DisableIfThreadNotSWTCapable;
import de.carne.test.swt.tester.SWTTest;

/**
 * Test {@linkplain SWTTest} class - Native dialog mocks.
 */
@DisableIfThreadNotSWTCapable
class DialogBuilderTest extends TestAppTest {

	@Test
	void testStartStop() {
		Script script = script(Application::run).args(getClass().getSimpleName());

		script.add(this::doMessageBox);
		script.add(this::doFileDialog);
		script.add(this::doDirectoryDialog);
		script.add(this::doPrintDialog);
		script.add(this::doColorDialog);
		script.add(this::doFontDialog);
		script.add(this::doClose).execute();
	}

	private void doMessageBox() {
		traceAction();

		mockMessageBox().result(SWT.NO);

		String mbText = getClass().getSimpleName();
		String mbMessage = "Guess the answer for this test?";

		MessageBox messageBox = MessageBoxBuilder.build(accessShell().get(), SWT.ICON_QUESTION | SWT.YES | SWT.NO)
				.withText(mbText).withMessage(mbMessage).get();

		Assertions.assertEquals(mbText, messageBox.getText());
		Assertions.assertEquals(mbMessage, messageBox.getMessage());
		Assertions.assertEquals(SWT.NO, messageBox.open());
		Assertions.assertEquals(SWT.CANCEL, messageBox.open());
	}

	private void doFileDialog() {
		traceAction();

		String testFileName = getClass().getSimpleName() + ".java";

		mockFileDialog().result(testFileName);

		String fdFileName = getClass().getSimpleName() + ".class";
		String fdText = "Open file";

		FileDialog fileDialog = FileDialogBuilder.open(accessShell().get()).withFileName(fdFileName).withText(fdText)
				.get();

		Assertions.assertEquals(fdFileName, fileDialog.getFileName());
		Assertions.assertEquals(fdText, fileDialog.getText());
		Assertions.assertEquals(testFileName, fileDialog.open());
		Assertions.assertNull(fileDialog.open());
	}

	private void doDirectoryDialog() {
		traceAction();

		String testDirectoryName = Paths.get(".").toAbsolutePath().toString();

		mockDirectoryDialog().result(testDirectoryName);

		DirectoryDialog directoryDialog = new DirectoryDialog(accessShell().get(), SWT.OPEN);

		Assertions.assertEquals(testDirectoryName, directoryDialog.open());
		Assertions.assertNull(directoryDialog.open());
	}

	private void doPrintDialog() {
		traceAction();

		PrinterData testPrinterData = new PrinterData("generic", "dummy");

		mockPrintDialog().result(testPrinterData);

		PrintDialog printDialog = new PrintDialog(accessShell().get());
		PrinterData printDialogResult = printDialog.open();

		Assertions.assertNotNull(printDialogResult);
		Assertions.assertEquals(testPrinterData.toString(), printDialogResult.toString());
		Assertions.assertNull(printDialog.open());
	}

	private void doColorDialog() {
		traceAction();

		RGB testRGB = new RGB(1, 2, 3);

		mockColorDialog().result(testRGB);

		ColorDialog colorDialog = new ColorDialog(accessShell().get());
		RGB colorDialogResult = colorDialog.open();

		Assertions.assertEquals(testRGB, colorDialogResult);
		Assertions.assertNull(colorDialog.open());
	}

	private void doFontDialog() {
		traceAction();

		Shell shell = accessShell().get();
		Display display = shell.getDisplay();
		FontData testFontData = display.getSystemFont().getFontData()[0];

		mockFontDialog().result(testFontData);

		FontDialog fontDialog = new FontDialog(shell);
		FontData fontDialogResult = fontDialog.open();

		Assertions.assertNotNull(fontDialogResult);
		Assertions.assertEquals(testFontData, fontDialogResult);
		Assertions.assertNull(fontDialog.open());
	}

}
