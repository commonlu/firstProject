package com.gdou.hr_system.entity;

import lombok.Data; // 导入Lombok
import java.time.LocalDate; // 用于日期字段

// 添加EasyExcel注解
import com.alibaba.excel.annotation.ExcelProperty;
// import com.alibaba.excel.annotation.format.NumberFormat;
// 移除未使用的导入语句
// 导入自定义转换器
import com.gdou.hr_system.util.LocalDateConverter;
@Data
public class Employee {
    //由于有了@Data注解，所以不需要getter和setter方法

    @ExcelProperty("员工ID")
    private Integer id;

    @ExcelProperty("员工编号")
    private String code;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("性别")
    private String gender;

    @ExcelProperty("密码")
    private String pwd;

    @ExcelProperty(value = "生日", converter = LocalDateConverter.class)
    private LocalDate birthday;


    @ExcelProperty("电话")
    private String phone;

    @ExcelProperty("地址")
    private String address;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("部门ID")
    private Integer deptId;

    @ExcelProperty("状态")
    private Integer status;
}
