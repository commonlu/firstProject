package com.gdou.hr_system.service.impl;

import com.gdou.hr_system.entity.Announcement;
import com.gdou.hr_system.mapper.AnnouncementMapper;
import com.gdou.hr_system.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public List<Announcement> getActiveAnnouncements() {
        return announcementMapper.selectActiveAnnouncements();
    }
}


