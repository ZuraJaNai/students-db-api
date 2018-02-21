import no.itera.model.Person;
import no.itera.services.PersonService;
import no.itera.services.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PersonServiceTest {

    private PersonService personService = new PersonServiceImpl();

    @BeforeEach
    public void init(){
        personService.deleteAllPersons();
        personService.addPerson(new Person(1));
        personService.addPerson(new Person(2));
        personService.addPerson(new Person(3));
    }

    @Test
    public void checkIfExistingPersonExists() {
        Person person = new Person(1);
        assertEquals(true, personService.isPersonExists(person));
    }

    @Test
    public void checkIfNonExistingPersonExists(){
        Person person = new Person(41);
        assertEquals(false,personService.isPersonExists(person));

        person = new Person(-3);
        assertEquals(false,personService.isPersonExists(person));
    }

    @Test
    public void checkGetExistingPersonById() {
        assertEquals(1, personService.getPersonById(1).getId());
    }

    @Test
    public void checkGetNonExistingPersonById(){
        assertEquals(null,personService.getPersonById(55));
    }

    @Test
    public void checkPersonAdditionIfPersonNotExists() {
        Person person = new Person(10);
        assertEquals(true, personService.addPerson(person));
    }

    @Test
    public void checkPersonAdditionIfPersonExists(){
        Person person = new Person(2);
        assertEquals(false,personService.addPerson(person));
    }

    @Test
    public void checkPersonDeletionIfPersonExists(){
        assertEquals(true,personService.deletePerson(1));
        assertEquals(true,personService.deletePerson(3));
        assertEquals(true,personService.deletePerson(2));
    }

    @Test
    public void checkPersonDeletionIfPersonNotExists() {
        assertEquals(false,personService.deletePerson(55));
        personService.deletePerson(3);
        assertEquals(false,personService.deletePerson(3));
    }


    @Test
    public void checkAllPersonsDeletion(){
        assertTrue(0 < personService.getPersonsList().size());
        personService.deleteAllPersons();
        assertEquals(0,(personService.getPersonsList().size()));
    }

    @Test
    public void checkExistingPersonUpdate(){
        Person person = personService.getPersonById(1);
        person.setName("New one");
        personService.updatePerson(person);
        assertEquals(person.getName(),personService.getPersonById(person.getId()).getName());
    }

    @Test
    public void checkNonExistingPersonUpdate(){
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->personService.updatePerson( new Person(41)));
    }
}
