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
import java.util.function.Supplier;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import de.carne.swt.events.EventConsumer;
import de.carne.swt.events.EventReceiver;

/**
 * {@linkplain ToolBar} builder.
 */
public final class ToolBarBuilder extends CompositeBuilder<ToolBar> {

	private @Nullable ToolItem currentItem = null;

	/**
	 * Construct {@linkplain ToolBarBuilder}.
	 *
	 * @param toolBar The {@linkplain ToolBarBuilder} to build up.
	 */
	public ToolBarBuilder(ToolBar toolBar) {
		super(toolBar);
	}

	/**
	 * Build a vertical {@linkplain ToolBar}.
	 *
	 * @param parent The {@linkplain ToolBar} owner.
	 * @param style The style of {@linkplain ToolBar} to create.
	 * @return The new builder.
	 */
	public static ToolBarBuilder vertical(Composite parent, int style) {
		return new ToolBarBuilder(new ToolBar(parent, SWT.VERTICAL | style));
	}

	/**
	 * Build a vertical {@linkplain ToolBar}.
	 *
	 * @param parent The {@linkplain ToolBar} owner.
	 * @param style The style of {@linkplain ToolBar} to create.
	 * @return The new builder.
	 */
	public static ToolBarBuilder vertical(Supplier<? extends Composite> parent, int style) {
		return vertical(parent.get(), style);
	}

	/**
	 * Build a horizontal {@linkplain ToolBar}.
	 *
	 * @param parent The {@linkplain ToolBar} owner.
	 * @param style The style of {@linkplain ToolBar} to create.
	 * @return The new builder.
	 */
	public static ToolBarBuilder horizontal(Composite parent, int style) {
		return new ToolBarBuilder(new ToolBar(parent, SWT.HORIZONTAL | style));
	}

	/**
	 * Build a horizontal {@linkplain ToolBar}.
	 *
	 * @param parent The {@linkplain ToolBar} owner.
	 * @param style The style of {@linkplain ToolBar} to create.
	 * @return The new builder.
	 */
	public static ToolBarBuilder horizontal(Supplier<? extends Composite> parent, int style) {
		return horizontal(parent.get(), style);
	}

	/**
	 * Get the {@linkplain ToolItem} the builder operates on at this state.
	 *
	 * @return The {@linkplain ToolItem} the builder operates on at this state.
	 */
	public ToolItem currentItem() {
		return checkCurrentItem(this.currentItem);
	}

	/**
	 * Add a {@linkplain ToolItem} to the {@linkplain ToolBar}.
	 * <p>
	 * All subsequent calls will operate on the new item.
	 *
	 * @param style The style of the {@linkplain ToolItem} to add.
	 * @return The updated builder.
	 */
	public ToolBarBuilder addItem(int style) {
		this.currentItem = new ToolItem(get(), style);
		return this;
	}

	/**
	 * Set the current {@linkplain ToolItem}'s text.
	 *
	 * @param text The text to set.
	 * @return The updated builder.
	 * @see ToolItem#setText(String)
	 */
	public ToolBarBuilder withText(String text) {
		checkCurrentItem(this.currentItem).setText(text);
		return this;
	}

	/**
	 * Set the current {@linkplain ToolItem}'s tooltip text.
	 *
	 * @param text The text to set.
	 * @return The updated builder.
	 * @see ToolItem#setText(String)
	 */
	public ToolBarBuilder withToolTipText(String text) {
		checkCurrentItem(this.currentItem).setToolTipText(text);
		return this;
	}

	/**
	 * Set the current {@linkplain ToolItem}'s image.
	 *
	 * @param image The {@linkplain Image} to set.
	 * @return The updated builder.
	 * @see ToolItem#setImage(Image)
	 */
	public ToolBarBuilder withImage(Image image) {
		checkCurrentItem(this.currentItem).setImage(image);
		return this;
	}

	/**
	 * Set the current {@linkplain ToolItem}'s image.
	 *
	 * @param image The {@linkplain Image} to set.
	 * @return The updated builder.
	 * @see ToolItem#setImage(Image)
	 */
	public ToolBarBuilder withImage(Supplier<Image> image) {
		return withImage(image.get());
	}

	/**
	 * Set the current {@linkplain ToolItem}'s disabled image.
	 *
	 * @param image The {@linkplain Image} to set.
	 * @return The updated builder.
	 * @see ToolItem#setDisabledImage(Image)
	 */
	public ToolBarBuilder withDisabledImage(Image image) {
		checkCurrentItem(this.currentItem).setDisabledImage(image);
		return this;
	}

	/**
	 * Set the current {@linkplain ToolItem}'s disabled image.
	 *
	 * @param image The {@linkplain Image} to set.
	 * @return The updated builder.
	 * @see ToolItem#setDisabledImage(Image)
	 */
	public ToolBarBuilder withDisabledImage(Supplier<Image> image) {
		return withDisabledImage(image.get());
	}

	@Override
	public ToolBarBuilder onSelected(Consumer<SelectionEvent> action) {
		ToolItem item = checkCurrentItem(this.currentItem);
		EventConsumer<SelectionEvent> listener = EventConsumer.selected(action);

		item.addListener(SWT.Selection, listener);
		item.addListener(SWT.DefaultSelection, listener);
		return this;
	}

	@Override
	public ToolBarBuilder onSelected(Runnable action) {
		ToolItem item = checkCurrentItem(this.currentItem);
		EventReceiver listener = EventReceiver.any(action);

		item.addListener(SWT.Selection, listener);
		item.addListener(SWT.DefaultSelection, listener);
		return this;
	}

	private static ToolItem checkCurrentItem(@Nullable ToolItem currentItem) {
		if (currentItem == null) {
			throw new IllegalStateException("Current tool bar item is not defined");
		}
		return currentItem;
	}

}
