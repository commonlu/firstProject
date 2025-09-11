package com.gdou.hr_system.mapper;

import com.gdou.hr_system.entity.Salary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SalaryMapper {
    List<Salary> selectAll( );
}
