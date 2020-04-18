package com.puja.documents.dao;

import java.util.List;

import com.puja.documents.model.Document;
import com.puja.documents.model.Revision;

public interface DocumentDao {

    Document createDocument(String name, Revision revision);
    
    Document addRevision(int documentId, Revision revision);
    
    List<Document> getDocuments();

    Document getDocument(int id);

	Document updateDocument(Document document);
    
}