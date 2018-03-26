package no.itera.model;

import io.swagger.annotations.ApiModel;

@ApiModel(value="PersonData")
public class PersonData extends AbstractPerson{

    public PersonData(){

    }

    public PersonData(AbstractPerson person){
        super(person.getId(),person.getLastName(),person.getFirstName(),person.getPatronymic(),
                person.getEmail(),person.getYearOfStudy(),person.getInternshipBegin(),
                person.getInternshipEnd(),person.getPracticeBegin(),person.getPracticeEnd(),
                person.getJobBegin(),person.getJobEnd(),person.getComment());
    }

}
