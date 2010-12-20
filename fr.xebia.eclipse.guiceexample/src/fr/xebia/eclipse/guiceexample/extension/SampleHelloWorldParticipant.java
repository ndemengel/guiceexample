package fr.xebia.eclipse.guiceexample.extension;

import fr.xebia.eclipse.guiceexample.extensionpoint.HelloWorldParticipant;

public class SampleHelloWorldParticipant implements HelloWorldParticipant {

	@Override
	public String sayHello() {
		return "Sample Hello";
	}
}
