package no.itera.controller.rest;

import no.itera.model.Person;
import no.itera.services.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("PhotoControllerRest")
@RequestMapping("/restapi/person")
public class PhotoController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/{id}/uploadphoto", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(@PathVariable("id") int personId,
                                             @RequestParam("file") MultipartFile file) throws IOException {
        logger.debug("Uploading file {}", file.getOriginalFilename());
        if(!personService.isPersonExists(new Person(personId))){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(file.isEmpty()){
            return new ResponseEntity<>("File is empty " + file.getOriginalFilename(),HttpStatus.OK);
        }
        personService.addPhoto(personId,file.getBytes());
        return new ResponseEntity<>("Photo uploaded " + file.getOriginalFilename(), HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}/deletephoto", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFile(@PathVariable("id") int personId){
        if(!personService.isPersonExists(new Person(personId))){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        personService.deletePhoto(personId);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
