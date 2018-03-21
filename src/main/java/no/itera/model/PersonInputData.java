package no.itera.model;

public class PersonInputData {

    private String lastName;

    private String firstName;

    private String patronymic;

    private String email;

    private String yearOfStudy;

    private String internshipBegin;

    private String internshipEnd;

    private String practiceBegin;

    private String practiceEnd;

    private String jobBegin;

    private String jobEnd;

    private String comment;

    public PersonInputData(String lastName, String firstName, String patronymic,
                           String email, String yearOfStudy, String internshipBegin,
                           String internshipEnd, String practiceBegin,
                           String practiceEnd, String jobBegin, String jobEnd,
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
