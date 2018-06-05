package no.itera.util.excelImport;

import no.itera.model.Person;

public interface PersonParameter {
    Person addParameterToPersonAndReturn(String parameter, Person person);
}
