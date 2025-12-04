-- ============================================
-- 公告表（announcements）建表SQL
-- ============================================

-- 创建公告表
CREATE TABLE IF NOT EXISTS `announcements` (
  `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '公告ID',
  `title` VARCHAR(200) NOT NULL COMMENT '公告标题',
  `content` TEXT NOT NULL COMMENT '公告内容',
  `level` INT DEFAULT 0 COMMENT '优先级（数值越大越靠前显示）',
  `status` TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-停用',
  `start_time` DATETIME NULL COMMENT '生效开始时间（NULL表示立即生效）',
  `end_time` DATETIME NULL COMMENT '生效结束时间（NULL表示永久有效）',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  INDEX `idx_status` (`status`),
  INDEX `idx_level` (`level`),
  INDEX `idx_time` (`start_time`, `end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统公告表';

-- ============================================
-- 插入初始公告数据
-- ============================================

-- 公告1：系统维护通知（高优先级）
INSERT INTO `announcements` (`title`, `content`, `level`, `status`, `start_time`, `end_time`, `create_time`) 
VALUES (
  '系统维护通知',
  '系统将于本周六（12月7日）晚上22:00-24:00进行例行维护，期间可能无法访问，请提前做好工作安排。',
  10,
  1,
  NOW(),
  DATE_ADD(NOW(), INTERVAL 7 DAY),
  NOW()
);

-- 公告2：节假日安排（中优先级）
INSERT INTO `announcements` (`title`, `content`, `level`, `status`, `start_time`, `end_time`, `create_time`) 
VALUES (
  '元旦假期安排',
  '根据公司安排，2024年12月30日至2025年1月1日为元旦假期，请各部门提前安排好工作。',
  5,
  1,
  NOW(),
  DATE_ADD(NOW(), INTERVAL 30 DAY),
  NOW()
);

-- 公告3：考勤提醒（普通优先级）
INSERT INTO `announcements` (`title`, `content`, `level`, `status`, `start_time`, `end_time`, `create_time`) 
VALUES (
  '考勤打卡提醒',
  '请各位员工每日按时打卡，迟到或忘记打卡需及时提交补卡申请。',
  3,
  1,
  NOW(),
  NULL,
  NOW()
);

-- 公告4：薪资发放通知（普通优先级）
INSERT INTO `announcements` (`title`, `content`, `level`, `status`, `start_time`, `end_time`, `create_time`) 
VALUES (
  '11月薪资发放通知',
  '11月份薪资已发放，请各位员工注意查收。如有疑问，请联系财务部。',
  3,
  1,
  NOW(),
  DATE_ADD(NOW(), INTERVAL 10 DAY),
  NOW()
);

-- 公告5：培训通知（低优先级）
INSERT INTO `announcements` (`title`, `content`, `level`, `status`, `start_time`, `end_time`, `create_time`) 
VALUES (
  '新员工入职培训',
  '本周五下午14:00将举行新员工入职培训，请新入职员工准时参加。',
  1,
  1,
  NOW(),
  DATE_ADD(NOW(), INTERVAL 3 DAY),
  NOW()
);

-- 查询验证：查看所有启用的公告
-- SELECT * FROM announcements WHERE status = 1 ORDER BY level DESC, create_time DESC;

