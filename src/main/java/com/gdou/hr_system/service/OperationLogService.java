package com.gdou.hr_system.service;

import com.gdou.hr_system.entity.OperationLog;

import java.util.List;
import java.util.Map;

public interface OperationLogService {
    /**
     * 记录操作日志
     */
    void logOperation(OperationLog log);

    /**
     * 查询操作日志列表（支持分页和筛选）
     */
    Map<String, Object> getOperationLogs(
            String username, String module, String operationType,
            String startTime, String endTime, Integer page, Integer pageSize
    );
}

