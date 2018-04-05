package no.itera.controller.rest;

import no.itera.model.PersonData;
import no.itera.model.PersonResponse;
import no.itera.model.PersonSearch;
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
@RequestMapping("/restapi/person")
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
     * @param person PersonSearch object containing fields and values to be searched by
     * @return ResponseEntity containing httpStatus and PersonResponse object
     *  with list of found persons,number of persons, current page and total number of pages
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<PersonResponse> findAllPersons(@RequestBody PersonSearch person,
                                                         @RequestParam(value = "page", required = false) Integer pageNum,
                                                         @RequestParam(value = "limit", required = false) Integer limit,
                                                         @RequestParam(value = "print", required = false, defaultValue = "false") Boolean print){
        if(print){
            List<PersonData> persons = personService
                    .transformPersonsToOutputFormat(personService.findAllPersons(person));
            if(persons.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else{
                return new ResponseEntity<>(new PersonResponse(persons,0,
                        0,persons.size()),HttpStatus.OK);
            }
        }
        if(pageNum == null){
            pageNum = 1;
        }
        if(limit == null){
            limit = this.limit;
        }
        logger.debug("Searching for persons with parameters {}", person);
        List<PersonData> persons = personService
                .transformPersonsToOutputFormat(personService.findAllPersons(person));
        PagedListHolder page = new PagedListHolder(persons);
        page.setPageSize(limit);
        page.setPage(pageNum);
        if(page.getPageList().isEmpty()) {
            logger.error("Page number {} not found", pageNum);
            return new ResponseEntity(new CustomErrorType("Page number "
                    + pageNum + " not found"), HttpStatus.NOT_FOUND);
        }
        PersonResponse response = new PersonResponse(page.getPageList(),pageNum,
                page.getPageCount(),persons.size());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
