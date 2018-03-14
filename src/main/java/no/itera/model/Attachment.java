package no.itera.model;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class Attachment implements Serializable {

    @Column(name = "FILENAME")
    private String filename;

    @Column(name = "ATTACHMENT")//, columnDefinition = "LONGBLOB")
    @Lob
    private byte[] content;

    private Attachment(){}

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
