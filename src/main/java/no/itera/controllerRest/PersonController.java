package no.itera.controllerRest;

import no.itera.model.Person;
import no.itera.services.PersonService;
import no.itera.util.CustomErrorType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.SQLException;

@RestController("PersonControllerRest")
@RequestMapping("/restapi")
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);
    private PersonService personService;
    //ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    //Get all persons
    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Person>> listAllPeople() throws SQLException {
        Iterable<Person> persons = personService.getAll();
        if (persons.spliterator().getExactSizeIfKnown() < 1){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    //Get one person by id
    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPersonById(@PathVariable("id") int id){
        logger.debug("Fetching person with id {}", id);
        Person person = personService.getById(id);
        if(person == null){
            logger.error("Person with id {} not found", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id +
                    " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    //Create person
    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public ResponseEntity<?> createPerson(@RequestBody Person person,
                                          UriComponentsBuilder ucBuilder) throws SQLException {
        logger.info("Creating person: {}", person);
        if(personService.isPersonExists(person)){
            logger.error("Unable to create. Person with id {} already exists",
                    person.getId());
            return new ResponseEntity(new CustomErrorType("Unable to create. Person with id "
                    + person.getId() + " already exists"), HttpStatus.CONFLICT);
        }
        personService.addPerson(person);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/restapi/person/{id}").buildAndExpand(person.getId()).toUri());
//        headers.setLocation(ucBuilder.path("/restapi/person/").buildAndExpand(person.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //Update person by id
    @RequestMapping(value = "/person/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePerson(@PathVariable("id") int id, @RequestBody Person person) {
        logger.info("Updating person with id {}", id);
        Person currentPerson = personService.getById(id);
        if (currentPerson == null) {
            logger.error("Unable to update. Person with id {} not found", id);
            return new ResponseEntity(new CustomErrorType("Unable to update user with id "
                    + id), HttpStatus.NOT_FOUND);
        }
        currentPerson.setLastName(person.getLastName());
        currentPerson.setFirstName(person.getFirstName());
        currentPerson.setPatronymic(person.getPatronymic());
        currentPerson.setEmail(person.getEmail());
        currentPerson.setYearOfStudy(person.getYearOfStudy());
        currentPerson.setInternship(person.getInternship());
        currentPerson.setPractice(person.getPractice());
        currentPerson.setComment(person.getComment());
        personService.updatePerson(currentPerson);
        return new ResponseEntity<Person>(currentPerson, HttpStatus.OK);
    }

    //Delete person by id
    @RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePerson(@PathVariable("id") int id){
        logger.info("Deleting person with id {}", id);
        Person person = personService.getById(id);
        if(person == null){
            logger.error("Unable to delete. Person with id {} not found", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Person with id "
                    + id + " not found"), HttpStatus.NOT_FOUND);
        }
        personService.deletePerson(id);
        return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
    }

    //Delete all persons
    @RequestMapping(value = "/person", method = RequestMethod.DELETE)
    public ResponseEntity<Person> deleteAllPersons(){
        logger.info("Deleting all persons");
        personService.deleteAll();
        return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
    }

    //    !!!!!
//    search for course AND practice, lastName and course etc. ?????????
//    !!!!!
//    for dao
//    @Query("SELECT a FROM Article a WHERE a.title=:title and a.category=:category")
//    List<Article> fetchArticles(@Param("title") String title, @Param("category") String category);

    //Get all persons by lastName
    @RequestMapping(value = "/search",params = {"lastName","yearOfStudy","internship","practice"},method = RequestMethod.GET)
    public ResponseEntity<?> getAllByLastName(@RequestParam(value = "lastName",required = false) String lastName,
                                              @RequestParam(value = "yearOfStudy",required = false) int yearOfStudy,
                                              @RequestParam(value = "internship",required = false) String internship,
                                              @RequestParam(value = "practice",required = false) String practice){
        logger.debug("Fetching person with lastName {}", lastName);
        Iterable<Person> persons = personService.getAllByLastName(lastName);
        if(persons.spliterator().getExactSizeIfKnown() < 1){
            logger.error("Person with lastName {} not found", lastName);
            return new ResponseEntity(new CustomErrorType("User with lastName "
                    + lastName + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

//    //Get all persons by lastName
//    @RequestMapping(value = "/search", method = RequestMethod.GET)
//    public ResponseEntity<?> getAllByLastName(@PathVariable("lastName") String lastName){
//        logger.debug("Fetching person with lastName {}", lastName);
//        Iterable<Person> persons = personService.getAllByLastName(lastName);
//        if(persons.spliterator().getExactSizeIfKnown() < 1){
//            logger.error("Person with lastName {} not found", lastName);
//            return new ResponseEntity(new CustomErrorType("User with lastName "
//                    + lastName + " not found"), HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(persons, HttpStatus.OK);
//    }
//
//    //Get all persons by yearOfStudy
//    @RequestMapping(value = "/search/?yearOfStudy={yearOfStudy}", method = RequestMethod.GET)
//    public ResponseEntity<?> getAllByYearOfStudy(@PathVariable("yearOfStudy") int yearOfStudy){
//        logger.debug("Fetching person with yearOfStudy {}", yearOfStudy);
//        Iterable<Person> persons = personService.getAllByYearOfStudy(yearOfStudy);
//        if(persons.spliterator().getExactSizeIfKnown() < 1){
//            logger.error("Person with yearOfStudy {} not found", yearOfStudy);
//            return new ResponseEntity(new CustomErrorType("User with yearOfStudy "
//                    + yearOfStudy + " not found"), HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(persons, HttpStatus.OK);
//    }
//
//    //Get all persons by internship
//    @RequestMapping(value = "/search/?internship={internship}", method = RequestMethod.GET)
//    public ResponseEntity<?> getAllByInternship(@PathVariable("internship") String internship){
//        logger.debug("Fetching person with internship {}", internship);
//        Iterable<Person> persons = personService.getAllByInternship(internship);
//        if(persons.spliterator().getExactSizeIfKnown() < 1){
//            logger.error("Person with internship {} not found", internship);
//            return new ResponseEntity(new CustomErrorType("User with internship "
//                    + internship + " not found"), HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(persons, HttpStatus.OK);
//    }
//
//    //Get all persons by practice
//    @RequestMapping(value = "/search/?practice={practice}", method = RequestMethod.GET)
//    public ResponseEntity<?> getAllByPractice(@PathVariable("practice") String practice){
//        logger.debug("Fetching person with practice {}", practice);
//        Iterable<Person> persons = personService.getAllByPractice(practice);
//        if(persons.spliterator().getExactSizeIfKnown() < 1){
//            logger.error("Person with practice {} not found", practice);
//            return new ResponseEntity(new CustomErrorType("User with practice "
//                    + practice + " not found"), HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(persons, HttpStatus.OK);
//    }

}
