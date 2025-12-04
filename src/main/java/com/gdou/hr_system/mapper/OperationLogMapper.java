package com.gdou.hr_system.mapper;

import com.gdou.hr_system.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperationLogMapper {
    /**
     * 插入操作日志
     */
    void insertOperationLog(OperationLog log);

    /**
     * 查询操作日志列表（支持分页和筛选）
     */
    List<OperationLog> selectOperationLogs(
            @Param("username") String username,
            @Param("module") String module,
            @Param("operationType") String operationType,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit
    );

    /**
     * 统计操作日志总数（用于分页）
     */
    int countOperationLogs(
            @Param("username") String username,
            @Param("module") String module,
            @Param("operationType") String operationType,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );
}

