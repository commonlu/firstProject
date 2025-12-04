package com.gdou.hr_system.controller;

import com.gdou.hr_system.entity.LoginData;
import com.gdou.hr_system.service.LoginDataService;
import com.gdou.hr_system.util.JwtUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/logindata")
public class LoginDataController {

    @Autowired
    private LoginDataService loginDataService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        // 从请求体中获取用户名和密码
        String userName = (String) requestBody.get("userName");
        String passWord = (String) requestBody.get("passWord");

        System.out.println("接收到的用户名: " + userName);
        System.out.println("接收到的密码: " + passWord);

        // 1. 先用数据库校验用户名和密码
        LoginData loginData = loginDataService.selectLoginData(userName, passWord);
        if (loginData == null) {
            // 账号或密码不匹配，返回失败
            result.put("success", false);
            result.put("message", "用户名或密码错误");
            return result;
        }

        // 2. 校验成功后，将用户基本信息放入 Session（可选，后续可以完全依赖 JWT 不用 Session）
        HttpSession session = request.getSession();
        session.setAttribute("username", userName);

        // 根据用户名判断角色（示例逻辑，后续可以从数据库字段读取角色）
        String role = "user"; // 默认为普通用户
        if ("admin".equals(userName)) {
            role = "admin"; // 管理员
        }
        session.setAttribute("role", role);

        // 3. 生成 JWT，后续前端会保存并在请求头中携带
        String token = JwtUtil.generateToken(userName, role);

        result.put("success", true);
        result.put("role", role);
        result.put("username", userName);
        result.put("token", token);

        return result;
    }
}

