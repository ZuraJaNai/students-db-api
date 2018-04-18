package no.itera.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import no.itera.util.CustomPersonBeginDateDeserializer;
import no.itera.util.CustomPersonEndDateDeserializer;
import no.itera.util.CustomPersonBeginDateSerializer;
import no.itera.util.CustomPersonEndDateSerializer;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
public abstract class AbstractPerson implements Serializable{

    @org.hibernate.annotations.GenericGenerator(
            name = "ID_GENERATOR",
            strategy = "enhanced-sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "sequence_name",
                            value = "JPWH_SEQUENCE"
                    ),
                    @org.hibernate.annotations.Parameter(
                            name = "initial_value",
                            value = "1000"
                    )
            }
    )
    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    @Column(name = "PERSON_ID",updatable = false, nullable = false)
    private int id;

    @Column(nullable = false,name = "LASTNAME")
    private String lastName;

    @Column(nullable = false,name = "FIRSTNAME")
    private String firstName;

    @Column(nullable = false,name = "PATRONYMIC")
    private String patronymic;

    @Column(name = "EMAIL")
    @ApiModelProperty(example = "user@mail.com")
    private String email;

    @Column(nullable = false,name = "YEAR")
    @ApiModelProperty(example = "2017")
    private String yearOfStudy;

    @Column(name = "INTERNSHIP_BEGIN")
    @Type(type = "org.hibernate.type.LocalDateType")
    @ApiModelProperty(example = "01.2018")
    @JsonFormat(pattern = "MM.yyyy")
    @JsonSerialize(using = CustomPersonBeginDateSerializer.class)
    @JsonDeserialize(using = CustomPersonBeginDateDeserializer.class)
    private LocalDate internshipBegin;

    @Column(name = "INTERNSHIP_END")
    @Type(type = "org.hibernate.type.LocalDateType")
    @ApiModelProperty(example = "02.2018")
    @JsonFormat(pattern = "MM.yyyy")
    @JsonSerialize(using = CustomPersonEndDateSerializer.class)
    @JsonDeserialize(using = CustomPersonEndDateDeserializer.class)
    private LocalDate internshipEnd;

    @Column(name = "PRACTICE_BEGIN")
    @Type(type = "org.hibernate.type.LocalDateType")
    @ApiModelProperty(example = "01.2018")
    @JsonFormat(pattern = "MM.yyyy")
    @JsonSerialize(using = CustomPersonBeginDateSerializer.class)
    @JsonDeserialize(using = CustomPersonBeginDateDeserializer.class)
    private LocalDate practiceBegin;

    @Column(name = "PRACTICE_END")
    @Type(type = "org.hibernate.type.LocalDateType")
    @ApiModelProperty(example = "02.2018")
    @JsonFormat(pattern = "MM.yyyy")
    @JsonSerialize(using = CustomPersonEndDateSerializer.class)
    @JsonDeserialize(using = CustomPersonEndDateDeserializer.class)
    private LocalDate practiceEnd;

    @Column(name = "JOB_BEGIN")
    @Type(type = "org.hibernate.type.LocalDateType")
    @ApiModelProperty(example = "01.2018")
    @JsonFormat(pattern = "MM.yyyy")
    @JsonSerialize(using = CustomPersonBeginDateSerializer.class)
    @JsonDeserialize(using = CustomPersonBeginDateDeserializer.class)
    private LocalDate jobBegin;

    @Column(name = "JOB_END")
    @Type(type = "org.hibernate.type.LocalDateType")
    @ApiModelProperty(example = "02.2018")
    @JsonFormat(pattern = "MM.yyyy")
    @JsonSerialize(using = CustomPersonEndDateSerializer.class)
    @JsonDeserialize(using = CustomPersonEndDateDeserializer.class)
    private LocalDate jobEnd;

    @Column(name = "COMMENT")
    private String comment;

    public AbstractPerson(){

    }

    public AbstractPerson(int id, String lastName, String firstName, String patronymic,
                           String email, String yearOfStudy, LocalDate internshipBegin,
                          LocalDate internshipEnd, LocalDate practiceBegin,
                          LocalDate practiceEnd, LocalDate jobBegin, LocalDate jobEnd,
                           String comment){
        this(lastName, firstName, patronymic, email, yearOfStudy, internshipBegin,
                internshipEnd, practiceBegin, practiceEnd, jobBegin, jobEnd, comment);
        this.id = id;
    }

    public AbstractPerson(String lastName, String firstName, String patronymic,
                          String email, String yearOfStudy, LocalDate internshipBegin,
                          LocalDate internshipEnd, LocalDate practiceBegin,
                          LocalDate practiceEnd, LocalDate jobBegin, LocalDate jobEnd,
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

    public LocalDate getInternshipBegin() {
        return internshipBegin;
    }

    public void setInternshipBegin(LocalDate internshipBegin) {
        this.internshipBegin = internshipBegin;
    }

    public LocalDate getInternshipEnd() {
        return internshipEnd;
    }

    public void setInternshipEnd(LocalDate internshipEnd) {
        this.internshipEnd = internshipEnd;
    }

    public LocalDate getPracticeBegin() {
        return practiceBegin;
    }

    public void setPracticeBegin(LocalDate practiceBegin) {
        this.practiceBegin = practiceBegin;
    }

    public LocalDate getPracticeEnd() {
        return practiceEnd;
    }

    public void setPracticeEnd(LocalDate practiceEnd) {
        this.practiceEnd = practiceEnd;
    }

    public LocalDate getJobBegin() {
        return jobBegin;
    }

    public void setJobBegin(LocalDate jobBegin) {
        this.jobBegin = jobBegin;
    }

    public LocalDate getJobEnd() {
        return jobEnd;
    }

    public void setJobEnd(LocalDate jobEnd) {
        this.jobEnd = jobEnd;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();

        info.append(String.format("ID: %d%n", id));
        if(StringUtils.isNoneEmpty(lastName)){
            info.append(String.format("Last name: %s%n",lastName));
        }
        if(StringUtils.isNoneEmpty(firstName)){
            info.append(String.format("First name: %s%n",firstName));
        }
        if(StringUtils.isNoneEmpty(patronymic)){
            info.append(String.format("Patronymic: %s%n",patronymic));
        }
        if(StringUtils.isNoneEmpty(email)){
            info.append(String.format("Email: %s%n",email));
        }
        if(StringUtils.isNoneEmpty(yearOfStudy)){
            info.append(String.format("Year of study: %s%n",yearOfStudy));
        }
        if(StringUtils.isNoneEmpty(comment)){
            info.append(String.format("Comment: %s%n",comment));
        }
        if(internshipBegin != null){
            info.append(String.format("Internship begin: %s%n",internshipBegin));
        }
        if(internshipEnd != null){
            info.append(String.format("Internship end: %s%n",internshipEnd));
        }
        if(practiceBegin != null){
            info.append(String.format("Practice begin: %s%n",practiceBegin));
        }
        if(practiceEnd != null){
            info.append(String.format("Practice end: %s%n",practiceEnd));
        }
        if(jobBegin != null){
            info.append(String.format("Job begin: %s%n",jobBegin));
        }
        if(jobEnd != null){
            info.append(String.format("Job end: %s%n",jobEnd));
        }
        return info.toString();
    }
}
