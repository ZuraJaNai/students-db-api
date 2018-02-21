import no.itera.model.Person;
import no.itera.services.PersonService;
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

import no.itera.controllerRest.PersonController;
import java.util.ArrayList;
import java.util.Arrays;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonController.class)
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;


    @Test
    public void retrieveAllPersons() throws Exception {
        Mockito.when(personService.getPersonsList()).thenReturn((new ArrayList<Person>( Arrays.asList(new Person(1)))));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/person/").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"One\",\n" +
                "        \"age\": 1,\n" +
                "        \"info\": \"ID: 1,Name: One, Age 1.\"\n" +
                "    }";
        JSONAssert.assertEquals(expected,result.getResponse().getContentAsString(),false);
    }

}
