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
package de.carne.swt.platform;

import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;

import de.carne.util.Exceptions;
import de.carne.util.Lazy;
import de.carne.util.Platform;

/**
 * This class provides a generic access to platform specific OS integration features (like the application menu on
 * macOS).
 */
public abstract class PlatformIntegration {

	private static final Lazy<PlatformIntegration> INSTANCE_HOLDER = new Lazy<>(PlatformIntegration::getInstance);

	private static PlatformIntegration getInstance() {
		PlatformIntegration instance;

		try {
			String platform = SWT.getPlatform();
			String platformName = PlatformIntegration.class.getPackage().getName() + "." + platform + "."
					+ platform.substring(0, 1).toUpperCase() + platform.substring(1)
					+ PlatformIntegration.class.getSimpleName();

			instance = Class.forName(platformName).asSubclass(PlatformIntegration.class).getConstructor().newInstance();
		} catch (ReflectiveOperationException e) {
			throw Exceptions.toRuntime(e);
		}
		return instance;
	}

	/**
	 * Gets the toolkit name of the currently running platform.
	 *
	 * @return the toolkit name of the currently running platform.
	 */
	public static String toolkitName() {
		StringBuilder toolkitName = new StringBuilder();

		toolkitName.append(SWT.getPlatform());
		if (Platform.IS_LINUX) {
			toolkitName.append("-linux");
		} else if (Platform.IS_MACOS) {
			toolkitName.append("-macosx");
		} else if (Platform.IS_WINDOWS) {
			toolkitName.append("-win32");
		} else {
			// Do not fail in case of unknown toolkit
			toolkitName.append("unknown-unknown");
		}
		if ("x86".equals(Platform.SYSTEM_OS_ARCH) || "x86_32".equals(Platform.SYSTEM_OS_ARCH)) {
			toolkitName.append("-x86");
		} else if ("x86_64".equals(Platform.SYSTEM_OS_ARCH) || "amd64".equals(Platform.SYSTEM_OS_ARCH)) {
			toolkitName.append("-x86_64");
		} else {
			// Do not fail in case of unknown toolkit
			toolkitName.append("unknown");
		}
		return toolkitName.toString();
	}

	/**
	 * Determines whether the preferred button order is left-to-right for the current platform.
	 *
	 * @return {@code true} if the preferred button order is left-to-right.
	 */
	public static boolean isButtonOrderLeftToRight() {
		return INSTANCE_HOLDER.get().internalIsButtonOrderLeftToRight();
	}

	/**
	 * Determines whether this code runs on the Cocoa platform.
	 *
	 * @return {@code true} if this code runs on the Cocoa platform.
	 */
	public static boolean isCocoa() {
		return INSTANCE_HOLDER.get().internalIsCocoa();
	}

	/**
	 * Adds a action to be invoked in case the Cocoa application menu's about entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	public static void cocoaAddAboutSelectionAction(Display display, Consumer<SelectionEvent> action) {
		INSTANCE_HOLDER.get().internalCocoaAddAboutSelectionAction(display, action);
	}

	/**
	 * Adds a action to be invoked in case the Cocoa application menu's about entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	public static void cocoaAddAboutSelectionAction(Display display, Runnable action) {
		INSTANCE_HOLDER.get().internalCocoaAddAboutSelectionAction(display, action);
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's preferences entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	public static void cocoaAddPreferencesSelectionAction(Display display, Consumer<SelectionEvent> action) {
		INSTANCE_HOLDER.get().internalCocoaAddPreferencesSelectionAction(display, action);
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's preferences entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	public static void cocoaAddPreferencesSelectionAction(Display display, Runnable action) {
		INSTANCE_HOLDER.get().internalCocoaAddPreferencesSelectionAction(display, action);
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's quit entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	public static void cocoaAddQuitSelectionAction(Display display, Consumer<SelectionEvent> action) {
		INSTANCE_HOLDER.get().internalCocoaAddQuitSelectionAction(display, action);
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's quit entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	public static void cocoaAddQuitSelectionAction(Display display, Runnable action) {
		INSTANCE_HOLDER.get().internalCocoaAddQuitSelectionAction(display, action);
	}

	/**
	 * Determines whether this code runs on the Win32 platform.
	 *
	 * @return {@code true} if this code runs on the Win32 platform.
	 */
	public static boolean isWin32() {
		return INSTANCE_HOLDER.get().internalIsWin32();
	}

	/**
	 * Determines whether this code runs on the GTK platform.
	 *
	 * @return {@code true} if this code runs on the GTK platform.
	 */
	public static boolean isGtk() {
		return INSTANCE_HOLDER.get().internalIsGtk();
	}

	/**
	 * Constructs a new {@linkplain PlatformIntegration} instance.
	 */
	protected PlatformIntegration() {
		// Nothing to do here
	}

	/**
	 * Determines whether the preferred button order is left-to-right for the current platform.
	 *
	 * @return {@code true} if the preferred button order is left-to-right.
	 */
	protected abstract boolean internalIsButtonOrderLeftToRight();

	/**
	 * Determines whether this code runs on the Cocoa platform.
	 *
	 * @return {@code true} if this code runs on the Cocoa platform.
	 */
	protected boolean internalIsCocoa() {
		return false;
	}

	/**
	 * Adds a action to be invoked in case the Cocoa application menu's about entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	protected void internalCocoaAddAboutSelectionAction(Display display, Consumer<SelectionEvent> action) {
		// default is to do nothing
	}

	/**
	 * Adds a action to be invoked in case the Cocoa application menu's about entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	protected void internalCocoaAddAboutSelectionAction(Display display, Runnable action) {
		// default is to do nothing
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's preferences entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	protected void internalCocoaAddPreferencesSelectionAction(Display display, Consumer<SelectionEvent> action) {
		// default is to do nothing
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's preferences entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	protected void internalCocoaAddPreferencesSelectionAction(Display display, Runnable action) {
		// default is to do nothing
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's quit entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	protected void internalCocoaAddQuitSelectionAction(Display display, Consumer<SelectionEvent> action) {
		// default is to do nothing
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's quit entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	protected void internalCocoaAddQuitSelectionAction(Display display, Runnable action) {
		// default is to do nothing
	}

	/**
	 * Determines whether this code runs on the Win32 platform.
	 *
	 * @return {@code true} if this code runs on the Win32 platform.
	 */
	protected boolean internalIsWin32() {
		return false;
	}

	/**
	 * Determines whether this code runs on the GTK platform.
	 *
	 * @return {@code true} if this code runs on the GTK platform.
	 */
	protected boolean internalIsGtk() {
		return false;
	}

}
