package com.gdou.hr_system.controller;
import com.gdou.hr_system.entity.LoginData;
import com.gdou.hr_system.mapper.LoginDataMapper;
import com.gdou.hr_system.service.LoginDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logindata")
public class LoginDataController {
    @Autowired
    private LoginDataMapper loginDataMapper;

    @PostMapping("/login")
    public String login(@RequestBody LoginData loginData) {
        System.out.println("接收到的用户名: " + loginData.getuserName());
        System.out.println("接收到的密码: " + loginData.getpassWord());
        LoginData loginData1 = loginDataMapper.selectLoginData(loginData.getuserName(),loginData.getpassWord());
        if (loginData1!=null) {
            System.out.println("成功");
            return "1";
        } else {
            System.out.println("失败");
            return "0";
        }
    }




}
