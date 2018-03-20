package no.itera.controller.view;

import no.itera.model.Person;
import no.itera.services.AttachmentService;
import no.itera.services.PersonService;
import no.itera.util.CustomExitEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("PersonControllerView")
@RequestMapping(value = "/views")
public class PersonController {

    private PersonService personService;
    private AttachmentService attachmentService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public void setAttachmentService(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }
    private static final Logger logger = LogManager.getLogger(no.itera.controller.rest.PersonController.class);

    @Value( "${userProperties.objectsPerPageLimit}" )
    private int limit;

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public String homepage(Model model,
                           @RequestParam(value = "page", required = false) Integer pageNum,
                           @RequestParam(value = "limit", required = false) Integer limit){
        if(pageNum == null){
            pageNum = 1;
        }
        if(limit == null){
            limit = this.limit;
        }
        model.addAttribute("searchPerson",new Person());
        logger.debug("Getting list of persons.Page {}.Limit {}", pageNum, limit);
        Page page = personService.getAll(new PageRequest(pageNum - 1,limit));
        if(!page.hasContent()) {
            logger.error("Page number {} not found", pageNum);
            return "homepage";
        }
        model.addAttribute("persons",page.getContent());
        model.addAttribute("result",false);
        if(pageNum > 1)
        {
            model.addAttribute("prevPage",pageNum - 1);
        }
        if(pageNum < page.getTotalPages()){
            model.addAttribute("nextPage",pageNum + 1);
        }
        return "homepage";
    }

    @RequestMapping(value = "/person/page/{pageNum}", method = RequestMethod.GET)
    public String getPage(@PathVariable("pageNum") String pageNum){
        if(StringUtils.equals(pageNum,"null")){
            return "redirect:/views/person";
        }
        return String.format("redirect:/views/person?page=%s",pageNum);
    }

    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    public String viewPersonById(@PathVariable("id") int id, Model model) {
        logger.debug("Fetching person with id {}", id);
        Person person = personService.getById(id);
        model.addAttribute("person", person);
        model.addAttribute("attachments", attachmentService.getAttachments(id));
        return "personView";
    }

    @RequestMapping(value = "/person/delete/{id}", method = RequestMethod.DELETE)
    public String deletePerson(@PathVariable("id") int id){
        logger.info("Deleting person with id {}", id);
        personService.deletePerson(id);
        return "redirect:/views/person";
    }

    @RequestMapping(value = "/person/edit/{id}", method = RequestMethod.GET)
    public String editPerson(@PathVariable("id") int id,Model model) {
        logger.info("Updating person with id {}", id);
        model.addAttribute("person",personService.getById(id));
        return "editPerson";
    }


    @RequestMapping(value = "/person/add", method = RequestMethod.GET)
    public String addPerson(Model model){
        model.addAttribute("newPerson",new Person());
        return "addPerson";
    }

    @RequestMapping(value = "/person/add", method = RequestMethod.POST)
    public String addPerson(@ModelAttribute(value = "newPerson") Person person){
        personService.addPerson(person);
        return String.format("redirect:/views/person/%d",person.getId());
    }

    @RequestMapping(value = "/person/edit/{id}", method = RequestMethod.POST)
    public String editPerson(@PathVariable("id") int id,@ModelAttribute(value = "person") Person person){
        personService.updatePerson(id,person);
        return String.format("redirect:/views/person/%d",id);
    }

    @RequestMapping(value = "/print", method = RequestMethod.GET)
    public String printAll(Model model){
        model.addAttribute("persons",personService.getAll());
        return "printPersons";
    }

    @RequestMapping(value = "/exit",method = RequestMethod.POST)
    public void exit(){
        CustomExitEvent customSpringEvent = new CustomExitEvent(this);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }


}
