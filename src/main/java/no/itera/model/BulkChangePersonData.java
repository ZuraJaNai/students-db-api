package no.itera.model;

import java.util.List;

public class BulkChangePersonData {

    private String yearOfStudy;

    private String internshipBegin;

    private String internshipEnd;

    private String practiceBegin;

    private String practiceEnd;

    private String jobBegin;

    private String jobEnd;

    private List<Integer> personsId;

    public BulkChangePersonData(){
        //empty constructor needed for initialization
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(String yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getInternshipBegin() {
        return internshipBegin;
    }

    public void setInternshipBegin(String internshipBegin) {
        this.internshipBegin = internshipBegin;
    }

    public String getInternshipEnd() {
        return internshipEnd;
    }

    public void setInternshipEnd(String internshipEnd) {
        this.internshipEnd = internshipEnd;
    }

    public String getPracticeBegin() {
        return practiceBegin;
    }

    public void setPracticeBegin(String practiceBegin) {
        this.practiceBegin = practiceBegin;
    }

    public String getPracticeEnd() {
        return practiceEnd;
    }

    public void setPracticeEnd(String practiceEnd) {
        this.practiceEnd = practiceEnd;
    }

    public String getJobBegin() {
        return jobBegin;
    }

    public void setJobBegin(String jobBegin) {
        this.jobBegin = jobBegin;
    }

    public String getJobEnd() {
        return jobEnd;
    }

    public void setJobEnd(String jobEnd) {
        this.jobEnd = jobEnd;
    }

    public List<Integer> getPersonsId() {
        return personsId;
    }

    public void setPersonsId(List<Integer> personsId) {
        this.personsId = personsId;
    }
}
