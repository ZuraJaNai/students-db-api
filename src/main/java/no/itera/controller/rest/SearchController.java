package no.itera.controller.rest;

import no.itera.model.Person;
import no.itera.services.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("SearchControllerRest")
@RequestMapping("/restapi/search")
public class SearchController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);
    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }


    /**
     * Method for searching persons by different parameters(lastname,internship,
     * course,practice, etc.)
     * @param person Person object containing fields and values to be searched by
     * @return ResponseEntity containing list of found persons and httpStatus
     */
    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public ResponseEntity<Iterable<Person>> findAllPersons(@RequestBody Person person){
        logger.debug("Searching for persons with parameters {}", person);
        Iterable<Person> persons = personService.findAllPersons(person);
        if (persons.spliterator().getExactSizeIfKnown() < 1){
            logger.error("Nothing found by this parameter(s): {}", person);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(persons, HttpStatus.FOUND);
    }
}
