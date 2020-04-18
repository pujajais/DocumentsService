package com.puja.documents.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "document")
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String name;
    
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Revision> revisions = new ArrayList<Revision>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Revision> getRevisions() {
        return revisions;
    }

    public void addToRevisions(Revision revision) {
        this.revisions.add(revision);
    } 
}