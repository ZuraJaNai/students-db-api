package no.itera.controller.view;

import no.itera.model.Person;
import no.itera.services.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller("PersonControllerView")
@RequestMapping(value = "/views")
public class PersonController {

    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/")
    public String homepage(Model model){
        model.addAttribute("persons", personService.getAll());
        return "homepage";
    }

    @RequestMapping(value = "/person/{id}")
    public String showPerson(@PathVariable int id, Model model) {
        model.addAttribute("person", personService.getById(id));
        return "personInfoView";
    }

    @RequestMapping(value = "/person/new", method = RequestMethod.GET)
    public String addPerson(Model model) {
        model.addAttribute("person", new Person());
        return "addPersonView";
    }

}
