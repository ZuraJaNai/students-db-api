package no.itera.dao;

import no.itera.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PersonDao extends CrudRepository<Person,Integer>{

    List<Person> findAll(Specification<Person> personSpecification,Sort sort);

    Page findAllByOrderByIdAsc(Pageable pageable);

    Iterable<Person> findAllByOrderByIdAsc();

    Integer countAllByYearOfStudy(String yearOfStudy);

    @Query("SELECT DISTINCT p.yearOfStudy FROM Person p")
    List<String> findDistinctYearsOfStudy();

    Integer countAllByPracticeBeginIsNotNullAndPracticeEndIsNull();

    Integer countAllByInternshipBeginIsNotNullAndInternshipEndIsNull();

    Integer countAllByJobBeginIsNotNullAndJobEndIsNull();

    Integer countAllByInternshipBeginIsNotNull();

    Integer countAllByPracticeBeginIsNotNull();

    Integer countAllByJobBeginIsNotNull();
}
