package com.gdou.hr_system.entity;

import lombok.Data;

@Data
public class Document {

    private Integer id;
    private String name;
    private String type;
    private String oldName;
    private String md5;
    private Integer size;
    private Integer staffId;
    private String remark;
    private String createTime;
    private String updateTime;
    private String employeeName;
}
