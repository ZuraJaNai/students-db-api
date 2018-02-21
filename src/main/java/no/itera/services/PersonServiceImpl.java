package no.itera.services;

import no.itera.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class PersonServiceImpl implements PersonService {

    private ArrayList<Person> personsList  = new ArrayList<>();

    @Autowired
    public void PersonServiceImpl(){
        this.personsList.add(new Person(1,"One",1));
        this.personsList.add(new Person(2,"Two",2));
        this.personsList.add(new Person(3,"Three",3));
    }

    @Override
    public ArrayList<Person> getPersonsList() {
        return personsList;
    }

    @Override
    public Person getPersonById(int id) {
        if(personsList.isEmpty()){
            return null;
        }
        Person tempPerson = null;
        for (Person person :
                this.personsList) {
            if (person.getId() == id) {
                tempPerson = person;
            }
        }
        return tempPerson;
    }

    @Override
    public boolean isPersonExists(Person person) {
        return getPersonById(person.getId()) != null;
    }

    @Override
    public boolean addPerson(Person person){
        try {
            if (this.getPersonById(person.getId()) == null){
                this.personsList.add(person);
                return true;
            }
        }
        catch (NullPointerException e){
            return false;
        }
        return false;
    }

    @Override
    public boolean deletePerson(int id){
        for(Iterator<Person> iterator = personsList.iterator(); iterator.hasNext();){
            Person person = iterator.next();
            if (person.getId() == id){
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public void updatePerson(Person person) {
        personsList.set(personsList.indexOf(person),person);
    }

    @Override
    public void deleteAllPersons() {
        personsList.clear();
    }
}
