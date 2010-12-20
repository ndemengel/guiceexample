package fr.xebia.eclipse.guiceexample.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.themes.IThemeManager;

import com.google.inject.Inject;

import fr.xebia.eclipse.guiceexample.extensionpoint.HelloWorldParticipant;

public class SampleAction implements IWorkbenchWindowActionDelegate {

	private static final String NL = System.getProperty("line.separator");

	private IWorkbenchWindow window;

	private final IThemeManager themeManager;
	private final Iterable<HelloWorldParticipant> participants;

	@Inject
	public SampleAction(IThemeManager themeManager, Iterable<HelloWorldParticipant> participants) {
		this.themeManager = themeManager;
		this.participants = participants;
	}

	public void run(IAction action) {
		String currentTheme = themeManager.getCurrentTheme().getLabel();
		String message = "Hello, Xebia world. " + NL + "The current theme is: " + currentTheme;

		message += NL + getHellosFromParticipants2();
		MessageDialog.openInformation(window.getShell(), "Guice Example", message);
	}

	private String getHellosFromParticipants2() {
		final StringBuilder buffer = new StringBuilder();
		
		for (final HelloWorldParticipant participant : participants) {
			appendParticipantHello(buffer, participant);
		}

		return buffer.toString();
	}

	private void appendParticipantHello(final StringBuilder buffer, final HelloWorldParticipant participant) {
		SafeRunner.run(new ISafeRunnable() {

			public void run() throws Exception {
				if (buffer.length() == 0) {
					buffer.append(NL).append("Other hellos:");
				}
				buffer.append(NL).append(participant.sayHello());
			}

			public void handleException(Throwable throwable) {/* ... */}
		});
	}
	
	// "standard way" to retrieve extensions
	private String getHellosFromParticipants() {
		String extensionPoint = "fr.xebia.eclipse.guiceexample.helloWorldParticipant";
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(extensionPoint);

		final StringBuilder buffer = new StringBuilder();
		for (IConfigurationElement element : elements) {
			try {
				final Object extension = element.createExecutableExtension("class");
				
				if (!(extension instanceof HelloWorldParticipant)) {
					continue;
				}
				
				appendParticipantHello(buffer, (HelloWorldParticipant) extension);
				
			} catch (CoreException e) {/* ... */}
		}

		return buffer.toString();
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}