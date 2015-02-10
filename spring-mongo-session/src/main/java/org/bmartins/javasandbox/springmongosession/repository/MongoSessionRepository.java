package org.bmartins.javasandbox.springmongosession.repository;

import org.bmartins.javasandbox.springmongosession.session.MongoSession;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MongoSessionRepository extends PagingAndSortingRepository<MongoSession, String> {

}
