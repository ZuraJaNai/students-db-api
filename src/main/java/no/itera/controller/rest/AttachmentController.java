package no.itera.controller.rest;

import no.itera.model.Attachment;
import no.itera.model.Person;
import no.itera.services.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController("AttachmentControllerRest")
@RequestMapping("/restapi/person/{id}")
public class AttachmentController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);
    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(@PathVariable("id") int personId,
                                             @RequestParam("file") MultipartFile file) throws IOException {
        if(!personService.isPersonExists(new Person(personId))){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(file.isEmpty()){
            return new ResponseEntity<>("File is empty " + file.getOriginalFilename(),HttpStatus.OK);
        }
        if(personService.getFile(personId,file.getOriginalFilename()) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        byte[] buffer = file.getBytes();
        personService.addFile(personId,buffer,file.getOriginalFilename(),file.getContentType());
        return new ResponseEntity<>("File uploaded " + file.getOriginalFilename(), HttpStatus.OK);
    }

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public ResponseEntity<List<Attachment>> getAllFiles(@PathVariable("id") int personId){
        List<Attachment> attachments = personService.getAttachments(personId);
        if(attachments == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(attachments.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(attachments,HttpStatus.OK);
    }

    @RequestMapping(value = "/downloadfile", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") int personId,
                                                 @RequestBody Attachment filenameAttachment){
        Attachment attachment = personService.getFile(personId,filenameAttachment.getFilename());
        if (attachment == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        byte[] buffer = attachment.getContent();
        ByteArrayResource resource = new ByteArrayResource(buffer);
        String name = "files";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", attachment.getType());
        headers.set("Content-Disposition",String.format("form-data; name=\"%s\"; filename=\"%s\"",
                name,attachment.getFilename()));
        return new ResponseEntity<>(resource,headers,HttpStatus.OK);
    }
}
