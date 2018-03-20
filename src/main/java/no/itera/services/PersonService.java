package no.itera.services;

import no.itera.model.Attachment;
import no.itera.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;


public interface PersonService {

    Iterable<Person> getAll();

    Page getAll(PageRequest pageRequest);

    Person getById(int id);

    List<Person> findAllPersons(Person filter);

    boolean isPersonExists(Person person);

    boolean addPerson(Person person);

    boolean deletePerson(int id);

    void updatePerson(int id, Person person);

    void updateAttachments(int id, Attachment attachment);

    void deleteAll();
}
