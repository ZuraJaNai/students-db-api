package no.itera.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "FILE")
public class File {

    @Id
    @GeneratedValue()
    @Column(name = "FILE_ID",updatable = false, nullable = false)
    private int id;

    @Column(name = "CONTENT")
    @Lob
    private byte[] content;


    public File(){

    }

    public File(byte[] buffer){
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
