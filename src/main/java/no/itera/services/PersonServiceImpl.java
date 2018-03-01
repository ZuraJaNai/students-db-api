package no.itera.services;

import no.itera.dao.PersonDao;
import no.itera.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    public void PersonServiceImpl() {
//        personDao.save(new Person("1","1","1",
//                "default",1,"default","default","default"));
//        personDao.save(new Person("2","2","2",
//                "default",2,"default","default","default"));
//        personDao.save(new Person("3","3","3",
//                "default",3,"default","default","default"));
    }

    @Override
    public Iterable<Person> getAll() {
        return personDao.findAll();
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
    public void updatePerson(Person person) {
            personDao.save(person);
    }

    @Override
    public void deleteAll() {
            personDao.deleteAll();
    }

    public Iterable<Person> findAllPersons(Person filter) {

        List<Person> persons = personDao.findAll(new Specification<Person>() {

            @Override
            public Predicate toPredicate(Root<Person> root, CriteriaQuery< ?> query, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();

                if (filter.getLastName() != null) {
                    predicates.add(cb.like(cb.lower(root.get("lastName")),
                            filter.getLastName().toLowerCase() + "%"));
                }

                if (filter.getFirstName() != null) {
                    predicates.add(cb.like(cb.lower(root.get("firstName")),
                             "%" +filter.getFirstName().toLowerCase() + "%"));
                }

                if (filter.getPatronymic() != null) {
                    predicates.add(cb.like(cb.lower(root.get("patronymic")),
                            "%" + filter.getPatronymic().toLowerCase() + "%"));
                }

                if (filter.getEmail() != null) {
                    predicates.add(cb.like(cb.lower(root.get("email")),
                            "%" + filter.getEmail().toLowerCase() + "%"));
                }

                if (filter.getYearOfStudy() != null) {
                    predicates.add(cb.like(cb.lower(root.get("yearOfStudy")),
                            "%" + filter.getYearOfStudy().toLowerCase() + "%"));
                }

                if (filter.getInternship() != null) {
                    predicates.add(cb.like(cb.lower(root.get("internship")),
                            "%" + filter.getInternship().toLowerCase() + "%"));
                }

                if (filter.getPractice() != null) {
                    predicates.add(cb.like(cb.lower(root.get("practice")),
                            "%" + filter.getPractice().toLowerCase() + "%"));
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        });
        return persons;
    }



}
