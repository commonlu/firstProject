package com.gdou.hr_system.service.impl;

import com.alibaba.excel.EasyExcel;
// import com.gdou.hr_system.entity.Employee;
import com.gdou.hr_system.entity.Salary;
import com.gdou.hr_system.mapper.SalaryMapper;
import com.gdou.hr_system.service.SalaryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalaryServiceImpl implements SalaryService {
    @Autowired
    private SalaryMapper salaryMapper;
    @Override
    public List<Salary> selectAll() {
        return salaryMapper.selectAll();
    }
    @Override
    public void exportSalaries(HttpServletResponse response) {
        List<Salary> salaries = salaryMapper.selectAll();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=salaries.xlsx");

        try {
            // 使用阿里巴巴EasyExcel简化操作
            EasyExcel.write(response.getOutputStream(), Salary.class)
                    .sheet("工资信息")
                    .doWrite(salaries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Map<String, Object>> getSalaryDistribution() {
        List<Salary> salaries = salaryMapper.selectAll();
        
        // 初始化薪资范围统计
        Map<String, Integer> distribution = new HashMap<>();
        distribution.put("<5K", 0);
        distribution.put("5K-10K", 0);
        distribution.put("10K-15K", 0);
        distribution.put("15K-20K", 0);
        distribution.put(">20K", 0);
        
        // 统计每个薪资范围的人数
        for (Salary salary : salaries) {
            if (salary.getTotalSalary() != null) {
                double total = salary.getTotalSalary();
                if (total < 5000) {
                    distribution.put("<5K", distribution.get("<5K") + 1);
                } else if (total < 10000) {
                    distribution.put("5K-10K", distribution.get("5K-10K") + 1);
                } else if (total < 15000) {
                    distribution.put("10K-15K", distribution.get("10K-15K") + 1);
                } else if (total < 20000) {
                    distribution.put("15K-20K", distribution.get("15K-20K") + 1);
                } else {
                    distribution.put(">20K", distribution.get(">20K") + 1);
                }
            }
        }
        
        // 转换为前端需要的格式
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(Map.of("range", "<5K", "count", distribution.get("<5K")));
        result.add(Map.of("range", "5K-10K", "count", distribution.get("5K-10K")));
        result.add(Map.of("range", "10K-15K", "count", distribution.get("10K-15K")));
        result.add(Map.of("range", "15K-20K", "count", distribution.get("15K-20K")));
        result.add(Map.of("range", ">20K", "count", distribution.get(">20K")));
        
        return result;
    }
}
