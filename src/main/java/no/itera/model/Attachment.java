package no.itera.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ATTACHMENT")
public class Attachment implements Serializable {

    @Id
    @GeneratedValue()
    @Column(name = "ATTACHMENT_ID",updatable = false, nullable = false)
    private int id;

    @Column(name = "FILENAME")
    private String filename;

    @Column(name = "MIMETYPE")
    private String mimetype;

    @OneToOne(fetch = FetchType.LAZY, optional=false, cascade=CascadeType.ALL)
    @JsonIgnore
    private AttachmentContent file;

    @Column(name = "PERSON_ID", nullable = false)
    private int personId;

    @Column(name = "TYPE")
    private AttachmentType attachmentType;

    private Attachment(){}

    public Attachment(byte[] buffer, String originalFilename, String contentType,
                      int personId,AttachmentType attachmentType) {
        this.file = new AttachmentContent(buffer);
        this.filename = originalFilename;
        this.mimetype = contentType;
        this.personId = personId;
        this.attachmentType = attachmentType;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public int getId() {
        return id;
    }

    public int getPersonId() {
        return personId;
    }

    public AttachmentType getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

    public void setFile(AttachmentContent file) {
        this.file = file;
    }

    public AttachmentContent getFile() {
        return file;
    }
}
