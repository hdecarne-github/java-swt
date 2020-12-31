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
package de.carne.swt.test.graphics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.swt.graphics.CreateResourceException;
import de.carne.swt.graphics.ResourceException;
import de.carne.swt.graphics.UnknownResourceException;

/**
 * Test {@linkplain ResourceException} class and derived classes.
 */
class ResourceExceptionTest {

	@Test
	void testResourceException1() {
		Assertions.assertEquals("ResourceExceptionTest",
				new ResourceException(getClass().getSimpleName()).getMessage());
		Assertions.assertEquals("java.lang.NullPointerException",
				new ResourceException(new NullPointerException()).getMessage());
		Assertions.assertEquals("ResourceExceptionTest",
				new ResourceException(getClass().getSimpleName(), new NullPointerException()).getMessage());
	}

	@Test
	void testUnknownResourceException1() {
		Assertions.assertEquals("ResourceExceptionTest",
				new UnknownResourceException(getClass().getSimpleName()).getMessage());
		Assertions.assertEquals("java.lang.NullPointerException",
				new UnknownResourceException(new NullPointerException()).getMessage());
		Assertions.assertEquals("ResourceExceptionTest",
				new UnknownResourceException(getClass().getSimpleName(), new NullPointerException()).getMessage());
	}

	@Test
	void testCreateResourceException1() {
		Assertions.assertEquals("ResourceExceptionTest",
				new CreateResourceException(getClass().getSimpleName()).getMessage());
		Assertions.assertEquals("java.lang.NullPointerException",
				new CreateResourceException(new NullPointerException()).getMessage());
		Assertions.assertEquals("ResourceExceptionTest",
				new CreateResourceException(getClass().getSimpleName(), new NullPointerException()).getMessage());
	}

}
