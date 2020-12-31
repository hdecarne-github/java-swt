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

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;

import de.carne.swt.events.EventConsumer;
import de.carne.swt.events.EventReceiver;

/**
 * {@linkplain CoolBar} builder.
 */
public final class CoolBarBuilder extends CompositeBuilder<CoolBar> {

	private @Nullable CoolItem currentItem = null;

	/**
	 * Construct {@linkplain CoolBarBuilder}.
	 *
	 * @param coolBar The {@linkplain CoolBarBuilder} to build up.
	 */
	public CoolBarBuilder(CoolBar coolBar) {
		super(coolBar);
	}

	/**
	 * Build a vertical {@linkplain CoolBar}.
	 *
	 * @param parent The {@linkplain CoolBar} owner.
	 * @param style The style of {@linkplain CoolBar} to create.
	 * @return The new builder.
	 */
	public static CoolBarBuilder vertical(Composite parent, int style) {
		return new CoolBarBuilder(new CoolBar(parent, SWT.VERTICAL | style));
	}

	/**
	 * Build a vertical {@linkplain CoolBar}.
	 *
	 * @param parent The {@linkplain CoolBar} owner.
	 * @param style The style of {@linkplain CoolBar} to create.
	 * @return The new builder.
	 */
	public static CoolBarBuilder vertical(Supplier<? extends Composite> parent, int style) {
		return vertical(parent.get(), style);
	}

	/**
	 * Build a horizontal {@linkplain CoolBar}.
	 *
	 * @param parent The {@linkplain CoolBar} owner.
	 * @param style The style of {@linkplain CoolBar} to create.
	 * @return The new builder.
	 */
	public static CoolBarBuilder horizontal(Composite parent, int style) {
		return new CoolBarBuilder(new CoolBar(parent, SWT.HORIZONTAL | style));
	}

	/**
	 * Build a horizontal {@linkplain CoolBar}.
	 *
	 * @param parent The {@linkplain CoolBar} owner.
	 * @param style The style of {@linkplain CoolBar} to create.
	 * @return The new builder.
	 */
	public static CoolBarBuilder horizontal(Supplier<? extends Composite> parent, int style) {
		return horizontal(parent.get(), style);
	}

	/**
	 * Get the {@linkplain CoolItem} the builder operates on at this state.
	 *
	 * @return The {@linkplain CoolItem} the builder operates on at this state.
	 */
	public CoolItem currentItem() {
		return checkCurrentItem(this.currentItem);
	}

	/**
	 * Add a {@linkplain CoolItem} to the {@linkplain CoolBar}.
	 * <p>
	 * All subsequent calls will operate on the new item.
	 *
	 * @param style The style of the {@linkplain CoolItem} to add.
	 * @return The updated builder.
	 */
	public CoolBarBuilder addItem(int style) {
		this.currentItem = new CoolItem(get(), style);
		return this;
	}

	/**
	 * Lock or unlock the {@linkplain CoolBar}'s repositioning capability.
	 *
	 * @param locked Whether to lock or unlock the {@linkplain CoolBar}'s repositioning capability.
	 * @return The updated builder.
	 * @see CoolBar#setLocked(boolean)
	 */
	public CoolBarBuilder lock(boolean locked) {
		get().setLocked(locked);
		return this;
	}

	/**
	 * Set the current {@linkplain CoolItem}'s text.
	 *
	 * @param text The text to set.
	 * @return The updated builder.
	 * @see CoolItem#setText(String)
	 */
	public CoolBarBuilder withText(String text) {
		checkCurrentItem(this.currentItem).setText(text);
		return this;
	}

	/**
	 * Set the current {@linkplain CoolItem}'s image.
	 *
	 * @param image The {@linkplain Image} to set.
	 * @return The updated builder.
	 * @see CoolItem#setImage(Image)
	 */
	public CoolBarBuilder withImage(Image image) {
		checkCurrentItem(this.currentItem).setImage(image);
		return this;
	}

	/**
	 * Set the current {@linkplain CoolItem}'s image.
	 *
	 * @param image The {@linkplain Image} to set.
	 * @return The updated builder.
	 * @see CoolItem#setImage(Image)
	 */
	public CoolBarBuilder withImage(Supplier<Image> image) {
		return withImage(image.get());
	}

	/**
	 * Set the current {@linkplain CoolItem}'s {@linkplain Control}.
	 *
	 * @param control The {@linkplain Control} to set.
	 * @return The updated builder.
	 * @see CoolItem#setControl(Control)
	 */
	public CoolBarBuilder withControl(Control control) {
		CoolItem item = checkCurrentItem(this.currentItem);

		item.setControl(control);

		Point controlSize = control.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		Point itemSize = item.computeSize(controlSize.x, controlSize.y);

		item.setSize(itemSize);
		item.setMinimumSize(itemSize);
		item.setPreferredSize(itemSize);
		return this;
	}

	/**
	 * Set the current {@linkplain CoolItem}'s {@linkplain Control}.
	 *
	 * @param control The {@linkplain Control} to set.
	 * @return The updated builder.
	 * @see CoolItem#setControl(Control)
	 */
	public CoolBarBuilder withControl(Supplier<? extends Control> control) {
		return withControl(control.get());
	}

	@Override
	public CoolBarBuilder onSelected(Consumer<SelectionEvent> action) {
		CoolItem item = checkCurrentItem(this.currentItem);
		EventConsumer<SelectionEvent> listener = EventConsumer.selected(action);

		item.addListener(SWT.Selection, listener);
		item.addListener(SWT.DefaultSelection, listener);
		return this;
	}

	@Override
	public CoolBarBuilder onSelected(Runnable action) {
		CoolItem item = checkCurrentItem(this.currentItem);
		EventReceiver listener = EventReceiver.any(action);

		item.addListener(SWT.Selection, listener);
		item.addListener(SWT.DefaultSelection, listener);
		return this;
	}

	private static CoolItem checkCurrentItem(@Nullable CoolItem currentItem) {
		if (currentItem == null) {
			throw new IllegalStateException("Current cool bar item is not defined");
		}
		return currentItem;
	}

}
