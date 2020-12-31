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

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import de.carne.swt.events.EventConsumer;
import de.carne.swt.events.EventReceiver;

/**
 * {@linkplain Menu} builder.
 */
public final class MenuBuilder implements Supplier<Menu> {

	private Deque<Menu> menuStack = new LinkedList<>();
	private @Nullable MenuItem currentItem = null;

	/**
	 * Constructs a new {@linkplain MenuBuilder} instance to build up the given menu.
	 *
	 * @param menu the {@linkplain Menu} to build up.
	 */
	public MenuBuilder(Menu menu) {
		this.menuStack.push(menu);
	}

	/**
	 * Constructs a new {@linkplain MenuBuilder} instance to build up the given menu.
	 *
	 * @param menu the {@linkplain Menu} to build up.
	 */
	public MenuBuilder(Supplier<Menu> menu) {
		this(menu.get());
	}

	/**
	 * Builds a {@linkplain Shell}s menu bar.
	 *
	 * @param shell the {@linkplain Shell} owning the menu bar.
	 * @return the new {@linkplain MenuBuilder}.
	 */
	public static MenuBuilder menuBar(Shell shell) {
		Menu menu = new Menu(shell, SWT.BAR);

		shell.setMenuBar(menu);
		return new MenuBuilder(menu);
	}

	/**
	 * Builds a {@linkplain Shell}s menu bar.
	 *
	 * @param shell the {@linkplain Shell} owning the menu bar.
	 * @return the new {@linkplain MenuBuilder}.
	 */
	public static MenuBuilder menuBar(Supplier<? extends Shell> shell) {
		return menuBar(shell.get());
	}

	/**
	 * Builds a pop up menu.
	 *
	 * @param parent the {@linkplain Control} owning the the pop up menu.
	 * @return the new {@linkplain MenuBuilder}.
	 */
	public static MenuBuilder popupMenu(Control parent) {
		return new MenuBuilder(new Menu(parent));
	}

	/**
	 * Builds a pop up menu.
	 *
	 * @param parent the {@linkplain Control} owning the the pop up menu.
	 * @return the new {@linkplain MenuBuilder}.
	 */
	public static MenuBuilder popupMenu(Supplier<? extends Control> parent) {
		return popupMenu(parent.get());
	}

	/**
	 * Gets the {@linkplain Menu} the builder operates on at this state.
	 *
	 * @return the {@linkplain Menu} the builder operates on at this state.
	 */
	@Override
	public Menu get() {
		return Objects.requireNonNull(this.menuStack.peek());
	}

	/**
	 * Gets the {@linkplain MenuItem} the builder operates on at this state.
	 *
	 * @return the {@linkplain MenuItem} the builder operates on at this state.
	 */
	public MenuItem currentItem() {
		return checkCurrentItem(this.currentItem);
	}

	/**
	 * Starts building a new sub menu.
	 * <p>
	 * All subsequent calls will operate on the new sub menu.
	 *
	 * @return the updated {@linkplain MenuBuilder}.
	 */
	public MenuBuilder beginMenu() {
		Menu menu = new Menu(this.menuStack.peek());
		MenuItem item = this.currentItem;

		if (item != null) {
			item.setMenu(menu);
		}
		this.menuStack.push(menu);
		return this;
	}

	/**
	 * Finishes building the current sub menu.
	 * <p>
	 * All subsequent calls will operate on the sub menu's parent.
	 *
	 * @return the updated {@linkplain MenuBuilder}.
	 */
	public MenuBuilder endMenu() {
		this.currentItem = null;
		this.menuStack.pop();

		Menu menu = this.menuStack.peek();

		if (menu != null) {
			int itemCount = menu.getItemCount();

			if (itemCount > 0) {
				this.currentItem = menu.getItem(itemCount - 1);
			}
		}
		return this;
	}

	/**
	 * Removes all {@linkplain MenuItem}s from the current {@linkplain Menu}.
	 *
	 * @return The updated {@linkplain MenuBuilder}.
	 */
	public MenuBuilder removeItems() {
		for (MenuItem item : get().getItems()) {
			item.dispose();
		}
		return this;
	}

	/**
	 * Adds a {@linkplain MenuItem} to the current {@linkplain Menu}.
	 * <p>
	 * All subsequent calls will operate on the new item.
	 *
	 * @param style The style of the {@linkplain MenuItem} to add.
	 * @return The updated {@linkplain MenuBuilder}.
	 */
	public MenuBuilder addItem(int style) {
		this.currentItem = new MenuItem(get(), style);
		return this;
	}

	/**
	 * Makes the current {@linkplain MenuItem} the default for it's parent {@linkplain Menu}.
	 *
	 * @return the updated {@linkplain MenuBuilder}.
	 * @see Menu#setDefaultItem(MenuItem)
	 */
	public MenuBuilder setDefault() {
		get().setDefaultItem(checkCurrentItem(this.currentItem));
		return this;
	}

	/**
	 * Sets the current {@linkplain MenuItem}'s text.
	 *
	 * @param text the text to set.
	 * @return the updated {@linkplain MenuBuilder}.
	 * @see MenuItem#setText(String)
	 */
	public MenuBuilder withText(String text) {
		checkCurrentItem(this.currentItem).setText(text);
		return this;
	}

	/**
	 * Sets the current {@linkplain MenuItem}'s image.
	 *
	 * @param image the image to set.
	 * @return the updated {@linkplain MenuBuilder}.
	 * @see MenuItem#setImage(Image)
	 */
	public MenuBuilder withImage(Image image) {
		checkCurrentItem(this.currentItem).setImage(image);
		return this;
	}

	/**
	 * Sets the current {@linkplain MenuItem}'s image.
	 *
	 * @param image the image to set.
	 * @return the updated {@linkplain MenuBuilder}.
	 * @see MenuItem#setImage(Image)
	 */
	public MenuBuilder withImage(Supplier<Image> image) {
		return withImage(image.get());
	}

	/**
	 * Adds a {@linkplain SelectionEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated {@linkplain MenuBuilder}.
	 * @see MenuItem#addSelectionListener(org.eclipse.swt.events.SelectionListener)
	 */
	public MenuBuilder onSelected(Consumer<SelectionEvent> action) {
		MenuItem item = checkCurrentItem(this.currentItem);
		EventConsumer<SelectionEvent> listener = EventConsumer.selected(action);

		item.addListener(SWT.Selection, listener);
		item.addListener(SWT.DefaultSelection, listener);
		return this;
	}

	/**
	 * Adds a {@linkplain SelectionEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated {@linkplain MenuBuilder}.
	 * @see MenuItem#addSelectionListener(org.eclipse.swt.events.SelectionListener)
	 */
	public MenuBuilder onSelected(Runnable action) {
		MenuItem item = checkCurrentItem(this.currentItem);
		EventReceiver listener = EventReceiver.any(action);

		item.addListener(SWT.Selection, listener);
		item.addListener(SWT.DefaultSelection, listener);
		return this;
	}

	private static MenuItem checkCurrentItem(@Nullable MenuItem currentItem) {
		if (currentItem == null) {
			throw new IllegalStateException("Current menu item is not defined");
		}
		return currentItem;
	}

}
