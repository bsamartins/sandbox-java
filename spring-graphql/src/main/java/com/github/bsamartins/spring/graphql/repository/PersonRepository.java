package com.github.bsamartins.spring.graphql.repository;

import com.github.bsamartins.spring.graphql.model.Person;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by martinsb on 13/10/2016.
 */
@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Long>{


}
