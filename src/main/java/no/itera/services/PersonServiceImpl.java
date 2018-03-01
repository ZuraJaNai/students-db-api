package no.itera.services;

import no.itera.dao.PersonDao;
import no.itera.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
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
    private PersonDao personDao;

    private SessionFactory factory;

    @Autowired
    public void PersonServiceImpl() {
//        personDao.save(new Person("1","1","1",
//                "default",1,"default","default","default"));
//        personDao.save(new Person("2","2","2",
//                "default",2,"default","default","default"));
//        personDao.save(new Person("3","3","3",
//                "default",3,"default","default","default"));
        factory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public Iterable<Person> getAll() {
        Session session = factory.openSession();
        Transaction tx = null;
        Iterable<Person> persons = null;

        try {
            tx = session.beginTransaction();

            persons = personDao.findAll();

            tx.commit();
        }

        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return persons;
    }

    @Override
    public Person getById(int id) {
        Session session = factory.openSession();
        Transaction tx = null;
        Person person = null;

        try {
            tx = session.beginTransaction();

            person = personDao.findOne(id);

            tx.commit();
        }

        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return person;
    }


    @Override
    public boolean isPersonExists(Person person) {
        Session session = factory.openSession();
        Transaction tx = null;
        boolean isExists =false;

        try {
            tx = session.beginTransaction();

            isExists = personDao.exists(person.getId());

            tx.commit();
        }

        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return isExists;
    }

    @Override
    public boolean addPerson(Person person) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            personDao.save(person);

            tx.commit();
        }

        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean deletePerson(int id) {
        personDao.delete(id);
        return true;
    }

    @Override
    public void updatePerson(Person person) {
        personDao.save(person);
    }

    @Override
    public void deleteAll() {
        personDao.deleteAll();
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

}
