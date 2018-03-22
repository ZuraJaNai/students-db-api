package no.itera.services;

import no.itera.dao.PersonDao;
import no.itera.model.Attachment;
import no.itera.model.Person;
import no.itera.model.PersonInputData;
import no.itera.model.SearchPerson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.toIntExact;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    private String date_format_string = "MM.yyyy";

    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern(date_format_string)
            .parseDefaulting(DAY_OF_MONTH, 15)
            .toFormatter();
    SimpleDateFormat format = new SimpleDateFormat(date_format_string);

    @Override
    public Iterable<Person> getAll() {
        return personDao.findAll();
    }

    @Override
    public Page getAll(PageRequest pageRequest) {
        return personDao.findAll(pageRequest);
    }

    @Override
    public Person getById(int id) {
        return personDao.findOne(id);
    }


    @Override
    public boolean isPersonExists(Person person) {
        return personDao.exists(person.getId());
    }

    @Override
    public boolean addPerson(Person person) {
        personDao.save(person);
        return true;
    }

    @Override
    public boolean deletePerson(int id) {
            personDao.delete(id);
            return true;
    }

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

    @Override
    public void updateAttachments(int id, Attachment attachment) {
        Person person = this.getById(id);
        person.addAttachment(attachment);
        personDao.save(person);
    }

    @Override
    public void deleteAll() {
            personDao.deleteAll();
    }


    public List<Person> findAllPersons(SearchPerson filter) {

        List<Person> persons = personDao.findAll((root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNoneEmpty(filter.getLastName())) {
                predicates.add(cb.like(cb.lower(root.get("lastName")),
                        filter.getLastName().toLowerCase() + "%"));
            }

            if (StringUtils.isNoneEmpty(filter.getFirstName())) {
                predicates.add(cb.like(cb.lower(root.get("firstName")),
                         "%" +filter.getFirstName().toLowerCase() + "%"));
            }


            if (StringUtils.isNoneEmpty(filter.getEmail())) {
                predicates.add(cb.like(cb.lower(root.get("email")),
                        "%" + filter.getEmail().toLowerCase() + "%"));
            }

            if (StringUtils.isNoneEmpty(filter.getYearOfStudy())) {
                predicates.add(cb.like(cb.lower(root.get("yearOfStudy")),
                        "%" + filter.getYearOfStudy().toLowerCase() + "%"));
            }

            if(filter.isInternship()){
               if(filter.getInternshipDate() != null){
//                   Date internshipDate = Date.from(LocalDate.parse(filter.getInternshipDate(),formatter)
//                           .atStartOfDay(ZoneId.systemDefault()).toInstant());
                   ParameterExpression<Timestamp> criteria = cb.parameter(Timestamp.class,format.format(filter.getInternshipDate()));
                   if(cb.isNotNull(root.get("internshipEnd")).isNegated()){
                       predicates.add(cb.greaterThanOrEqualTo(criteria,root.<Timestamp>get("internshipBegin")));
                   }
                   else {
                       predicates.add(cb.between(criteria,root.<Date>get("internshipBegin"),root.<Date>get("internshipEnd")));
                   }
                }
                else{
                    predicates.add(cb.isNotNull(root.get("internshipBegin")));
                }
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        });
        return persons;
    }


    public int count(){
        return toIntExact(personDao.count());
    }

}
