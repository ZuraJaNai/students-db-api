package no.itera.controller.rest;

import no.itera.model.Person;
import no.itera.model.PersonInputData;
import no.itera.model.PersonResponse;
import no.itera.services.PersonService;
import no.itera.util.CustomErrorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *  REST controller for Person class
 */
@RestController("PersonControllerRest")
@RequestMapping("/restapi")
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);
    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Value( "${userProperties.objectsPerPageLimit}" )
    private int limit;

    /**
     *  Method for getting all existing persons with pagination
     *  @param pageNum number of page,which you want to get (starts with 1)
     *  @param limit number of elements on one page
     *  @return ResponseEntity containing list of persons and httpStatus
     */
    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public ResponseEntity<PersonResponse> listAllPersonsPageable(
            @RequestParam(value = "page", required = false) Integer pageNum,
            @RequestParam(value = "limit", required = false) Integer limit){
        if(pageNum == null){
            pageNum = 1;
        }
        if(limit == null){
            limit = this.limit;
        }
        logger.debug("Getting list of persons.Page {}.Limit {}", pageNum, limit);
        Page page = personService.getAll(new PageRequest(pageNum - 1,limit));
        if(!page.hasContent()) {
            logger.error("Page number {} not found", pageNum);
            return new ResponseEntity(new CustomErrorType("Page number "
                    + pageNum + " not found"), HttpStatus.NOT_FOUND);
        }
        PersonResponse response = new PersonResponse(personService
                .transformPersonsToOutputFormat(page.getContent()),pageNum,
                page.getTotalPages(),personService.count());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Method for getting person specified by ID
     * @param id path variable containing person's ID
     * @return ResponseEntity with person(with specified ID) and httpStatus
     */
    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> getPersonById(@PathVariable("id") int id){
        logger.debug("Fetching person with id {}", id);
        Person person = personService.getById(id);
        if(person == null){
            logger.error("Person with id {} not found", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id +
                    " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    /**
     * Method for person creation
     * @param personInputData the person to be created
     * @param ucBuilder object for creating URI
     * @return ResposneEntity containing header with URL to created person and
     * httpStatus
     */

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public ResponseEntity<String> createPerson(@RequestBody PersonInputData personInputData,
                                          UriComponentsBuilder ucBuilder){
        logger.info("Creating person: {}", personInputData);
        Person person = new Person(personInputData);
        personService.addPerson(person);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/restapi/person/{id}")
                .buildAndExpand(person.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Method for updating existing person be specified ID
     * @param id  path variable containing person's ID
     * @param person Person containing new data
     * @return Person with new data and httpStatus
     */
    @RequestMapping(value = "/person/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Person> updatePerson(@PathVariable("id") int id,
                                               @RequestBody PersonInputData person) {
        logger.info("Updating person with id {}", id);
        Person currentPerson = personService.getById(id);
        if (currentPerson == null) {
            logger.error("Unable to update. Person with id {} not found", id);
            return new ResponseEntity(new CustomErrorType("Unable to update user with id "
                    + id), HttpStatus.NOT_FOUND);
        }
        personService.updatePerson(id, person);
        return new ResponseEntity<>(currentPerson, HttpStatus.OK);
    }

    /**
     * Method for deletion of existing person by specified ID
     * @param id path variable containing person's ID
     * @return ResponseEntity containing httpStatus
     */
    @RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Person> deletePerson(@PathVariable("id") int id){
        logger.info("Deleting person with id {}", id);
        Person person = personService.getById(id);
        if(person == null){
            logger.error("Unable to delete. Person with id {} not found", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Person with id "
                    + id + " not found"), HttpStatus.NOT_FOUND);
        }
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Method for deletion of all existing persons
     * @return ResponseEntity containing httpStatus
     */
    @RequestMapping(value = "/person", method = RequestMethod.DELETE)
    public ResponseEntity<Person> deleteAllPersons(){
        logger.info("Deleting all persons");
        personService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
