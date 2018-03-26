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

    @Column(name = "MIMETYPE")
    private String mimetype;

    @Column(name = "CONTENT")
    @Lob
    private byte[] content;

    @Column(name = "PERSON_ID", nullable = false)
    private int personId;

    @Column(name = "TYPE")
    private Type type;

    private Attachment(){}

    public Attachment(byte[] buffer, String originalFilename, String contentType,
                      int personId,Type type) {
        this.content = buffer;
        this.filename = originalFilename;
        this.mimetype = contentType;
        this.personId = personId;
        this.type = type;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
