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
package de.carne.swt.graphics;

import de.carne.util.Exceptions;

/**
 * Indicates that a SWT {@linkplain org.eclipse.swt.graphics.Resource} is not accessible.
 */
public class ResourceException extends Exception {

	private static final long serialVersionUID = 515875293439048677L;

	/**
	 * Construct {@linkplain ResourceException}.
	 *
	 * @param message The exception message.
	 */
	public ResourceException(String message) {
		super(message);
	}

	/**
	 * Construct {@linkplain ResourceException}.
	 *
	 * @param cause The causing exception.
	 */
	public ResourceException(Throwable cause) {
		super(Exceptions.toString(cause), cause);
	}

	/**
	 * Construct {@linkplain ResourceException}.
	 *
	 * @param message The exception message.
	 * @param cause The causing exception.
	 */
	public ResourceException(String message, Throwable cause) {
		super(message, cause);
	}

}
