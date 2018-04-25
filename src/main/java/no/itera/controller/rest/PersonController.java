package no.itera.controller.rest;

import no.itera.model.Person;
import no.itera.model.PersonData;
import no.itera.model.PersonResponse;
import no.itera.services.PersonService;
import no.itera.util.CustomErrorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     *
     *  @param pageNum  number of page,which you want to get (starts with 1)
     *  @param limit  number of elements on one page
     *  @return ResponseEntity containing httpStatus and PersonResponse object
     *  with list of persons,number of persons, current page and total number of pages
     */
    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public ResponseEntity<PersonResponse> listAllPersonsPageable(
            @RequestParam(value = "page", required = false) Integer pageNum,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "pagination", required = false, defaultValue = "true") Boolean pagination){
        if(!pagination){
            List<PersonData> persons = personService
                    .transformPersonsToOutputFormat(personService.getAll());
            if(persons.isEmpty()) {
                return new ResponseEntity<>( new PersonResponse(new ArrayList<PersonData>(),0,
                        0,personService.count()),HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(new PersonResponse(persons,0,
                        0,personService.count()),HttpStatus.OK);
            }
        }
        if(pageNum == null){
            pageNum = 1;
        }
        if(limit == null){
            limit = this.limit;
        }
        logger.debug("Getting list of persons.Page {}.Limit {}", pageNum, limit);
        Page page = personService.getAll(new PageRequest(pageNum - 1,limit));
        if(!page.hasContent()) {
            return new ResponseEntity<>( new PersonResponse(new ArrayList<PersonData>(),1,
                    1,personService.count()),HttpStatus.OK);
        }
        PersonResponse response = new PersonResponse(personService
                .transformPersonsToOutputFormat(page.getContent()),pageNum,
                page.getTotalPages(),personService.count());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Method for getting person specified by ID
     *
     * @param id  path variable containing person's ID
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
     *
     * @param personData  data for the person to be created
     * @param ucBuilder  object for creating URI
     * @return ResponseEntity containing header with URL to created person and
     * httpStatus
     */

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public ResponseEntity<String> createPerson(@RequestBody PersonData personData,
                                          UriComponentsBuilder ucBuilder){
        logger.info("Creating person: {}", personData);
        Person person = new Person(personData);
        personService.addPerson(person);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/restapi/person/{id}")
                .buildAndExpand(person.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/person/import", method = RequestMethod.POST)
    public ResponseEntity<String> importPersonsFromExcel(@RequestParam("file") MultipartFile file){
        logger.debug("Importing from excel file {}", file.getOriginalFilename());
        if(file.isEmpty()){
            return new ResponseEntity<>("File is empty " + file.getOriginalFilename(),HttpStatus.OK);
        }
        File excelFile = null;
        boolean isFileCreated = false;
        try {
            excelFile = new File(file.getOriginalFilename());
            isFileCreated = excelFile.createNewFile();
        } catch (IOException e) {
            logger.error("IOException " + e.getMessage());
        }
        finally {
            if(!isFileCreated){
                return new ResponseEntity<>(file.getOriginalFilename()+" not imported",
                        HttpStatus.NOT_ACCEPTABLE);
            }
        }


        try(FileOutputStream fileOutputStream = new FileOutputStream(excelFile)) {
            fileOutputStream.write(file.getBytes());
            personService.importFromExcel(excelFile);
        } catch (IOException | InvalidFormatException e) {
            logger.error("Exception " + e.getMessage());
        }
        return new ResponseEntity<>(file.getOriginalFilename()+" imported",HttpStatus.OK);
    }

    /**
     * Method for updating existing person be specified ID
     *
     * @param id  path variable containing person's ID
     * @param personData  PersonData object containing new data for Person
     * @return Person with new data and httpStatus
     */
    @RequestMapping(value = "/person/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Person> updatePerson(@PathVariable("id") int id,
                                               @RequestBody PersonData personData) {
        logger.info("Updating person with id {}", id);
        Person currentPerson = personService.getById(id);
        if (currentPerson == null) {
            logger.error("Unable to update. Person with id {} not found", id);
            return new ResponseEntity(new CustomErrorType("Unable to update user with id "
                    + id), HttpStatus.NOT_FOUND);
        }
        personService.updatePerson(id, personData);
        return new ResponseEntity<>(currentPerson, HttpStatus.OK);
    }

    /**
     * Method for deletion of existing person by specified ID
     *
     * @param id  path variable containing person's ID
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
     *
     * @return ResponseEntity containing httpStatus
     */
    @RequestMapping(value = "/person", method = RequestMethod.DELETE)
    public ResponseEntity<Person> deleteAllPersons(){
        logger.info("Deleting all persons");
        personService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
