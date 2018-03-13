package no.itera.controller.rest;

import no.itera.controller.rest.PersonController;
import no.itera.model.Person;
import no.itera.services.PersonServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonServiceImpl personService;


    @Test
    public void checkGetExistingPeronById() throws Exception {
        String expected = "{\"id\":1,\"lastName\":\"default\",\"firstName\":" +
                "\"default\",\"patronymic\":\"default\",\"email\":\"default" +
                "\",\"yearOfStudy\":\"default\",\"internship\":\"default\",\"practice\":" +
                "\"default\",\"comment\":\"default\"}";
        Person person = new Person(1);
        when(personService.getById(1)).thenReturn(person);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/restapi/person/1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(expected,result.getResponse().getContentAsString(),false);
    }

    @Test
    public void checkGetNonExistingPeronById() throws Exception {
        when(personService.getById(1)).thenReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/restapi/person/1").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void checkPersonCreation() throws Exception{
        String contentJson =  "{\"id\":1,\"lastName\":\"default\",\"firstName\":" +
                "\"default\",\"patronymic\":\"default\",\"email\":\"default" +
                "\",\"yearOfStudy\":\"default\",\"internship\":\"default\",\"practice\":" +
                "\"default\",\"comment\":\"default\"}";
        Person person = new Person(1);
        Mockito.when(personService.addPerson(person)).thenReturn(true);
        Mockito.when(personService.isPersonExists(person)).thenReturn(false);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/restapi/person").accept(MediaType.APPLICATION_JSON)
                .content(contentJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        Assert.assertEquals("http://localhost/restapi/person/1",
                result.getResponse().getHeader(HttpHeaders.LOCATION));
    }

    @Test
    public void checkPersonCreationIfPersonExists() throws Exception{
        String contentJson = "{\"id\":1,\"lastName\":\"default\",\"firstName\":" +
                "\"default\",\"patronymic\":\"default\",\"email\":\"default" +
                "\",\"yearOfStudy\":\"default\",\"internship\":\"default\",\"practice\":" +
                "\"default\",\"comment\":\"default\"}";
        Mockito.when(personService.isPersonExists(any(Person.class))).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/restapi/person").accept(MediaType.APPLICATION_JSON)
                .content(contentJson).contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isConflict());
    }

    @Test
    public void checkPersonDeletion() throws Exception {
        Mockito.when(personService.getById(1)).thenReturn(new Person(1));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                "/restapi/person/1").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isNoContent());
    }

    @Test
    public void checkPersonDeletionIfNotExists() throws Exception{
        Mockito.when(personService.getById(1)).thenReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                "/restapi/person/1").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void checkDeleteAll() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                "/restapi/person").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isNoContent());
    }

    @Test
    public void checkUpdatingPersonIfExists() throws Exception{
        String contentJson = "{\"id\":1,\"lastName\":\"default\",\"firstName\":" +
                "\"default\",\"patronymic\":\"default\",\"email\":\"default" +
                "\",\"yearOfStudy\":\"default\",\"internship\":\"default\",\"practice\":" +
                "\"default\",\"comment\":\"default\"}";
        Person person = new Person(1);
        Mockito.when(personService.getById(1)).thenReturn(person);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
                "/restapi/person/1").accept(MediaType.APPLICATION_JSON)
                .content(contentJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(contentJson,result.getResponse().getContentAsString(),false);
    }

    @Test
    public void checkUpdatingPersonIfNotExists() throws Exception{
        String contentJson = "{\"id\":1,\"lastName\":\"default\",\"firstName\":" +
                "\"default\",\"patronymic\":\"default\",\"email\":\"default" +
                "\",\"yearOfStudy\":\"default\",\"internship\":\"default\",\"practice\":" +
                "\"default\",\"comment\":\"default\"}";
        Person person = new Person(1);
        Mockito.when(personService.getById(any(Integer.class))).thenReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
                "/restapi/person/1").accept(MediaType.APPLICATION_JSON)
                .content(contentJson).contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void checkPaginationIfPageNotExists() throws Exception {
        when(personService.getAll(any(PageRequest.class))).thenReturn(new PageImpl(Arrays.asList()));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/restapi/person?page=1&limit=3").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    public void checkPaginationIfPageExists() throws Exception {
        when(personService.getAll(any(PageRequest.class))).thenReturn(new PageImpl(Arrays.asList("some content")));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/restapi/person?page=1&limit=3").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void checkPaginationIfLimitNotSpecified() throws Exception {
        when(personService.getAll(any(PageRequest.class))).thenReturn(new PageImpl(Arrays.asList("some content")));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/restapi/person?page=1").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void checkPaginationIfPageNotSpecified() throws Exception {
        when(personService.getAll(any(PageRequest.class))).thenReturn(new PageImpl(Arrays.asList("some content")));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/restapi/person?limit=3").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void checkPaginationIfPageAndLimitNotSpecified() throws Exception {
        when(personService.getAll(any(PageRequest.class))).thenReturn(new PageImpl(Arrays.asList("some content")));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/restapi/person").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }


}
