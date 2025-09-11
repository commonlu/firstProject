package com.gdou.hr_system.mapper;
import com.gdou.hr_system.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
//关键注解，springboot会为它创建代理对象。
public interface EmployeeMapper {
    /**
     * 查询所有员工信息
     * @return 员工列表
     */

//因为要接受返回的数据是一个列表嘛，所以用List,这也是最常用的。
    List<Employee> selectAllEmployees();
    Employee selectEmployeeById(@Param("id") Integer id);
    void addEmployee(Employee employee);
    void updateEmployee(Integer id,Employee employee);
    void deleteEmployee(@Param("id") Integer id);
    List<Employee> searchEmployee(Employee employee);
    @Select("SELECT COUNT(*) FROM employees")
    int countEmployees();
    @Select("SELECT COUNT(*) FROM employees WHERE status = '0'")
    int countInactiveEmployees();
    @Select("SELECT COUNT(*) FROM employees WHERE status = '1'")
    int countActiveEmployees();
    @Select("SELECT COUNT(*) FROM employees WHERE gender = '男'")
    int getGenderMan();
    @Select("SELECT COUNT(*) FROM employees WHERE gender = '女'")
    int getGenderWomen();
}
