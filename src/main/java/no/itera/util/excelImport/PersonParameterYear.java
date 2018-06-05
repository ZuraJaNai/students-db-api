package no.itera.util.excelImport;

import no.itera.model.Person;

public class PersonParameterYear implements PersonParameter {
    @Override
    public Person addParameterToPersonAndReturn(String parameter, Person person) {
        person.setYearOfStudy(parameter);
        return person;
    }
}
