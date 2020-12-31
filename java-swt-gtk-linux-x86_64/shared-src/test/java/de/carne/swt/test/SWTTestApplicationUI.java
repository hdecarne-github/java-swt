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

import java.util.Arrays;
import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import de.carne.swt.graphics.ResourceException;
import de.carne.swt.graphics.ResourceTracker;
import de.carne.swt.layout.GridLayoutBuilder;
import de.carne.swt.platform.PlatformIntegration;
import de.carne.swt.widgets.ColorDialogBuilder;
import de.carne.swt.widgets.ControlBuilder;
import de.carne.swt.widgets.CoolBarBuilder;
import de.carne.swt.widgets.DirectoryDialogBuilder;
import de.carne.swt.widgets.FileDialogBuilder;
import de.carne.swt.widgets.FontDialogBuilder;
import de.carne.swt.widgets.MenuBuilder;
import de.carne.swt.widgets.MessageBoxBuilder;
import de.carne.swt.widgets.PrintDialogBuilder;
import de.carne.swt.widgets.ShellBuilder;
import de.carne.swt.widgets.ShellUserInterface;
import de.carne.swt.widgets.TabFolderBuilder;
import de.carne.swt.widgets.ToolBarBuilder;
import de.carne.swt.widgets.aboutinfo.AboutInfoDialog;
import de.carne.swt.widgets.logview.LogViewDialog;
import de.carne.swt.widgets.notification.Notification;
import de.carne.swt.widgets.runtimeinfo.RuntimeInfo;
import de.carne.util.Check;
import de.carne.util.Exceptions;
import de.carne.util.Late;
import de.carne.util.ManifestInfos;
import de.carne.util.logging.Log;

class SWTTestApplicationUI extends ShellUserInterface {

	private final ResourceTracker resources;

	private final Late<List> messageListHolder = new Late<>();

	SWTTestApplicationUI(Shell shell) {
		super(shell);
		this.resources = ResourceTracker.forDevice(shell.getDisplay());
	}

	@Override
	public void open() throws ResourceException {
		Shell root = root();

		setupRoot();

		root.layout();
		root.open();
	}

	private void setupRoot() {
		ShellBuilder rootBuilder = new ShellBuilder(root());

		rootBuilder.withText(SWTTestApplication.ROOT_TITLE).withImages(getAppIcons());
		rootBuilder.onShellIconified(() -> addMessage("shellIconified"));
		rootBuilder.onShellDeiconified(() -> addMessage("shellDeiconified"));
		rootBuilder.onShellActivated(() -> addMessage("shellActivated"));
		rootBuilder.onShellDeactivated(() -> addMessage("shellDeactivated"));
		rootBuilder.onShellClosed(() -> addMessage("shellClosed"));
		GridLayoutBuilder.layout(1, true).apply(rootBuilder);

		setupMenuBar();

		ToolBarBuilder dialogBarBuilder = ToolBarBuilder.horizontal(rootBuilder, SWT.FLAT);

		setupDialogBar(dialogBarBuilder);
		GridLayoutBuilder.data().align(SWT.FILL, SWT.TOP).grab(true, false).apply(dialogBarBuilder);

		CoolBarBuilder coolBarBuilder = CoolBarBuilder.horizontal(rootBuilder, SWT.FLAT);

		setupCoolBar(coolBarBuilder);
		GridLayoutBuilder.data().align(SWT.FILL, SWT.TOP).grab(true, false).apply(coolBarBuilder);

		TabFolderBuilder tabFolderBuilder = TabFolderBuilder.top(rootBuilder, SWT.NONE);

		setupTabFolder(tabFolderBuilder);
		GridLayoutBuilder.data().align(SWT.FILL, SWT.TOP).grab(true, false).apply(tabFolderBuilder);

		ControlBuilder<RuntimeInfo> runtimeInfoBuilder = rootBuilder.addControlChild(RuntimeInfo.class, SWT.BORDER);

		GridLayoutBuilder.data().align(SWT.FILL, SWT.TOP).grab(true, false).apply(runtimeInfoBuilder);

		ControlBuilder<Label> messageListLabelBuilder = rootBuilder.addLabelChild(SWT.LEFT);

		messageListLabelBuilder.get().setText("Messages");
		GridLayoutBuilder.data().align(SWT.FILL, SWT.TOP).grab(true, false).apply(messageListLabelBuilder);

		ControlBuilder<List> messageListBuilder = rootBuilder.addControlChild(List.class, SWT.LEFT);

		GridLayoutBuilder.data().align(SWT.FILL, SWT.FILL).grab(true, true).apply(messageListBuilder);
		this.messageListHolder.set(messageListBuilder);
	}

