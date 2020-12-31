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
package de.carne.swt.widgets.logview;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.carne.swt.graphics.ResourceException;
import de.carne.swt.graphics.ResourceTracker;
import de.carne.swt.layout.GridLayoutBuilder;
import de.carne.swt.layout.RowLayoutBuilder;
import de.carne.swt.widgets.ButtonBuilder;
import de.carne.swt.widgets.CompositeBuilder;
import de.carne.swt.widgets.FileDialogBuilder;
import de.carne.swt.widgets.ShellBuilder;
import de.carne.swt.widgets.ShellUserInterface;
import de.carne.util.Late;
import de.carne.util.Strings;
import de.carne.util.logging.Log;
import de.carne.util.logging.LogBuffer;
import de.carne.util.logging.LogLineFormatter;

class LogViewUI extends ShellUserInterface {

	private static final Log LOG = new Log();

	private static final int MAX_ENTRIES = 1000;

	private static final int MAX_PREFERRED_WIDTH = 600;
	private static final int MAX_PREFERRED_HEIGHT = 240;

	private static final LogLineFormatter LOG_LINE_FORMATTER = new LogLineFormatter();

	private final LogHandler logHandler = new LogHandler();
	private final ResourceTracker resources;
	private final Logger logger;
	private final NavigableMap<Integer, URL> logoMap;
	private final Late<Table> logTableHolder = new Late<>();

	public LogViewUI(Shell root, Logger logger, NavigableMap<Integer, URL> logoMap) {
		super(root);
		this.resources = ResourceTracker.forDevice(root.getDisplay()).forShell(root);
		this.logger = logger;
		this.logoMap = logoMap;
	}

	@Override
	public void open() throws ResourceException {
		ShellBuilder rootBuilder = buildRoot();

		rootBuilder.pack();
		rootBuilder.position(SWT.CENTER, SWT.CENTER);

		Shell root = rootBuilder.get();

		root.setMinimumSize(root.getSize());
		root.open();
	}

