package no.itera.model;

import java.time.LocalDate;
import java.util.List;

public class BulkChangePersonData {

    private String yearOfStudy;

    private LocalDate internshipBegin;

    private LocalDate internshipEnd;

    private LocalDate practiceBegin;

    private LocalDate practiceEnd;

    private LocalDate jobBegin;

    private LocalDate jobEnd;

    private String comment;

    private List<Integer> personsId;

    public BulkChangePersonData(){}

//    public BulkChangePersonData(String yearOfStudy, String comment,LocalDate internshipBegin, LocalDate internshipEnd,
//                                LocalDate practiceBegin, LocalDate practiceEnd, LocalDate jobBegin, LocalDate jobEnd, List<Integer> personsId){
//        this.yearOfStudy = yearOfStudy;
//        this.internshipBegin = internshipBegin;
//        this.internshipEnd = internshipEnd;
//        this.practiceBegin = practiceBegin;
//        this.practiceEnd = practiceEnd;
//        this.jobBegin = jobBegin;
//        this.jobEnd = jobEnd;
//        this.comment = comment;
//        this.personsId = personsId;
//    }

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

    public List<Integer> getPersonsId() {
        return personsId;
    }

    public void setPersonsId(List<Integer> personsId) {
        this.personsId = personsId;
    }
}
