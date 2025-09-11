package com.gdou.hr_system.service.impl;

import com.gdou.hr_system.entity.Leave;
import com.gdou.hr_system.mapper.LeaveMapper;
import com.gdou.hr_system.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveMapper leaveMapper;

    @Override
    public List<Leave> selectAll() {
        return leaveMapper.selectAll();
    }

    @Override
    public List<Leave> searchLeave(String employeeName, String staffId) {
         return leaveMapper.searchLeave(employeeName,staffId);
    }
    @Override
    public int countLeaveByType(String type) {
        return leaveMapper.countLeaveByType(type);
    }
}
