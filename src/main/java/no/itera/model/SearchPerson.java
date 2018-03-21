package no.itera.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

@ApiModel(value="SearchPerson")
public class SearchPerson {

    private String lastName;

    private String firstName;

    @ApiModelProperty(example = "user@mail.com")
    private String email;

    @ApiModelProperty(example = "2017")
    private String yearOfStudy;

    @ApiModelProperty(example = "01.2018")
    private String internshipDate;

    @ApiModelProperty(allowableValues = "true, false")
    private boolean isInternship;

    @ApiModelProperty(example = "01.2018")
    private String practiceDate;

    @ApiModelProperty(allowableValues = "true, false")
    private boolean isPractice;

    @ApiModelProperty(example = "01.2018")
    private String jobDate;

    @ApiModelProperty(allowableValues = "true, false")
    private boolean isJob;

    private String comment;


    public SearchPerson(String lastName, String firstName, String email,
                        String yearOfStudy, String internshipDate, boolean isInternship,
                        String practiceDate, boolean isPractice, String jobDate,
                        boolean isJob, String comment) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.yearOfStudy = yearOfStudy;
        this.isInternship = isInternship;
        this.isPractice = isPractice;
        this.isJob = isJob;
        this.internshipDate = internshipDate;
        if(StringUtils.isNoneEmpty(internshipDate)){
            this.isInternship = true;
        }
        this.practiceDate = practiceDate;
        if(StringUtils.isNoneEmpty(practiceDate)){
            this.isPractice = true;
        }
        this.jobDate = jobDate;
        if(StringUtils.isNoneEmpty(jobDate)){
            this.isJob = true;
        }
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
        this.internshipDate = internshipDate;
    }

    public boolean isInternship() {
        return isInternship;
    }

    public void setInternship(boolean internship) {
        isInternship = internship;
    }

    public String getPracticeDate() {
        return practiceDate;
    }

    public void setPracticeDate(String practiceDate) {
        this.practiceDate = practiceDate;
    }

    public boolean isPractice() {
        return isPractice;
    }

    public void setPractice(boolean practice) {
        isPractice = practice;
    }

    public String getJobDate() {
        return jobDate;
    }

    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }

    public boolean isJob() {
        return isJob;
    }

    public void setJob(boolean job) {
        isJob = job;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
