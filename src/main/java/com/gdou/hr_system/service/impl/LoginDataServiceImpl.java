package com.gdou.hr_system.service.impl;

import com.gdou.hr_system.entity.LoginData;
import com.gdou.hr_system.mapper.LoginDataMapper;
import com.gdou.hr_system.service.LoginDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginDataServiceImpl implements LoginDataService {
    @Autowired
    private LoginDataMapper loginDataMapper;
    @Override
    public LoginData selectLoginData(String userName, String passWord) {
        return loginDataMapper.selectLoginData(userName,passWord);
    }

}
