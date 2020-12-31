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
package de.carne.swt.widgets;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;

import de.carne.swt.events.EventConsumer;
import de.carne.swt.events.EventReceiver;

/**
 * {@linkplain Widget} builder.
 *
 * @param <T> the actual control type.
 */
public class WidgetBuilder<T extends Widget> implements Supplier<T> {

	private final T widget;

	/**
	 * Constructs a new {@linkplain WidgetBuilder} instance.
	 *
	 * @param widget the control to widget.
	 */
	public WidgetBuilder(T widget) {
		this.widget = widget;
	}

	/**
	 * Gets the {@linkplain Widget} the builder operates on.
	 *
	 * @return the {@linkplain Widget} the builder operates on.
	 */
	@Override
	public final T get() {
		return this.widget;
	}

	/**
	 * Sets a specific event action.
	 *
	 * @param eventType the event type to set the action for.
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Widget#addListener(int, org.eclipse.swt.widgets.Listener)
	 */
	public WidgetBuilder<T> onEvent(int eventType, Consumer<Event> action) {
		this.widget.addListener(eventType, action::accept);
		return this;
	}

	/**
	 * Sets a specific event action.
	 *
	 * @param eventType the event type to set the action for.
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Widget#addListener(int, org.eclipse.swt.widgets.Listener)
	 */
	public WidgetBuilder<T> onEvent(int eventType, Runnable action) {
		EventReceiver listener = EventReceiver.any(action);

		this.widget.addListener(eventType, listener);
		return this;
	}

	/**
	 * Sets the {@linkplain DisposeEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Widget#addDisposeListener(org.eclipse.swt.events.DisposeListener)
	 */
	public WidgetBuilder<T> onDisposed(Consumer<DisposeEvent> action) {
		EventConsumer<DisposeEvent> listener = EventConsumer.disposed(action);

		this.widget.addListener(SWT.Dispose, listener);
		return this;
	}

	/**
	 * Sets the {@linkplain DisposeEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Widget#addDisposeListener(org.eclipse.swt.events.DisposeListener)
	 */
	public WidgetBuilder<T> onDisposed(Runnable action) {
		EventReceiver listener = EventReceiver.any(action);

		this.widget.addListener(SWT.Dispose, listener);
		return this;
	}

}
