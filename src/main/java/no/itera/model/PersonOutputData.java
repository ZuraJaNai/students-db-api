package no.itera.model;

import java.text.SimpleDateFormat;

public class PersonOutputData {

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM.yyyy");

    private int id;

    private String lastName;

    private String firstName;

    private String patronymic;

    private String email;

    private String yearOfStudy;

    private String internshipBegin;

    private String internshipEnd;

    private String practiceBegin;

    private String practiceEnd;

    private String jobBegin;

    private String jobEnd;

    public PersonOutputData(){

    }

    public PersonOutputData(Person person){
        this.id = person.getId();
        this.lastName = person.getLastName();
        this.firstName = person.getFirstName();
        this.patronymic = person.getPatronymic();
        this.email = person.getEmail();
        this.yearOfStudy = person.getYearOfStudy();
        this.internshipBegin = dateFormat.format(person.getInternshipBegin());
        this.internshipEnd = dateFormat.format(person.getInternshipEnd());
        this.practiceBegin = dateFormat.format(person.getPracticeBegin());
        this.practiceEnd = dateFormat.format(person.getPracticeEnd());
        this.jobBegin = dateFormat.format(person.getJobBegin());
        this.jobEnd = dateFormat.format(person.getJobEnd());
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getEmail() {
        return email;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public String getInternshipBegin() {
        return internshipBegin;
    }

    public String getInternshipEnd() {
        return internshipEnd;
    }

    public String getPracticeBegin() {
        return practiceBegin;
    }

    public String getPracticeEnd() {
        return practiceEnd;
    }

    public String getJobBegin() {
        return jobBegin;
    }

    public String getJobEnd() {
        return jobEnd;
    }

    public int getId() {
        return id;
    }
}
