package com.puja.documents.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.puja.documents.dao.DocumentDao;
import com.puja.documents.model.Document;
import com.puja.documents.model.Revision;

@Repository
public class DocumentDaoImpl implements DocumentDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public Document createDocument(String name, Revision revision) {
		Document document = new Document();
		document.setName(name);
		document.addToRevisions(revision);
		return entityManager.merge(document);		
	}

	@Transactional
	@Override
	public Document addRevision(int documentId, Revision revision) {
		Document document = entityManager.find(Document.class, documentId);
		document.addToRevisions(revision);
		
		return entityManager.merge(document);
	}

	@Transactional
	@Override
	public Document updateDocument(Document document) {
        return entityManager.merge(document);
        
	}

	
	@Override
	public List<Document> getDocuments() {
        return entityManager.createQuery("from Document", Document.class)
                .getResultList();
        
	}

	@Override
	public Document getDocument(int id) {
        return entityManager.find(Document.class, id);

	}

}
