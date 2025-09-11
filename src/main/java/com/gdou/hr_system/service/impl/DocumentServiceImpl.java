package com.gdou.hr_system.service.impl;

import com.gdou.hr_system.entity.Document;
import com.gdou.hr_system.mapper.DocumentMapper;
import com.gdou.hr_system.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentMapper documentMapper;
    @Override
    public List<Document> selectAll() {
        return documentMapper.selectAll();
    }

    @Override
    public List<Document> searchDocument(Document document) {
        return documentMapper.searchDocument(document);
    }

    @Override
    public int countTotalDocuments() {
        return documentMapper.countDocuments();
    }
    public int getTotalFileSize() {
        return documentMapper.getTotalFileSize();
    }
}
