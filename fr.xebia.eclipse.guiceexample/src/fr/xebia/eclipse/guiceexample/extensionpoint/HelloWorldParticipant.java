package fr.xebia.eclipse.guiceexample.extensionpoint;

import org.ops4j.peaberry.eclipse.ExtensionBean;

@ExtensionBean("fr.xebia.eclipse.guiceexample.helloWorldParticipant")
public interface HelloWorldParticipant {
	
	String sayHello();
}
