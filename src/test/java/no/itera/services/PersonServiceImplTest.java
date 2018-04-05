package no.itera.services;

import no.itera.dao.PersonDao;
import no.itera.model.AbstractPerson;
import no.itera.model.Person;
import no.itera.model.PersonData;
import no.itera.model.PersonSearch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {

    @Mock
    private PersonDao daoMock;

    @InjectMocks
    private PersonServiceImpl personService;

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
        when(daoMock.save(any(Person.class))).thenThrow(new NullPointerException() );
        assertThrows(NullPointerException.class, () -> personService.updatePerson(10, new PersonData()));
    }

    @Test
    public void getPersonCount(){
        when(daoMock.count()).thenReturn(new Long(12));
        assertEquals(12,personService.count());
    }

    @Test
    public void checkPersonTransformationToOutputFormat(){
        Person person = new Person(1);
        AbstractPerson personData = new PersonData(person);
        assertEquals(personData.toString(),
                personService.transformPersonsToOutputFormat(Arrays.asList(person)).get(0).toString());
    }

    @Test
    public void checkExistingPersonSearch(){
        AbstractPerson person = new Person(1);
        when(daoMock.findAll(any(Specification.class))).thenReturn(Arrays.asList(person));
        assertEquals(person.toString(),personService.findAllPersons(new PersonSearch()).get(0).toString());
    }
}
