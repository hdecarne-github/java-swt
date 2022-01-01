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
package de.carne.swt.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolItem;

/**
 * {@linkplain UICommand} object combining multiple {@linkplain UICommand} instance into one.
 */
public final class UICommandSet implements UICommand {

	private final List<UICommand> commands = new ArrayList<>();

	@Override
	public UICommandSet setEnabled(boolean enabled) {
		for (UICommand command : this.commands) {
			command.setEnabled(enabled);
		}
		return this;
	}

	/**
	 * Adds a {@linkplain UICommand} to the set.
	 *
	 * @param command the {@linkplain UICommand} to add.
	 * @return the updated {@linkplain UICommandSet}.
	 */
	public UICommandSet add(UICommand command) {
		this.commands.add(command);
		return this;
	}

	/**
	 * Adds a {@linkplain MenuItem} based {@linkplain UICommand} to the set.
	 *
	 * @param menuItem the {@linkplain MenuItem} to add.
	 * @return the updated {@linkplain UICommandSet}.
	 */
	public UICommandSet add(MenuItem menuItem) {
		return add(UICommands.of(menuItem));
	}

	/**
	 * Adds a {@linkplain ToolItem} based {@linkplain UICommand} to the set.
	 *
	 * @param toolItem the {@linkplain ToolItem} to add.
	 * @return the updated {@linkplain UICommandSet}.
	 */
	public UICommandSet add(ToolItem toolItem) {
		return add(UICommands.of(toolItem));
	}

	/**
	 * Adds a {@linkplain Control} based {@linkplain UICommand} to the set.
	 *
	 * @param control the {@linkplain Control} to add.
	 * @return the updated {@linkplain UICommandSet}.
	 */
	public UICommandSet add(Control control) {
		return add(UICommands.of(control));
	}

}
