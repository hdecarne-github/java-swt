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

import java.util.function.Supplier;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 * {@linkplain TabFolder} builder.
 */
public final class TabFolderBuilder extends CompositeBuilder<TabFolder> {

	private @Nullable TabItem currentItem = null;

	private TabFolderBuilder(TabFolder tabFolder) {
		super(tabFolder);
	}

	/**
	 * Build a top style {@linkplain TabFolder}.
	 *
	 * @param parent The {@linkplain TabFolder} owner.
	 * @param style The style of {@linkplain TabFolder} to create.
	 * @return The new builder.
	 */
	public static TabFolderBuilder top(Composite parent, int style) {
		return new TabFolderBuilder(new TabFolder(parent, SWT.TOP | style));
	}

	/**
	 * Build a top style {@linkplain TabFolder}.
	 *
	 * @param parent The {@linkplain TabFolder} owner.
	 * @param style The style of {@linkplain TabFolder} to create.
	 * @return The new builder.
	 */
	public static TabFolderBuilder top(Supplier<? extends Composite> parent, int style) {
		return top(parent.get(), style);
	}

	/**
	 * Build a bottom style {@linkplain TabFolder}.
	 *
	 * @param parent The {@linkplain TabFolder} owner.
	 * @param style The style of {@linkplain TabFolder} to create.
	 * @return The new builder.
	 */
	public static TabFolderBuilder bottom(Composite parent, int style) {
		return new TabFolderBuilder(new TabFolder(parent, SWT.BOTTOM | style));
	}

	/**
	 * Build a bottom style {@linkplain TabFolder}.
	 *
	 * @param parent The {@linkplain TabFolder} owner.
	 * @param style The style of {@linkplain TabFolder} to create.
	 * @return The new builder.
	 */
	public static TabFolderBuilder bottom(Supplier<? extends Composite> parent, int style) {
		return bottom(parent.get(), style);
	}

	/**
	 * Get the {@linkplain TabItem} the builder operates on at this state.
	 *
	 * @return The {@linkplain TabItem} the builder operates on at this state.
	 */
	public TabItem currentItem() {
		return checkCurrentItem(this.currentItem);
	}

	/**
	 * Add a {@linkplain TabItem} to the {@linkplain TabFolder}.
	 * <p>
	 * All subsequent calls will operate on the new item.
	 *
	 * @param style The style of the {@linkplain TabItem} to add.
	 * @return The updated builder.
	 */
	public TabFolderBuilder addItem(int style) {
		this.currentItem = new TabItem(get(), style);
		return this;
	}

	/**
	 * Set the current {@linkplain TabItem}'s text.
	 *
	 * @param text The text to set.
	 * @return The updated builder.
	 * @see TabItem#setText(String)
	 */
	public TabFolderBuilder withText(String text) {
		checkCurrentItem(this.currentItem).setText(text);
		return this;
	}

	/**
	 * Set the current {@linkplain TabItem}'s image.
	 *
	 * @param image The {@linkplain Image} to set.
	 * @return The updated builder.
	 * @see TabItem#setImage(Image)
	 */
	public TabFolderBuilder withImage(Image image) {
		checkCurrentItem(this.currentItem).setImage(image);
		return this;
	}

	/**
	 * Set the current {@linkplain TabItem}'s image.
	 *
	 * @param image The {@linkplain Image} to set.
	 * @return The updated builder.
	 * @see TabItem#setImage(Image)
	 */
	public TabFolderBuilder withImage(Supplier<Image> image) {
		return withImage(image.get());
	}

	/**
	 * Set the current {@linkplain TabItem}'s {@linkplain Control}.
	 *
	 * @param control The {@linkplain Control} to set.
	 * @return The updated builder.
	 * @see TabItem#setControl(Control)
	 */
	public TabFolderBuilder withControl(Control control) {
		checkCurrentItem(this.currentItem).setControl(control);
		return this;
	}

	/**
	 * Set the current {@linkplain TabItem}'s {@linkplain Control}.
	 *
	 * @param control The {@linkplain Control} to set.
	 * @return The updated builder.
	 * @see TabItem#setControl(Control)
	 */
	public TabFolderBuilder withControl(Supplier<? extends Control> control) {
		return withControl(control.get());
	}

	private static TabItem checkCurrentItem(@Nullable TabItem currentItem) {
		if (currentItem == null) {
			throw new IllegalStateException("Current tab item is not defined");
		}
		return currentItem;
	}

}
