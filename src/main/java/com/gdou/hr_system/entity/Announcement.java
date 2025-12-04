package com.gdou.hr_system.entity;

public class Announcement {
    private Integer id;
    /** 公告标题 */
    private String title;
    /** 公告内容 */
    private String content;
    /** 优先级（数值越大越靠前） */
    private Integer level;
    /** 是否启用：1-启用，0-停用 */
    private Integer status;
    /** 生效开始时间（可选） */
    private String startTime;
    /** 生效结束时间（可选） */
    private String endTime;
    /** 创建时间 */
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}


