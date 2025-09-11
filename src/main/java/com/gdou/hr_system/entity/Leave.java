package com.gdou.hr_system.entity;

import lombok.Data;

@Data
public class Leave {

    private Integer id;
    private Integer staffId;
    private Integer days;
    private Integer typeNum;
    private String startDate;
    private Integer status;
    private String remark;
    private String createTime;
    private String updateTime;
    private String employeeName;
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

}
