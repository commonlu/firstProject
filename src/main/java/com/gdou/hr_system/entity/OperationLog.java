package com.gdou.hr_system.entity;

public class OperationLog {
    private Integer id;
    /** 操作用户名 */
    private String username;
    /** 用户角色 */
    private String role;
    /** 操作类型：add/update/delete/approve/reject等 */
    private String operationType;
    /** 操作模块：employee/leave/salary/document等 */
    private String module;
    /** 操作对象ID（如员工ID、请假ID等） */
    private String targetId;
    /** 操作描述 */
    private String description;
    /** 操作IP地址 */
    private String ipAddress;
    /** 操作时间 */
    private String createTime;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

