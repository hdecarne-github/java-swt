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
package de.carne.swt;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.carne.swt.graphics.ResourceException;
import de.carne.swt.widgets.ShellUserInterface;
import de.carne.util.Exceptions;
import de.carne.util.Late;
import de.carne.util.cmdline.CmdLineException;
import de.carne.util.cmdline.CmdLineProcessor;
import de.carne.util.logging.Log;

/**
 * Base class for any kind of SWT based application.
 */
public abstract class UserApplication {

	private static final Log LOG = new Log();

	private final Late<@NonNull CmdLineProcessor> cmdLineHolder = new Late<>();
	private final Late<@NonNull Display> displayHolder = new Late<>();

	private int status = 0;

	/**
	 * Run the application by setting up the user interface and executing the event loop.
	 *
	 * @param cmdLine The {@linkplain CmdLineProcessor} to invoke after the user interface has been set up.
	 * @return The application's exit code (as set by {@linkplain #setStatus(int)}).
	 * @throws ResourceException if a required resource is not available.
	 */
	public int run(CmdLineProcessor cmdLine) throws ResourceException {
		this.cmdLineHolder.set(cmdLine);

		Display display = this.displayHolder.set(setupDisplay());

		display.setRuntimeExceptionHandler(this::handleRuntimeException);
		display.setErrorHandler(this::handleError);

		ShellUserInterface userInterface = setupUserInterface(display);

		userInterface.open();
		try {
			cmdLine.process();
		} catch (CmdLineException e) {
			Exceptions.warn(e);
		}
		userInterface.run();
		display.dispose();
		return this.status;
	}

	/**
	 * Setup the {@linkplain Display}.
	 *
	 * @return The application's {@linkplain Display}.
	 * @throws ResourceException if a required resource is not available.
	 */
	protected abstract Display setupDisplay() throws ResourceException;

	/**
	 * Setup the start {@linkplain Shell}.
	 *
	 * @param display The application's {@linkplain Display}.
	 * @return The application's start {@linkplain Shell}.
	 * @throws ResourceException if a required resource is not available.
	 */
	protected abstract ShellUserInterface setupUserInterface(Display display) throws ResourceException;

	/**
	 * Gets the application's {@linkplain CmdLineProcessor}.
	 *
	 * @return the application's {@linkplain CmdLineProcessor}.
	 */
	protected CmdLineProcessor getCmdLine() {
		return this.cmdLineHolder.get();
	}

	/**
	 * Get the application's {@linkplain Display}.
	 *
	 * @return The application's {@linkplain Display}.
	 */
	public Display getDisplay() {
		return this.displayHolder.get();
	}

	/**
	 * Run a {@linkplain Runnable} on the UI thread.
	 * <p>
	 * The submitted {@linkplain Runnable} is invoked asynchronously and this function returns immediately.
	 *
	 * @param runnable The {@linkplain Runnable} to invoke.
	 * @see Display#asyncExec(Runnable)
	 */
	public void runNoWait(Runnable runnable) {
		this.displayHolder.get().asyncExec(runnable);
	}

	/**
	 * Run a {@linkplain Runnable} on the UI thread and wait for it to finish.
	 *
	 * @param runnable The {@linkplain Runnable} to invoke.
	 */
	public void runWait(Runnable runnable) {
		Display display = this.displayHolder.get();

		if (Thread.currentThread().equals(display.getThread())) {
			runnable.run();
		} else {
			display.syncExec(runnable);
		}
	}

	/**
	 * Invoke a {@linkplain Supplier} on the UI thread and wait for it to finish.
	 *
	 * @param <T> The {@linkplain Supplier} result type.
	 * @param supplier The {@linkplain Supplier} to invoke.
	 * @return The result of the {@linkplain Supplier#get()} call (may be {@code null}).
	 */
	@Nullable
	public <T> T runWait(Supplier<T> supplier) {
		Display display = this.displayHolder.get();
		T result;

		if (Thread.currentThread().equals(display.getThread())) {
			result = supplier.get();
		} else {
			final AtomicReference<T> resultHolder = new AtomicReference<>();

			display.syncExec(() -> resultHolder.set(supplier.get()));
			result = resultHolder.get();
		}
		return result;
	}

	/**
	 * Set the application's exit status (as returned by {@linkplain #run(CmdLineProcessor)}).
	 *
	 * @param status The exit status to set.
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	private void handleRuntimeException(RuntimeException exception) {
		LOG.error(exception, "Uncaught runtime exception: {0}", Exceptions.toString(exception));
	}

	private void handleError(Error error) {
		LOG.error(error, "Uncaught error: {0}", Exceptions.toString(error));
	}

}
