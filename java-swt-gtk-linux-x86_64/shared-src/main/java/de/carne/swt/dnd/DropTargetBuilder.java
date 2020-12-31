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
package de.carne.swt.dnd;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Control;

import de.carne.util.Check;

/**
 * {@linkplain DropTarget} builder.
 */
public class DropTargetBuilder implements Supplier<DropTarget> {

	private final DropTarget dropTarget;

	/**
	 * Constructs a new {@linkplain DropTargetBuilder} instance.
	 *
	 * @param dropTarget the {@linkplain DropTarget} to build.
	 */
	public DropTargetBuilder(DropTarget dropTarget) {
		this.dropTarget = dropTarget;
	}

	/**
	 * Convenience function for creating a {@linkplain DropTargetBuilder} for file transfers.
	 *
	 * @param control the {@linkplain Control} targeted by the drop operation.
	 * @param style the allowed drop operations.
	 * @return the created {@linkplain DropTargetBuilder}
	 * @see DropTarget#DropTarget(Control, int)
	 */
	public static DropTargetBuilder fileTransfer(Control control, int style) {
		return new DropTargetBuilder(new DropTarget(control, style)).withTransfers(FileTransfer.getInstance());
	}

	/**
	 * Sets the accepted {@linkplain Transfer} types.
	 *
	 * @param transferAgents the accepted transfer types.
	 * @return the updated {@linkplain DropTargetBuilder} for chaining.
	 * @see DropTarget#setTransfer(Transfer...)
	 */
	public DropTargetBuilder withTransfers(Transfer... transferAgents) {
		this.dropTarget.setTransfer(transferAgents);
		return this;
	}

	/**
	 * Registers the given {@linkplain DropTargetListener} instance for drop event notification.
	 *
	 * @param listener the {@linkplain DropTargetListener} to add.
	 * @return the updated {@linkplain DropTargetBuilder} for chaining.
	 * @see DropTarget#addDropListener(DropTargetListener)
	 */
	public DropTargetBuilder withDropListener(DropTargetListener listener) {
		this.dropTarget.addDropListener(listener);
		return this;
	}

	/**
	 * Sets the action to be invoked if one or more file objects are dropped.
	 *
	 * @param action the action to set.
	 * @return the updated {@linkplain DropTargetBuilder} for chaining.
	 */
	public DropTargetBuilder onFileDrop(Consumer<String[]> action) {
		return withDropListener(new DropTargetAdapter() {

			@Override
			public void dragEnter(DropTargetEvent event) {
				dropAccept(event);
			}

			@Override
			public void drop(DropTargetEvent event) {
				action.accept(Check.isInstanceOf(event.data, String[].class));
			}

			@Override
			public void dropAccept(DropTargetEvent event) {
				TransferData acceptedDataType = acceptDataType(FileTransfer.getInstance(),
						Objects.requireNonNull(event.dataTypes), Objects.requireNonNull(event.currentDataType));

				if (acceptedDataType != null) {
					event.currentDataType = acceptedDataType;
					updateDropTargetEventDetail(event);
				} else {
					event.detail = DND.DROP_NONE;
				}
			}

			private void updateDropTargetEventDetail(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT) {
					if ((event.operations & DND.DROP_COPY) == DND.DROP_COPY) {
						event.detail = DND.DROP_COPY;
					} else if ((event.operations & DND.DROP_LINK) == DND.DROP_LINK) {
						event.detail = DND.DROP_LINK;
					}
				}
			}

		});
	}

	@Override
	public DropTarget get() {
		return this.dropTarget;
	}

	/**
	 * Determines the most acceptable data type for a drop operation.
	 *
	 * @param transfer the transfer type to determine the data type for.
	 * @param dataTypes the data types offered by the drop source.
	 * @param preferredDataType the preferred data type provided by the drop source.
	 * @return the most acceptable data type or {@code null} if no match exists at al.
	 */
	@Nullable
	public static TransferData acceptDataType(Transfer transfer, TransferData[] dataTypes,
			TransferData preferredDataType) {
		TransferData acceptedDataType = null;

		if (transfer.isSupportedType(preferredDataType)) {
			acceptedDataType = preferredDataType;
		} else {
			for (TransferData dataType : dataTypes) {
				if (transfer.isSupportedType(dataType)) {
					acceptedDataType = dataType;
					break;
				}
			}
		}
		return acceptedDataType;
	}

}
