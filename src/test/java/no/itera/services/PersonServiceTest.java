package no.itera.services;

import no.itera.dao.PersonDao;
import no.itera.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = PersonServiceImpl.class)
public class PersonServiceTest {


    @Mock
    private PersonDao daoMock;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkIfExistingPersonExists() {
        when(daoMock.exists(1)).thenReturn(true);
        assertEquals(true, personService.isPersonExists(new Person(1)));
    }

    @Test
    public void checkIfNonExistingPersonExists(){
        when(daoMock.exists(41)).thenReturn(false);
        Person person = new Person(41);
        assertEquals(false,personService.isPersonExists(person));

        when(daoMock.exists(-3)).thenReturn(false);
        person = new Person(-3);
        assertEquals(false,personService.isPersonExists(person));
    }

    @Test
    public void checkGetExistingPersonById() {
        when(daoMock.findOne(1)).thenReturn(new Person(1));
        assertEquals(1, personService.getById(1).getId());
    }

    @Test
    public void checkGetNonExistingPersonById(){
        when(daoMock.findOne(55)).thenReturn(null);
        assertEquals(null,personService.getById(55));
    }


    @Test
    public void checkAllPersonsDeletion() {
        when(daoMock.findAll()).thenReturn(Arrays.asList(new Person(1), new Person(2)));
        assertTrue(0 < personService.getAll().spliterator().getExactSizeIfKnown());
        when(daoMock.findAll()).thenReturn(Arrays.asList());
        assertEquals(0,(personService.getAll().spliterator().getExactSizeIfKnown()));
    }


    @Test
    public void checkNonExistingPersonUpdate(){
        Person person =  new Person(41);
        when(daoMock.save(person)).thenThrow(new ArrayIndexOutOfBoundsException() );
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> personService.updatePerson( person));
    }

}
