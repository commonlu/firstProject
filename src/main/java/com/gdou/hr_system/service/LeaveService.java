package com.gdou.hr_system.service;

import com.gdou.hr_system.entity.Leave;

import java.util.List;

public interface LeaveService {
    List<Leave> selectAll();
    List<Leave> searchLeave(String employeeName,String staffId);
    int countLeaveByType(String type);
}
