package no.itera.model;

import javax.persistence.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoField.DAY_OF_YEAR;

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

@Entity
@Table(name = "PERSONS")
public class Person {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    @Column(name = "PERSON_ID",updatable = false, nullable = false)
    private int id;

    @Column(name = "PHOTO")
    @Lob
    private byte[] photo;

    @Column(nullable = false,name = "LASTNAME")
    private String lastName;

    @Column(nullable = false,name = "FIRSTNAME")
    private String firstName;

    @Column(nullable = false,name = "PATRONYMIC")
    private String patronymic;

    @Column(name = "EMAIL")
    private String email;

    @Column(nullable = false,name = "YEAR")
    private String yearOfStudy;

    @Column(name = "INTERNSHIP_BEGIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date internshipBegin;

    @Column(name = "INTERNSHIP_END")
    @Temporal(TemporalType.TIMESTAMP)
    private Date internshipEnd;

    @Column(name = "PRACTICE_BEGIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date practiceBegin;

    @Column(name = "PRACTICE_END")
    @Temporal(TemporalType.TIMESTAMP)
    private Date practiceEnd;

    @Column(name = "JOB_BEGIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date jobBegin;

    @Column(name = "JOB_END")
    @Temporal(TemporalType.TIMESTAMP)
    private Date jobEnd;

    @Column(name = "COMMENT")
    private String comment;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "ATTACHMENTS", referencedColumnName = "PERSON_ID")
    private List<Attachment> attachments;

    public Person(PersonInputData inputData){
        this.lastName = inputData.getLastName();
        this.firstName = inputData.getFirstName();
        this.patronymic = inputData.getPatronymic();
        this.email = inputData.getEmail();
        this.yearOfStudy = inputData.getYearOfStudy();
        this.internshipBegin = inputData.getInternshipBegin();
        this.internshipEnd = inputData.getInternshipEnd();
        this.practiceBegin = inputData.getPracticeBegin();
        this.practiceEnd = inputData.getPracticeEnd();
        this.jobBegin = inputData.getJobBegin();
        this.jobEnd = inputData.getJobEnd();
        this.comment = inputData.getComment();
    }
    public Person(String lastName, String firstName, String patronymic,
                  String email, String yearOfStudy, Date internshipBegin,
                  Date internshipEnd, Date practiceBegin, Date practiceEnd,
                  Date jobBegin, Date jobEnd, String comment){
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

    public Person(int id){
        String byDefalt = "default";
        this.id = id;
        this.lastName = byDefalt;
        this.firstName = byDefalt;
        this.patronymic = byDefalt;
        this.email = byDefalt;
        this.yearOfStudy = byDefalt;
        this.internshipBegin = new Date(10);
        this.internshipEnd = new Date(11);
        this.practiceBegin = new Date(10);
        this.practiceEnd = new Date(11);
        this.jobBegin = new Date(10);
        this.jobEnd = new Date(11);
        this.comment = byDefalt;
    }

    public Person() {

    }

    public String fullName(){
        return this.lastName + " " + this.firstName + " " + this.patronymic;
    }

    public int getId() {
        return id;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }


    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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
        if(internshipBegin != null){
            info.append(String.format("Internship begin: %s%n",internshipBegin.toString()));
        }
        if(internshipEnd != null){
            info.append(String.format("Internship end: %s%n",internshipEnd.toString()));
        }
        if(practiceBegin != null){
            info.append(String.format("Practice begin: %s%n",practiceBegin.toString()));
        }
        if(practiceEnd != null){
            info.append(String.format("Practice end: %s%n",practiceEnd.toString()));
        }
        if(jobBegin != null){
            info.append(String.format("Job begin: %s%n",jobBegin.toString()));
        }
        if(jobEnd != null){
            info.append(String.format("Job end: %s%n",jobEnd.toString()));
        }
        return info.toString();
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
}
