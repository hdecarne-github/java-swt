/*
 * Copyright (c) 2007-2018 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.swt.util;

import de.carne.boot.check.Nullable;

/**
 * Functional interface used to signal a property value has been changed.
 *
 * @param <T> the actual property type.
 */
@FunctionalInterface
public interface PropertyChangedListener<T> {

	/**
	 * Called every time the property value has been changed.
	 *
	 * @param newValue the new property value.
	 * @param oldValue the old property value.
	 */
	void changed(@Nullable T newValue, @Nullable T oldValue);

}
