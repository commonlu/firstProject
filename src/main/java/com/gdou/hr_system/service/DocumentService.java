package com.gdou.hr_system.service;

import com.gdou.hr_system.entity.Document;

import java.util.List;

public interface DocumentService {
    public List<Document> selectAll();
    public List<Document> searchDocument(Document document);
    public int countTotalDocuments();
    public int getTotalFileSize();
}
