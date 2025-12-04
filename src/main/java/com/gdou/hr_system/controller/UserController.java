package com.gdou.hr_system.controller;

import com.gdou.hr_system.mapper.EmployeeMapper;
import com.gdou.hr_system.mapper.LeaveMapper;
// 移除未使用的导入语句
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class UserController {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private LeaveMapper leaveMapper;

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        // 只需要获取这两个动态数据
        stats.put("totalEmployees", employeeMapper.countEmployees());
        stats.put("pendingLeaves", leaveMapper.countLeaves());

        return stats;
    }

}
