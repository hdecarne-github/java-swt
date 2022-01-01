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
package de.carne.swt.graphics;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Shell;

/**
 * Helper class used to track and manage dynamically created {@linkplain Resource} instances.
 */
public abstract class ResourceTracker {

	private static final Map<Device, ResourceTracker> DEVICE_TRACKER = new HashMap<>();

	private final Map<Object, Color> colorCache = new HashMap<>();
	private final Map<Object, Font> fontCache = new HashMap<>();
	private final Map<Object, Image> imageCache = new HashMap<>();

	/**
	 * Gets the {@linkplain Device} this instance is assigned to.
	 *
	 * @return the {@linkplain Device} this instance is assigned to.
	 */
	public abstract Device getDevice();

	/**
	 * Removes the given {@linkplain Device} instance from the list of tracked {@linkplain Device}s.
	 *
	 * @param device the {@linkplain Device} to remove.
	 */
	protected static void removeDevice(Device device) {
		DEVICE_TRACKER.remove(device);
	}

	/**
	 * Disposes any {@linkplain Resource} tracked by this instance.
	 */
	public void disposeAll() {
		this.colorCache.forEach((descriptor, resource) -> resource.dispose());
		this.colorCache.clear();
		this.fontCache.forEach((descriptor, resource) -> resource.dispose());
		this.fontCache.clear();
		this.imageCache.forEach((descriptor, resource) -> resource.dispose());
		this.imageCache.clear();
	}

	/**
	 * Gets a {@linkplain Color} resource.
	 *
	 * @param rgb the {@linkplain RGB} object describing the color to get.
	 * @return the requested {@linkplain Color} resource.
	 */
	public Color getColor(RGB rgb) {
		return getColor(rgb, this::createColorFromRgb);
	}

	/**
	 * Gets a {@linkplain Color} resource for a given descriptor.
	 *
	 * @param <D> the actual descriptor type.
	 * @param descriptor the descriptor describing the color to get.
	 * @param factory the factory to use for {@linkplain Color} resource creation.
	 * @return the requested {@linkplain Color} resource.
	 */
	public <@NonNull D> Color getColor(D descriptor, ResourceFactory<D, Color> factory) {
		Color cachedColor = getCachedColor(descriptor);

		if (cachedColor == null) {
			cachedColor = factory.create(getDevice(), descriptor);
			this.colorCache.put(descriptor, cachedColor);
		}
		return cachedColor;
	}

	/**
	 * Gets a cached {@linkplain Color} resource.
	 *
	 * @param <D> the actual descriptor type.
	 * @param descriptor the descriptor describing the color to get.
	 * @return the cached {@linkplain Color} resource or {@code null} if not yet cached.
	 */
	@Nullable
	protected <@NonNull D> Color getCachedColor(D descriptor) {
		return this.colorCache.get(descriptor);
	}

	private Color createColorFromRgb(Device device, RGB descriptor) {
		return new Color(device, descriptor);
	}

	/**
	 * Gets a {@linkplain Font} resource.
	 *
	 * @param fontData the {@linkplain FontData} object describing the font to get.
	 * @return the requested {@linkplain Font} resource.
	 */
	public Font getFont(FontData fontData) {
		return getFont(fontData, this::createFontFromFontData);
	}

	/**
	 * Gets a {@linkplain Font} resource for a given descriptor.
	 *
	 * @param <D> the actual descriptor type.
	 * @param descriptor the descriptor describing the font to get.
	 * @param factory the factory to use for {@linkplain Font} resource creation.
	 * @return the requested {@linkplain Font} resource.
	 */
	public <@NonNull D> Font getFont(D descriptor, ResourceFactory<D, Font> factory) {
		Font cachedFont = getCachedFont(descriptor);

		if (cachedFont == null) {
			cachedFont = factory.create(getDevice(), descriptor);
			this.fontCache.put(descriptor, cachedFont);
		}
		return cachedFont;
	}

	/**
	 * Gets a cached {@linkplain Font} resource.
	 *
	 * @param <D> the actual descriptor type.
	 * @param descriptor the descriptor describing the font to get.
	 * @return the cached {@linkplain Font} resource or {@code null} if not yet cached.
	 */
	@Nullable
	protected <@NonNull D> Font getCachedFont(D descriptor) {
		return this.fontCache.get(descriptor);
	}

	private Font createFontFromFontData(Device device, FontData fontData) {
		return new Font(device, fontData);
	}

