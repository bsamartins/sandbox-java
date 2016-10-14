package com.github.bsamartins.spring.graphql.schema.objecttype;

import com.github.bsamartins.spring.graphql.model.Person;
import com.github.bsamartins.spring.graphql.repository.PersonRepository;
import com.github.bsamartins.spring.graphql.schema.AppSchema;
import com.oembedler.moon.graphql.engine.stereotype.*;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by martinsb on 13/10/2016.
 */
@GraphQLObject("Root")
public class RootObjectType {

    @Autowired
    @GraphQLIgnore
    private PersonRepository personRepository;

    @Autowired
    @GraphQLIgnore
    private AppSchema appSchema;

    @GraphQLNonNull
    @GraphQLField("version")
    @GraphQLDescription("Root query version number")
    public static final String VERSION = "1.0";

    @GraphQLField
    public List<Person> allPeople(DataFetchingEnvironment environment) {
        return StreamSupport.stream(personRepository.findAll().spliterator(), true)
                .collect(Collectors.toList());
    }
}
