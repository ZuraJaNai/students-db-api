package no.itera.util.excelimport;

import no.itera.model.Person;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public class PersonBuilder {

    private static Map<String,PersonParameter> parameters;
    private Person person;
    private Row row;
    private DataFormatter dataFormatter;

    public PersonBuilder(Row row){
        this.row = row;
        this.person = new Person();
        this.dataFormatter = new DataFormatter();
        parameters = new HashMap<>();
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

    public Person getPerson() {
        if(StringUtils.isEmpty(person.getYearOfStudy())){
            person.setYearOfStudy(Year.now().toString());
        }
        return this.person;
    }

    public void addParameter(String key, Integer value) {
        PersonParameter parameter = parameters.get(key);
        person = parameter.addParameterToPersonAndReturn(
                dataFormatter.formatCellValue(row.getCell(value)),this.person);
    }
}
