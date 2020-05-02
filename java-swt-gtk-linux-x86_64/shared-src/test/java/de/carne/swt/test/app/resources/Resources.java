/*
 * Copyright (c) 2007-2020 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.swt.test.app.resources;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;

import de.carne.swt.graphics.ResourceException;
import de.carne.swt.graphics.ResourceTracker;

/**
 * Test application's resources.
 */
@SuppressWarnings("javadoc")
public final class Resources {

	private Resources() {
		// prevent instantiation
	}

	public static final String ABOUT_TEXT = "about.txt";

	public static final String APP_ICON16 = "app_icon16.png";
	public static final String APP_ICON32 = "app_icon32.png";
	public static final String APP_ICON48 = "app_icon48.png";
	public static final String APP_ICON128 = "app_icon128.png";

	public static final Iterable<String> APP_ICON = Arrays.asList(APP_ICON16, APP_ICON32, APP_ICON48, APP_ICON128);

	public static Image getImage(Device device, String name) throws ResourceException {
		URL imageUrl = Resources.class.getResource(name);

		if (imageUrl == null) {
			throw new ResourceException("Cannot find image: " + name);
		}
		return ResourceTracker.forDevice(device).getImage(imageUrl);
	}

	public static Image[] getImages(Device device, Iterable<String> names) throws ResourceException {
		ResourceTracker resourceTracker = ResourceTracker.forDevice(device);
		List<Image> images = new ArrayList<>();

		for (String name : names) {
			URL imageUrl = Resources.class.getResource(name);

			if (imageUrl == null) {
				throw new ResourceException("Cannot find image: " + name);
			}
			images.add(resourceTracker.getImage(imageUrl));
		}
		return images.toArray(new @Nullable Image[images.size()]);
	}

}
