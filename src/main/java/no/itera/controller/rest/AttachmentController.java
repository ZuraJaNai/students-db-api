package no.itera.controller.rest;

import no.itera.model.Attachment;
import no.itera.model.Person;
import no.itera.services.AttachmentService;
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
import java.util.List;

/**
 *  REST controller for Attachment class
 */
@RestController("AttachmentControllerRest")
@RequestMapping("/restapi/person")
public class AttachmentController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private PersonService personService;

    /**
     * Method for uploading file for specified Person into database
     *
     * @param personId  id of Person to whom to add file
     * @param file the actual MultipartFile file to be uploaded
     * @return Response entity containing HttpStatus and message
     */
    @RequestMapping(value = "/{person_id}/attachments", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(@PathVariable("person_id") int personId,
                                             @RequestParam("file") MultipartFile file) throws IOException {
        logger.debug("Uploading file {}", file.getOriginalFilename());
        if(!personService.isPersonExists(new Person(personId))){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(file.isEmpty()){
            return new ResponseEntity<>("File is empty " + file.getOriginalFilename(),HttpStatus.OK);
        }
        if(attachmentService.isPersonHasFile(personId,file.getOriginalFilename())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        byte[] buffer = file.getBytes();
        if(attachmentService.addFile(personId,buffer,file.getOriginalFilename()
                ,file.getContentType())){
            return new ResponseEntity<>("File uploaded " + file.getOriginalFilename(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Method for deletion of uploaded file for defined Person
     *
     * @param personId  id of Person for whom to delete file
     * @param attachmentId  id of the file to delete
     * @return ResponseEntity containing HttpStatus
     */
    @RequestMapping(value = "/{person_id}/attachments/{attachment_id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFile(@PathVariable("person_id") int personId,
                                             @PathVariable("attachment_id") int attachmentId){
        if(!personService.isPersonExists(new Person(personId))){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(attachmentService.isPersonHasFile(personId,attachmentId)){
            attachmentService.deleteFile(attachmentId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Method to get list of filenames of defined Person
     *
     * @param personId  id of the Person
     * @return ResponseEntity containing list of names and HttpStatus
     */
    @RequestMapping(value = "/{person_id}/attachments", method = RequestMethod.GET)
    public ResponseEntity<List<Attachment>> getAllAttachments(@PathVariable("person_id") int personId){
        if(!personService.isPersonExists(new Person(personId))){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Attachment> attachments = attachmentService.getAttachments(personId);
        if(attachments == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(attachments.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(attachments,HttpStatus.OK);
    }

    /**
     * Method for downloading from the database the file which was uploaded there
     *
     * @param personId  id of Person whos file to download
     * @param attachmentId  id of file to download
     * @return ResponseEntity containing byte array with file, headers with
     * information about file and HttpStatus
     */
    @RequestMapping(value = "/{person_id}/attachments/{attachment_id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadFile(@PathVariable("person_id") int personId,
                                               @PathVariable("attachment_id") int attachmentId){
        if(!personService.isPersonExists(new Person(personId))){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Attachment attachment = attachmentService.getFile(personId,attachmentId);
        if (attachment == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        byte[] buffer = attachment.getFile().getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(attachment.getMimetype()));
        headers.set("Content-Disposition",String.format("form-data; filename=\"%s\"",
                attachment.getFilename()));
        return new ResponseEntity<>(buffer,headers,HttpStatus.OK);
    }

    /**
     * Method for adding photo to Person instance
     *
     * @param personId  id of Person for whom we upload photo
     * @param file  the actual photo MultipartFile
     * @return ResponseEntity containnig message and HttpStatus
     * @throws IOException if can't get bytes from the file
     */
    @RequestMapping(value = "/{person_id}/photo", method = RequestMethod.POST)
    public ResponseEntity<String> uploadPhoto(@PathVariable("person_id") int personId,
                                             @RequestParam("file") MultipartFile file) throws IOException {
        logger.debug("Uploading file {}", file.getOriginalFilename());
        if(!personService.isPersonExists(new Person(personId))){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(file.isEmpty()){
            return new ResponseEntity<>("File is empty " + file.getOriginalFilename(),HttpStatus.OK);
        }
        attachmentService.addPhoto(personId,file.getBytes(),file.getOriginalFilename(),file.getContentType());
        return new ResponseEntity<>("Photo uploaded " + file.getOriginalFilename(), HttpStatus.OK);

    }

    /**
     * Method for deleting Person photo
     *
     * @param personId  id of Person whos photo to delete
     * @return ResponseEntity with HttpStatus
     */
    @RequestMapping(value = "/{person_id}/photo", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePhoto(@PathVariable("person_id") int personId){
        if(!personService.isPersonExists(new Person(personId))){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        attachmentService.deletePhoto(personId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
