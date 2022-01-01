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

import org.eclipse.swt.widgets.Composite;

import de.carne.swt.graphics.ResourceException;

/**
 * Base class for SWT based user interfaces.
 *
 * @param <R> the root widget's type.
 */
public abstract class UserInterface<R extends Composite> implements Supplier<R> {

	private final R root;

	/**
	 * Constructs a new {@linkplain UserInterface} instance.
	 *
	 * @param root the interface's root widget.
	 */
	protected UserInterface(R root) {
		this.root = root;
	}

	/**
	 * Gets the interface's root widget.
	 *
	 * @return the interface's root widget.
	 */
	public R root() {
		return this.root;
	}

	@Override
	public R get() {
		return this.root;
	}

	/**
	 * Sets up and opens the interface.
	 *
	 * @throws ResourceException if a required resource is not available.
	 */
	public abstract void open() throws ResourceException;

}
