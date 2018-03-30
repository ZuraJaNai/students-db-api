package no.itera.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import no.itera.util.CustomPersonDateSerializer;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(example = "01.2018")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "MM.yyyy")
    @JsonSerialize(using = CustomPersonDateSerializer.class)
    private Date internshipBegin;

    @Column(name = "INTERNSHIP_END")
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(example = "02.2018")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "MM.yyyy")
    @JsonSerialize(using = CustomPersonDateSerializer.class)
    private Date internshipEnd;

    @Column(name = "PRACTICE_BEGIN")
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(example = "01.2018")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "MM.yyyy")
    @JsonSerialize(using = CustomPersonDateSerializer.class)
    private Date practiceBegin;

    @Column(name = "PRACTICE_END")
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(example = "02.2018")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "MM.yyyy")
    @JsonSerialize(using = CustomPersonDateSerializer.class)
    private Date practiceEnd;

    @Column(name = "JOB_BEGIN")
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(example = "01.2018")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "MM.yyyy")
    @JsonSerialize(using = CustomPersonDateSerializer.class)
    private Date jobBegin;

    @Column(name = "JOB_END")
    @Temporal(TemporalType.DATE)
    @ApiModelProperty(example = "02.2018")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "MM.yyyy")
    @JsonSerialize(using = CustomPersonDateSerializer.class)
    private Date jobEnd;

    @Column(name = "COMMENT")
    private String comment;

    public AbstractPerson(){

    }

    public AbstractPerson(int id, String lastName, String firstName, String patronymic,
                           String email, String yearOfStudy, Date internshipBegin,
                           Date internshipEnd, Date practiceBegin,
                           Date practiceEnd, Date jobBegin, Date jobEnd,
                           String comment){
        this(lastName, firstName, patronymic, email, yearOfStudy, internshipBegin,
                internshipEnd, practiceBegin, practiceEnd, jobBegin, jobEnd, comment);
        this.id = id;
    }

    public AbstractPerson(String lastName, String firstName, String patronymic,
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

    public void setInternshipBegin(Date internshipBegin) {
        this.internshipBegin = internshipBegin;
    }

    public Date getInternshipEnd() {
        return internshipEnd;
    }

    public void setInternshipEnd(Date internshipEnd) {
        this.internshipEnd = internshipEnd;
    }

    public Date getPracticeBegin() {
        return practiceBegin;
    }

    public void setPracticeBegin(Date practiceBegin) {
        this.practiceBegin = practiceBegin;
    }

    public Date getPracticeEnd() {
        return practiceEnd;
    }

    public void setPracticeEnd(Date practiceEnd) {
        this.practiceEnd = practiceEnd;
    }

    public Date getJobBegin() {
        return jobBegin;
    }

    public void setJobBegin(Date jobBegin) {
        this.jobBegin = jobBegin;
    }

    public Date getJobEnd() {
        return jobEnd;
    }

    public void setJobEnd(Date jobEnd) {
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
