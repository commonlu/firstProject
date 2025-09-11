package com.gdou.hr_system.entity;

import lombok.Data;

// 添加EasyExcel注解
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;

@Data
public class Salary {

    @ExcelProperty("ID")
    private Integer id;

    @ExcelProperty("员工ID")
    private Integer staffId;

    @ExcelProperty("基础工资")
    @NumberFormat("#.##")
    private Double baseSalary;//基础工资

    @ExcelProperty("生活补贴")
    @NumberFormat("#.##")
    private Double subside;//生活补贴

    @ExcelProperty("奖金")
    @NumberFormat("#.##")
    private Double bonus;//奖金

    @ExcelProperty("总工资")
    @NumberFormat("#.##")
    private Double totalSalary;//总工资

    @ExcelProperty("迟到罚款")
    @NumberFormat("#.##")
    private Double lateDeduct;//迟到罚款

    @ExcelProperty("早退罚款")
    @NumberFormat("#.##")
    private Double leaveDeduct;//早退罚款

    @ExcelProperty("月份")
    private String month;

    @ExcelProperty("员工姓名")
    private String employeeName;

    public String getEmployeeName(){
        return employeeName;
    };

    public void setEmployeeName(String employeeName){
        this.employeeName = employeeName;
    };
}
