package com.gdou.hr_system.controller;

import com.gdou.hr_system.entity.Announcement;
import com.gdou.hr_system.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    /**
     * 获取当前有效公告列表，用于首页滚动公告显示
     */
    @GetMapping("/api/announcements/active")
    public List<Announcement> getActiveAnnouncements() {
        try {
            return announcementService.getActiveAnnouncements();
        } catch (Exception e) {
            // 如果查询失败（比如表不存在），返回空列表而不是抛异常
            System.err.println("获取公告失败: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}


