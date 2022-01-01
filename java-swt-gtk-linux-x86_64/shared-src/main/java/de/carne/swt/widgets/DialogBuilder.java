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

import java.util.function.Supplier;

import org.eclipse.swt.widgets.Dialog;

/**
 * {@linkplain Dialog} builder.
 *
 * @param <T> the actual dialog type.
 */
public class DialogBuilder<T extends Dialog> implements Supplier<T> {

	private final T dialog;

	/**
	 * Constructs a new {@linkplain DialogBuilder} instance.
	 *
	 * @param dialog the dialog to build.
	 */
	public DialogBuilder(T dialog) {
		this.dialog = dialog;
	}

	/**
	 * Gets the {@linkplain Dialog} the builder operates on.
	 *
	 * @return the {@linkplain Dialog} the builder operates on.
	 */
	@Override
	public T get() {
		return this.dialog;
	}

	/**
	 * Sets the {@linkplain Dialog}'s text.
	 *
	 * @param text the text to set.
	 * @return the updated builder.
	 * @see Dialog#setText(String)
	 */
	public DialogBuilder<T> withText(String text) {
		this.dialog.setText(text);
		return this;
	}

}
