package no.itera.model;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
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
        if(data.getYearOfStudy() != null) {
            this.setYearOfStudy(data.getYearOfStudy());
        }
        if(data.getInternshipBegin() != null) {
            this.setInternshipBegin(data.getInternshipBegin());
        }
        if(data.getInternshipEnd() != null) {
            this.setInternshipEnd(data.getInternshipEnd());
        }
        if(data.getPracticeBegin() != null) {
            this.setPracticeBegin(data.getPracticeBegin());
        }
        if(data.getPracticeEnd() != null) {
            this.setPracticeEnd(data.getPracticeEnd());
        }
        if(data.getJobBegin() != null) {
            this.setJobBegin(data.getJobBegin());
        }
        if(data.getJobEnd() != null) {
            this.setJobEnd(data.getJobEnd());
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
