package no.itera.controllerRest;

import no.itera.model.Person;
import no.itera.services.PersonService;
import no.itera.util.CustomErrorType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController("PersonControllerRest")
@RequestMapping("/restapi")
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);
    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    //Get all persons
    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Person>> listAllPeople(){
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
                                          UriComponentsBuilder ucBuilder){
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

    //Search
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<Iterable<Person>> findAllPersons(@RequestBody Person person){
        Iterable<Person> persons = personService.findAllPersons(person);
        return new ResponseEntity<>(persons,HttpStatus.FOUND);
    }


}
