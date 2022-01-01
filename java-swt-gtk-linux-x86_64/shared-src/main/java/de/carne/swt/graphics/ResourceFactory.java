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

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Resource;

/**
 * Functional interface used for {@linkplain Resource} creation.
 *
 * @param <D> the actual type describing the resource.
 * @param <R> the actual resource type.
 */
@FunctionalInterface
public interface ResourceFactory<D, R extends Resource> {

	/**
	 * Create a new {@linkplain Resource} instance.
	 *
	 * @param device the {@linkplain Device} to create the {@linkplain Resource} for.
	 * @param descriptor the descriptor defining the {@linkplain Resource} to create.
	 * @return the created {@linkplain Resource}.
	 */
	R create(Device device, D descriptor);

}
