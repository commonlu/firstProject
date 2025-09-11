package com.gdou.hr_system.mapper;

import com.gdou.hr_system.entity.LoginData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface LoginDataMapper {
     LoginData selectLoginData(@Param("UserName")String userName, @Param("PassWord")String passWord);
}