	/**
	 * Gets a {@linkplain Image} resource.
	 *
	 * @param clazz the {@linkplain Class} to use for resource access.
	 * @param name the name of the {@linkplain Image} resource to get.
	 * @return the requested {@linkplain Image} resource.
	 */
	public Image getImage(Class<?> clazz, String name) {
		URL imageUrl = clazz.getResource(name);

		if (imageUrl == null) {
			throw new IllegalArgumentException("Unknown image resource: " + clazz.getName() + ":" + name);
		}
		return getImage(imageUrl);
	}

	/**
	 * Gets multiple {@linkplain Image} resources.
	 *
	 * @param clazz the {@linkplain Class} to use for resource access.
	 * @param names the names of the {@linkplain Image} resources to get.
	 * @return the requested {@linkplain Image} resources.
	 */
	public Image[] getImages(Class<?> clazz, Iterable<String> names) {
		List<Image> images = new ArrayList<>();

		for (String name : names) {
			URL imageUrl = clazz.getResource(name);

			if (imageUrl == null) {
				throw new IllegalArgumentException("Unknown image resource: " + clazz.getName() + ":" + name);
			}
			images.add(getImage(imageUrl));
		}
		return images.toArray(new @Nullable Image[images.size()]);
	}

	/**
	 * Gets a {@linkplain Image} resource.
	 *
	 * @param imageUrl the {@linkplain URL} object describing the image to get.
	 * @return the requested {@linkplain Image} resource.
	 */
	public Image getImage(URL imageUrl) {
		return getImage(imageUrl, ScaledImageResourceProvider::createImageFromUrl);
	}

	/**
	 * Gets a {@linkplain Image} resource for a given descriptor.
	 *
	 * @param <D> the actual descriptor type.
	 * @param descriptor the descriptor describing the image to get.
	 * @param factory the factory to use for {@linkplain Image} resource creation.
	 * @return the requested {@linkplain Image} resource.
	 */
	public <@NonNull D> Image getImage(D descriptor, ResourceFactory<D, Image> factory) {
		Image cachedImage = getCachedImage(descriptor);

		if (cachedImage == null) {
			cachedImage = factory.create(getDevice(), descriptor);
			this.imageCache.put(descriptor, cachedImage);
		}
		return cachedImage;
	}

	/**
	 * Gets a cached {@linkplain Image} resource.
	 *
	 * @param <D> the actual descriptor type.
	 * @param descriptor the descriptor describing the color to get.
	 * @return the cached {@linkplain Image} resource or {@code null} if not yet cached.
	 */
	@Nullable
	protected <@NonNull D> Image getCachedImage(D descriptor) {
		return this.imageCache.get(descriptor);
	}

	/**
	 * Gets the {@linkplain ResourceTracker} instance for the given {@linkplain Device}.
	 *
	 * @param device the {@linkplain Device} to get the {@linkplain ResourceTracker} instance for.
	 * @return the requested {@linkplain ResourceTracker} instance.
	 */
	public static ResourceTracker forDevice(Device device) {
		return DEVICE_TRACKER.computeIfAbsent(device, DeviceResourceTracker::new);
	}

	/**
	 * Creates a {@linkplain ResourceTracker} instance for the given {@linkplain Shell}.
	 *
	 * @param shell the {@linkplain Shell} to get the {@linkplain ResourceTracker} instance for.
	 * @return the requested {@linkplain ResourceTracker} instance.
	 */
	public ResourceTracker forShell(Shell shell) {
		return new ShellResourceTracker(shell);
	}

	private static class DeviceResourceTracker extends ResourceTracker {

		private final Device device;

		DeviceResourceTracker(Device device) {
			this.device = device;
		}

		@Override
		public Device getDevice() {
			return this.device;
		}

		@Override
		public void disposeAll() {
			removeDevice(this.device);
			super.disposeAll();
		}

	}

	private class ShellResourceTracker extends ResourceTracker {

		ShellResourceTracker(Shell shell) {
			shell.addDisposeListener(event -> disposeAll());
		}

		@Override
		public Device getDevice() {
			return ResourceTracker.this.getDevice();
		}

		@Override
		@Nullable
		protected <@NonNull D> Color getCachedColor(D descriptor) {
			Color cachedColor = super.getCachedColor(descriptor);

			return (cachedColor != null ? cachedColor : ResourceTracker.this.getCachedColor(descriptor));
		}

		@Override
		@Nullable
		protected <@NonNull D> Font getCachedFont(D descriptor) {
			Font cachedFont = super.getCachedFont(descriptor);

			return (cachedFont != null ? cachedFont : ResourceTracker.this.getCachedFont(descriptor));
		}

		@Override
		@Nullable
		protected <@NonNull D> Image getCachedImage(D descriptor) {
			Image cachedImage = super.getCachedImage(descriptor);

			return (cachedImage != null ? cachedImage : ResourceTracker.this.getCachedImage(descriptor));
		}

	}

}
