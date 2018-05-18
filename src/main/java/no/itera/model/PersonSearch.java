package no.itera.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import no.itera.util.CustomSearchDateDeserializer;
import no.itera.util.CustomSearchYearOfStudyDeserializer;

import java.util.List;

@ApiModel(value="PersonSearch")
public class PersonSearch{

    private String lastName;

    private String firstName;

    @ApiModelProperty(example = "user@mail.com")
    private String email;

    @ApiModelProperty(example = "2017")
    @JsonDeserialize(using = CustomSearchYearOfStudyDeserializer.class)
    private List<String> yearOfStudy;

    @ApiModelProperty(example = "01.2018")
    @JsonDeserialize(using = CustomSearchDateDeserializer.class)
    private SearchDate internshipDate;

    @ApiModelProperty(allowableValues = "true, false")
    private String internship;

    @ApiModelProperty(example = "01.2018")

    @JsonDeserialize(using = CustomSearchDateDeserializer.class)
    private SearchDate practiceDate;

    @ApiModelProperty(allowableValues = "true, false")
    private String practice;

    @ApiModelProperty(example = "01.2018")
    @JsonDeserialize(using = CustomSearchDateDeserializer.class)
    private SearchDate jobDate;

    @ApiModelProperty(allowableValues = "true, false")
    private String job;

    private String comment;

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

    public List<String> getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(List<String> yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public SearchDate getInternshipDate() {
        return internshipDate;
    }

    public void setInternshipDate(SearchDate internshipDate) {
        if(internshipDate != null){
            this.internship = "true";
        }
            this.internshipDate = internshipDate;
    }

    public String isInternship() {
        return internship;
    }

    public void setInternship(String internship) {
        this.internship = internship;
    }

    public SearchDate getPracticeDate() {
        return practiceDate;
    }

    public void setPracticeDate(SearchDate practiceDate) {
        if(practiceDate != null){
            this.practice = "true";
        }
            this.practiceDate =  practiceDate;
    }

    public String isPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public SearchDate getJobDate() {
        return jobDate;
    }

    public void setJobDate(SearchDate jobDate) {
        if(jobDate != null){
            this.job = "true";
        }
            this.jobDate =  jobDate;
    }

    public String isJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
