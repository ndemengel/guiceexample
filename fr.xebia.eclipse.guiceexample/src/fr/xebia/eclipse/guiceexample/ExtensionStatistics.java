package fr.xebia.eclipse.guiceexample;

import org.eclipse.core.runtime.IExtensionRegistry;

import com.google.inject.Inject;

public class ExtensionStatistics {
	
	private final IExtensionRegistry extensionRegistry;

	@Inject
	public ExtensionStatistics(IExtensionRegistry extensionRegistry) {
		this.extensionRegistry = extensionRegistry;
	}

	public int countDeclaredExtensions() {
		return extensionRegistry.getExtensions(GuiceExamplePlugin.PLUGIN_ID).length;
	}

	public int countDeclaredExtensionPoints() {
		return extensionRegistry.getExtensionPoints(GuiceExamplePlugin.PLUGIN_ID).length;
	}
}
