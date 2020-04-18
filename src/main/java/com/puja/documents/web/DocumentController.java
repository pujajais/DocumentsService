package com.puja.documents.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.puja.documents.APIOutput;
import com.puja.documents.dao.DocumentDao;
import com.puja.documents.dao.RevisionDao;
import com.puja.documents.model.Document;
import com.puja.documents.model.Revision;

@RestController
@RequestMapping("/document")
public class DocumentController {

	@Autowired
	private DocumentDao documentDao;

	@Autowired
	private RevisionDao revisionDao;

	// 1.Gets all documents.
	@GetMapping
	public List<APIOutput> getAllDocuments() {
		List<APIOutput> output = new ArrayList<APIOutput>();
		List<Document> documents = documentDao.getDocuments();
		for (Document document : documents) {
			output.add(APIOutput.convertDocument(document));
		}
		return output;
	}

	// 2.Gets a document.
	@GetMapping("/{docID}")
	public Document getDocument(@PathVariable Integer docID) {
		Document document = documentDao.getDocument(docID);
		List<Revision> revisions = document.getRevisions();
		for (Revision revision : revisions) {
			revision.setDownloadLink();
		}
		return document;
	}

	// 3. Add a document, which creates the first revision of the document.
	@PostMapping
	public APIOutput createNewDocument(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("notes") String notes) throws IOException {
		Revision revision = new Revision();
		revision.setData(file);
		revision.setRevisionNumber(1);
		revision.setNotes(notes);
		revision.setTimestamp();
		revision.setFileName(file);
		revision.setFileType(file);
		revision.setDownloadLink();
		return APIOutput.convertDocument(documentDao.createDocument(name, revision));
	}

	// 4. Add a new revision to a document.
	@PutMapping("/revision/{docID}")
	public APIOutput addRevision(@RequestParam("file") MultipartFile file, @PathVariable("docID") int documentId,
			@RequestParam("notes") String notes) throws IOException {
		Document document = documentDao.getDocument(documentId);
		int count = document.getRevisions().size() + 1;
		Revision revision = new Revision();
		revision.setData(file);
		revision.setRevisionNumber(count);
		revision.setNotes(notes);
		revision.setTimestamp();
		revision.setFileName(file);
		revision.setFileType(file);
		revision.setDownloadLink();
		return APIOutput.convertDocument(documentDao.addRevision(document.getId(), revision));

	}

	// 5.Edit a revision -- only the revision notes can be edited, not the file.
	@PutMapping("/notes/revision/{id}")
	public Revision editRevisionNotes(@PathVariable Integer id, @RequestBody Revision notes) throws IOException {
		Revision revision = revisionDao.getRevision(id);
		revision.setNotes(notes.getNotes());
		revision.setTimestamp();
		revision.setDownloadLink();
		return revisionDao.updateRevision(revision);
	}

	// 6. Download the file of a revision.
	@GetMapping("/revision/{id}")
	public ResponseEntity<Resource> editRevision(@PathVariable Integer id) throws IOException {
		Revision revision = revisionDao.getRevision(id);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + revision.getFileName() + "\"")
				.body(new ByteArrayResource(revision.getData()));

	}

}