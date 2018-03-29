package no.itera.controller.view;

import no.itera.controller.rest.PersonController;
import no.itera.model.Attachment;
import no.itera.services.AttachmentService;
import no.itera.services.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@ApiIgnore
@Controller("AttachmentControllerView")
@RequestMapping(value = "/views/person")
public class AttachmentController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    String redirectToPersonView = "redirect:/views/person/%d";

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/{id}/uploadfile", method = RequestMethod.POST)
    public String uploadFile(@PathVariable("id") int personId,
                                             @RequestParam("file") MultipartFile file) throws IOException {
        logger.debug("Uploading file {}", file.getOriginalFilename());
        if(attachmentService.isPersonHasFile(personId,file.getOriginalFilename())){
            return String.format(redirectToPersonView,personId);
        }
        byte[] buffer = file.getBytes();
        attachmentService.addFile(personId,buffer,file.getOriginalFilename(),file.getContentType());
        return String.format(redirectToPersonView,personId);
    }

    @RequestMapping(value = "/{id}/deletefile/{attachmentId}", method = RequestMethod.DELETE)
    public String deleteFile(@PathVariable("id") int personId,
                             @PathVariable("attachmentId") int attachmentId){
        attachmentService.isPersonHasFile(personId,attachmentId);
        attachmentService.deleteFile(attachmentId);
        return String.format(redirectToPersonView,personId);
    }

    @RequestMapping(value = "/{id}/downloadfile/{attachmentId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") int personId,
                                               @PathVariable("attachmentId") int attachmentId){
        Attachment attachment = attachmentService.getFile(personId,attachmentId);
        byte[] buffer = attachment.getFile().getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(attachment.getMimetype()));
        headers.set("Content-Disposition",String.format("form-data; filename=\"%s\"",
                attachment.getFilename()));
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(buffer, headers, HttpStatus.OK);
    }
}
