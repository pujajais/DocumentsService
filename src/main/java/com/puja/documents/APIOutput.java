package com.puja.documents;

import com.puja.documents.model.Document;

public class APIOutput {
	
	public String name;
	public int totalRevisions;
	public long lastTimestamp;
	
	public APIOutput(String name, int totalRevisions, long lastTimestamp) {
		this.name = name;
		this.totalRevisions = totalRevisions;
		this.lastTimestamp = lastTimestamp;
	}
	
	public static APIOutput convertDocument(Document document) {
		return new APIOutput (document.getName(),
				document.getRevisions().size(), 
				document.getRevisions().get(document.getRevisions().size() -1).getTimestamp());
	}
}
