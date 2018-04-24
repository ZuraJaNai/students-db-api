package no.itera.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FILE")
public class AttachmentContent implements Serializable{

    @Id
    @GeneratedValue()
    @Column(name = "FILE_ID",updatable = false, nullable = false)
    private int id;

    @Type(type="org.hibernate.type.BinaryType")
    private byte[] content;


    public AttachmentContent(){

    }

    public AttachmentContent(byte[] buffer){
        this.content = buffer;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }
}

