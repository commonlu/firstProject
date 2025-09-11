package com.gdou.hr_system.service;

import com.gdou.hr_system.entity.Employee;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface EmployeeService {
    // 获取所有员工
    List<Employee> getAllEmployees();

    // 根据ID获取员工
    Employee getEmployeeById(Integer id);

    //新增员工 (等你写完Controller，可以再来实现增删改)
    void addEmployee(Employee employee);

    //更新员工
    void updateEmployee(Integer id,Employee employee);

//    删除员工
    void deleteEmployee(Integer id);

    //    查询员工
    List<Employee> searchEmployee(Employee employee);

    void exportEmployees(HttpServletResponse response);

    int countEmployees();

    int countInactiveEmployees();
    int countActiveEmployees();
    int getGenderMan();
    int getGenderWomen();
}
