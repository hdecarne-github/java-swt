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

import java.util.function.Consumer;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ToolBar;
import org.junit.jupiter.api.Assertions;

import de.carne.boot.Application;
import de.carne.test.swt.tester.SWTTest;
import de.carne.test.swt.tester.accessor.ButtonAccessor;
import de.carne.test.swt.tester.accessor.ControlAccessor;
import de.carne.test.swt.tester.accessor.CoolBarAccessor;
import de.carne.test.swt.tester.accessor.ItemAccessor;
import de.carne.test.swt.tester.accessor.ToolBarAccessor;

/**
 * Base class providing the actual {@linkplain TestApp} tests for execution within multiple projects.
 */
@SuppressWarnings("squid:S2187")
public class TestAppTest extends SWTTest {

	protected void executeTestScript(String testName) {
		Script script = script(Application::run).args(testName);

		script.add(this::checkShellActivated);
		script.add(this::doSelectCommand);
		script.add(this::doOpenAboutInfo, true);
		script.add(this::waitAboutInfoOpened, this::doCloseAboutInfo);
		script.add(this::doOpenLogView, true);
		script.add(this::waitLogViewOpened, this::doCloseLogView);
		script.add(this::doClose);
		script.execute();
	}

	private void checkMessage(Consumer<String> check) {
		List list = accessShell().accessChild(ControlAccessor::wrapControl, List.class, 1).get();
		@NonNull String[] listSelection = list.getSelection();

		Assertions.assertEquals(1, listSelection.length);

		check.accept(listSelection[0]);
	}

	protected void checkShellActivated() {
		traceAction();
		checkMessage(message -> Assertions.assertTrue(message.startsWith("ShellEvent{")));
	}

	protected void doSelectCommand() {
		traceAction();
		accessShell().accessChild(CoolBarAccessor::wrapCoolBar, CoolBar.class, 0).accessCoolItem(0)
				.accessControl(ToolBarAccessor::wrapToolBar, ToolBar.class).accessToolItem(2).select();
		checkMessage(message -> Assertions.assertTrue(message.startsWith("SelectionEvent{")));
	}

	protected void doOpenAboutInfo() {
		traceAction();
		accessShell().accessMenuItem(ItemAccessor.matchText(TestApp.ROOT_MENU_WIDGETS_ABOUTINFO)).select();
	}

	protected ButtonAccessor waitAboutInfoOpened() {
		traceAction();
		return accessShell("About n/a").accessButton(ButtonAccessor.matchText("Close"));
	}

	protected void doCloseAboutInfo(ButtonAccessor buttonAccessor) {
		traceAction();
		buttonAccessor.select();
	}

	protected void doOpenLogView() {
		traceAction();
		accessShell().accessMenuItem(ItemAccessor.matchText(TestApp.ROOT_MENU_WIDGETS_LOGVIEW)).select();
	}

	protected ButtonAccessor waitLogViewOpened() {
		traceAction();
		return accessShell("Log").accessButton(ButtonAccessor.matchText("Close"));
	}

	protected void doCloseLogView(ButtonAccessor buttonAccessor) {
		traceAction();
		buttonAccessor.select();
	}

	protected void doClose() {
		traceAction();
		accessShell().accessMenuItem(ItemAccessor.matchText(TestApp.ROOT_MENU_SHELL_CLOSE)).select();
	}

}
