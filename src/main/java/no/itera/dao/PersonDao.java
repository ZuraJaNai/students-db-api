package no.itera.dao;

import no.itera.model.Person;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface PersonDao extends CrudRepository<Person,Integer>{

    Iterable<Person> findByLastName(String lastName);

    Iterable<Person>  findByYearOfStudy(int yearOfStudy);

    Iterable<Person>  findByInternship(String internship);

    Iterable<Person>  findByPractice(String practice);

}
