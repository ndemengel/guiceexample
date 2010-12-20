package fr.xebia.eclipse.guiceexample;

import com.google.inject.Injector;

import fr.xebia.eclipse.guiceexample.guice.AbstractGuiceInjectorExtensionFactory;

public class GuiceExampleExtensionFactory extends AbstractGuiceInjectorExtensionFactory {
	
	@Override
	protected Injector getInjector() {
		return GuiceExamplePlugin.getDefault().getInjector();
	}
}
