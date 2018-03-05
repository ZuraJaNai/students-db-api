package no.itera.controllerRest;

import no.itera.model.Person;
import no.itera.services.PersonService;
import no.itera.util.CustomErrorType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import com.google.common.base.Preconditions;

@RestController("PersonControllerRest")
@RequestMapping("/restapi")
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);
    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    //get all with pagination
    @RequestMapping(value = "/person",params = {"page","limit"},method = RequestMethod.GET)
    public ResponseEntity<Iterable<Person>> listAllPersons(@RequestParam("page") int pageNum,@RequestParam("limit") int limit){
        Page page = personService.getAll(new PageRequest(pageNum - 1,limit));
        if(!page.hasContent()) {
            return new ResponseEntity(new CustomErrorType("Page number " + pageNum +
                    " not found"), HttpStatus.NOT_FOUND);
        }
        //HttpHeaders headers = createHeadersForPagination(page, pageNum, UriComponentsBuilder.newInstance());
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
        //add headers
    }

//    private HttpHeaders createHeadersForPagination(Page page,int currentPageNum, UriComponentsBuilder ucBuilder){
//        HttpHeaders headers = new HttpHeaders();
//        int pageNum;
//        pageNum = page.getTotalPages() - 1;
//        String lastPageUri = ucBuilder.path("/restapi/person/page/{number}")
//                .buildAndExpand(pageNum).toUri().toString();
//        headers.add("last", lastPageUri);
//        pageNum = 0;
//        String firstPageUri = ucBuilder.path("/restapi/person/page/{number}")
//                .buildAndExpand(pageNum).toUri().toString();
//        headers.add("first",firstPageUri);
//        if(page.hasNext()){
//            pageNum = currentPageNum + 1;
//            String nextPageUri = ucBuilder.path("/restapi/person/page/{number}")
//                    .buildAndExpand(pageNum).toUri().toString();
//            headers.add("next",nextPageUri);
//        }
//        if(page.hasPrevious()){
//            pageNum = currentPageNum - 1;
//            String prevPageUri = ucBuilder.path("/restapi/person/page/{number}")
//                    .buildAndExpand(pageNum).toUri().toString();
//            headers.add("prev",prevPageUri);
//        }
//        return headers;
//    }

    //get all
    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Person>> listAllPersons(){
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
}
