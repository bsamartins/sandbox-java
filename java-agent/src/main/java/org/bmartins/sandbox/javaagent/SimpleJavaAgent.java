package org.bmartins.sandbox.javaagent;

import java.lang.instrument.Instrumentation;

public class SimpleJavaAgent {
	public static void premain(String args, Instrumentation instrumentation) {
		System.out.println("Simple Agent");
        FindUsageTransformer transformer = new FindUsageTransformer ();
        instrumentation.addTransformer(transformer,true);		
	}
}
