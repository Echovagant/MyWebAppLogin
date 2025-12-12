-- --------------------------------------------------------
-- 数据库名: competition_db
-- --------------------------------------------------------

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `competition_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `competition_db`;

-- 1. 用户表 (users): 存储学生和管理员信息
CREATE TABLE `users` (
                         `id` INT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                         `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '登录名/学号',
                         `password` VARCHAR(100) NOT NULL COMMENT '密码 (此处为明文，生产环境应为哈希)',
                         `role` VARCHAR(10) NOT NULL COMMENT '角色: student/admin',
                         `name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
                         `student_id` VARCHAR(50) UNIQUE COMMENT '学号',
                         `major` VARCHAR(100) COMMENT '专业信息',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 初始化数据：管理员和学生账户
INSERT INTO `users` (`username`, `password`, `role`, `name`, `student_id`, `major`) VALUES
                                                                                        ('admin', '123456', 'admin', '系统管理员', NULL, NULL),
                                                                                        ('2331051544', '123456', 'student', '王小明', '2331051544', '软件工程');

-- 2. 竞赛信息表 (competitions): 存储竞赛类别、级别、时间
CREATE TABLE `competitions` (
                                `id` INT NOT NULL AUTO_INCREMENT COMMENT '竞赛ID',
                                `name` VARCHAR(100) NOT NULL COMMENT '竞赛类别名称 (如: 蓝桥杯)',
                                `level` VARCHAR(50) NOT NULL COMMENT '级别 (院级/校级/省级/全国)',
                                `form_type` VARCHAR(50) NOT NULL COMMENT '参赛形式 (个人赛/团体赛)',
                                `start_date` DATE NOT NULL COMMENT '报名开始日期',
                                `end_date` DATE NOT NULL COMMENT '报名截止日期',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='竞赛信息表';

-- 初始化数据：竞赛信息
INSERT INTO `competitions` (`name`, `level`, `form_type`, `start_date`, `end_date`) VALUES
                                                                                        ('电子商务三创赛', '省级赛', '团体赛', '2025-03-01', '2025-04-15'),
                                                                                        ('蓝桥杯大赛', '全国大赛', '个人赛', '2025-01-10', '2025-03-10'),
                                                                                        ('数学建模国际大赛', '全国大赛', '团体赛', '2025-04-01', '2025-06-30');

-- 3. 报名记录表 (registrations): 存储学生的报名详情
CREATE TABLE `registrations` (
                                 `id` INT NOT NULL AUTO_INCREMENT COMMENT '报名记录ID',
                                 `user_id` INT NOT NULL COMMENT '报名学生ID (FK to users)',
                                 `comp_id` INT NOT NULL COMMENT '竞赛ID (FK to competitions)',
                                 `team_name` VARCHAR(100) NULL COMMENT '团体赛队名',
                                 `registration_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
                                 `status` VARCHAR(20) NOT NULL DEFAULT 'Pending' COMMENT '报名状态: Pending/Approved/Rejected',
                                 PRIMARY KEY (`id`),
                                 FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
                                 FOREIGN KEY (`comp_id`) REFERENCES `competitions`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生报名记录表';

-- 4. 团队成员表 (team_members): 存储团体赛的其他成员信息
CREATE TABLE `team_members` (
                                `id` INT NOT NULL AUTO_INCREMENT COMMENT '成员ID',
                                `registration_id` INT NOT NULL COMMENT '报名记录ID (FK to registrations)',
                                `member_name` VARCHAR(50) NOT NULL COMMENT '成员姓名',
                                `member_student_id` VARCHAR(50) NOT NULL COMMENT '成员学号',
                                PRIMARY KEY (`id`),
                                FOREIGN KEY (`registration_id`) REFERENCES `registrations`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团体赛成员信息表';