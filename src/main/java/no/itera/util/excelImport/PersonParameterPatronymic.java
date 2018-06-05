package no.itera.util.excelImport;

import no.itera.model.Person;

public class PersonParameterPatronymic implements PersonParameter {
    @Override
    public Person addParameterToPersonAndReturn(String parameter, Person person) {
        person.setPatronymic(parameter);
        return person;
    }
}
