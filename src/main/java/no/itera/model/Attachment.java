package no.itera.model;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Access(AccessType.PROPERTY)
public class Attachment implements Serializable {

    @Column(name = "FILENAME")
    private String filename;

    @Column(name = "ATTACHMENT", length = 999999999)
    @Lob
    private byte[] content;

    public Attachment(byte[] buffer, String originalFilename) {
        this.content = buffer;
        this.filename = originalFilename;
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
}
