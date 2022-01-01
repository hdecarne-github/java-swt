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
package de.carne.swt.widgets.runtimeinfo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * Custom control displaying the current VM's runtime info.
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class RuntimeInfo extends Canvas implements PaintListener {

	private static final int BORDER_WIDTH = 1;
	private static final int MARGIN_WIDTH = 2;

	private int timer = 0;
	private String text = "";
	private int usage = 0;

	/**
	 * Constructs a new {@linkplain RuntimeInfo} instance.
	 *
	 * @param parent the control's parent.
	 * @param style the control's style (no styles supported yet).
	 */
	public RuntimeInfo(Composite parent, int style) {
		super(parent, style);
		updateRuntimeInfo();
		addPaintListener(this);
	}

	/**
	 * Sets the update timer frequency (in milliseconds).
	 * <p>
	 * A timer frequency of 0 or less disables the update timer.
	 *
	 * @param timer the timer frequency to set.
	 */
	public void setTimer(int timer) {
		int oldTimer = this.timer;

		this.timer = timer;
		if (oldTimer <= 0 && this.timer > 0) {
			getDisplay().timerExec(this.timer, this::onTimer);
		}
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		GC gc = new GC(this);
		Point size;

		try {
			size = gc.textExtent("0000B/0000B", SWT.NONE);
			size.x += 2 * MARGIN_WIDTH;
			size.y += 2 * MARGIN_WIDTH;
		} finally {
			gc.dispose();
		}
		return super.computeSize(size.x, size.y, changed);
	}

	@Override
	public void paintControl(PaintEvent evt) {
		int style = getStyle();
		int borderWidth = ((style & SWT.BORDER) == SWT.BORDER ? BORDER_WIDTH : 0);
		Rectangle clientArea = getClientArea();
		Rectangle selectedArea = new Rectangle(clientArea.x + borderWidth, clientArea.y + borderWidth,
				(clientArea.width * this.usage) / 100, clientArea.height);
		Rectangle unselectedArea = new Rectangle(clientArea.x + borderWidth + selectedArea.width,
				clientArea.y + borderWidth, clientArea.width - selectedArea.width, clientArea.height);
		Display display = getDisplay();
		Color selectedBackground = display.getSystemColor(SWT.COLOR_LIST_SELECTION);
		Color selectedForeground = display.getSystemColor(SWT.COLOR_LIST_SELECTION_TEXT);
		Color background = getBackground();
		Color foreground = getForeground();
		GC gc = evt.gc;

		gc.setBackground(selectedBackground);
		gc.setAlpha(selectedBackground.getAlpha());
		gc.fillRectangle(selectedArea);
		gc.setForeground(selectedForeground);
		gc.setAlpha(selectedForeground.getAlpha());
		gc.setClipping(selectedArea);
		gc.drawString(this.text, MARGIN_WIDTH, MARGIN_WIDTH, true);
		gc.setBackground(background);
		gc.setAlpha(background.getAlpha());
		gc.fillRectangle(unselectedArea);
		gc.setClipping(unselectedArea);
		gc.setForeground(foreground);
		gc.setAlpha(foreground.getAlpha());
		gc.drawString(this.text, MARGIN_WIDTH, MARGIN_WIDTH, true);
	}

	private void onTimer() {
		if (!isDisposed() && this.timer > 0) {
			updateRuntimeInfo();
			if (isVisible()) {
				redraw();
			}
			getDisplay().timerExec(this.timer, this::onTimer);
		}
	}

	private static final char[] MEM_UNITS = { 'B', 'K', 'M', 'G', 'T', 'P', 'E', 'Z', 'Y' };

	private void updateRuntimeInfo() {
		Runtime runtime = Runtime.getRuntime();

		long total = runtime.totalMemory();
		long used = Math.max(0, total - runtime.freeMemory());

		this.usage = (int) ((used * 100) / total);

		int totalMemUnitIndex = 0;
		int usedMemUnitIndex = 0;

		while (total > 1024 && totalMemUnitIndex < MEM_UNITS.length) {
			total >>= 10;
			totalMemUnitIndex++;
			if (used > 1024) {
				used >>= 10;
				usedMemUnitIndex++;
			}
		}
		this.text = new StringBuilder().append(used).append(MEM_UNITS[usedMemUnitIndex]).append('/').append(total)
				.append(MEM_UNITS[totalMemUnitIndex]).toString();

		StringBuilder toolTipText = new StringBuilder();

		toolTipText.append("Java VM: ").append(System.getProperty("java.vm.name")).append(System.lineSeparator());
		toolTipText.append("VM version: ").append(System.getProperty("java.runtime.version"))
				.append(System.lineSeparator());
		toolTipText.append("Processors: ").append(runtime.availableProcessors()).append(System.lineSeparator());
		toolTipText.append("Heap usage: ").append(this.text);
		setToolTipText(toolTipText.toString());
	}

}
