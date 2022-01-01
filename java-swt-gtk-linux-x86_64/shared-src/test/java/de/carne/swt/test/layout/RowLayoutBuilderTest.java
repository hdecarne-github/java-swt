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
package de.carne.swt.test.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.swt.layout.RowLayoutBuilder;

/**
 * Test {@linkplain RowLayoutBuilder} class.
 */
class RowLayoutBuilderTest {

	@Test
	void testRowLayoutBuilder() {
		RowLayout layout1 = RowLayoutBuilder.layout().margin(42, 43).spacing(44).wrap(false).pack(false).fill(true)
				.center(true).justify(true).get();

		Assertions.assertEquals(SWT.HORIZONTAL, layout1.type);
		Assertions.assertEquals(42, layout1.marginWidth);
		Assertions.assertEquals(43, layout1.marginHeight);
		Assertions.assertEquals(3, layout1.marginLeft);
		Assertions.assertEquals(3, layout1.marginTop);
		Assertions.assertEquals(3, layout1.marginRight);
		Assertions.assertEquals(3, layout1.marginBottom);
		Assertions.assertEquals(44, layout1.spacing);
		Assertions.assertFalse(layout1.wrap);
		Assertions.assertFalse(layout1.pack);
		Assertions.assertTrue(layout1.fill);
		Assertions.assertTrue(layout1.center);
		Assertions.assertTrue(layout1.justify);

		RowLayout layout2 = RowLayoutBuilder.layout(SWT.VERTICAL).margin(42, 43, 44, 45).get();

		Assertions.assertEquals(SWT.VERTICAL, layout2.type);
		Assertions.assertEquals(0, layout2.marginWidth);
		Assertions.assertEquals(0, layout2.marginHeight);
		Assertions.assertEquals(42, layout2.marginLeft);
		Assertions.assertEquals(43, layout2.marginTop);
		Assertions.assertEquals(44, layout2.marginRight);
		Assertions.assertEquals(45, layout2.marginBottom);
		Assertions.assertEquals(3, layout2.spacing);
		Assertions.assertTrue(layout2.wrap);
		Assertions.assertTrue(layout2.pack);
		Assertions.assertFalse(layout2.fill);
		Assertions.assertFalse(layout2.center);
		Assertions.assertFalse(layout2.justify);

		RowData data1 = RowLayoutBuilder.data().size(42, 43).exclude(true).get();

		Assertions.assertEquals(42, data1.width);
		Assertions.assertEquals(43, data1.height);
		Assertions.assertTrue(data1.exclude);

		RowData data2 = RowLayoutBuilder.data().get();

		Assertions.assertEquals(SWT.DEFAULT, data2.width);
		Assertions.assertEquals(SWT.DEFAULT, data2.height);
		Assertions.assertFalse(data2.exclude);
	}

}
