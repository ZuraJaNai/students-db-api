package no.itera.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PERSONS")
public class Person extends AbstractPerson{


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
        super(id,"default","default","default","default",
                "default",null,null,null
                ,null,null,null,"default");
    }

    public Person() {

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
