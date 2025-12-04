-- ============================================
-- 操作日志表（operation_logs）建表SQL
-- ============================================

-- 创建操作日志表
CREATE TABLE IF NOT EXISTS `operation_logs` (
  `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '日志ID',
  `username` VARCHAR(50) NOT NULL COMMENT '操作用户名',
  `role` VARCHAR(20) DEFAULT 'user' COMMENT '用户角色',
  `operation_type` VARCHAR(20) NOT NULL COMMENT '操作类型：add/update/delete/approve/reject等',
  `module` VARCHAR(50) NOT NULL COMMENT '操作模块：employee/leave/salary/document等',
  `target_id` VARCHAR(50) NULL COMMENT '操作对象ID（如员工ID、请假ID等）',
  `description` TEXT NULL COMMENT '操作描述',
  `ip_address` VARCHAR(50) NULL COMMENT '操作IP地址',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  INDEX `idx_username` (`username`),
  INDEX `idx_module` (`module`),
  INDEX `idx_operation_type` (`operation_type`),
  INDEX `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统操作日志表';

-- ============================================
-- 插入测试数据（可选）
-- ============================================

-- 示例：插入一条测试日志
-- INSERT INTO `operation_logs` (`username`, `role`, `operation_type`, `module`, `target_id`, `description`, `ip_address`) 
-- VALUES ('admin', 'admin', 'add', 'employee', 'E001', '新增员工：张三', '127.0.0.1');

