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
package de.carne.swt.events;

import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * Event listener for mapping a typed SWT event to a consuming action.
 *
 * @param <T> The event type to listen for.
 */
public class EventConsumer<T extends TypedEvent> implements Listener {

	private final Function<Event, T> eventFactory;
	private final Consumer<T> consumer;

	private EventConsumer(Function<Event, T> eventFactory, Consumer<T> consumer) {
		this.eventFactory = eventFactory;
		this.consumer = consumer;
	}

	/**
	 * {@linkplain DisposeEvent} listener.
	 *
	 * @param consumer The consuming action.
	 * @return The event listener.
	 */
	public static EventConsumer<DisposeEvent> disposed(Consumer<DisposeEvent> consumer) {
		return new EventConsumer<>(DisposeEvent::new, consumer);
	}

	/**
	 * {@linkplain SelectionEvent} listener.
	 *
	 * @param consumer The consuming action.
	 * @return The event listener.
	 */
	public static EventConsumer<SelectionEvent> selected(Consumer<SelectionEvent> consumer) {
		return new EventConsumer<>(SelectionEvent::new, consumer);
	}

	/**
	 * {@linkplain ShellEvent} listener.
	 *
	 * @param consumer The consuming action.
	 * @return The event listener.
	 */
	public static EventConsumer<ShellEvent> shellEvent(Consumer<ShellEvent> consumer) {
		return new EventConsumer<>(ShellEvent::new, consumer);
	}

	@Override
	public void handleEvent(Event event) {
		this.consumer.accept(this.eventFactory.apply(event));
	}

}
