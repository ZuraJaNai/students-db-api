package no.itera.util.excelimport;

import no.itera.model.Person;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public class PersonBuilder {

    private static Map<String,PersonParameter> parameters = new HashMap<>();
    static {
        parameters.put(ExcelConstants.FULL_NAME, (parameter, person) -> {
            String[] fullName = parameter.split(" ");
            person.setLastName(fullName[0]);
            if (StringUtils.isNoneEmpty(fullName[1])) {
                person.setFirstName(fullName[1]);
            } else {
                person.setFirstName(" ");
            }
            return person;
        });
        parameters.put(ExcelConstants.FIRST_NAME, (parameter, person) -> {
            person.setFirstName(parameter);
            return person;
        });
        parameters.put(ExcelConstants.LAST_NAME, (parameter, person) -> {
            person.setLastName(parameter);
            return person;
        });
        parameters.put(ExcelConstants.PATRONYMIC, (parameter, person) -> {
            person.setPatronymic(parameter);
            return person;
        });
        parameters.put(ExcelConstants.EMAIL, (parameter, person) -> {
            person.setEmail(parameter);
            return person;
        });
        parameters.put(ExcelConstants.YEAR, (parameter, person) -> {
            person.setYearOfStudy(parameter);
            return person;
        });
    }
    private Person newPerson;
    private Row row;
    private DataFormatter dataFormatter;

    public PersonBuilder(Row row){
        this.row = row;
        this.newPerson = new Person();
        this.dataFormatter = new DataFormatter();
    }

    public Person getPerson() {
        if(StringUtils.isEmpty(newPerson.getYearOfStudy())){
            newPerson.setYearOfStudy(Year.now().toString());
        }
        return this.newPerson;
    }

    public void addParameter(String key, Integer value) {
        PersonParameter parameter = parameters.get(key);
        newPerson = parameter.addParameterToPersonAndReturn(
                dataFormatter.formatCellValue(row.getCell(value)),this.newPerson);
    }
}
