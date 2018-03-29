package no.itera.controller.rest;

import no.itera.model.Person;
import no.itera.model.PersonData;
import no.itera.model.PersonSearch;
import no.itera.services.PersonServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonServiceImpl personService;

    @Test
    public void checkPersonSearchByLastNameIfExists() throws Exception {
        String expected = "{\"persons\":[{\"id\":1,\"lastName\":\"lastName\",\"firstName\":\"default\",\"patronymic\":\"default\",\"email\":\"default\",\"yearOfStudy\":\"default\",\"internshipBegin\":\"01.1970\",\"internshipEnd\":\"01.1970\",\"practiceBegin\":\"01.1970\",\"practiceEnd\":\"01.1970\",\"jobBegin\":\"01.1970\",\"jobEnd\":\"01.1970\",\"comment\":\"default\"}],\"currentPage\":1,\"totalPages\":1,\"count\":1}";
        Person person = new Person(1);
        person.setLastName("lastName");
        Mockito.when(personService.findAllPersons(any(PersonSearch.class))).thenReturn(Arrays.asList(person));
        Mockito.when(personService.transformPersonsToOutputFormat(any(List.class))).thenReturn(Arrays.asList(new PersonData(person)));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/restapi/person/search").accept(MediaType.APPLICATION_JSON)
                .content("{\"lastName\":\"lastName\"}").contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isFound()).andReturn();
        JSONAssert.assertEquals(expected,result.getResponse().getContentAsString(),false);
    }

    @Test
    public void checkPersonSearchByLastNameIfNotExists() throws Exception {
        Mockito.when(personService.findAllPersons(any(PersonSearch.class))).thenReturn(Arrays.asList());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/restapi/search/person").accept(MediaType.APPLICATION_JSON)
                .content("{\"lastName\":\"lastName\"}").contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }
}