	private ShellBuilder buildRoot() {
		// Setup all widgets
		ShellBuilder rootBuilder = new ShellBuilder(root());
		CompositeBuilder<Table> logTable = rootBuilder.addCompositeChild(Table.class,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		CompositeBuilder<Composite> buttons = rootBuilder.addCompositeChild(SWT.NONE);

		rootBuilder.withText(LogViewI18N.i18nTitle()).withDefaultImages();
		rootBuilder.onDisposed(() -> LogBuffer.removeHandler(this.logger, this.logHandler));
		buildLogTable(logTable);
		buildButtons(buttons);

		// Populate log table
		String loggerName = this.logger.getName();

		LOG.notice("Viewing log: ''{0}''...", (Strings.notEmpty(loggerName) ? loggerName : "<root>"));

		LogBuffer logBuffer = LogBuffer.get(this.logger);

		if (logBuffer == null) {
			throw new IllegalStateException("LogBuffer not configured");
		}
		logBuffer.addHandler(this.logHandler, true);

		// Layout UI
		for (TableColumn logTableColumn : this.logTableHolder.get().getColumns()) {
			logTableColumn.pack();
		}

		Point logTableSize = logTable.get().computeSize(SWT.DEFAULT, SWT.DEFAULT);

		logTableSize.x = Math.min(logTableSize.x, MAX_PREFERRED_WIDTH);
		logTableSize.y = Math.min(logTableSize.y, MAX_PREFERRED_HEIGHT);

		GridLayoutBuilder.layout(1).apply(rootBuilder);
		GridLayoutBuilder.data(GridData.FILL_BOTH).preferredSize(logTableSize.x, logTableSize.y).apply(logTable);
		GridLayoutBuilder.data(GridData.FILL_HORIZONTAL).align(SWT.END, SWT.CENTER).apply(buttons);
		return rootBuilder;
	}

	private void buildLogTable(CompositeBuilder<Table> logTable) {
		Table logTableControl = this.logTableHolder.set(logTable);

		logTableControl.setHeaderVisible(true);
		logTableControl.setLinesVisible(true);

		TableColumn levelColumn = new TableColumn(logTableControl, SWT.NONE);
		TableColumn timeColumn = new TableColumn(logTableControl, SWT.NONE);
		TableColumn messageColumn = new TableColumn(logTableControl, SWT.NONE);

		levelColumn.setText(LogViewI18N.i18nLabelLevel());
		timeColumn.setText(LogViewI18N.i18nLabelTime());
		messageColumn.setText(LogViewI18N.i18nLabelMessage());
	}

	private void buildButtons(CompositeBuilder<Composite> buttons) {
		ButtonBuilder clearButton = buttons.addButtonChild(SWT.PUSH);
		ButtonBuilder exportButton = buttons.addButtonChild(SWT.PUSH);
		ButtonBuilder closeButton = buttons.addButtonChild(SWT.PUSH);

		clearButton.withText(LogViewI18N.i18nButtonClear());
		clearButton.onSelected(this::onClear);
		exportButton.withText(LogViewI18N.i18nButtonExport());
		exportButton.onSelected(this::onExport);
		closeButton.withText(LogViewI18N.i18nButtonClose());
		closeButton.onSelected(() -> root().close());
		root().setDefaultButton(closeButton.get());
		RowLayoutBuilder.layout().fill(true).apply(buttons);
		RowLayoutBuilder.data().apply(clearButton);
		RowLayoutBuilder.data().apply(exportButton);
		RowLayoutBuilder.data().apply(closeButton);
	}

	private void onClear() {
		LogBuffer.flush(this.logger);
		this.logTableHolder.get().removeAll();

		LOG.notice("Log cleared");
	}

	private void onExport() {
		try {
			FileDialog fileDialog = FileDialogBuilder.save(get()).withFilter("*.log")
					.withFileName(Display.getAppName() + ".log").get();
			String fileName = fileDialog.open();

			if (fileName != null) {
				LogBuffer.exportTo(this.logger, new File(fileName), false);
			}
		} catch (Exception e) {
			LOG.error(e, "Failed to export log");
		}
	}

	void publish(LogRecord record) {
		Display display = get().getDisplay();

		if (Thread.currentThread().equals(display.getThread())) {
			Table logTable = this.logTableHolder.get();
			int itemCount = logTable.getItemCount();

			while (itemCount > MAX_ENTRIES) {
				logTable.remove(0);
				itemCount--;
			}

			TableItem logTableItem = new TableItem(logTable, SWT.NONE);

			if (this.logoMap.isEmpty()) {
				logTableItem.setText(0, record.getLevel().getLocalizedName());
			} else {
				Map.Entry<Integer, URL> logoEntry = this.logoMap.floorEntry(record.getLevel().intValue());

				if (logoEntry != null) {
					logTableItem.setImage(this.resources.getImage(logoEntry.getValue()));
				}
			}
			logTableItem.setText(1, LOG_LINE_FORMATTER.formatMillis(record));
			logTableItem.setText(2, LOG_LINE_FORMATTER.formatMessage(record));

			int selection = logTable.getSelectionIndex();

			if (selection == -1 || (selection + 1) == itemCount) {
				logTable.select(itemCount);
				logTable.setTopIndex(itemCount);
			}
			logTableItem.setData(record);
		} else {
			display.syncExec(() -> publish(record));
		}
	}

	private class LogHandler extends Handler {

		private final AtomicBoolean locked = new AtomicBoolean();

		LogHandler() {
			// Nothing to do here
		}

		@Override
		public void publish(LogRecord record) {
			if (this.locked.compareAndSet(false, true)) {
				try {
					LogViewUI.this.publish(record);
				} finally {
					this.locked.set(false);
				}
			}
		}

		@Override
		public void flush() {
			// Nothing to here
		}

		@Override
		public void close() {
			// Nothing to here
		}

	}

}
