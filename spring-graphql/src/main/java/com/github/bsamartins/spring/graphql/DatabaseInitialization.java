package com.github.bsamartins.spring.graphql;

import com.github.bsamartins.spring.graphql.model.Person;
import com.github.bsamartins.spring.graphql.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by martinsb on 13/10/2016.
 */
@Component
public class DatabaseInitialization {

    @Autowired
    private PersonRepository personRepository;

    @PostConstruct
    public void init() {
        Person flash = new Person("Barry", "Allen", "Flash");
        personRepository.save(flash);

        Person batman = new Person("Bruce", "Wayne", "Batman");
        personRepository.save(batman);

        Person joker = new Person("Jack", "Napier", "Joker");
        personRepository.save(joker);

        batman.getEnemies().add(joker);
        joker.getEnemies().add(batman);

        personRepository.save(batman);
        personRepository.save(joker);

    }
}
