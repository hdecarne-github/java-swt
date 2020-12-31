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
package de.carne.swt.platform.cocoa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;

import de.carne.swt.events.EventConsumer;
import de.carne.swt.events.EventReceiver;
import de.carne.swt.platform.PlatformIntegration;

/**
 * Cocoa platform integration.
 */
public class CocoaPlatformIntegration extends PlatformIntegration {

	@Override
	protected boolean internalIsButtonOrderLeftToRight() {
		return false;
	}

	@Override
	protected boolean internalIsCocoa() {
		return true;
	}

	@Override
	protected void internalCocoaAddAboutSelectionAction(Display display, Consumer<SelectionEvent> action) {
		addSystemMenuItemSelectionListener(display, SWT.ID_ABOUT, EventConsumer.selected(action));
	}

	@Override
	protected void internalCocoaAddAboutSelectionAction(Display display, Runnable action) {
		addSystemMenuItemSelectionListener(display, SWT.ID_ABOUT, EventReceiver.any(action));
	}

	@Override
	protected void internalCocoaAddPreferencesSelectionAction(Display display, Consumer<SelectionEvent> action) {
		addSystemMenuItemSelectionListener(display, SWT.ID_PREFERENCES, EventConsumer.selected(action));
	}

	@Override
	protected void internalCocoaAddPreferencesSelectionAction(Display display, Runnable action) {
		addSystemMenuItemSelectionListener(display, SWT.ID_PREFERENCES, EventReceiver.any(action));
	}

	@Override
	protected void internalCocoaAddQuitSelectionAction(Display display, Consumer<SelectionEvent> action) {
		addSystemMenuItemSelectionListener(display, SWT.ID_QUIT, EventConsumer.selected(action));
	}

	@Override
	protected void internalCocoaAddQuitSelectionAction(Display display, Runnable action) {
		addSystemMenuItemSelectionListener(display, SWT.ID_QUIT, EventReceiver.any(action));
	}

	private void addSystemMenuItemSelectionListener(Display display, int itemId, Listener listener) {
		for (MenuItem menuItem : display.getSystemMenu().getItems()) {
			if (menuItem.getID() == itemId) {
				Listener wrappedListener = event -> invokeSystemMenuListener(display, listener, event);

				menuItem.addListener(SWT.Selection, wrappedListener);
				menuItem.addListener(SWT.DefaultSelection, wrappedListener);
				break;
			}
		}
	}

	private static void invokeSystemMenuListener(Display display, Listener listener, Event event) {
		Collection<MenuItem> disabledMenuItems = new ArrayList<>();

		try {
			for (MenuItem menuItem : display.getSystemMenu().getItems()) {
				int menuItemId = menuItem.getID();

				if ((menuItemId == SWT.ID_ABOUT || menuItemId == SWT.ID_PREFERENCES || menuItemId == SWT.ID_QUIT)
						&& menuItem.getEnabled()) {
					disabledMenuItems.add(menuItem);
					menuItem.setEnabled(false);
				}
			}
			listener.handleEvent(event);
		} finally {
			for (MenuItem disabledMenuItem : disabledMenuItems) {
				disabledMenuItem.setEnabled(true);
			}
		}
	}

}
