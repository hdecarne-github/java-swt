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

import java.lang.reflect.Constructor;
import java.util.function.Function;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import de.carne.util.Exceptions;

/**
 * {@linkplain Composite} builder.
 *
 * @param <T> The actual composite type.
 */
public class CompositeBuilder<T extends Composite> extends ControlBuilder<T> {

	/**
	 * Construct {@linkplain CompositeBuilder}.
	 *
	 * @param composite The {@linkplain Composite} widget to build up.
	 */
	public CompositeBuilder(T composite) {
		super(composite);
	}

	/**
	 * Add a child control to the {@linkplain Composite}.
	 *
	 * @param <C> The actual control type (either direct or wrapped in a builder).
	 * @param createChild The function to use to create the new control.
	 * @return The added child control.
	 */
	public <C> C addChild(Function<T, C> createChild) {
		return createChild.apply(get());
	}

	/**
	 * Add a child of the submitted {@linkplain Control} derived type.
	 * <p>
	 * The control is created by invoking the constructor with signature {@linkplain Control#Control(Composite, int)}
	 * via reflection.
	 *
	 * @param <C> The actual control type.
	 * @param controlClass The actual control type to add.
	 * @param style The control style.
	 * @return The added child control.
	 */
	public <C extends Control> ControlBuilder<C> addControlChild(Class<C> controlClass, int style) {
		return addChild(parent -> {
			try {
				Constructor<C> constructor = controlClass.getConstructor(Composite.class, Integer.TYPE);

				return new ControlBuilder<>(constructor.newInstance(parent, style));
			} catch (ReflectiveOperationException e) {
				throw Exceptions.toRuntime(e);
			}
		});
	}

	/**
	 * Add a child of the submitted {@linkplain Composite} derived type.
	 * <p>
	 * The composite is created by invoking the constructor with signature
	 * {@linkplain Composite#Composite(Composite, int)} via reflection.
	 *
	 * @param <C> The actual composite type.
	 * @param compositeClass The actual composite type to add.
	 * @param style The composite style.
	 * @return The added composite control.
	 */
	public <C extends Composite> CompositeBuilder<C> addCompositeChild(Class<C> compositeClass, int style) {
		return addChild(parent -> {
			try {
				Constructor<C> constructor = compositeClass.getConstructor(Composite.class, Integer.TYPE);

				return new CompositeBuilder<>(constructor.newInstance(parent, style));
			} catch (ReflectiveOperationException e) {
				throw Exceptions.toRuntime(e);
			}
		});
	}

	/**
	 * Add a child of type {@linkplain Composite}.
	 *
	 * @param style The {@linkplain Composite} style.
	 * @return The added child control.
	 */
	@SuppressWarnings("null")
	public CompositeBuilder<Composite> addCompositeChild(int style) {
		return addChild(parent -> new CompositeBuilder<>(new Composite(parent, style)));
	}

	/**
	 * Add a child of type {@linkplain Group}.
	 *
	 * @param style The {@linkplain Group} style.
	 * @return The added child control.
	 */
	@SuppressWarnings("null")
	public CompositeBuilder<Group> addGroupChild(int style) {
		return addChild(parent -> new CompositeBuilder<>(new Group(parent, style)));
	}

	/**
	 * Add a child of type {@linkplain Button}.
	 *
	 * @param style The {@linkplain Button} style.
	 * @return The added child control.
	 */
	public ButtonBuilder addButtonChild(int style) {
		return addChild(parent -> new ButtonBuilder(new Button(parent, style)));
	}

	/**
	 * Add a child of type {@linkplain Label}.
	 *
	 * @param style The {@linkplain Label} style.
	 * @return The added child control.
	 */
	public LabelBuilder addLabelChild(int style) {
		return addChild(parent -> new LabelBuilder(new Label(parent, style)));
	}

}
