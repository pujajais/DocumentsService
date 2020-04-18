package com.puja.documents.dao;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.puja.documents.model.Revision;

public interface RevisionDao {

    Revision createRevision(MultipartFile file, int count, String notes) throws IOException;

	Revision getRevision(int id);

	Revision updateRevision(Revision revision);
    
}