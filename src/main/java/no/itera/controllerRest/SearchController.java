package no.itera.controllerRest;

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


    //Search
    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public ResponseEntity<Iterable<Person>> findAllPersons(@RequestBody Person person){
        Iterable<Person> persons = personService.findAllPersons(person);
        if (persons.spliterator().getExactSizeIfKnown() < 1){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(persons, HttpStatus.FOUND);
    }
}
