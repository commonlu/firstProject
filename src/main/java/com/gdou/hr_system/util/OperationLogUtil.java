package com.gdou.hr_system.util;

import com.gdou.hr_system.entity.OperationLog;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

/**
 * 操作日志工具类：用于从请求中提取用户信息并创建日志对象
 */
public class OperationLogUtil {

    /**
     * 从请求中提取用户信息并创建操作日志对象
     */
    public static OperationLog createLog(
            HttpServletRequest request,
            String operationType,
            String module,
            String targetId,
            String description) {
        
        OperationLog log = new OperationLog();
        
        // 从请求头获取 JWT token
        String token = getTokenFromRequest(request);
        if (token != null) {
            try {
                Claims claims = JwtUtil.parseToken(token);
                log.setUsername(claims.getSubject()); // 用户名
                log.setRole((String) claims.get("role")); // 角色
            } catch (Exception e) {
                // 如果解析失败，使用默认值
                log.setUsername("unknown");
                log.setRole("unknown");
            }
        } else {
            log.setUsername("anonymous");
            log.setRole("unknown");
        }
        
        // 设置操作信息
        log.setOperationType(operationType);
        log.setModule(module);
        log.setTargetId(targetId);
        log.setDescription(description);
        
        // 获取IP地址
        log.setIpAddress(getClientIpAddress(request));
        
        return log;
    }

    /**
     * 从请求头中提取 JWT token
     */
    private static String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    /**
     * 获取客户端真实IP地址
     */
    private static String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，取第一个IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip != null ? ip : "unknown";
    }
}

