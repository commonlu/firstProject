-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hr_db
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `announcements`
--

DROP TABLE IF EXISTS `announcements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `announcements` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `title` varchar(200) NOT NULL COMMENT '公告标题',
  `content` text NOT NULL COMMENT '公告内容',
  `level` int DEFAULT '0' COMMENT '优先级（数值越大越靠前显示）',
  `status` tinyint DEFAULT '1' COMMENT '状态：1-启用，0-停用',
  `start_time` datetime DEFAULT NULL COMMENT '生效开始时间（NULL表示立即生效）',
  `end_time` datetime DEFAULT NULL COMMENT '生效结束时间（NULL表示永久有效）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`),
  KEY `idx_level` (`level`),
  KEY `idx_time` (`start_time`,`end_time`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcements`
--

/*!40000 ALTER TABLE `announcements` DISABLE KEYS */;
INSERT INTO `announcements` VALUES (1,'系统维护通知','系统将于本周六（12月7日）晚上22:00-24:00进行例行维护，期间可能无法访问，请提前做好工作安排。',10,1,'2025-12-04 00:23:15','2025-12-11 00:23:15','2025-12-04 00:23:15'),(2,'元旦假期安排','根据公司安排，2024年12月30日至2025年1月1日为元旦假期，请各部门提前安排好工作。',5,1,'2025-12-04 00:23:15','2026-01-03 00:23:15','2025-12-04 00:23:15'),(3,'考勤打卡提醒','请各位员工每日按时打卡，迟到或忘记打卡需及时提交补卡申请。',3,1,'2025-12-04 00:23:15',NULL,'2025-12-04 00:23:15'),(4,'11月薪资发放通知','11月份薪资已发放，请各位员工注意查收。如有疑问，请联系财务部。',3,1,'2025-12-04 00:23:15','2025-12-14 00:23:15','2025-12-04 00:23:15'),(5,'新员工入职培训','本周五下午14:00将举行新员工入职培训，请新入职员工准时参加。',1,1,'2025-12-04 00:23:15','2025-12-07 00:23:15','2025-12-04 00:23:15'),(6,'系统维护通知','系统将于本周六（12月7日）晚上22:00-24:00进行例行维护，期间可能无法访问，请提前做好工作安排。',10,1,'2025-12-04 00:23:27','2025-12-11 00:23:27','2025-12-04 00:23:27'),(7,'元旦假期安排','根据公司安排，2024年12月30日至2025年1月1日为元旦假期，请各部门提前安排好工作。',5,1,'2025-12-04 00:23:30','2026-01-03 00:23:30','2025-12-04 00:23:30'),(8,'考勤打卡提醒','请各位员工每日按时打卡，迟到或忘记打卡需及时提交补卡申请。',3,1,'2025-12-04 00:23:34',NULL,'2025-12-04 00:23:34'),(9,'11月薪资发放通知','11月份薪资已发放，请各位员工注意查收。如有疑问，请联系财务部。',3,1,'2025-12-04 00:23:36','2025-12-14 00:23:36','2025-12-04 00:23:36'),(10,'新员工入职培训','本周五下午14:00将举行新员工入职培训，请新入职员工准时参加。',1,1,'2025-12-04 00:23:38','2025-12-07 00:23:38','2025-12-04 00:23:38');
/*!40000 ALTER TABLE `announcements` ENABLE KEYS */;

--
-- Table structure for table `att_attendance`
--

DROP TABLE IF EXISTS `att_attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `att_attendance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int DEFAULT NULL,
  `mor_start` time DEFAULT NULL,
  `mor_end` time DEFAULT NULL,
  `aft_start` time DEFAULT NULL,
  `aft_end` time NOT NULL,
  `attendance_date` date DEFAULT NULL,
  `status` int DEFAULT '1',
  `remark` varchar(100) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `att_attendance`
--

/*!40000 ALTER TABLE `att_attendance` DISABLE KEYS */;
INSERT INTO `att_attendance` VALUES (1,1,'09:00:00','18:00:00','18:30:00','19:00:00','2021-01-01',1,'正常','2020-12-31 16:00:00','2020-12-31 16:00:00'),(2,2,'09:00:00','18:00:00','18:30:00','19:00:00','2021-01-01',1,'正常','2020-12-31 16:00:00','2020-12-31 16:00:00');
/*!40000 ALTER TABLE `att_attendance` ENABLE KEYS */;

--
-- Table structure for table `att_leave`
--

DROP TABLE IF EXISTS `att_leave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `att_leave` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dept_id` int DEFAULT NULL,
  `days` int DEFAULT NULL,
  `type_num` int DEFAULT NULL,
  `status` int DEFAULT '1',
  `remark` varchar(100) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `att_leave`
--

/*!40000 ALTER TABLE `att_leave` DISABLE KEYS */;
INSERT INTO `att_leave` VALUES (1,1,1,1,1,'正常','2020-12-31 16:00:00','2020-12-31 16:00:00'),(2,2,1,1,1,'正常','2020-12-31 16:00:00','2020-12-31 16:00:00');
/*!40000 ALTER TABLE `att_leave` ENABLE KEYS */;

--
-- Table structure for table `att_staff_leave`
--

DROP TABLE IF EXISTS `att_staff_leave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `att_staff_leave` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int DEFAULT NULL,
  `days` int DEFAULT NULL,
  `type_num` int DEFAULT NULL,
  `start_date` timestamp NULL DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `remark` varchar(100) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `att_staff_leave`
--

/*!40000 ALTER TABLE `att_staff_leave` DISABLE KEYS */;
INSERT INTO `att_staff_leave` VALUES (1,1,3,1,'2020-12-31 16:00:00',1,'回家探亲','2020-12-31 16:00:00','2025-09-08 08:42:03'),(2,2,2,2,'2020-12-31 16:00:00',1,'生病','2020-12-31 16:00:00','2025-09-08 08:42:11'),(3,1,5,1,'2024-09-09 16:00:00',1,'年假-外出旅游','2025-09-09 08:53:41','2025-09-09 08:53:41'),(4,2,3,2,'2024-09-14 16:00:00',1,'病假-身体不适','2025-09-09 08:53:41','2025-09-09 08:53:41'),(5,5,1,3,'2024-09-19 16:00:00',0,'事假-办理证件（待审批）','2025-09-09 08:53:41','2025-09-09 08:53:41'),(6,6,2,1,'2024-09-24 16:00:00',1,'年假-休息调整','2025-09-09 08:53:41','2025-09-09 08:53:41'),(7,7,7,4,'2024-09-30 16:00:00',1,'婚假-结婚休假','2025-09-09 08:53:41','2025-09-09 08:53:41');
/*!40000 ALTER TABLE `att_staff_leave` ENABLE KEYS */;

--
-- Table structure for table `depts`
--

DROP TABLE IF EXISTS `depts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `depts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `status` int NOT NULL DEFAULT '1',
  `remark` varchar(100) NOT NULL,
  `parent_id` int NOT NULL,
  `morning_start` time NOT NULL,
  `morning_end` time NOT NULL,
  `afternoon_start` time NOT NULL,
  `afternoon_end` time NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `depts`
--

/*!40000 ALTER TABLE `depts` DISABLE KEYS */;
/*!40000 ALTER TABLE `depts` ENABLE KEYS */;

--
-- Table structure for table `docs`
--

DROP TABLE IF EXISTS `docs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `docs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `old_name` varchar(50) DEFAULT NULL,
  `md5` varchar(50) DEFAULT NULL,
  `size` int DEFAULT NULL,
  `staff_id` int DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docs`
--

/*!40000 ALTER TABLE `docs` DISABLE KEYS */;
INSERT INTO `docs` VALUES (1,'textdata1','1','data1','1',551,1,'测试文件1','2020-12-31 16:00:00','2025-09-09 06:43:17'),(2,'textdata2','2','data2','2',994,2,'测试文件2','2020-12-31 16:00:00','2025-09-09 06:43:17');
/*!40000 ALTER TABLE `docs` ENABLE KEYS */;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `pwd` varchar(50) NOT NULL,
  `birthday` date NOT NULL,
  `phone` varchar(20) NOT NULL,
  `address` varchar(100) NOT NULL,
  `remark` varchar(100) NOT NULL,
  `dept_id` int NOT NULL,
  `status` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'EMP001','张三','女','password123','1990-05-15','13800138001','北京市朝阳区','在职员工',1,0),(2,'EMP002','李四','女','password456','1992-08-20','13800138002','上海市浦东区','在职员工',2,1),(3,'EMP003','王五','男','password789','1995-02-10','13800138003','广东省深圳市','在职员工',1,1),(4,'EMP006','赵六','男','abc123','1988-11-25','13900139001','杭州市西湖区','资深工程师',3,1),(5,'EMP007','钱七','女','def456','1993-07-12','13900139002','广州市天河区','人事专员',4,1),(6,'EMP008','孙八','男','ghi789','1991-03-08','13900139003','成都市锦江区','财务主管',5,1),(7,'EMP009','周九','女','jkl012','1994-09-30','13900139004','武汉市江汉区','销售经理',6,1),(8,'EMP010','吴十','男','mno345','1989-12-15','13900139005','南京市玄武区','技术总监',3,1);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;

--
-- Table structure for table `logindata`
--

DROP TABLE IF EXISTS `logindata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logindata` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `UserName` varchar(20) NOT NULL,
  `PassWord` varchar(20) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logindata`
--

/*!40000 ALTER TABLE `logindata` DISABLE KEYS */;
INSERT INTO `logindata` VALUES (1,'admin','123456'),(2,'user','123456');
/*!40000 ALTER TABLE `logindata` ENABLE KEYS */;

--
-- Table structure for table `operation_logs`
--

DROP TABLE IF EXISTS `operation_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operation_logs` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `username` varchar(50) NOT NULL COMMENT '操作用户名',
  `role` varchar(20) DEFAULT 'user' COMMENT '用户角色',
  `operation_type` varchar(20) NOT NULL COMMENT '操作类型：add/update/delete/approve/reject等',
  `module` varchar(50) NOT NULL COMMENT '操作模块：employee/leave/salary/document等',
  `target_id` varchar(50) DEFAULT NULL COMMENT '操作对象ID（如员工ID、请假ID等）',
  `description` text COMMENT '操作描述',
  `ip_address` varchar(50) DEFAULT NULL COMMENT '操作IP地址',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `idx_username` (`username`),
  KEY `idx_module` (`module`),
  KEY `idx_operation_type` (`operation_type`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation_logs`
--

/*!40000 ALTER TABLE `operation_logs` DISABLE KEYS */;
INSERT INTO `operation_logs` VALUES (1,'admin','admin','add','employee','E001','新增员工：张三','127.0.0.1','2025-12-04 13:43:03');
/*!40000 ALTER TABLE `operation_logs` ENABLE KEYS */;

--
-- Table structure for table `per_role`
--

DROP TABLE IF EXISTS `per_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `per_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `per_role`
--

/*!40000 ALTER TABLE `per_role` DISABLE KEYS */;
INSERT INTO `per_role` VALUES (1,'1','1','1','2025-09-02 07:48:55','2025-09-02 07:48:55'),(2,'2','2','2','2025-09-02 07:48:55','2025-09-02 07:48:55'),(3,'1','1','1','2020-12-31 16:00:00','2020-12-31 16:00:00'),(4,'2','2','2','2020-12-31 16:00:00','2020-12-31 16:00:00');
/*!40000 ALTER TABLE `per_role` ENABLE KEYS */;

--
-- Table structure for table `role_menu`
--

DROP TABLE IF EXISTS `role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `path` varchar(100) DEFAULT NULL,
  `parent_id` int NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menu`
--

/*!40000 ALTER TABLE `role_menu` DISABLE KEYS */;
INSERT INTO `role_menu` VALUES (1,'1','1','1',1,'1','2020-12-31 16:00:00','2020-12-31 16:00:00'),(2,'2','2','2',2,'2','2020-12-31 16:00:00','2020-12-31 16:00:00');
/*!40000 ALTER TABLE `role_menu` ENABLE KEYS */;

--
-- Table structure for table `sal_salary`
--

DROP TABLE IF EXISTS `sal_salary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sal_salary` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int DEFAULT NULL,
  `base_salary` decimal(10,2) DEFAULT NULL,
  `subside` decimal(10,2) DEFAULT NULL,
  `bonus` decimal(10,2) DEFAULT NULL,
  `total_salary` decimal(10,2) DEFAULT NULL,
  `late_deduct` decimal(10,2) DEFAULT NULL,
  `leave_deduct` decimal(10,2) DEFAULT NULL,
  `leave_early_deduct` decimal(10,2) DEFAULT NULL,
  `absenteeism` decimal(10,2) DEFAULT NULL,
  `month` varchar(20) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sal_salary`
--

/*!40000 ALTER TABLE `sal_salary` DISABLE KEYS */;
INSERT INTO `sal_salary` VALUES (1,1,3000.00,500.00,1000.00,4500.00,20.00,0.00,0.00,0.00,'2021-01-01','1','2020-12-31 16:00:00','2025-09-08 07:37:30'),(2,2,3200.00,500.00,1000.00,4700.00,10.00,0.00,0.00,0.00,'2021-01-01','2','2020-12-31 16:00:00','2025-09-08 07:37:30'),(3,1,8500.00,500.00,1200.00,10200.00,50.00,0.00,30.00,0.00,'2024-09','9月工资-全勤','2025-09-09 08:50:16','2025-09-09 08:50:16'),(4,2,7800.00,500.00,800.00,9100.00,0.00,0.00,0.00,0.00,'2024-09','9月工资-表现优秀','2025-09-09 08:50:16','2025-09-09 08:50:16'),(5,5,9200.00,500.00,1500.00,11200.00,100.00,50.00,0.00,200.00,'2024-09','9月工资-迟到早退缺勤','2025-09-09 08:50:16','2025-09-09 08:50:16'),(6,6,7500.00,500.00,900.00,8900.00,0.00,0.00,0.00,0.00,'2024-09','9月工资-正常发放','2025-09-09 08:50:16','2025-09-09 08:50:16'),(7,7,6800.00,500.00,700.00,8000.00,30.00,0.00,20.00,0.00,'2024-09','9月工资-轻微迟到早退','2025-09-09 08:50:16','2025-09-09 08:50:16');
/*!40000 ALTER TABLE `sal_salary` ENABLE KEYS */;

--
-- Table structure for table `sal_salary_deduct`
--

DROP TABLE IF EXISTS `sal_salary_deduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sal_salary_deduct` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dept_id` int DEFAULT NULL,
  `type_num` int DEFAULT NULL,
  `deduct` int DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sal_salary_deduct`
--

/*!40000 ALTER TABLE `sal_salary_deduct` DISABLE KEYS */;
INSERT INTO `sal_salary_deduct` VALUES (1,1,1,1,'1','2020-12-31 16:00:00','2020-12-31 16:00:00'),(2,2,2,2,'2','2020-12-31 16:00:00','2020-12-31 16:00:00');
/*!40000 ALTER TABLE `sal_salary_deduct` ENABLE KEYS */;

--
-- Dumping routines for database 'hr_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-04 21:54:44
