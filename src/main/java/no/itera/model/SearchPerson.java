package no.itera.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;

@ApiModel(value="SearchPerson")
public class SearchPerson implements Serializable {

    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("MM.yyyy")
            .parseDefaulting(DAY_OF_MONTH, 15)
            .toFormatter();

    private String lastName;

    private String firstName;

    @ApiModelProperty(example = "user@mail.com")
    private String email;

    @ApiModelProperty(example = "2017")
    private String yearOfStudy;

    @ApiModelProperty(example = "01.2018")
    private Date internshipDate;

    @ApiModelProperty(allowableValues = "true, false")
    private boolean internship;

    @ApiModelProperty(example = "01.2018")
    private Date practiceDate;

    @ApiModelProperty(allowableValues = "true, false")
    private boolean practice;

    @ApiModelProperty(example = "01.2018")
    private Date jobDate;

    @ApiModelProperty(allowableValues = "true, false")
    private boolean job;

    private String comment;

    public SearchPerson(){

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

    public Date getInternshipDate() {
        return internshipDate;
    }

    public void setInternshipDate(String internshipDate) {
        if(StringUtils.isNoneEmpty(internshipDate)){
            this.internship = true;
        }
            this.internshipDate = Date.from(LocalDate.parse(internshipDate,formatter)
                    .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public boolean isInternship() {
        return internship;
    }

    public void setInternship(boolean internship) {
        this.internship = internship;
    }

    public Date getPracticeDate() {
        return practiceDate;
    }

    public void setPracticeDate(String practiceDate) {
        if(StringUtils.isNoneEmpty(practiceDate)){
            this.practice = true;
        }
            this.practiceDate =  Date.from(LocalDate.parse(practiceDate,formatter)
                    .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public boolean isPractice() {
        return practice;
    }

    public void setPractice(boolean practice) {
        this.practice = practice;
    }

    public Date getJobDate() {
        return jobDate;
    }

    public void setJobDate(String jobDate) {
        if(StringUtils.isNoneEmpty(jobDate)){
            this.job = true;
        }
            this.jobDate =  Date.from(LocalDate.parse(jobDate,formatter)
                    .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public boolean isJob() {
        return job;
    }

    public void setJob(boolean job) {
        this.job = job;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
