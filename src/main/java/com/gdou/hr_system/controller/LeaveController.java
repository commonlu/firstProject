package com.gdou.hr_system.controller;

import com.gdou.hr_system.entity.Leave;
import com.gdou.hr_system.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LeaveController {
    @Autowired
    private LeaveService leaveService;
    @GetMapping("/api/leaves")
    public List<Leave> getAllLeaves(){
       return leaveService.selectAll();
    }
    @GetMapping("/api/leaves/search")
    public List<Leave> searchLeave(String employeeName, String staffId){
        return leaveService.searchLeave(employeeName,staffId);
    }
    @GetMapping("/api/leaves/stats")
    public Map<String, Integer> getLeaveStats() {
        Map<String, Integer> stats = new HashMap<>();

        // 各种请假类型的统计
        stats.put("事假", leaveService.countLeaveByType("1"));
        stats.put("病假", leaveService.countLeaveByType("2"));
        stats.put("年假", leaveService.countLeaveByType("3"));
        stats.put("调休", leaveService.countLeaveByType("4"));
        return stats;
    }
}
