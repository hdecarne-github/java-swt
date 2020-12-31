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
package de.carne.swt.widgets.notification;

import java.util.Objects;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import de.carne.swt.events.EventConsumer;
import de.carne.swt.layout.GridLayoutBuilder;
import de.carne.util.Check;
import de.carne.util.Strings;

/**
 * This class is used to display simple and short lived message box style messages (without any buttons) to the user.
 * The message is dismissed as soon as the user continues working.
 */
public class Notification {

	private static final int[] SYSTEM_ICONS = new int[] { SWT.ICON_INFORMATION, SWT.ICON_WARNING, SWT.ICON_ERROR,
			SWT.ICON_CANCEL, SWT.ICON_QUESTION, SWT.ICON_SEARCH, SWT.ICON_WORKING };

	private final Shell parent;
	private final int style;
	private @Nullable Image icon = null;
	private @Nullable String text = null;
	private @Nullable String message = null;

	/**
	 * Constructs a new {@linkplain Notification} instance.
	 *
	 * @param parent the notification's parent.
	 * @param style the notificationl's style.
	 */
	public Notification(Shell parent, int style) {
		this.parent = parent;
		this.style = style;
	}

	/**
	 * Convenience function for creating a {@linkplain Notification} instance with style {@link SWT#ICON_INFORMATION}.
	 *
	 * @param parent the notification's parent.
	 * @return the created {@linkplain Notification} instance.
	 */
	public static Notification information(Shell parent) {
		return new Notification(parent, SWT.ICON_INFORMATION);
	}

	/**
	 * Convenience function for creating a {@linkplain Notification} instance with style {@link SWT#ICON_WARNING}.
	 *
	 * @param parent the notification's parent.
	 * @return the created {@linkplain Notification} instance.
	 */
	public static Notification warning(Shell parent) {
		return new Notification(parent, SWT.ICON_WARNING);
	}

	/**
	 * Convenience function for creating a {@linkplain Notification} instance with style {@link SWT#ICON_ERROR}.
	 *
	 * @param parent the notification's parent.
	 * @return the created {@linkplain Notification} instance.
	 */
	public static Notification error(Shell parent) {
		return new Notification(parent, SWT.ICON_ERROR);
	}

	/**
	 * Sets the icon to display.
	 *
	 * @param notificaitonIcon the icon to display:
	 * @return the updated {@linkplain Notification} instance for chaining.
	 */
	public Notification withIcon(Image notificaitonIcon) {
		this.icon = notificaitonIcon;
		return this;
	}

	/**
	 * Sets the text to display.
	 *
	 * @param notificationText the text to display:
	 * @return the updated {@linkplain Notification} instance for chaining.
	 */
	public Notification withText(String notificationText) {
		this.text = notificationText;
		return this;
	}

	/**
	 * Sets the message to display.
	 *
	 * @param notificationMessage the text to display:
	 * @return the updated {@linkplain Notification} instance for chaining.
	 */
	public Notification withMessage(String notificationMessage) {
		this.message = notificationMessage;
		return this;
	}

	/**
	 * Opens and displays the notification to the user.
	 */
	public void open() {
		Display display = this.parent.getDisplay();
		Color background = display.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
		Color foreground = display.getSystemColor(SWT.COLOR_INFO_FOREGROUND);

		Shell shell = new Shell(this.parent, this.style | SWT.MODELESS);
		Label iconLabel = new Label(shell, SWT.NONE);
		Label textLabel = new Label(shell, SWT.SINGLE);
		Label messageLabel = new Label(shell, SWT.WRAP);

		shell.setBackground(display.getSystemColor(SWT.COLOR_INFO_BACKGROUND));
		shell.setForeground(display.getSystemColor(SWT.COLOR_INFO_FOREGROUND));
		shell.addListener(SWT.Deactivate, EventConsumer.shellEvent(this::onDeactivated));
		shell.addKeyListener(KeyListener.keyPressedAdapter(this::onKeyPressed));
		iconLabel.setBackground(background);
		iconLabel.setForeground(foreground);
		if (this.icon != null) {
			iconLabel.setImage(this.icon);
		} else {
			for (int standardIcon : SYSTEM_ICONS) {
				if ((this.style & standardIcon) == standardIcon) {
					iconLabel.setImage(display.getSystemImage(standardIcon));
					break;
				}
			}
		}
		textLabel.setBackground(background);
		textLabel.setForeground(foreground);
		textLabel.setText(Strings.safe(this.text));
		messageLabel.setBackground(background);
		messageLabel.setForeground(foreground);
		messageLabel.setText(Strings.safe(this.message));

		GridLayoutBuilder.layout(2).apply(shell);
		GridLayoutBuilder.data().apply(iconLabel);
		GridLayoutBuilder.data().grab(true, false).apply(textLabel);
		GridLayoutBuilder.data().span(2, 1).grab(true, true).apply(messageLabel);

		shell.pack();

		Rectangle parentClientArea = this.parent.getClientArea();
		Point location = new Point((parentClientArea.width - shell.getBounds().width) / 2, 0);

		shell.setLocation(this.parent.toDisplay(location));
		shell.setVisible(true);
		shell.forceActive();
	}

	private void onDeactivated(ShellEvent event) {
		Check.isInstanceOf(event.widget, Shell.class).close();
	}

	private void onKeyPressed(KeyEvent event) {
		Objects.requireNonNull(event);

		if ("\u001b\r ".indexOf(event.keyCode) >= 0) {
			Check.isInstanceOf(event.widget, Shell.class).close();
		}
	}

}
