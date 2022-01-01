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
package de.carne.swt.widgets.aboutinfo;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.carne.swt.graphics.ResourceException;
import de.carne.swt.graphics.ResourceTracker;
import de.carne.swt.layout.GridLayoutBuilder;
import de.carne.swt.layout.RowLayoutBuilder;
import de.carne.swt.widgets.ButtonBuilder;
import de.carne.swt.widgets.CompositeBuilder;
import de.carne.swt.widgets.ControlBuilder;
import de.carne.swt.widgets.LabelBuilder;
import de.carne.swt.widgets.ShellBuilder;
import de.carne.swt.widgets.ShellUserInterface;
import de.carne.util.ManifestInfos;
import de.carne.util.Strings;

class AboutInfoUI extends ShellUserInterface {

	private static final int MAX_WIDTH = 600;
	private static final int MAX_HEIGHT = 240;

	private final ResourceTracker resources;
	private final ManifestInfos moduleInfos;
	private final @Nullable URL logoUrl;
	private final List<URL> copyrightUrls;

	public AboutInfoUI(Shell root, ManifestInfos moduleInfos, @Nullable URL logoUrl, List<URL> copyrightUrls) {
		super(root);
		this.resources = ResourceTracker.forDevice(root.getDisplay()).forShell(root);
		this.moduleInfos = moduleInfos;
		this.logoUrl = logoUrl;
		this.copyrightUrls = copyrightUrls;
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

	private ShellBuilder buildRoot() throws ResourceException {
		ShellBuilder rootBuilder = new ShellBuilder(root());
		LabelBuilder logo = rootBuilder.addLabelChild(SWT.NONE);
		LabelBuilder title = rootBuilder.addLabelChild(SWT.NONE);
		LabelBuilder version = rootBuilder.addLabelChild(SWT.NONE);
		LabelBuilder build = rootBuilder.addLabelChild(SWT.NONE);
		LabelBuilder separator1 = rootBuilder.addLabelChild(SWT.HORIZONTAL | SWT.SEPARATOR);
		CompositeBuilder<TabFolder> infos = rootBuilder.addCompositeChild(TabFolder.class, SWT.BOTTOM);
		LabelBuilder separator2 = rootBuilder.addLabelChild(SWT.HORIZONTAL | SWT.SEPARATOR);
		CompositeBuilder<Composite> buttons = rootBuilder.addCompositeChild(SWT.NONE);

		rootBuilder.withText(AboutInfoI18N.i18nTitle(this.moduleInfos.name())).withDefaultImages();

		URL checkedLogoUrl = this.logoUrl;

		if (checkedLogoUrl != null) {
			logo.withImage(this.resources.getImage(checkedLogoUrl));
		}
		title.withText(AboutInfoI18N.i18nLabelTitle(this.moduleInfos.name()));
		version.withText(AboutInfoI18N.i18nLabelVersion(this.moduleInfos.version()));
		build.withText(AboutInfoI18N.i18nLabelBuild(this.moduleInfos.build()));
		buildCopyrightTabs(infos);
		buildSystemPropertiesTab(infos);
		buildButtons(buttons);

		TabFolder infosTab = infos.get();
		Point infoSize = infosTab.getItem(0).getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		Point infosSize = infosTab.computeSize(Math.min(infoSize.x, MAX_WIDTH), Math.min(infoSize.y, MAX_HEIGHT));

		GridLayoutBuilder.layout(2).apply(rootBuilder);
		GridLayoutBuilder.data().span(1, 3).apply(logo);
		GridLayoutBuilder.data(GridData.FILL_HORIZONTAL).apply(title);
		GridLayoutBuilder.data(GridData.FILL_HORIZONTAL).apply(version);
		GridLayoutBuilder.data(GridData.FILL_HORIZONTAL).apply(build);
		GridLayoutBuilder.data(GridData.FILL_HORIZONTAL).span(2, 1).apply(separator1);
		GridLayoutBuilder.data(GridData.FILL_BOTH).span(2, 1).preferredSize(infosSize.x, infosSize.y).apply(infos);
		GridLayoutBuilder.data(GridData.FILL_HORIZONTAL).span(2, 1).apply(separator2);
		GridLayoutBuilder.data(GridData.FILL_HORIZONTAL).align(SWT.END, SWT.CENTER).span(2, 1).apply(buttons);

		infos.get().pack();
		return rootBuilder;
	}

	private void buildCopyrightTabs(CompositeBuilder<TabFolder> infos) throws ResourceException {
		for (URL copyrightUrl : this.copyrightUrls) {
			try (BufferedReader copyrightReader = new BufferedReader(
					new InputStreamReader(copyrightUrl.openStream()))) {
				String copyrightTitle = copyrightReader.readLine();

				if (copyrightTitle == null) {
					throw new EOFException();
				}

				StringBuilder copyrightInfo = new StringBuilder();
				String copyrightInfoLine;

				while ((copyrightInfoLine = copyrightReader.readLine()) != null) {
					if (copyrightInfo.length() > 0) {
						copyrightInfo.append(System.lineSeparator());
					}
					copyrightInfo.append(copyrightInfoLine);
				}

				TabItem copyrightItem = new TabItem(infos.get(), SWT.NONE);

				copyrightItem.setText(copyrightTitle);

				ControlBuilder<Link> copyrightLink = infos.addControlChild(Link.class, SWT.NONE);

				copyrightItem.setControl(copyrightLink.get());
				copyrightLink.get().setText(copyrightInfo.toString());
				copyrightLink.onSelected(this::onLinkSelected);
			} catch (IOException e) {
				throw new ResourceException("Failed to load copyright info: " + copyrightUrl, e);
			}
		}
	}

	private void buildSystemPropertiesTab(CompositeBuilder<TabFolder> infos) {
		TabItem systemPropertiesItem = new TabItem(infos.get(), SWT.NONE);

		systemPropertiesItem.setText(AboutInfoI18N.i18nTabSystemProperties());

		Table keyValueTable = infos.addControlChild(Table.class, SWT.H_SCROLL | SWT.V_SCROLL).get();

		systemPropertiesItem.setControl(keyValueTable);
		keyValueTable.setHeaderVisible(true);
		keyValueTable.setLinesVisible(true);

		TableColumn keyColumn = new TableColumn(keyValueTable, SWT.NONE);
		TableColumn valueColumn = new TableColumn(keyValueTable, SWT.NONE);

		keyColumn.setText(AboutInfoI18N.i18nLabelSystemPropertyKey());
		valueColumn.setText(AboutInfoI18N.i18nLabelSystemPropertyValue());

		Properties systemProperties = System.getProperties();
		List<String> keys = systemProperties.keySet().stream().map(Object::toString).collect(Collectors.toList());

		Collections.sort(keys);
		for (String key : keys) {
			String value = systemProperties.getProperty(key);
			TableItem keyValueItem = new TableItem(keyValueTable, SWT.NONE);

			keyValueItem.setText(0, Strings.encode(key));
			keyValueItem.setText(1, Strings.encode(Strings.valueOf(value)));
		}
		keyColumn.pack();
		valueColumn.pack();
	}

	private void buildButtons(CompositeBuilder<Composite> buttons) {
		ButtonBuilder closeButton = buttons.addButtonChild(SWT.PUSH);

		closeButton.withText(AboutInfoI18N.i18nButtonClose());
		closeButton.onSelected(() -> root().close());
		root().setDefaultButton(closeButton.get());
		RowLayoutBuilder.layout().fill(true).apply(buttons);
		RowLayoutBuilder.data().apply(closeButton);
	}

	private void onLinkSelected(SelectionEvent event) {
		Program.launch(event.text);
	}

}
