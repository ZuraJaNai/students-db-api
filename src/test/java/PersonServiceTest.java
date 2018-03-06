import no.itera.dao.PersonDao;
import no.itera.model.Person;
import no.itera.services.PersonService;
import no.itera.services.PersonServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


public class PersonServiceTest {


    @Mock
    private PersonDao daoMock;

    @InjectMocks
    private PersonServiceImpl personService;

//    @Captor
//    private ArgumentCaptor<Person> personArgumentCaptor;

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
    public void checkAllPersonsDeletion() throws SQLException {
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
