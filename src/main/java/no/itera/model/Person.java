package no.itera.model;

import no.itera.util.CustomPersonEndDateDeserializer;
import no.itera.util.DateConstants;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "PERSONS")
public class Person extends AbstractPerson{

    @Transient
    public static final String DEFAULT = "default";

    @OneToOne(targetEntity = Attachment.class,cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Attachment photo;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "ATTACHMENTS", referencedColumnName = "PERSON_ID")
    private List<Attachment> attachments;

    public Person(AbstractPerson inputData){
        super(inputData.getId(),inputData.getLastName(),inputData.getFirstName(),inputData.getPatronymic(),
                inputData.getEmail(),inputData.getYearOfStudy(),inputData.getInternshipBegin(),
                inputData.getInternshipEnd(),inputData.getPracticeBegin(),inputData.getPracticeEnd(),
                inputData.getJobBegin(),inputData.getJobEnd(),inputData.getComment());
    }

    public Person(int id){
        super(id,DEFAULT,DEFAULT,DEFAULT,DEFAULT,
                DEFAULT,null,null,null
                ,null,null,null,DEFAULT);
    }

    public Person() {

    }

    public void updatePersonData(AbstractPerson person){

        if(StringUtils.isNoneEmpty(person.getLastName())){
            this.setLastName(person.getLastName());
        }
        if(StringUtils.isNoneEmpty(person.getFirstName())){
            this.setFirstName(person.getFirstName());
        }
        this.setPatronymic(person.getPatronymic());
        this.setEmail(person.getEmail());
        if(StringUtils.isNoneEmpty(person.getYearOfStudy())) {
            this.setYearOfStudy(person.getYearOfStudy());
        }
        this.setInternshipBegin(person.getInternshipBegin());
        this.setInternshipEnd(person.getInternshipEnd());
        this.setPracticeBegin(person.getPracticeBegin());
        this.setPracticeEnd(person.getPracticeEnd());
        this.setJobBegin(person.getJobBegin());
        this.setJobEnd(person.getJobEnd());
        this.setComment(person.getComment());
    }

    public void bulkUpdatePersonData(BulkChangePersonData data){
        if(StringUtils.isNoneEmpty(data.getYearOfStudy())) {
            this.setYearOfStudy(data.getYearOfStudy());
        }
        if(StringUtils.isNoneBlank(data.getInternshipBegin())) {
            this.setInternshipBegin(this.getDateOfString(data.getInternshipBegin(), true));
        }
        else if(data.getInternshipBegin() == null){
            this.setInternshipBegin(null);
        }
        if(StringUtils.isNoneBlank(data.getInternshipEnd())) {
            this.setInternshipEnd(this.getDateOfString(data.getInternshipEnd(), false));
        }
        else if(data.getInternshipEnd() == null){
            this.setInternshipEnd(null);
        }
        if(StringUtils.isNoneBlank(data.getPracticeBegin())) {
            this.setPracticeBegin(this.getDateOfString(data.getPracticeBegin(), true));
        }
        else if(data.getPracticeBegin() == null){
            this.setPracticeBegin(null);
        }
        if(StringUtils.isNoneBlank(data.getPracticeEnd())) {
            this.setPracticeEnd(this.getDateOfString(data.getPracticeEnd(), false));
        }
        else if(data.getPracticeEnd() == null){
            this.setPracticeEnd(null);
        }
        if(StringUtils.isNoneBlank(data.getJobBegin())) {
            this.setJobBegin(this.getDateOfString(data.getJobBegin(), true));
        }
        else if(data.getJobBegin() == null){
            this.setJobBegin(null);
        }
        if(StringUtils.isNoneBlank(data.getJobEnd())) {
            this.setJobEnd(this.getDateOfString(data.getJobEnd(), false));
        }
        else if(data.getJobEnd() == null){
            this.setJobEnd(null);
        }
    }

    private LocalDate getDateOfString(String dateString,boolean startDate){
        LocalDate date = LocalDate.parse(dateString, DateConstants.dateFormatterDeserialization);
        if(startDate){
            return date;
        }
        else {
            return date.withDayOfMonth(date.lengthOfMonth());
        }
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

    public Attachment getPhoto() {
        return photo;
    }

    public void setPhoto(Attachment photo) {
        this.photo = photo;
    }


}
