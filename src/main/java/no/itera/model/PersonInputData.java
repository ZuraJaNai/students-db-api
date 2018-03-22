package no.itera.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;

@ApiModel(value="PersonInputData")
public class PersonInputData {

    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("MM.yyyy")
            .parseDefaulting(DAY_OF_MONTH, 1)
            .toFormatter();

    private String lastName;

    private String firstName;

    private String patronymic;

    @ApiModelProperty(example = "user@mail.com")
    private String email;

    @ApiModelProperty(example = "2017")
    private String yearOfStudy;

    @ApiModelProperty(example = "01.2018")
    private Date internshipBegin;

    @ApiModelProperty(example = "02.2018")
    private Date internshipEnd;

    @ApiModelProperty(example = "01.2018")
    private Date practiceBegin;

    @ApiModelProperty(example = "02.2018")
    private Date practiceEnd;

    @ApiModelProperty(example = "01.2018")
    private Date jobBegin;

    @ApiModelProperty(example = "02.2018")
    private Date jobEnd;

    private String comment;

    public PersonInputData(){

    }

    public PersonInputData(String lastName, String firstName, String patronymic,
                           String email, String yearOfStudy, Date internshipBegin,
                           Date internshipEnd, Date practiceBegin,
                           Date practiceEnd, Date jobBegin, Date jobEnd,
                           String comment){
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.email = email;
        this.yearOfStudy = yearOfStudy;
        this.internshipBegin = internshipBegin;
        this.internshipEnd = internshipEnd;
        this.practiceBegin = practiceBegin;

        this.practiceEnd = practiceEnd;
        this.jobBegin = jobBegin;
        this.jobEnd = jobEnd;
        this.comment = comment;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(String yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public Date getInternshipBegin() {
        return internshipBegin;
    }

    public void setInternshipBegin(String internshipBegin) {
        this.internshipBegin = Date.from(LocalDate.parse(internshipBegin,formatter)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getInternshipEnd() {
        return internshipEnd;
    }

    public void setInternshipEnd(String internshipEnd) {
        this.internshipEnd = Date.from(LocalDate.parse(internshipEnd,formatter)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getPracticeBegin() {
        return practiceBegin;
    }

    public void setPracticeBegin(String practiceBegin) {
        this.practiceBegin = Date.from(LocalDate.parse(practiceBegin,formatter)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getPracticeEnd() {
        return practiceEnd;
    }

    public void setPracticeEnd(String practiceEnd) {
        this.practiceEnd = Date.from(LocalDate.parse(practiceEnd,formatter)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getJobBegin() {
        return jobBegin;
    }

    public void setJobBegin(String jobBegin) {
        this.jobBegin = Date.from(LocalDate.parse(jobBegin,formatter)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getJobEnd() {
        return jobEnd;
    }

    public void setJobEnd(String jobEnd) {
        this.jobEnd = Date.from(LocalDate.parse(jobEnd,formatter)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
