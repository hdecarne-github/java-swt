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
package de.carne.swt.graphics;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;

import de.carne.util.Exceptions;

final class ScaledImageResourceProvider implements ImageDataProvider {

	private final URL url;

	private ScaledImageResourceProvider(URL url) {
		this.url = url;
	}

	public static Image createImageFromUrl(Device device, URL imageUrl) {
		return new Image(device, new ScaledImageResourceProvider(imageUrl));
	}

	@Override
	@Nullable
	public ImageData getImageData(int zoom) {
		ImageData imageData = null;

		switch (zoom) {
		case 100:
			imageData = loadStandardImageData();
			break;
		case 200:
			imageData = loadScaledImageData("@2x");
			break;
		default:
			// do nothing
			break;
		}
		return imageData;
	}

	private ImageData loadStandardImageData() {
		ImageData imageData;

		try (InputStream imageDataStream = this.url.openStream()) {
			imageData = new ImageData(imageDataStream);
		} catch (IOException e) {
			SWT.error(SWT.ERROR_IO, e);
			// Never reached; just to make the compiler happy
			throw Exceptions.toRuntime(e);
		}
		return imageData;
	}

	@Nullable
	private ImageData loadScaledImageData(String scaleSuffix) {
		ImageData imageData = null;
		String urlString = this.url.toExternalForm();
		int extensionIndex = urlString.lastIndexOf('.');

		if (extensionIndex > 0) {
			URL scaledImageDataUrl;

			try {
				String scaledImageDataUrlString = urlString.substring(0, extensionIndex) + scaleSuffix
						+ urlString.substring(extensionIndex);

				scaledImageDataUrl = new URL(scaledImageDataUrlString);
			} catch (IOException e) {
				SWT.error(SWT.ERROR_IO, e);
				// Never reached; just to make the compiler happy
				throw Exceptions.toRuntime(e);
			}
			try (InputStream imageDataStream = scaledImageDataUrl.openStream()) {
				imageData = new ImageData(imageDataStream);
			} catch (IOException e) {
				Exceptions.ignore(e);
			}
		}
		return imageData;
	}

}
