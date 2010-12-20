package fr.xebia.eclipse.guiceexample;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.service.environment.EnvironmentInfo;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.ops4j.peaberry.Peaberry;
import org.ops4j.peaberry.eclipse.EclipseRegistry;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceExamplePlugin extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "fr.xebia.eclipse.guiceexample"; //$NON-NLS-1$

	private static GuiceExamplePlugin plugin;

	private Injector injector;

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		// without Peaberry
		// injector = Guice.createInjector(new CoreModule());

		// with Peaberry's OSGi support
		// injector = Guice.createInjector(Peaberry.osgiModule(context), new CoreModule());

		// with Peaberry's special Eclipse support
		injector = Guice.createInjector(Peaberry.osgiModule(context, EclipseRegistry.eclipseRegistry()), new CoreModule());

		logExtensionStatistics();
		logEnvironmentStatistics();
	}

	private void logExtensionStatistics() {
		ExtensionStatistics extensionStats = injector.getInstance(ExtensionStatistics.class);
		String message = "This plugin declares " + extensionStats.countDeclaredExtensions() + " extensions and provides "
				+ extensionStats.countDeclaredExtensionPoints() + " extension points";
		getLog().log(new Status(IStatus.INFO, PLUGIN_ID, message));
	}
	
	private void logEnvironmentStatistics() {
		EnvironmentInfo info = injector.getInstance(EnvironmentInfo.class);
		getLog().log(new Status(IStatus.INFO, PLUGIN_ID, "This platform runs on " + info.getOS()));
	}

	public Injector getInjector() {
		return injector;
	}

	public void stop(BundleContext context) throws Exception {
		injector = null;

		plugin = null;
		super.stop(context);
	}

	public static GuiceExamplePlugin getDefault() {
		return plugin;
	}
}
