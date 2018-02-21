package no.itera.services;

import no.itera.model.Person;

import java.util.ArrayList;

public interface PersonService {

    ArrayList<Person> getPersonsList();

    Person getPersonById(int id);

    boolean isPersonExists(Person person);

    boolean addPerson(Person person);

    boolean deletePerson(int id);

    void updatePerson(Person person);

    void deleteAllPersons();
}
