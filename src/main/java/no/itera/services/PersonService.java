package no.itera.services;

import no.itera.model.Person;

public interface PersonService {

    Iterable<Person> getAll();

    Person getById(int id);

    Iterable<Person> findAllPersons(Person filter);

    boolean isPersonExists(Person person);

    boolean addPerson(Person person);

    boolean deletePerson(int id);

    void updatePerson(Person person);

    void deleteAll();
}
