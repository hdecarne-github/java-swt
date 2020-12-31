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
package de.carne.swt.test.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.swt.layout.GridLayoutBuilder;

/**
 * Test {@linkplain GridLayoutBuilder} class.
 */
class GridLayoutBuilderTest {

	@Test
	void testGridLayoutBuilder() {
		GridLayout layout1 = GridLayoutBuilder.layout().margin(42, 43).spacing(44, 45).get();

		Assertions.assertEquals(1, layout1.numColumns);
		Assertions.assertFalse(layout1.makeColumnsEqualWidth);
		Assertions.assertEquals(42, layout1.marginWidth);
		Assertions.assertEquals(43, layout1.marginHeight);
		Assertions.assertEquals(0, layout1.marginLeft);
		Assertions.assertEquals(0, layout1.marginTop);
		Assertions.assertEquals(0, layout1.marginRight);
		Assertions.assertEquals(0, layout1.marginBottom);
		Assertions.assertEquals(44, layout1.horizontalSpacing);
		Assertions.assertEquals(45, layout1.verticalSpacing);

		GridLayout layout2 = GridLayoutBuilder.layout(42).margin(43, 44, 45, 46).get();

		Assertions.assertEquals(42, layout2.numColumns);
		Assertions.assertFalse(layout2.makeColumnsEqualWidth);
		Assertions.assertEquals(5, layout2.marginWidth);
		Assertions.assertEquals(5, layout2.marginHeight);
		Assertions.assertEquals(43, layout2.marginLeft);
		Assertions.assertEquals(44, layout2.marginTop);
		Assertions.assertEquals(45, layout2.marginRight);
		Assertions.assertEquals(46, layout2.marginBottom);

		GridLayout layout3 = GridLayoutBuilder.layout(42, true).get();

		Assertions.assertEquals(42, layout3.numColumns);
		Assertions.assertTrue(layout3.makeColumnsEqualWidth);
		Assertions.assertEquals(5, layout3.marginWidth);
		Assertions.assertEquals(5, layout3.marginHeight);
		Assertions.assertEquals(0, layout3.marginLeft);
		Assertions.assertEquals(0, layout3.marginTop);
		Assertions.assertEquals(0, layout3.marginRight);
		Assertions.assertEquals(0, layout3.marginBottom);

		GridData data1 = GridLayoutBuilder.data().align(SWT.LEFT, SWT.BOTTOM).minimumSize(42, 43).preferredSize(44, 45)
				.indent(46, 47).span(48, 49).grab(true, true).exclude(true).get();

		Assertions.assertEquals(SWT.LEFT, data1.horizontalAlignment);
		Assertions.assertEquals(SWT.BOTTOM, data1.verticalAlignment);
		Assertions.assertEquals(42, data1.minimumWidth);
		Assertions.assertEquals(43, data1.minimumHeight);
		Assertions.assertEquals(44, data1.widthHint);
		Assertions.assertEquals(45, data1.heightHint);
		Assertions.assertEquals(46, data1.horizontalIndent);
		Assertions.assertEquals(47, data1.verticalIndent);
		Assertions.assertEquals(48, data1.horizontalSpan);
		Assertions.assertEquals(49, data1.verticalSpan);
		Assertions.assertTrue(data1.grabExcessHorizontalSpace);
		Assertions.assertTrue(data1.grabExcessVerticalSpace);
		Assertions.assertTrue(data1.exclude);

		GridData data2 = GridLayoutBuilder.data().get();

		Assertions.assertEquals(SWT.BEGINNING, data2.horizontalAlignment);
		Assertions.assertEquals(GridData.CENTER, data2.verticalAlignment);
		Assertions.assertEquals(0, data2.minimumWidth);
		Assertions.assertEquals(0, data2.minimumHeight);
		Assertions.assertEquals(SWT.DEFAULT, data2.widthHint);
		Assertions.assertEquals(SWT.DEFAULT, data2.heightHint);
		Assertions.assertEquals(0, data2.horizontalIndent);
		Assertions.assertEquals(0, data2.verticalIndent);
		Assertions.assertEquals(1, data2.horizontalSpan);
		Assertions.assertEquals(1, data2.verticalSpan);
		Assertions.assertFalse(data2.grabExcessHorizontalSpace);
		Assertions.assertFalse(data2.grabExcessVerticalSpace);
		Assertions.assertFalse(data2.exclude);
	}

}
