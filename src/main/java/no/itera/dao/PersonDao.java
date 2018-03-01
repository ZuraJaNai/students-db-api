package no.itera.dao;

import no.itera.model.Person;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PersonDao extends CrudRepository<Person,Integer>{

    Iterable<Person> findByLastName(String lastName);

    Iterable<Person>  findByYearOfStudy(int yearOfStudy);

    Iterable<Person>  findByInternship(String internship);

    Iterable<Person>  findByPractice(String practice);


    List<Person> findAll(Specification<Person> personSpecification);
}
