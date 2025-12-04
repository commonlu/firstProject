package com.gdou.hr_system.controller;

import com.gdou.hr_system.entity.Employee;
import com.gdou.hr_system.entity.OperationLog;
import com.gdou.hr_system.service.EmployeeService;
import com.gdou.hr_system.service.OperationLogService;
import com.gdou.hr_system.util.JwtUtil;
import com.gdou.hr_system.util.OperationLogUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 从请求头的 JWT 中解析出角色
     */
    private String extractRoleFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7);
        try {
            Claims claims = JwtUtil.parseToken(token);
            Object roleObj = claims.get("role");
            return roleObj != null ? roleObj.toString() : null;
        } catch (Exception e) {
            // 解析失败视为无有效角色
            return null;
        }
    }

    /**
     * 判断当前请求是否为管理员角色
     */
    private boolean isAdmin(HttpServletRequest request) {
        String role = extractRoleFromToken(request);
        return "admin".equals(role);
    }

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
    public String deleteEmployee(@PathVariable Integer id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return "权限不足：仅管理员可以删除员工";
        }
        employeeService.deleteEmployee(id);
        
        // 记录操作日志
        OperationLog log = OperationLogUtil.createLog(
                request, "delete", "employee", String.valueOf(id),
                "删除员工，员工ID: " + id
        );
        operationLogService.logOperation(log);
        
        return "删除员工成功";
    }

    @PostMapping
    public String addEmployee(@RequestBody Employee employee, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return "权限不足：仅管理员可以新增员工";
        }
        employeeService.addEmployee(employee);
        
        // 记录操作日志
        OperationLog log = OperationLogUtil.createLog(
                request, "add", "employee", employee.getCode() != null ? employee.getCode() : "unknown",
                "新增员工: " + (employee.getName() != null ? employee.getName() : "未知")
        );
        operationLogService.logOperation(log);
        
        return "添加员工成功";
    }

    @PutMapping("/{id}")
    public String updateEmployee(@PathVariable Integer id,
                                 @RequestBody Employee employee,
                                 HttpServletRequest request) {
        if (!isAdmin(request)) {
            return "权限不足：仅管理员可以编辑员工信息";
        }
        employeeService.updateEmployee(id, employee);
        
        // 记录操作日志
        OperationLog log = OperationLogUtil.createLog(
                request, "update", "employee", String.valueOf(id),
                "更新员工信息，员工ID: " + id + (employee.getName() != null ? "，姓名: " + employee.getName() : "")
        );
        operationLogService.logOperation(log);
        
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
    public void exportEmployees(HttpServletResponse response, HttpServletRequest request) throws IOException {
        if (!isAdmin(request)) {
            // 非管理员直接返回 403 文本提示
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("权限不足：仅管理员可以导出员工数据");
            return;
        }
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

    // 获取所有员工用于打印
    @GetMapping("/print")
    public List<Employee> printEmployees() {
        return employeeService.getAllEmployees();
    }

    // 获取当前用户信息
    @GetMapping("/current")
    public Map<String, Object> getCurrentUser(HttpServletRequest request) {
        Map<String, Object> currentUser = new HashMap<>();

        // 优先从 JWT 中解析当前登录用户信息
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String role = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = JwtUtil.parseToken(token);
                username = claims.getSubject();
                Object roleObj = claims.get("role");
                role = roleObj != null ? roleObj.toString() : null;
            } catch (Exception e) {
                // 如果解析失败，则退回到 Session 方案
            }
        }

        // 如果 JWT 中没有解析到，则尝试从 Session 中获取（兼容老逻辑）
        if (username == null || role == null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                username = (String) session.getAttribute("username");
                role = (String) session.getAttribute("role");
            }
        }

        if (username != null && role != null) {
            currentUser.put("role", role);
            currentUser.put("username", username);
        } else {
            // 如果Session中没有用户信息，设置默认值
            currentUser.put("role", "user"); // 默认为普通用户
            currentUser.put("username", "anonymous");
        }

        return currentUser;
    }
}
