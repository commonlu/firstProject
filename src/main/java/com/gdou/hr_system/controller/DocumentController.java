package com.gdou.hr_system.controller;

import com.gdou.hr_system.entity.Document;
import com.gdou.hr_system.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class DocumentController {

    @Autowired
    private DocumentService documentService;
    @RequestMapping("/api/documents")
    public List<Document> selectAll()
    {
        return documentService.selectAll();
    }
    @RequestMapping("/api/documents/search")
    public List<Document> searchDocument(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) int staffId)
    {
        Document document = new Document();
        document.setName(name);
        document.setStaffId(staffId);
        return documentService.searchDocument(document);
    }

    @GetMapping("/api/documents/stats")
    public Map<String, Object> getDocumentStats() {
        Map<String, Object> stats = new HashMap<>();

        // 文件总数
        int totalCount = documentService.countTotalDocuments();
        stats.put("totalCount", totalCount);

        // 总文件大小（MB）
        double totalSize = documentService.getTotalFileSize();
        stats.put("totalSize",totalSize);

        return stats;
    }
}
