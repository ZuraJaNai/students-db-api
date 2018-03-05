package no.itera.services;

import no.itera.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PersonService {

    Iterable<Person> getAll();

    Page getAll(PageRequest pageRequest);

    Person getById(int id);

    Iterable<Person> findAllPersons(Person filter);

    boolean isPersonExists(Person person);

    boolean addPerson(Person person);

    boolean deletePerson(int id);

    void updatePerson(Person person);

    void deleteAll();
}