	private void setupMenuBar() {
		MenuBuilder menuBarBuilder = MenuBuilder.menuBar(root());

		menuBarBuilder.addItem(SWT.CASCADE).withText(SWTTestApplication.MENU_APPLICATION);
		menuBarBuilder.beginMenu();
		menuBarBuilder.addItem(SWT.PUSH).withText(SWTTestApplication.MENU_ITEM_QUIT).onSelected(() -> root().close());
		menuBarBuilder.endMenu();
		menuBarBuilder.addItem(SWT.CASCADE).withText(SWTTestApplication.MENU_TEST);
		menuBarBuilder.beginMenu();
		menuBarBuilder.addItem(SWT.PUSH).withText(SWTTestApplication.MENU_ITEM_ABOUT).onSelected(this::onAboutSelected);
		menuBarBuilder.addItem(SWT.PUSH).withText(SWTTestApplication.MENU_ITEM_LOGS).onSelected(this::onLogsSelected);
		menuBarBuilder.addItem(SWT.PUSH).withText(SWTTestApplication.MENU_ITEM_NOTIFICATION)
				.onSelected(this::onNotificationSelected);
		menuBarBuilder.endMenu();
	}

	private void setupDialogBar(ToolBarBuilder dialogBarBuilder) {
		dialogBarBuilder.addItem(SWT.PUSH).withText(SWTTestApplication.TOOL_ITEM_MESSAGE)
				.onSelected(this::onMessageBoxSelected);
		dialogBarBuilder.addItem(SWT.PUSH).withText(SWTTestApplication.TOOL_ITEM_COLOR)
				.onSelected(this::onColorDialogSelected);
		dialogBarBuilder.addItem(SWT.PUSH).withText(SWTTestApplication.TOOL_ITEM_DIRECTORY)
				.onSelected(this::onDirectoryDialogSelected);
		dialogBarBuilder.addItem(SWT.PUSH).withText(SWTTestApplication.TOOL_ITEM_FILE)
				.onSelected(this::onFileDialogSelected);
		dialogBarBuilder.addItem(SWT.PUSH).withText(SWTTestApplication.TOOL_ITEM_FONT)
				.onSelected(this::onFontDialogSelected);
		dialogBarBuilder.addItem(SWT.PUSH).withText(SWTTestApplication.TOOL_ITEM_PRINT)
				.onSelected(this::onPrintDialogSelected);
	}

	private void setupCoolBar(CoolBarBuilder coolBarBuilder) {
		coolBarBuilder.addItem(SWT.NONE);
		coolBarBuilder.withControl(coolBarBuilder.addButtonChild(SWT.PUSH).withText(SWTTestApplication.BUTTON_LEFT)
				.onSelected(this::onCoolButtonSelected));
		coolBarBuilder.addItem(SWT.NONE);
		coolBarBuilder.withControl(coolBarBuilder.addButtonChild(SWT.PUSH).withText(SWTTestApplication.BUTTON_MIDDLE)
				.onSelected(this::onCoolButtonSelected));
		coolBarBuilder.addItem(SWT.NONE);
		coolBarBuilder.withControl(coolBarBuilder.addButtonChild(SWT.PUSH).withText(SWTTestApplication.BUTTON_RIGHT)
				.onSelected(this::onCoolButtonSelected));
	}

	private void setupTabFolder(TabFolderBuilder tabFolderBuilder) {
		tabFolderBuilder.addItem(SWT.NONE).withText(SWTTestApplication.TAB_LEFT);
		tabFolderBuilder.addItem(SWT.NONE).withText(SWTTestApplication.TAB_MIDDLE);
		tabFolderBuilder.addItem(SWT.NONE).withText(SWTTestApplication.TAB_RIGHT);
	}

