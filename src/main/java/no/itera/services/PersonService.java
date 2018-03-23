package no.itera.services;

import no.itera.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;


public interface PersonService {

    Iterable<Person> getAll();

    Page getAll(PageRequest pageRequest);

    Person getById(int id);

    List<Person> findAllPersons(SearchPerson filter);

    boolean isPersonExists(Person person);

    boolean addPerson(Person person);

    boolean deletePerson(int id);

    void updatePerson(int id, PersonInputData person);

    void updateAttachments(int id, Attachment attachment);

    void deleteAll();

    int count();

    void addPhoto(int personId, byte[] bytes);

    void deletePhoto(int personId);

    List<PersonOutputData> transformPersonsToOutputFormat(List<Person> personList);
}
