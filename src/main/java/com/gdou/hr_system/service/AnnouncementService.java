package com.gdou.hr_system.service;

import com.gdou.hr_system.entity.Announcement;

import java.util.List;

public interface AnnouncementService {

    List<Announcement> getActiveAnnouncements();
}


