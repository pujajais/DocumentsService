package com.puja.documents.dao.jpa;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.puja.documents.dao.RevisionDao;
import com.puja.documents.model.Revision;


@Repository
public class RevisionDaoImpl implements RevisionDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	@Override
	public Revision createRevision(MultipartFile file, int count, String notes) throws IOException {
		Revision revision = new Revision();
		revision.setData(file);;
		revision.setRevisionNumber(count);
		revision.setNotes(notes);
		revision.setTimestamp();
		
		entityManager.merge(revision);
		
		return revision;
	}
	
	@Override
	public Revision getRevision(int id) {
        return entityManager.find(Revision.class, id);
	}

	@Transactional
	@Override
	public Revision updateRevision(Revision revision) {
        return entityManager.merge(revision);
	}
}