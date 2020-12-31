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
package de.carne.swt.util;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolItem;

/**
 * Utility class providing {@linkplain UICommand} related functions.
 */
public final class UICommands {

	private UICommands() {
		// prevent instantiation
	}

	/**
	 * Creates a {@linkplain MenuItem} based {@linkplain UICommand} instance.
	 *
	 * @param menuItem the {@linkplain MenuItem} to wrap.
	 * @return the created {@linkplain UICommand} instance.
	 */
	public static UICommand of(MenuItem menuItem) {
		return new MenuItemUICommand(menuItem);
	}

	/**
	 * Creates a {@linkplain ToolItem} based {@linkplain UICommand} instance.
	 *
	 * @param toolItem the {@linkplain ToolItem} to wrap.
	 * @return the created {@linkplain UICommand} instance.
	 */
	public static UICommand of(ToolItem toolItem) {
		return new ToolItemUICommand(toolItem);
	}

	/**
	 * Creates a {@linkplain Control} based {@linkplain UICommand} instance.
	 *
	 * @param control the {@linkplain Control} to wrap.
	 * @return the created {@linkplain UICommand} instance.
	 */
	public static UICommand of(Control control) {
		return new ControlUICommand(control);
	}

	private static class MenuItemUICommand implements UICommand {

		private final MenuItem menuItem;

		MenuItemUICommand(MenuItem menuItem) {
			this.menuItem = menuItem;
		}

		@Override
		public UICommand setEnabled(boolean enabled) {
			this.menuItem.setEnabled(enabled);
			return this;
		}

	}

	private static class ToolItemUICommand implements UICommand {

		private final ToolItem toolItem;

		ToolItemUICommand(ToolItem toolItem) {
			this.toolItem = toolItem;
		}

		@Override
		public UICommand setEnabled(boolean enabled) {
			this.toolItem.setEnabled(enabled);
			return this;
		}

	}

	private static class ControlUICommand implements UICommand {

		private final Control control;

		ControlUICommand(Control control) {
			this.control = control;
		}

		@Override
		public UICommand setEnabled(boolean enabled) {
			this.control.setEnabled(enabled);
			return this;
		}

	}

}
