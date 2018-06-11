package no.itera.util.excelImport;

import no.itera.model.Person;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public class PersonBuilder {

    private static Map<String,PersonParameter> parameters = new HashMap<String,PersonParameter>(){{
        put(ExcelConstants.FULL_NAME,new PersonParameter(){
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
        });
        put(ExcelConstants.FIRST_NAME,new PersonParameter(){
            @Override
            public Person addParameterToPersonAndReturn(String parameter, Person person) {
                person.setFirstName(parameter);
                return person;
            }
        });
        put(ExcelConstants.LAST_NAME,new PersonParameter(){
            @Override
            public Person addParameterToPersonAndReturn(String parameter, Person person) {
                person.setLastName(parameter);
                return person;
            }
        });
        put(ExcelConstants.PATRONYMIC,new PersonParameter(){
            @Override
            public Person addParameterToPersonAndReturn(String parameter, Person person) {
                person.setPatronymic(parameter);
                return person;
            }
        });
        put(ExcelConstants.EMAIL,new PersonParameter(){
            @Override
            public Person addParameterToPersonAndReturn(String parameter, Person person) {
                person.setEmail(parameter);
                return person;
            }
        });
        put(ExcelConstants.YEAR, new PersonParameter() {
            @Override
            public Person addParameterToPersonAndReturn(String parameter, Person person) {
                person.setYearOfStudy(parameter);
                return person;
            }
        });
    }};
    private Person person;
    private Row row;
    private DataFormatter dataFormatter;

    public PersonBuilder(Row row){
        this.row = row;
        this.person = new Person();
        this.dataFormatter = new DataFormatter();
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
