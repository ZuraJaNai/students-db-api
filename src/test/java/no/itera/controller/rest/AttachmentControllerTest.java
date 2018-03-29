package no.itera.controller.rest;

import no.itera.model.Attachment;
import no.itera.model.Person;
import no.itera.model.Type;
import no.itera.services.AttachmentServiceImpl;
import no.itera.services.PersonServiceImpl;
import org.codehaus.groovy.runtime.powerassert.AssertionRenderer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AttachmentController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class AttachmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonServiceImpl personService;

    @MockBean
    private AttachmentServiceImpl attachmentService;


    @Test
    public void addNewAttachmentToPerson() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file","fileName",
                "application/pdf",new byte[]{0});

        when(personService.isPersonExists(any(Person.class))).thenReturn(true);
        doReturn(false).when(attachmentService).isPersonHasFile(1,file.getOriginalFilename());
        doReturn(true).when(attachmentService).addFile(1,file.getBytes(),
                file.getOriginalFilename(),file.getContentType());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.fileUpload(
                "/restapi/person/1/attachments").file(file);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void addNewAttachmentToNonExistingPerson() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file","fileName",
                "application/pdf",new byte[]{0});

        when(personService.isPersonExists(any(Person.class))).thenReturn(false);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.fileUpload(
                "/restapi/person/1/attachments").file(file);
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void addSameAttachmentToPerson() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file","fileName",
                "application/pdf",new byte[]{0});

        when(personService.isPersonExists(any(Person.class))).thenReturn(true);
        doReturn(true).when(attachmentService).isPersonHasFile(1,file.getOriginalFilename());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.fileUpload(
                "/restapi/person/1/attachments").file(file);
        mockMvc.perform(requestBuilder).andExpect(status().isConflict());
    }

    @Test
    public void deleteAttachmentFromPerson() throws Exception {
        when(personService.isPersonExists(any(Person.class))).thenReturn(true);
        doReturn(true).when(attachmentService).isPersonHasFile(1,1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                "/restapi/person/1/attachments/1");
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void deleteAttachmentFromNotExistingPerson() throws Exception {
        when(personService.isPersonExists(any(Person.class))).thenReturn(false);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                "/restapi/person/1/attachments/1");
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void deleteNonExistingAttachmentFromPerson() throws Exception {
        when(personService.isPersonExists(any(Person.class))).thenReturn(true);
        doReturn(false).when(attachmentService).isPersonHasFile(1,1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                "/restapi/person/1/attachments/1");
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void getAllPersonsAttachments() throws Exception {
        String expected = "[{\"id\":0,\"filename\":\"fileName\",\"mimetype\":\"application/pdf\",\"personId\":1,\"type\":\"DOCUMENT\"}]";
        when(personService.isPersonExists(any(Person.class))).thenReturn(true);
        when(attachmentService.getAttachments(1)).thenReturn(Arrays.asList(new Attachment(
                new byte[]{0},"fileName","application/pdf",1, Type.DOCUMENT)));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/restapi/person/1/attachments").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(expected,result.getResponse().getContentAsString(),false);
    }

    @Test
    public void getAllPersonAttachmentsIfNotExists() throws Exception {
        String expected = "[]";
        when(personService.isPersonExists(any(Person.class))).thenReturn(true);
        when(attachmentService.getAttachments(1)).thenReturn(Arrays.asList());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/restapi/person/1/attachments").accept(MediaType.APPLICATION_JSON);
         mockMvc.perform(requestBuilder).andExpect(status().isNoContent());
    }

    @Test
    public void downloadPersonAttachment() throws Exception {
        when(personService.isPersonExists(any(Person.class))).thenReturn(true);
        when(attachmentService.getFile(1,1)).thenReturn(new Attachment(
                new byte[]{0},"fileName","application/pdf",1,Type.DOCUMENT));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/restapi/person/1/attachments/1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        Assert.assertEquals("application/pdf",result.getResponse().getContentType());
    }

    @Test
    public void uploadPersonPhoto() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file","fileName",
                "application/pdf",new byte[]{0});
        when(personService.isPersonExists(any(Person.class))).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.fileUpload(
                "/restapi/person/1/photo").file(file);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void deletePersonPhoto() throws Exception {
        when(personService.isPersonExists(any(Person.class))).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                "/restapi/person/1/photo").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }
}
