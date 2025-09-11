package com.gdou.hr_system.mapper;

import com.gdou.hr_system.entity.Leave;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface LeaveMapper {
    List<Leave> selectAll();
    List<Leave> searchLeave(String employeeName,String staffId);
    @Select("SELECT COUNT(*) FROM att_staff_leave")
    int countLeaves();
    @Select("SELECT COUNT(*) FROM att_staff_leave WHERE type_num = #{type}")
    int countLeaveByType(String type);
}
