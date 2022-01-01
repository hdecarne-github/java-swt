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

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.graphics.Device;

/**
 * Utility class providing {@linkplain Device} related functions.
 */
public final class Devices {

	private Devices() {
		// Prevent instantiation
	}

	/**
	 * Dispose a (possibly {@code null}) {@linkplain Device}.
	 *
	 * @param <T> The actual {@linkplain Device} type.
	 * @param device The {@linkplain Device} to dispose (may be {@code null}).
	 * @return Always {@code null}.
	 */
	@Nullable
	public static <T extends Device> T safeDispose(@Nullable T device) {
		if (device != null) {
			device.dispose();
		}
		return null;
	}

}
