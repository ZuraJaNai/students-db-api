package no.itera.services;

import no.itera.dao.PersonDao;
import no.itera.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

/**
 * Service for managing Person classes
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    private static final String LAST_NAME_PARAM = "lastName";
    private static final String FIRST_NAME_PARAM = "firstName";
    private static final String EMAIL_PARAM = "email";
    private static final String YEAR_OF_STUDY_PARAM = "yearOfStudy";
    private static final String INTERNSHIP_BEGIN_PARAM = "internshipBegin";
    private static final String INTERNSHIP_END_PARAM = "internshipEnd";
    private static final String PRACTICE_BEGIN_PARAM = "practiceBegin";
    private static final String PRACTICE_END_PARAM = "practiceEnd";
    private static final String JOB_BEGIN_PARAM = "jobBegin";
    private static final String JOB_END_PARAM = "jobEnd";
    private static final String COMMENT_PARAM = "comment";

    /**
     * Method to get all persons from the database
     *
     * @return Iterable of Persons
     */
    @Override
    public Iterable<Person> getAll() {
        return personDao.findAllByOrderByIdAsc();
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
        return personDao.findAllByOrderByIdAsc(pageRequest);
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
     * @param person PersonData object containing new data for Person
     */
    @Override
    public void updatePerson(int id, PersonData person) {
        Person tempPerson = this.getById(id);
        tempPerson.updatePersonData(person);
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

        return personDao.findAll((Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getYearOfStudy() != null) {
                predicates.add(root.get(YEAR_OF_STUDY_PARAM).in(filter.getYearOfStudy()));

            }

            predicates = this.checkString(predicates,cb,root,filter.getLastName(),LAST_NAME_PARAM);
            predicates = this.checkString(predicates,cb,root,filter.getFirstName(),FIRST_NAME_PARAM);
            predicates = this.checkString(predicates,cb,root,filter.getEmail(),EMAIL_PARAM);
            predicates = this.checkString(predicates,cb,root,filter.getComment(),COMMENT_PARAM);

            if (filter.isInternship()) {
                predicates = this.checkDate(predicates,cb,root,filter.getInternshipDate(),
                        INTERNSHIP_BEGIN_PARAM,INTERNSHIP_END_PARAM);
            }

            if (filter.isJob()) {
                predicates = this.checkDate(predicates,cb,root,filter.getJobDate(),
                        JOB_BEGIN_PARAM,JOB_END_PARAM);
            }

            if (filter.isPractice()) {
                predicates = this.checkDate(predicates,cb,root,filter.getPracticeDate(),
                        PRACTICE_BEGIN_PARAM,PRACTICE_END_PARAM);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        },new Sort(Sort.Direction.ASC,"id"));
    }

    private List<Predicate> checkString(List<Predicate> predicates, CriteriaBuilder cb,
                                        Root<Person> root,String value,String param){
        if (StringUtils.isNoneEmpty(value)) {
            predicates.add(cb.like(cb.lower(root.get(param)),
                    value.toLowerCase() + "%"));
        }
        return predicates;
    }

    private List<Predicate> checkDate(List<Predicate> predicates, CriteriaBuilder cb,
                                        Root<Person> root,SearchDate value,
                                      String paramBegin,String paramEnd){
        if (value != null) {
            if (value.getType() == SearchDateType.SINGLE) {
                predicates.add(cb.lessThanOrEqualTo(root.get(paramBegin), value.getDateOne()));
                predicates.add(cb.or(cb.greaterThanOrEqualTo(root.get(paramEnd), value.getDateOne()),
                        cb.isNull(root.get(paramEnd))));
            } else if (value.getType() == SearchDateType.DOUBLE) {
                predicates.add(cb.lessThanOrEqualTo(root.get(paramBegin), value.getDateTwo()));
                predicates.add(cb.or(cb.greaterThanOrEqualTo(root.get(paramEnd), value.getDateOne()),
                        cb.isNull(root.get(paramEnd))));
            }
        }
        else {
            predicates.add(cb.isNotNull(root.get(paramBegin)));
        }
        return predicates;
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
     * Method to transform List of Persons to List of PersonData,needed
     * when we want to get only main information about person
     *
     * @param personList  List of Person objects
     * @return List of PersonData objects
     */
    public List<PersonData> transformPersonsToOutputFormat(List<Person> personList){
        List<PersonData> personOutputData = new ArrayList<>();
        for (AbstractPerson person :
                personList) {
            personOutputData.add(new PersonData(person));
        }
        return personOutputData;
    }

    /**
     * Method to transform List of Persons to List of PersonData,needed
     * when we want to get only main information about person
     *
     * @param personList  List of Person objects
     * @return List of PersonData objects
     */
    public List<PersonData> transformPersonsToOutputFormat(Iterable<Person> personList){
        List<PersonData> personOutputData = new ArrayList<>();
        for (AbstractPerson person :
                personList) {
            personOutputData.add(new PersonData(person));
        }
        return personOutputData;
    }

    public void importFromExcel(File excelFile) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(excelFile);
        Sheet studentsSheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();

        for (Row row: studentsSheet) {
            if(dataFormatter.formatCellValue(row.getCell(0)).matches("[0-9]+") &&
                dataFormatter.formatCellValue(row.getCell(1)).matches("[^\\x00-\\x7F]+.*")){
                Person person = new Person();
                String[] fullName = dataFormatter.formatCellValue(row.getCell(1)).split(" ");
                person.setLastName(fullName[0]);
                if(StringUtils.isNoneEmpty(fullName[1])){
                    person.setFirstName(fullName[1]);
                }
                else {
                    person.setFirstName(" ");
                }
                String email = dataFormatter.formatCellValue(row.getCell(2));
                if(email.matches("[\\w]+@[\\w]+.[\\w]+")){
                    person.setEmail(email);
                }
                person.setYearOfStudy(Year.now().toString());
                this.addPerson(person);
            }
        }
    }
}
