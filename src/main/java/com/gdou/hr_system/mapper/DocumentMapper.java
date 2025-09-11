package com.gdou.hr_system.mapper;

import com.gdou.hr_system.entity.Document;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface DocumentMapper {
    List<Document> selectAll();
    List<Document> searchDocument(Document document);
    @Select("SELECT COUNT(*) FROM docs")
    int  countDocuments();
    @Select("SELECT SUM(size) FROM docs")
    int getTotalFileSize();
}