	private void onAboutSelected() {
		try {
			AboutInfoDialog dialog = AboutInfoDialog.build(root(),
					new ManifestInfos("java-test-swt-" + PlatformIntegration.toolkitName()));

			dialog.withCopyright(Objects.requireNonNull(SWTTestApplication.class.getResource("about.txt")));
			dialog.withLogo(Objects.requireNonNull(SWTTestApplication.class.getResource("app_icon128.png")));
			dialog.open();
		} catch (ResourceException e) {
			throw Exceptions.toRuntime(e);
		}
	}

	private void onLogsSelected() {
		try {
			LogViewDialog dialog = LogViewDialog.build(root(), Log.root());

			dialog.open();
		} catch (ResourceException e) {
			throw Exceptions.toRuntime(e);
		}
	}

	private void onNotificationSelected() {
		Notification error = Notification.error(root()).withText("Error");

		error.open();
	}

	private void onMessageBoxSelected() {
		MessageBoxBuilder messageBox = MessageBoxBuilder.build(root(), SWT.ICON_INFORMATION | SWT.CLOSE);

		messageBox.withText("MessageBox").withMessage("Message");

		int result = messageBox.open();

		addMessage("MessageBox: " + result);
	}

	private static final RGB DEFAULT_COLOR = new RGB(1, 2, 3);
	private static final RGB[] DEFAULT_COLORS = new RGB[] { DEFAULT_COLOR, new RGB(3, 2, 1) };

	private void onColorDialogSelected() {
		ColorDialogBuilder colorDialog = ColorDialogBuilder.choose(root());

		colorDialog.withText("ColorDialog");
		colorDialog.withRgb(DEFAULT_COLOR);
		colorDialog.withRgbs(DEFAULT_COLORS);

		RGB color = colorDialog.open();

		addMessage("ColorDialog: " + color);
	}

	private void onDirectoryDialogSelected() {
		DirectoryDialogBuilder directoryDialog = DirectoryDialogBuilder.choose(root());

		directoryDialog.withText("DirectoryDialog");
		directoryDialog.withMessage("Message").withFilterPath("*");

		String directory = directoryDialog.open();

		addMessage("DirectoryDialog: " + directory);
	}

	private void onFileDialogSelected() {
		FileDialogBuilder fileDialog = FileDialogBuilder.save(root());

		fileDialog.withText("FileDialog");
		fileDialog.withFileName("file.txt");
		fileDialog.withFilterPath("*");
		fileDialog.withFilter("*|All|*.txt|Text");
		fileDialog.withFilterIndex(1);
		fileDialog.withOverwrite(false);

		String file = fileDialog.open();

		addMessage("FileDialog: " + file);
	}

	private void onFontDialogSelected() {
		FontDialogBuilder fontDialog = FontDialogBuilder.choose(root());

		fontDialog.withText("FontDialog");
		fontDialog.withFontList(root().getDisplay().getFontList(null, true));
		fontDialog.withRgb(DEFAULT_COLOR);

		FontData font = fontDialog.open();

		addMessage("FontDialog: " + font);
	}

	private void onPrintDialogSelected() {
		PrintDialogBuilder printDialog = PrintDialogBuilder.choose(root());

		printDialog.withText("PrintDialog");
		printDialog.withPrinterData(new PrinterData());
		printDialog.withScope(PrinterData.PAGE_RANGE);
		printDialog.withStartPage(1);
		printDialog.withEndPage(2);
		printDialog.withPrintToFile(false);

		PrinterData printer = printDialog.open();

		addMessage("PrintDialog: " + printer);
	}

	private void onCoolButtonSelected(SelectionEvent evt) {
		addMessage("CoolButton selected: " + Check.isInstanceOf(evt.widget, Button.class).getText());
	}

	private Image[] getAppIcons() {
		return this.resources.getImages(getClass(),
				Arrays.asList("app_icon16.png", "app_icon32.png", "app_icon48.png", "app_icon128.png"));
	}

	private void addMessage(String message) {
		List messageList = this.messageListHolder.get();

		messageList.add(message);
		messageList.select(messageList.getItemCount() - 1);
	}

}
