package com.gdou.hr_system.service;

import com.gdou.hr_system.entity.Salary;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface SalaryService {
    List<Salary> selectAll();
    //导出员工工资
    void exportSalaries(HttpServletResponse response);
}
