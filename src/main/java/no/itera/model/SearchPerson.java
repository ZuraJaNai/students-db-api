package no.itera.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApiModel(value="SearchPerson")
public class SearchPerson implements Serializable {

    SimpleDateFormat format = new SimpleDateFormat("MM.yyyy");

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
        try {
            this.internshipDate = format.parse(internshipDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        try {
            this.practiceDate =  format.parse(practiceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        try {
            this.jobDate =  format.parse(jobDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
