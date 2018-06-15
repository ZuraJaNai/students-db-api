package no.itera.services;

import no.itera.model.*;
import no.itera.util.CustomErrorType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;


public interface PersonService {

    /**
     * Method to get all persons from the database
     *
     * @return Iterable of Persons
     */
    Iterable<Person> getAll();

    /**
     * Method to get page containing several
     *
     * @param pageRequest object containing number of page and number of
     *                    persons to get from the database
     * @return Page object with persons list
     */
    Page getAll(PageRequest pageRequest);

    /**
     * Method to get defined Person by id from the database
     *
     * @param id id of the Person
     * @return Person object
     */
    Person getById(int id);

    /**
     * Method to find persons according to certain criteria
     *
     * @param filter object with fields that are a criteria (if not null or empty)
     * @return List of persons,which suits to the criteria
     */
    Iterable<Person> findAllPersons(PersonSearch filter);

    /**
     * Method to find out if Person with specified id exists in database
     *
     * @param person Person object
     * @return Boolean true or false
     */
    boolean isPersonExists(Person person);

    /**
     * Method for adding person to the database
     *
     * @param person Person object to add
     * @return Boolean true
     */
    boolean addPerson(Person person);

    /**
     * Method for deleting person from the database
     *
     * @param id id of person to delete
     * @return Boolean true
     */
    boolean deletePerson(int id);

    /**
     * Method for updating existing person data
     *
     * @param id     id of Person to update
     * @param person PersonData object containing new data for Person
     */
    void updatePerson(int id, PersonData person);

    /**
     * Method to add attachment to defined Person
     *
     * @param id         id of person for who to add attachment
     * @param attachment Attachment object which to add
     */
    void updateAttachments(int id, Attachment attachment);

    /**
     * Method to delete all persons in database
     */
    void deleteAll();

    /**
     * Method to get total number of students in the database
     *
     * @return int number
     */
    int count();


    /**
     * Method to transform List of Persons to List of PersonData,needed
     * when we want to get only main information about person
     *
     * @param personList List of Person objects
     * @return List of PersonData objects
     */
    List<PersonData> transformPersonsToOutputFormat(List<Person> personList);

    List<PersonData> transformPersonsToOutputFormat(Iterable<Person> personList);

    void importFromExcel(File excelFile) throws IOException, InvalidFormatException, CustomErrorType;

    void updateSeveralPersons(BulkChangePersonData data);
}
