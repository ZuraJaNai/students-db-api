package no.itera.util.excelimport;

import no.itera.model.Person;

public interface PersonParameter {
    Person addParameterToPersonAndReturn(String parameter, Person person);
}
