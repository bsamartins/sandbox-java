package org.bmartins.javasandbox.springmongosession.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.SessionRepository;

public class MongoSessionRepository implements SessionRepository<MongoSession> {

	private static final Logger LOG = LoggerFactory.getLogger(MongoSessionRepository.class);

	@Autowired
	private org.bmartins.javasandbox.springmongosession.repository.MongoSessionRepository sessionRepository;
	
	@Override
	public MongoSession createSession() {
		return new MongoSession();
	}

	@Override
	public void save(MongoSession session) {
		LOG.debug("save");
		sessionRepository.save(session);
	}

	@Override
	public MongoSession getSession(String id) {
		LOG.debug("getSession");
		MongoSession result = sessionRepository.findOne(id);
		if(result != null) {
			result.setLastAccessedTime(System.currentTimeMillis());			
		}
		LOG.debug("result: {}@{}", id, result);
		return result;
	}

	@Override
	public void delete(String id) {
		LOG.debug("delete");
		sessionRepository.delete(id);
	}
}
