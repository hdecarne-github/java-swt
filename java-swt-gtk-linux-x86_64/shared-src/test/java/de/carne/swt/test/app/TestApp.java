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

import java.util.regex.Pattern;

/**
 * Test application texts.
 */
@SuppressWarnings("javadoc")
public final class TestApp {

	private TestApp() {
		// prevent instantiation
	}

	// Root shell
	public static final Pattern ROOT_TEXT_PATTERN = Pattern.compile(TestApp.class.getSimpleName() + ".*");

	// Root shell menu
	public static final String ROOT_MENU_SHELL = "Shell";
	public static final String ROOT_MENU_SHELL_CLOSE = "Close";

	// Widget menus
	public static final String ROOT_MENU_WIDGETS = "Widgets";
	public static final String ROOT_MENU_WIDGETS_ABOUTINFO = "Aboutinfo...";
	public static final String ROOT_MENU_WIDGETS_LOGVIEW = "Logview...";

}
