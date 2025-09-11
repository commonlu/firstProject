package com.gdou.hr_system.service;

import com.gdou.hr_system.entity.LoginData;

public interface LoginDataService {
    LoginData selectLoginData(String userName, String passWord);
}
