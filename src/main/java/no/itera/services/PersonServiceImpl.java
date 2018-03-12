package no.itera.services;

import no.itera.dao.PersonDao;
import no.itera.model.Person;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public void updatePerson(Person person) {
            personDao.save(person);
    }

    @Override
    public void deleteAll() {
            personDao.deleteAll();
    }

    public Iterable<Person> findAllPersons(Person filter) {

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

            if (StringUtils.isNoneEmpty(filter.getPatronymic())) {
                predicates.add(cb.like(cb.lower(root.get("patronymic")),
                        "%" + filter.getPatronymic().toLowerCase() + "%"));
            }

            if (StringUtils.isNoneEmpty(filter.getEmail())) {
                predicates.add(cb.like(cb.lower(root.get("email")),
                        "%" + filter.getEmail().toLowerCase() + "%"));
            }

            if (StringUtils.isNoneEmpty(filter.getYearOfStudy())) {
                predicates.add(cb.like(cb.lower(root.get("yearOfStudy")),
                        "%" + filter.getYearOfStudy().toLowerCase() + "%"));
            }

            if (StringUtils.isNoneEmpty(filter.getInternship())) {
                predicates.add(cb.like(cb.lower(root.get("internship")),
                        "%" + filter.getInternship().toLowerCase() + "%"));
            }

            if (StringUtils.isNoneEmpty(filter.getPractice())) {
                predicates.add(cb.like(cb.lower(root.get("practice")),
                        "%" + filter.getPractice().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
        return persons;
    }



}
