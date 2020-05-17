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
package de.carne.swt.test;

import java.util.Arrays;
import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import de.carne.swt.graphics.ResourceException;
import de.carne.swt.graphics.ResourceTracker;
import de.carne.swt.layout.GridLayoutBuilder;
import de.carne.swt.platform.PlatformIntegration;
import de.carne.swt.widgets.ControlBuilder;
import de.carne.swt.widgets.MenuBuilder;
import de.carne.swt.widgets.ShellBuilder;
import de.carne.swt.widgets.ShellUserInterface;
import de.carne.swt.widgets.aboutinfo.AboutInfoDialog;
import de.carne.swt.widgets.logview.LogViewDialog;
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
		menuBarBuilder.endMenu();
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
