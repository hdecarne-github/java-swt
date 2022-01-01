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
package de.carne.swt.test.platform;

import org.eclipse.swt.SWT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.swt.platform.PlatformIntegration;

/**
 * Test {@linkplain PlatformIntegration} class.
 */
class PlatformIntegrationTest {

	@Test
	void testPlatformIntegration() {
		String platform = SWT.getPlatform();

		Assertions.assertTrue(PlatformIntegration.toolkitName().startsWith(platform));
		if ("cocoa".equals(platform)) {
			Assertions.assertTrue(PlatformIntegration.isCocoa());
			Assertions.assertFalse(PlatformIntegration.isGtk());
			Assertions.assertFalse(PlatformIntegration.isWin32());
		} else if ("gtk".equals(platform)) {
			Assertions.assertFalse(PlatformIntegration.isCocoa());
			Assertions.assertTrue(PlatformIntegration.isGtk());
			Assertions.assertFalse(PlatformIntegration.isWin32());
		} else if ("win32".equals(platform)) {
			Assertions.assertFalse(PlatformIntegration.isCocoa());
			Assertions.assertFalse(PlatformIntegration.isGtk());
			Assertions.assertTrue(PlatformIntegration.isWin32());
		} else {
			Assertions.fail("Unsupported platform: " + platform);
		}
	}

}
