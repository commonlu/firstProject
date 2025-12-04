package com.gdou.hr_system.controller;

import com.gdou.hr_system.entity.Salary;
import com.gdou.hr_system.service.SalaryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class SalaryController {
    @Autowired
    private SalaryService salaryService;
    
    @GetMapping("/api/salaries")
    public List<Salary> selectAll()
    {
        return salaryService.selectAll();
    }
    
    @GetMapping("/api/salaries/export")
    public void exportSalaries(HttpServletResponse response) throws IOException {
        salaryService.exportSalaries(response);
        return;
    }

    /**
     * 获取薪资分布统计
     */
    @GetMapping("/api/salaries/distribution")
    public List<Map<String, Object>> getSalaryDistribution() {
        return salaryService.getSalaryDistribution();
    }

}
