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
package de.carne.swt.widgets;

import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;

import de.carne.swt.events.EventConsumer;
import de.carne.swt.events.EventReceiver;

/**
 * {@linkplain Control} builder.
 *
 * @param <T> the actual control type.
 */
public class ControlBuilder<T extends Control> extends WidgetBuilder<T> {

	/**
	 * Constructs a new {@linkplain ControlBuilder} instance.
	 *
	 * @param control the control to build.
	 */
	public ControlBuilder(T control) {
		super(control);
	}

	/**
	 * Resizes the {@linkplain Control} to it's preferred size.
	 *
	 * @return the updated builder.
	 * @see Control#pack()
	 */
	public ControlBuilder<T> pack() {
		get().pack(true);
		return this;
	}

	/**
	 * Sets the {@linkplain SelectionEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 */
	public ControlBuilder<T> onSelected(Consumer<SelectionEvent> action) {
		EventConsumer<SelectionEvent> listener = EventConsumer.selected(action);
		T control = get();

		control.addListener(SWT.Selection, listener);
		control.addListener(SWT.DefaultSelection, listener);
		return this;
	}

	/**
	 * Sets the {@linkplain SelectionEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 */
	public ControlBuilder<T> onSelected(Runnable action) {
		EventReceiver listener = EventReceiver.any(action);
		T control = get();

		control.addListener(SWT.Selection, listener);
		control.addListener(SWT.DefaultSelection, listener);
		return this;
	}

}
