package no.itera.controller.rest;

import no.itera.model.Person;
import no.itera.model.SearchPerson;
import no.itera.services.PersonService;
import no.itera.util.CustomErrorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("SearchControllerRest")
@RequestMapping("/restapi/search")
public class SearchController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);
    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Value( "${userProperties.objectsPerPageLimit}" )
    private int limit;

    /**
     * Method for searching persons by different parameters(lastname,internship,
     * course,practice, etc.)
     * @param person Person object containing fields and values to be searched by
     * @return ResponseEntity containing list of found persons and httpStatus
     */
    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public ResponseEntity<PagedListHolder> findAllPersons(@RequestBody SearchPerson person,
                                                           @RequestParam(value = "page", required = false) Integer pageNum,
                                                           @RequestParam(value = "limit", required = false) Integer limit){
        if(pageNum == null){
            pageNum = 1;
        }
        if(limit == null){
            limit = this.limit;
        }
        logger.debug("Searching for persons with parameters {}", person);
        List<Person> persons = personService.findAllPersons(person);
        long totalPersons = persons.size();
        PagedListHolder page = new PagedListHolder(persons);
        page.setPageSize(limit);
        page.setPage(pageNum);
        if(page.getPageList().isEmpty()) {
            logger.error("Page number {} not found", pageNum);
            return new ResponseEntity(new CustomErrorType("Page number " + pageNum +
                    " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(page, HttpStatus.FOUND);
    }
}
