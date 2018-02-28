package no.itera.services;

import no.itera.dao.PersonDao;
import no.itera.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

@Service
public class PersonServiceImpl implements PersonService {


    @Autowired
    public void PersonServiceImpl(){
//        personDao.save(new Person("1","1","1",
//                "default",1,"default","default","default"));
//        personDao.save(new Person("2","2","2",
//                "default",2,"default","default","default"));
//        personDao.save(new Person("3","3","3",
//                "default",3,"default","default","default"));

    }
    @Autowired
    private PersonDao personDao;


    @Override
    public Iterable<Person> getAll() {
        return personDao.findAll();
    }

    @Override
    public Person getById(int id) {
        return personDao.findOne(id);
//        if(personsList.isEmpty()){
//            return null;
//        }
//        Person tempPerson = null;
//        for (Person person :
//                this.personsList) {
//            if (person.getId() == id) {
//                tempPerson = person;
//            }
//        }
//        return tempPerson;
    }

    @Override
    public Iterable<Person> getAllByLastName(String lastName) {
        return personDao.findByLastName(lastName);
    }

    @Override
    public Iterable<Person> getAllByYearOfStudy(int yearOfStudy) {
        return personDao.findByYearOfStudy(yearOfStudy);
    }

    @Override
    public Iterable<Person> getAllByInternship(String internship) {
        return personDao.findByInternship(internship);
    }

    @Override
    public Iterable<Person> getAllByPractice(String practice) {
        return personDao.findByPractice(practice);
    }

    @Override
    public boolean isPersonExists(Person person) {
        return personDao.exists(person.getId());
//        return getById(person.getId()) != null;
    }

    @Override
    public boolean addPerson(Person person){
        personDao.save(person);
        return true;
//        try {
//            if (this.getById(person.getId()) == null){
//                this.personsList.add(person);
//                return true;
//            }
//        }
//        catch (NullPointerException e){
//            return false;
//        }
//        return false;
    }

    @Override
    public boolean deletePerson(int id){
        personDao.delete(id);
        return true;
//        for(Iterator<Person> iterator = personsList.iterator(); iterator.hasNext();){
//            Person person = iterator.next();
//            if (person.getId() == id){
//                iterator.remove();
//                return true;
//            }
//        }
//        return false;
    }

    @Override
    public void updatePerson(Person person) {
        personDao.save(person);
//        personsList.set(personsList.indexOf(person),person);
    }

    @Override
    public void deleteAll() {
        personDao.deleteAll();
//        personsList.clear();
    }
}
