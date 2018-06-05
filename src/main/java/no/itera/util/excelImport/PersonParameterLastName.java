package no.itera.util.excelImport;

import no.itera.model.Person;

public class PersonParameterLastName implements PersonParameter {
    @Override
    public Person addParameterToPersonAndReturn(String parameter, Person person) {
        person.setLastName(parameter);
        return person;
    }
}
