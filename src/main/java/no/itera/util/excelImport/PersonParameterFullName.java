package no.itera.util.excelImport;

import no.itera.model.Person;
import org.apache.commons.lang3.StringUtils;

public class PersonParameterFullName implements PersonParameter {
    @Override
    public Person addParameterToPersonAndReturn(String parameter, Person person) {
        String[] fullName = parameter.split(" ");
        person.setLastName(fullName[0]);
        if (StringUtils.isNoneEmpty(fullName[1])) {
            person.setFirstName(fullName[1]);
        } else {
            person.setFirstName(" ");
        }
        return person;
    }
}
