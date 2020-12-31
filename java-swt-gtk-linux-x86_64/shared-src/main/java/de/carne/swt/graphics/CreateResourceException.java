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
package de.carne.swt.graphics;

import de.carne.util.Exceptions;

/**
 * Indicates that a SWT {@linkplain org.eclipse.swt.graphics.Resource} cannot be created.
 */
public class CreateResourceException extends ResourceException {

	private static final long serialVersionUID = -5358133313274639864L;

	/**
	 * Construct {@linkplain CreateResourceException}.
	 *
	 * @param message The exception message.
	 */
	public CreateResourceException(String message) {
		super(message);
	}

	/**
	 * Construct {@linkplain CreateResourceException}.
	 *
	 * @param cause The causing exception.
	 */
	public CreateResourceException(Throwable cause) {
		super(Exceptions.toString(cause), cause);
	}

	/**
	 * Construct {@linkplain CreateResourceException}.
	 *
	 * @param message The exception message.
	 * @param cause The causing exception.
	 */
	public CreateResourceException(String message, Throwable cause) {
		super(message, cause);
	}

}
