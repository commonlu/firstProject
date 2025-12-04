package com.gdou.hr_system.service.impl;

import com.gdou.hr_system.entity.OperationLog;
import com.gdou.hr_system.mapper.OperationLogMapper;
import com.gdou.hr_system.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public void logOperation(OperationLog log) {
        operationLogMapper.insertOperationLog(log);
    }

    @Override
    public Map<String, Object> getOperationLogs(
            String username, String module, String operationType,
            String startTime, String endTime, Integer page, Integer pageSize) {
        
        // 默认分页参数
        if (page == null || page < 1) page = 1;
        if (pageSize == null || pageSize < 1) pageSize = 20;
        
        int offset = (page - 1) * pageSize;
        
        // 查询列表
        List<OperationLog> logs = operationLogMapper.selectOperationLogs(
                username, module, operationType, startTime, endTime, offset, pageSize
        );
        
        // 查询总数
        int total = operationLogMapper.countOperationLogs(
                username, module, operationType, startTime, endTime
        );
        
        Map<String, Object> result = new HashMap<>();
        result.put("list", logs);
        result.put("total", total);
        result.put("page", page);
        result.put("pageSize", pageSize);
        
        return result;
    }
}

