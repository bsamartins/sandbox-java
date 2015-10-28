package org.bmartins.springdata.pageable.repository;

import org.bmartins.springdata.pageable.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
