package no.itera.dao;

import no.itera.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PersonDao extends CrudRepository<Person,Integer>{

    List<Person> findAll(Specification<Person> personSpecification);

    Page findAll(Pageable pageable);
}
