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
package de.carne.swt.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.swt.util.Property;
import de.carne.swt.util.PropertyChangedListener;

/**
 * Test {@linkplain Property} class.
 */
class PropertyTest {

	private static final String STRING_PROPERTY_VALUE_A = "A";
	private static final String STRING_PROPERTY_VALUE_B = "B";

	@Test
	void testProperty() {
		Counter counter1 = new Counter();
		Counter counter2 = new Counter();
		Property<String> stringProperty = new Property<>(STRING_PROPERTY_VALUE_A);

		stringProperty.addChangedListener(counter1);
		stringProperty.set(STRING_PROPERTY_VALUE_B);

		Assertions.assertEquals(STRING_PROPERTY_VALUE_B, stringProperty.get());
		Assertions.assertEquals(1, counter1.value());
		Assertions.assertEquals(0, counter2.value());

		stringProperty.addChangedListener(counter2);
		stringProperty.set(STRING_PROPERTY_VALUE_B);

		Assertions.assertEquals(STRING_PROPERTY_VALUE_B, stringProperty.get());
		Assertions.assertEquals(1, counter1.value());
		Assertions.assertEquals(0, counter2.value());

		stringProperty.set(STRING_PROPERTY_VALUE_A);

		Assertions.assertEquals(STRING_PROPERTY_VALUE_A, stringProperty.get());
		Assertions.assertEquals(2, counter1.value());
		Assertions.assertEquals(1, counter2.value());

		stringProperty.removeChangedListener(counter1);
		stringProperty.set(STRING_PROPERTY_VALUE_A, true);

		Assertions.assertEquals(STRING_PROPERTY_VALUE_A, stringProperty.get());
		Assertions.assertEquals(2, counter1.value());
		Assertions.assertEquals(2, counter2.value());
	}

	private class Counter implements PropertyChangedListener<String> {

		private int value = 0;

		public int value() {
			return this.value;
		}

		@Override
		public void changed(String newValue, String oldValue) {
			this.value++;
		}

	}

}
