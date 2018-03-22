package no.itera.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@ApiModel(value="SearchPerson")
public class SearchPerson implements Serializable {

    private String lastName;

    private String firstName;

    @ApiModelProperty(example = "user@mail.com")
    private String email;

    @ApiModelProperty(example = "2017")
    private String yearOfStudy;

    @ApiModelProperty(example = "01.2018")
    private String internshipDate;

    @ApiModelProperty(allowableValues = "true, false")
    private boolean internship;

    @ApiModelProperty(example = "01.2018")
    private String practiceDate;

    @ApiModelProperty(allowableValues = "true, false")
    private boolean practice;

    @ApiModelProperty(example = "01.2018")
    private String jobDate;

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

    public String getInternshipDate() {
        return internshipDate;
    }

    public void setInternshipDate(String internshipDate) {
        if(StringUtils.isNoneEmpty(internshipDate)){
            this.internship = true;
        }
        this.internshipDate = internshipDate;
    }

    public boolean isInternship() {
        return internship;
    }

    public void setInternship(boolean internship) {
        this.internship = internship;
    }

    public String getPracticeDate() {
        return practiceDate;
    }

    public void setPracticeDate(String practiceDate) {
        if(StringUtils.isNoneEmpty(practiceDate)){
            this.practice = true;
        }
        this.practiceDate = practiceDate;
    }

    public boolean isPractice() {
        return practice;
    }

    public void setPractice(boolean practice) {
        this.practice = practice;
    }

    public String getJobDate() {
        return jobDate;
    }

    public void setJobDate(String jobDate) {
        if(StringUtils.isNoneEmpty(jobDate)){
            this.job = true;
        }
        this.jobDate = jobDate;
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
