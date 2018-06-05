package no.itera.util.excelImport;

import no.itera.model.Person;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public class PersonBuilder {
    Map<String,PersonParameter> parameters;
    Person person;
    Row row;
    DataFormatter dataFormatter;

    public PersonBuilder(Row row){
        this.row = row;
        this.person = new Person();
        this.dataFormatter = new DataFormatter();
        this.parameters = new HashMap<String,PersonParameter>(){{
            put(ExcelConstants.FULL_NAME,new PersonParameterFullName());
            put(ExcelConstants.FIRST_NAME,new PersonParameterFirstName());
            put(ExcelConstants.LAST_NAME,new PersonParameterLastName());
            put(ExcelConstants.PATRONYMIC,new PersonParameterPatronymic());
            put(ExcelConstants.EMAIL,new PersonParameterEmail());
            put(ExcelConstants.YEAR,new PersonParameterYear());
        }};
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
