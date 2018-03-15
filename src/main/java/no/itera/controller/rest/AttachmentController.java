package no.itera.controller.rest;

import no.itera.model.Attachment;
import no.itera.model.Person;
import no.itera.services.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController("AttachmentControllerRest")
@RequestMapping("/restapi/person")
public class AttachmentController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);
    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/{id}/uploadfile", method = RequestMethod.POST)
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

    @RequestMapping(value = "/{id}/files", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getAllFiles(@PathVariable("id") int personId){
        List<Attachment> attachments = personService.getAttachments(personId);
        if(attachments == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(attachments.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ArrayList<String> fileNames= new ArrayList<>();
        for (Attachment attachment:attachments) {
            fileNames.add(attachment.getFilename());
        }
        return new ResponseEntity<>(fileNames,HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/downloadfile", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") int personId,
                                               @RequestParam("filename") String filename){
        Attachment attachment = personService.getFile(personId,filename);
        if (attachment == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        byte[] buffer = attachment.getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(attachment.getType()));
        headers.set("Content-Disposition",String.format("form-data; filename=\"%s\"",filename));
        return new ResponseEntity<>(buffer,headers,HttpStatus.OK);
    }

}
