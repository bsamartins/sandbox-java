package com.github.bsamartins.spring.graphql.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by martinsb on 13/10/2016.
 */
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String alias;

    @OneToMany
    private List<Person> enemies;
    @OneToMany
    private List<Person> allies;

    public Person() {}

    public Person(String firstName, String lastName, String alias) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return alias;
    }

    public void setEmail(String email) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<Person> getEnemies() {
        if(enemies == null) {
            enemies = new ArrayList<>();
        }
        return enemies;
    }

    public void setEnemies(List<Person> enemies) {
        this.enemies = enemies;
    }

    public List<Person> getAllies() {
        if(allies == null) {
            allies = new ArrayList<>();
        }
        return allies;
    }

    public void setAllies(List<Person> allies) {
        this.allies = allies;
    }
}
