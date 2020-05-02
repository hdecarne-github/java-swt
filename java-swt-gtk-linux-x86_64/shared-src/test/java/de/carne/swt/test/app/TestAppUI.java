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

import java.net.URL;
import java.util.Objects;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.junit.jupiter.api.Assertions;

import de.carne.swt.graphics.ResourceException;
import de.carne.swt.layout.GridLayoutBuilder;
import de.carne.swt.test.app.resources.Resources;
import de.carne.swt.widgets.ControlBuilder;
import de.carne.swt.widgets.CoolBarBuilder;
import de.carne.swt.widgets.MenuBuilder;
import de.carne.swt.widgets.ShellBuilder;
import de.carne.swt.widgets.ShellUserInterface;
import de.carne.swt.widgets.ToolBarBuilder;
import de.carne.swt.widgets.aboutinfo.AboutInfoDialog;
import de.carne.swt.widgets.logview.LogViewDialog;
import de.carne.swt.widgets.runtimeinfo.RuntimeInfo;
import de.carne.util.Late;
import de.carne.util.ManifestInfos;
import de.carne.util.logging.Log;

/**
 * Test application root shell user interface.
 */
public class TestAppUI extends ShellUserInterface {

	private static final Log LOG = new Log();

	private final String title;
	private final Late<List> messageListHolder = new Late<>();

	/**
	 * Constructs a new {@linkplain TestAppUI} instance.
	 *
	 * @param shell the user interface {@linkplain Shell}.
	 * @param title the title to set.
	 */
	protected TestAppUI(Shell shell, String title) {
		super(shell);
		this.title = title;
	}

	@Override
	public void open() throws ResourceException {
		LOG.notice("Opening Test UI...");

		Shell shell = root();
		ShellBuilder shellBuilder = new ShellBuilder(shell);

		shellBuilder.withText(this.title).withImages(Resources.getImages(shell.getDisplay(), Resources.APP_ICON));
		shellBuilder.onShellActivated(this::onShellActivated);
		buildMenuBar();
		CoolBar commandBar = buildCommandBar();
		List messageList = this.messageListHolder.set(
				shellBuilder.addControlChild(List.class, SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL).get());

		GridLayoutBuilder.data(GridData.FILL_HORIZONTAL).apply(commandBar);
		GridLayoutBuilder.data(GridData.FILL_BOTH).apply(messageList);
		GridLayoutBuilder.layout().apply(shell);
		shell.layout();
		shell.open();
	}

	private Menu buildMenuBar() {
		MenuBuilder menuBarBuilder = MenuBuilder.menuBar(root());

		menuBarBuilder.addItem(SWT.CASCADE).withText(TestApp.ROOT_MENU_SHELL);
		menuBarBuilder.beginMenu();
		menuBarBuilder.addItem(SWT.PUSH).withText(TestApp.ROOT_MENU_SHELL_CLOSE).onSelected(this::onShellClose);
		menuBarBuilder.endMenu();
		menuBarBuilder.addItem(SWT.CASCADE).withText(TestApp.ROOT_MENU_WIDGETS);
		menuBarBuilder.beginMenu();
		menuBarBuilder.addItem(SWT.PUSH).withText(TestApp.ROOT_MENU_WIDGETS_ABOUTINFO).onSelected(this::onAbout);
		menuBarBuilder.addItem(SWT.PUSH).withText(TestApp.ROOT_MENU_WIDGETS_LOGVIEW).onSelected(this::onLog);
		menuBarBuilder.endMenu();
		return menuBarBuilder.get();
	}

	private CoolBar buildCommandBar() throws ResourceException {
		Shell root = root();
		Display display = root.getDisplay();
		CoolBarBuilder commandBarBuilder = CoolBarBuilder.horizontal(root, SWT.FLAT);
		ToolBarBuilder commandsBuilder = ToolBarBuilder.horizontal(commandBarBuilder, SWT.FLAT);
		ControlBuilder<RuntimeInfo> runtimeInfoBuilder = new ControlBuilder<>(
				new RuntimeInfo(commandBarBuilder.get(), SWT.BORDER));

		commandsBuilder.addItem(SWT.PUSH);
		commandsBuilder.withImage(Resources.getImage(display, Resources.APP_ICON16));
		commandsBuilder.onSelected(this::onSelected);
		commandsBuilder.addItem(SWT.SEPARATOR);
		commandsBuilder.addItem(SWT.PUSH);
		commandsBuilder.withImage(Resources.getImage(display, Resources.APP_ICON16));
		commandsBuilder.onSelected(this::onSelected);
		commandsBuilder.addItem(SWT.SEPARATOR);
		commandBarBuilder.addItem(SWT.NONE).withControl(commandsBuilder);
		commandBarBuilder.addItem(SWT.NONE).withControl(runtimeInfoBuilder.get());
		commandBarBuilder.lock(true).pack();
		runtimeInfoBuilder.get().setTimer(500);
		return commandBarBuilder.get();
	}

	private void onShellClose() {
		LOG.info("Closing Main UI...");

		root().close();
	}

	private void onAbout() {
		LOG.info("Showing AboutInfo...");

		AboutInfoDialog about = AboutInfoDialog.build(root(), new ManifestInfos("TestApp"));

		try {
			URL logoUrl = Objects.requireNonNull(Resources.class.getResource(Resources.APP_ICON48));
			URL copyrightUrl = Objects.requireNonNull(Resources.class.getResource(Resources.ABOUT_TEXT));

			about.withLogo(logoUrl).withCopyright(copyrightUrl).open();
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}

	private void onLog() {
		LOG.info("Showing LogView...");

		LogViewDialog log = LogViewDialog.build(root(), Logger.getLogger(""));

		try {
			log.open();
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}

	private void onShellActivated(ShellEvent event) {
		LOG.info("Main UI activated");

		this.messageListHolder.get().add(event.toString());
		this.messageListHolder.get().select(this.messageListHolder.get().getItemCount() - 1);
	}

	private void onSelected(SelectionEvent event) {
		LOG.info("Main UI command selected");

		this.messageListHolder.get().add(event.toString());
		this.messageListHolder.get().select(this.messageListHolder.get().getItemCount() - 1);
	}

}
