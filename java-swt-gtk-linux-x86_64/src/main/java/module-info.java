/**
 * module-info
 */
module de.carne.swt {
	requires transitive org.eclipse.swt.gtk.linux.x86_64;
	requires transitive org.eclipse.jdt.annotation;
	requires transitive de.carne;

	exports de.carne.swt;
	exports de.carne.swt.dnd;
	exports de.carne.swt.events;
	exports de.carne.swt.graphics;
	exports de.carne.swt.layout;
	exports de.carne.swt.platform;
	exports de.carne.swt.util;
	exports de.carne.swt.widgets;
	exports de.carne.swt.widgets.aboutinfo;
	exports de.carne.swt.widgets.logview;
	exports de.carne.swt.widgets.notification;
	exports de.carne.swt.widgets.runtimeinfo;
}
