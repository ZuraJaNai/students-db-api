package no.itera.model;

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

    @Column(name = "TYPE")
    private String type;

    @Column(name = "CONTENT")
    @Lob
    private byte[] content;

    @Column(name = "PERSON_ID", nullable = false)
    private int personId;

    private Attachment(){}

    public Attachment(byte[] buffer, String originalFilename, String contentType,int personId) {
        this.content = buffer;
        this.filename = originalFilename;
        this.type = contentType;
        this.personId = personId;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getPersonId() {
        return personId;
    }
}
