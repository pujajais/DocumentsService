package com.puja.documents.model;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "revisions")
public class Revision implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @JsonIgnore
    @Lob
   private byte[] data;
   
    
    private String fileName;

    private String fileType;
    
    private int RevisionNumber;
    private String notes;
    private long timestamp;    
    private String downloadLink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public byte[] getData() {
        return this.data;
   }

    public void setData(MultipartFile file) throws IOException {
    	//String str = new String(file.getBytes(), "UTF-8");
        this.data = file.getBytes();
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(MultipartFile file) {
        this.fileName = file.getOriginalFilename();
    }
    
    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(MultipartFile file) {
        this.fileType = file.getContentType();
    }    
    
    public int getRevisionNumber() {
        return this.RevisionNumber;
    }

    public void setRevisionNumber(int count) {
        this.RevisionNumber = count;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp() {
        this.timestamp = System.currentTimeMillis();
    }

    public String getDownloadLink() {
        return this.downloadLink;
    }

    public void setDownloadLink() {
        this.downloadLink = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("document/revision/")
				.path(String.valueOf(this.getId()))
                .toUriString();
    	
    } 
}