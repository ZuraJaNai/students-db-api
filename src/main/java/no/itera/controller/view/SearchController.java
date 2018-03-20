package no.itera.controller.view;

import no.itera.controller.rest.PersonController;
import no.itera.model.Person;
import no.itera.services.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("SearchControllerView")
@RequestMapping(value = "/views")
public class SearchController {
    private static final Logger logger = LogManager.getLogger(PersonController.class);
    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String findAllPersons(Model model,
                                 @ModelAttribute(value = "searchPerson") Person person){
        logger.debug("Searching for persons with parameters {}", person);
        Iterable<Person> persons = personService.findAllPersons(person);
        if (persons.spliterator().getExactSizeIfKnown() < 1){
            logger.error("Nothing found by this parameter(s): {}", person);
            return "redirect:/views/person";
        }
        model.addAttribute("persons", persons);
        model.addAttribute("result",true);
        return "homepage";
    }

    @RequestMapping(value = "/search/print", method = RequestMethod.GET)
    public String printAll(Model model,
                           @ModelAttribute(value = "searchPerson") Person person){
        model.addAttribute("persons",personService.findAllPersons(person));
        return "printPersons";
    }
}
