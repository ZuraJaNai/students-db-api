package no.itera.controllerView;

import no.itera.model.Person;
import no.itera.services.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("PersonControllerView")
@RequestMapping(value = "/views")
public class PersonController {

    private Logger logger = LogManager.getLogger(PersonController.class);
    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/")
    public String homepage(Model model) {
        model.addAttribute("persons", personService.getPersonsList());
        return "homepage";
    }

    @RequestMapping(value = "/person/{id}")
    public String showPerson(@PathVariable int id, Model model) {
        model.addAttribute("person", personService.getPersonById(id));
        return "personInfoView";
    }

    @RequestMapping(value = "/person/new", method = RequestMethod.GET)
    public String addPerson(Model model) {
        model.addAttribute("person", new Person());
        return "addPersonView";
    }
//
//    @RequestMapping(value = "/person", method = RequestMethod.POST)
//    public String savePerson(Person person) {
//        logger.debug("Created person: {}", person.getInfo());
//        this.personService.addPerson(person);
//        return "personInfoView";
//    }
//
//    @RequestMapping(value = "/person/delete/{id}")
//    public String deletePerson(@PathVariable int id, Model model){
//        model.addAttribute("person", this.personService.getPersonById(id));
//        logger.debug("Deleted person: {}", personService.getPersonById(id).getInfo());
//        this.personService.deletePerson(id);
//        return "deletedPerson";
//    }

}
