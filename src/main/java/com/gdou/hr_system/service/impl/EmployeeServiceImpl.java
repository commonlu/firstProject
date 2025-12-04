package com.gdou.hr_system.service.impl;

import com.gdou.hr_system.entity.Employee;
import com.gdou.hr_system.mapper.EmployeeMapper;
import com.gdou.hr_system.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 添加Excel相关导入
import com.alibaba.excel.EasyExcel;
// import com.alibaba.excel.ExcelWriter;
// import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.IOException;
import java.util.List;
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired // 自动注入Mapper
    private EmployeeMapper employeeMapper;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeMapper.selectAllEmployees();
    }

    // 这里就是业务逻辑层，现在很简单，就是直接调用Mapper
    // 以后这里可以加入权限校验、数据加工、日志记录等复杂逻辑
    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeMapper.selectEmployeeById(id);
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeMapper.addEmployee(employee);
    }

    @Override
    public void updateEmployee(Integer id, Employee employee) {
        employeeMapper.updateEmployee(id, employee);
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeMapper.deleteEmployee(id);
    }

    @Override
    public List<Employee> searchEmployee(Employee employee) {
        return employeeMapper.searchEmployee(employee);
    }

    // 导出员工数据
    @Override
    public void exportEmployees(HttpServletResponse response) {
        List<Employee> employees = employeeMapper.selectAllEmployees();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=employees.xlsx");

        try {
            // 使用阿里巴巴EasyExcel简化操作
            EasyExcel.write(response.getOutputStream(), Employee.class)
                    .sheet("员工信息")
                    .doWrite(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int countEmployees() {
        return employeeMapper.countEmployees();
    }

    @Override
    public int countInactiveEmployees() {
        return employeeMapper.countInactiveEmployees();
    }
    @Override
    public int countActiveEmployees() {
        return employeeMapper.countActiveEmployees();
    }
    @Override
    public int getGenderMan() {
        return employeeMapper.getGenderMan();
    }
    @Override
    public int getGenderWomen() {
        return employeeMapper.getGenderWomen();
    }
}

