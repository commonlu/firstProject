package com.gdou.hr_system.service.impl;

import com.alibaba.excel.EasyExcel;
import com.gdou.hr_system.entity.Employee;
import com.gdou.hr_system.entity.Salary;
import com.gdou.hr_system.mapper.SalaryMapper;
import com.gdou.hr_system.service.SalaryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
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
}
