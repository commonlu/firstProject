package com.gdou.hr_system.controller;

import com.gdou.hr_system.entity.Document;
import com.gdou.hr_system.entity.Employee;
import com.gdou.hr_system.entity.LoginData;
import com.gdou.hr_system.mapper.LoginDataMapper;
import com.gdou.hr_system.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // = @Controller + @ResponseBody (直接返回JSON数据，不跳转页面)
    @RequestMapping("/api/employees") // 这个Controller下所有接口的URL前缀
    public class EmployeeController {

    @Autowired // 自动注入Service
    private EmployeeService employeeService;

    /**
     * 获取所有员工列表的API接口
     * GET请求：http://localhost:8080/api/employees
     *
     * @return 员工列表JSON
     */
    @GetMapping // 处理GET请求，URL为 /api/employees
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    /**
     * 根据ID获取单个员工的API接口
     * GET请求：http://localhost:8080/api/employees/1
     *
     * @param id 路径中的员工ID
     * @return 单个员工的JSON
     */
    @GetMapping("/{id}") // 处理GET请求，URL为 /api/employees/1 (动态id)
    public Employee getEmployeeById(@PathVariable Integer id) { // @PathVariable 获取路径中的id
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return "删除员工成功";
    }

    @PostMapping
    public String addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
        return "添加员工成功";
    }

    @PutMapping("/{id}")
    public String updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        employeeService.updateEmployee(id, employee);
        return "更新员工成功";
    }

    @GetMapping("/search")
    public List<Employee> searchEmployee(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name) {
        Employee employee = new Employee();
        employee.setCode(code);
        employee.setName(name);

        return employeeService.searchEmployee(employee);
    }

    @GetMapping("/export")
    public void exportEmployees(HttpServletResponse response) throws IOException {
        employeeService.exportEmployees(response);
        return;
    }

    @GetMapping("/stats")
    public Map<String, Object> getEmployeeStats() {
        Map<String, Object> stats = new HashMap<>();

        // 员工总数
        int total = employeeService.countEmployees();
        stats.put("total", total);
        // 在职员工数
        int active = employeeService.countActiveEmployees();
        stats.put("active", active);
        // 离职员工数
        int inactive = employeeService.countInactiveEmployees();
        stats.put("inactive", inactive);
        //男性员工数量
        int mans = employeeService.getGenderMan();
        stats.put("malePercentage", mans);
        //女性员工数量
        int women = employeeService.getGenderWomen();
        stats.put("femalePercentage", women);

        return stats;
    }

    // 在 EmployeeController.java 中添加
    @GetMapping("/print")
    public List<Employee> printEmployees() {
        return employeeService.getAllEmployees();
    }

    // 在 EmployeeController.java 中添加获取当前用户信息的接口

//    @Autowired
//    private LoginDataMapper loginDataMapper;
//    @GetMapping("/current")
//    public Map<String, Object> getCurrentUser(@RequestBody LoginData loginData) {
//        Map<String, Object> currentUser = new HashMap<>();
//        System.out.println("接收到的用户名: " + loginData.getuserName());
//        System.out.println("接收到的密码: " + loginData.getpassWord());
//        // 这里需要根据实际的认证方式获取当前用户信息
//        // 示例：从SecurityContext或Session中获取
//
//        currentUser.put("role", loginData.getuserName()); // 或 "admin"
//        currentUser.put("username", loginData.getuserName());
//        return currentUser;
//    }

}

