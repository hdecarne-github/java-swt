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
package de.carne.swt.widgets;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Shell;

import de.carne.swt.events.EventConsumer;
import de.carne.swt.events.EventReceiver;
import de.carne.swt.platform.PlatformIntegration;

/**
 * {@linkplain Shell} builder.
 */
public class ShellBuilder extends CompositeBuilder<Shell> {

	/**
	 * Constructs a new {@linkplain ShellBuilder} instance.
	 *
	 * @param shell the {@linkplain Shell} widget to build up.
	 */
	public ShellBuilder(Shell shell) {
		super(shell);
	}

	/**
	 * Sets the {@linkplain Shell}'s text.
	 *
	 * @param text the text to set.
	 * @return the updated builder.
	 * @see Shell#setText(String)
	 */
	public ShellBuilder withText(String text) {
		get().setText(text);
		return this;
	}

	/**
	 * Sets the {@linkplain Shell}'s images.
	 *
	 * @param images the {@linkplain Image}s to set.
	 * @return the updated builder.
	 * @see Shell#setImages(Image[])
	 */
	public ShellBuilder withImages(Image[] images) {
		get().setImages(images);
		return this;
	}

	/**
	 * Sets the {@linkplain Shell}'s images.
	 *
	 * @param images the {@linkplain Image}s to set.
	 * @return the updated builder.
	 * @see Shell#setImages(Image[])
	 */
	public ShellBuilder withImages(Supplier<Image[]> images) {
		return withImages(images.get());
	}

	/**
	 * Sets the {@linkplain Shell}'s images to a suitable default by traversing the widget hierarchy.
	 *
	 * @return the updated builder.
	 */
	public ShellBuilder withDefaultImages() {
		Shell shell = get();
		Composite parent = shell.getParent();

		while (parent != null) {
			if (parent instanceof Decorations) {
				shell.setImages(((Decorations) parent).getImages());
			}
			parent = parent.getParent();
		}
		return this;
	}

	/**
	 * Sets the activated {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellActivated(Consumer<ShellEvent> action) {
		EventConsumer<ShellEvent> listener = EventConsumer.shellEvent(action);

		get().addListener(SWT.Activate, listener);
		return this;
	}

	/**
	 * Sets the activated {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellActivated(Runnable action) {
		EventReceiver listener = EventReceiver.any(action);

		get().addListener(SWT.Activate, listener);
		return this;
	}

	/**
	 * Sets the deactivated {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellDeactivated(Consumer<ShellEvent> action) {
		EventConsumer<ShellEvent> listener = EventConsumer.shellEvent(action);

		get().addListener(SWT.Deactivate, listener);
		return this;
	}

	/**
	 * Sets the deactivated {@linkplain ShellEvent} action.
	 *
	 * @param action The action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellDeactivated(Runnable action) {
		EventReceiver listener = EventReceiver.any(action);

		get().addListener(SWT.Deactivate, listener);
		return this;
	}

	/**
	 * Sets the iconified {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellIconified(Consumer<ShellEvent> action) {
		EventConsumer<ShellEvent> listener = EventConsumer.shellEvent(action);

		get().addListener(SWT.Iconify, listener);
		return this;
	}

	/**
	 * Sets the iconified {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellIconified(Runnable action) {
		EventReceiver listener = EventReceiver.any(action);

		get().addListener(SWT.Iconify, listener);
		return this;
	}

	/**
	 * Sets the deiconified {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellDeiconified(Consumer<ShellEvent> action) {
		EventConsumer<ShellEvent> listener = EventConsumer.shellEvent(action);

		get().addListener(SWT.Deiconify, listener);
		return this;
	}

	/**
	 * Sets the deiconified {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellDeiconified(Runnable action) {
		EventReceiver listener = EventReceiver.any(action);

		get().addListener(SWT.Deiconify, listener);
		return this;
	}

	/**
	 * Sets the closed {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellClosed(Consumer<ShellEvent> action) {
		EventConsumer<ShellEvent> listener = EventConsumer.shellEvent(action);

		get().addListener(SWT.Close, listener);
		return this;
	}

	/**
	 * Sets the closed {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellClosed(Runnable action) {
		EventReceiver listener = EventReceiver.any(action);

		get().addListener(SWT.Close, listener);
		return this;
	}

	/**
	 * Set the {@linkplain Shell}'s position according to the given horizontal (LEFT|CENTER|RIGHT) and vertical
	 * (TOP|CENTER|BOTTOM) alignment.
	 * <p>
	 * If DEFAULT is submitted as the alignment value a platform specific default is selected.
	 *
	 * @param horizontal the horizontal alignment to apply.
	 * @param vertical the vertical alignment to apply.
	 * @return the updated builder.
	 */
	public ShellBuilder position(int horizontal, int vertical) {
		Shell shell = get();
		Composite parent = shell.getParent();

		if (parent != null) {
			Rectangle shellBounds = shell.getBounds();
			Rectangle parentBounds = parent.getBounds();
			int shellX;
			int shellY;

			switch (getEffectiveHorizontal(horizontal)) {
			case SWT.LEFT:
				shellX = parentBounds.x;
				break;
			case SWT.RIGHT:
				shellX = parentBounds.x + parentBounds.width - shellBounds.width;
				break;
			default:
				shellX = parentBounds.x + (parentBounds.width - shellBounds.width) / 2;
			}
			switch (getEffectiveVertical(vertical)) {
			case SWT.TOP:
				shellY = parentBounds.y;
				break;
			case SWT.BOTTOM:
				shellY = parentBounds.y + parentBounds.height - shellBounds.height;
				break;
			default:
				shellY = parentBounds.y + (parentBounds.height - shellBounds.height) / 2;
			}
			shell.setLocation(shellX, shellY);
		}
		return this;
	}

	private int getEffectiveHorizontal(int horizontal) {
		int effectiveHorizontal;

		if (horizontal == SWT.DEFAULT) {
			effectiveHorizontal = SWT.CENTER;
		} else {
			effectiveHorizontal = horizontal;
		}
		return effectiveHorizontal;
	}

	private int getEffectiveVertical(int vertical) {
		int effectiveVertical;

		if (vertical == SWT.DEFAULT) {
			effectiveVertical = (PlatformIntegration.isCocoa() ? SWT.TOP : SWT.CENTER);
		} else {
			effectiveVertical = vertical;
		}
		return effectiveVertical;
	}

}
