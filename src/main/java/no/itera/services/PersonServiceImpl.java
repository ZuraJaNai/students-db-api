package no.itera.services;

import no.itera.dao.PersonDao;
import no.itera.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;

/**
 * Service for managing Person classes
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    /**
     * Method to get all persons from the database
     *
     * @return Iterable of Persons
     */
    @Override
    public Iterable<Person> getAll() {
        return personDao.findAll();
    }

    /**
     * Method to get page containing several
     *
     * @param pageRequest  object containing number of page and number of
     *                     persons to get from the database
     * @return Page object with persons list
     */
    @Override
    public Page getAll(PageRequest pageRequest) {
        return personDao.findAll(pageRequest);
    }

    /**
     * Method to get defined Person by id from the database
     *
     * @param id  id of the Person
     * @return Person object
     */
    @Override
    public Person getById(int id) {
        return personDao.findOne(id);
    }


    /**
     * Method to find out if Person with specified id exists in database
     *
     * @param person  Person object
     * @return Boolean true or false
     */
    @Override
    public boolean isPersonExists(Person person) {
        return personDao.exists(person.getId());
    }

    /**
     * Method for adding person to the database
     *
     * @param person  Person object to add
     * @return Boolean true
     */
    @Override
    public boolean addPerson(Person person) {
        personDao.save(person);
        return true;
    }

    /**
     * Method for deleting person from the database
     *
     * @param id  id of person to delete
     * @return Boolean true
     */
    @Override
    public boolean deletePerson(int id) {
            personDao.delete(id);
            return true;
    }

    /**
     * Method for updating existing person data
     *
     * @param id id of Person to update
     * @param person PersonInputData object containing new data for Person
     */
    @Override
    public void updatePerson(int id, PersonInputData person) {
        Person tempPerson = this.getById(id);

        if(StringUtils.isNoneEmpty(person.getLastName())){
            tempPerson.setLastName(person.getLastName());
        }
        if(StringUtils.isNoneEmpty(person.getFirstName())){
            tempPerson.setFirstName(person.getFirstName());
        }
        if(StringUtils.isNoneEmpty(person.getPatronymic())){
            tempPerson.setPatronymic(person.getPatronymic());
        }
        if(StringUtils.isNoneEmpty(person.getEmail())){
            tempPerson.setEmail(person.getEmail());
        }
        if(StringUtils.isNoneEmpty(person.getYearOfStudy())){
            tempPerson.setYearOfStudy(person.getYearOfStudy());
        }
        if(person.getInternshipBegin() != null){
            tempPerson.setInternshipBegin(person.getInternshipBegin());
        }
        if(person.getInternshipEnd() != null){
            tempPerson.setInternshipEnd(person.getInternshipEnd());
        }
        if(person.getPracticeBegin() != null){
            tempPerson.setPracticeBegin(person.getPracticeBegin());
        }
        if(person.getPracticeEnd() != null){
            tempPerson.setPracticeEnd(person.getPracticeEnd());
        }
        if(person.getJobBegin() != null){
            tempPerson.setJobBegin(person.getJobBegin());
        }
        if(person.getJobEnd() != null){
            tempPerson.setJobEnd(person.getJobEnd());
        }
        tempPerson.setComment(person.getComment());
        personDao.save(tempPerson);
    }

    /**
     * Method to add attachment to defined Person
     * @param id  id of person for who to add attachment
     * @param attachment Attachment object which to add
     */
    @Override
    public void updateAttachments(int id, Attachment attachment) {
        Person person = this.getById(id);
        person.addAttachment(attachment);
        personDao.save(person);
    }

    /**
     * Method to delete all persons in database
     */
    @Override
    public void deleteAll() {
            personDao.deleteAll();
    }


    /**
     * Method to find persons according to certain criteria
     *
     * @param filter  object with fields that are a criteria (if not null or empty)
     * @return List of persons,which suits to the criteria
     */
    @Override
    public List<Person> findAllPersons(PersonSearch filter) {

        List<Person> persons = personDao.findAll((Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNoneEmpty(filter.getLastName())) {
                predicates.add(cb.like(cb.lower(root.get("lastName")),
                        filter.getLastName().toLowerCase() + "%"));
            }

            if (StringUtils.isNoneEmpty(filter.getFirstName())) {
                predicates.add(cb.like(cb.lower(root.get("firstName")),
                        "%" + filter.getFirstName().toLowerCase() + "%"));
            }


            if (StringUtils.isNoneEmpty(filter.getEmail())) {
                predicates.add(cb.like(cb.lower(root.get("email")),
                        "%" + filter.getEmail().toLowerCase() + "%"));
            }

            if (StringUtils.isNoneEmpty(filter.getYearOfStudy())) {
                predicates.add(cb.like(cb.lower(root.get("yearOfStudy")),
                        "%" + filter.getYearOfStudy().toLowerCase() + "%"));
            }

            if (filter.isInternship()) {
                if (filter.getInternshipDate() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("internshipBegin"), filter.getInternshipDate()));
                    predicates.add(cb.or(cb.greaterThanOrEqualTo(root.get("internshipEnd"), filter.getInternshipDate()),
                            cb.isNull(root.get("internshipEnd"))));
                } else {
                    predicates.add(cb.isNotNull(root.get("internshipBegin")));
                }
            }

            if (filter.isPractice()) {
                if (filter.getPracticeDate() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("practiceBegin"), filter.getPracticeDate()));
                    predicates.add(cb.or(cb.greaterThanOrEqualTo(root.get("practiceEnd"), filter.getPracticeDate()),
                            cb.isNull(root.get("practiceEnd"))));
                } else {
                    predicates.add(cb.isNotNull(root.get("practiceBegin")));
                }
            }

            if (filter.isJob()) {
                if (filter.getJobDate() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("jobBegin"), filter.getJobDate()));
                    predicates.add(cb.or(cb.greaterThanOrEqualTo(root.get("jobEnd"), filter.getJobDate()),
                            cb.isNull(root.get("jobEnd"))));
                } else {
                    predicates.add(cb.isNotNull(root.get("jobBegin")));
                }
            }

            if (StringUtils.isNoneEmpty(filter.getComment())) {
                predicates.add(cb.like(cb.lower(root.get("comment")),
                        "%" + filter.getComment().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });

        return persons;
    }

    /**
     * Method to get total number of students in the database
     *
     * @return int number
     */
    @Override
    public int count(){
        return toIntExact(personDao.count());
    }

    /**
     * Method to add photo to instance of Person
     *
     * @param personId  id of Person to whom to add photo
     * @param bytes  the photo data
     */
    @Override
    public void addPhoto(int personId, byte[] bytes) {
        Person person = getById(personId);
        person.setPhoto(bytes);
        personDao.save(person);
    }

    /**
     * Method to delete photo from the instance of Person
     *
     * @param personId  id of person whos photo to delete
     */
    @Override
    public void deletePhoto(int personId) {
        Person person = getById(personId);
        person.setPhoto(null);
        personDao.save(person);
    }

    /**
     * Method to transform List of Persons to List of PersonOutputData,needed
     * when we want to get only main information about person
     *
     * @param personList  List of Person objects
     * @return List of PersonOutputData objects
     */
    public List<PersonOutputData> transformPersonsToOutputFormat(List<Person> personList){
        List<PersonOutputData> personOutputData = new ArrayList<>();
        for (Person person :
                personList) {
            personOutputData.add(new PersonOutputData(person));
        }
        return personOutputData;
    }
}
