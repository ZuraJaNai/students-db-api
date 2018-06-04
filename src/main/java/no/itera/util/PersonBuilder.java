package no.itera.util;

import no.itera.model.Person;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.time.Year;

public class PersonBuilder {
    Person person;
    Row row;
    DataFormatter dataFormatter;

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
        switch (key){
            case ExcelConstants.FULL_NAME:
                String[] fullName = dataFormatter.formatCellValue(row.getCell(value)).split(" ");
                this.person.setLastName(fullName[0]);
                if (StringUtils.isNoneEmpty(fullName[1])) {
                    this.person.setFirstName(fullName[1]);
                } else {
                    this.person.setFirstName(" ");
                }
                break;
            case ExcelConstants.FIRST_NAME:
                this.person.setFirstName(dataFormatter.formatCellValue(row.getCell(value)));
                break;
            case ExcelConstants.LAST_NAME:
                this.person.setLastName(dataFormatter.formatCellValue(row.getCell(value)));
                break;
            case ExcelConstants.PATRONYMIC:
                this.person.setPatronymic(dataFormatter.formatCellValue(row.getCell(value)));
                break;
            case ExcelConstants.EMAIL:
                this.person.setEmail(dataFormatter.formatCellValue(row.getCell(value)));
                break;
            case ExcelConstants.YEAR:
                this.person.setYearOfStudy(dataFormatter.formatCellValue(row.getCell(value)));
                break;
            default:
                break;
        }
    }
}
