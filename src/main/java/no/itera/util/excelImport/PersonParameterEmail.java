package no.itera.util.excelImport;

import no.itera.model.Person;

public class PersonParameterEmail implements PersonParameter {
    @Override
    public Person addParameterToPersonAndReturn(String parameter, Person person) {
        person.setEmail(parameter);
        return person;
    }
}
