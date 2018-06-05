package no.itera.util.excelImport;

import no.itera.model.Person;

public class PersonParameterFirstName implements PersonParameter {
    @Override
    public Person addParameterToPersonAndReturn(String parameter, Person person) {
        person.setFirstName(parameter);
        return person;
    }
}
