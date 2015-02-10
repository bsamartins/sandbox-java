package org.bmartins.javasandbox.springmongosession;

import org.springframework.session.ExpiringSession;

@FunctionalInterface
public interface SessionFactory<S extends ExpiringSession> {
	
	S createSession();
	
}
