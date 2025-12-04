package com.gdou.hr_system.controller;

import com.gdou.hr_system.service.OperationLogService;
import com.gdou.hr_system.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/operation-logs")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 获取操作日志列表（仅管理员可访问）
     */
    @GetMapping
    public Map<String, Object> getOperationLogs(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String module,
            @RequestParam(required = false) String operationType,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            HttpServletRequest request) {
        
        // 权限检查：仅管理员可查看
        if (!isAdmin(request)) {
            return Map.of("error", "权限不足：仅管理员可以查看操作日志");
        }
        
        return operationLogService.getOperationLogs(
                username, module, operationType, startTime, endTime, page, pageSize
        );
    }

    /**
     * 检查是否为管理员
     */
    private boolean isAdmin(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                Claims claims = JwtUtil.parseToken(token);
                String role = (String) claims.get("role");
                return "admin".equals(role);
            }
        } catch (Exception e) {
            // 解析失败，返回false
        }
        return false;
    }
}

