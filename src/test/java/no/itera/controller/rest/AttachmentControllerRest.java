package no.itera.controller.rest;

import no.itera.model.Attachment;
import no.itera.model.Person;
import no.itera.model.Type;
import no.itera.services.AttachmentService;
import no.itera.services.PersonServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class AttachmentControllerRest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonServiceImpl personService;

    @MockBean
    private AttachmentService attachmentService;

    @Test
    public void addNewAttachmentToPerson() throws Exception {
        Person person = new Person(1);
        MockMultipartFile file = new MockMultipartFile("file","fileName",
                "application/pdf",new byte[]{0});
        when(personService.isPersonExists(person)).thenReturn(true);
        when(attachmentService.isPersonHasFile(person.getId(),file.getOriginalFilename()))
                .thenReturn(false);
        when(attachmentService.addFile(person.getId(),file.getBytes(),
                file.getOriginalFilename(),file.getContentType())).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/restapi/person/1/uploadfile").accept(MediaType.MULTIPART_FORM_DATA)
                .contentType(MediaType.MULTIPART_FORM_DATA).requestAttr("file",file);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }
}
