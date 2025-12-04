package com.gdou.hr_system.mapper;

import com.gdou.hr_system.entity.Announcement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface  AnnouncementMapper {

    /**
     * 查询当前有效公告（已启用，且在有效期内或未设置有效期），按优先级和创建时间排序
     */
    List<Announcement> selectActiveAnnouncements();
}


