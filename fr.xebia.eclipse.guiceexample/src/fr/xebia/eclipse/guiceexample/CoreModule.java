package fr.xebia.eclipse.guiceexample;

import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.environment.EnvironmentInfo;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;
import org.ops4j.peaberry.Peaberry;
import org.ops4j.peaberry.util.TypeLiterals;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import fr.xebia.eclipse.guiceexample.extensionpoint.HelloWorldParticipant;

public class CoreModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(EnvironmentInfo.class).toProvider(Peaberry.service(EnvironmentInfo.class).single());
		bind(TypeLiterals.iterable(HelloWorldParticipant.class)).toProvider(Peaberry.service(HelloWorldParticipant.class).multiple());
	}

	@Provides
	protected IExtensionRegistry provideExtensionRegistry() {
		return Platform.getExtensionRegistry();
	}

	@Provides
	protected IThemeManager provideThemeManager() {
		return PlatformUI.getWorkbench().getThemeManager();
	}
}
