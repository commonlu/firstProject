package com.gdou.hr_system.service;

import com.gdou.hr_system.entity.Salary;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

public interface SalaryService {
    List<Salary> selectAll();
    //导出员工工资
    void exportSalaries(HttpServletResponse response);
    // 获取薪资分布统计
    List<Map<String, Object>> getSalaryDistribution();
}
