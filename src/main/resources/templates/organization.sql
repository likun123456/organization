/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : organization

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 25/05/2022 18:17:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `actype` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `activityname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `starttime` datetime(0) NULL DEFAULT NULL,
  `endtime` datetime(0) NULL DEFAULT NULL,
  `place` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `promoter` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shouldnumber` int(0) NOT NULL,
  `realnumber` int(0) NULL DEFAULT NULL,
  `status` int(0) NOT NULL DEFAULT 0,
  `rejectdesc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 269 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (265, '科研', 'dabian', '2022-05-26 00:02:00', '2022-05-27 00:10:00', '504', '答辩', '张三', 6, 1, 0, NULL);
INSERT INTO `activity` VALUES (266, '知识竞赛', '答辩', '2022-05-25 00:04:00', '2022-05-22 10:37:27', NULL, NULL, '张三', 6, 0, 0, NULL);
INSERT INTO `activity` VALUES (267, '科研', '安康', '2022-05-30 00:00:00', '2022-05-31 00:00:00', '509', '答辩', '张三', 5, 1, 0, NULL);
INSERT INTO `activity` VALUES (268, '教学', '科研', '2022-06-01 00:02:00', '2022-06-02 00:00:00', '890', '教研', '张三', 5, 0, 0, NULL);
INSERT INTO `activity` VALUES (278, NULL, '家长会', '2022-05-25 17:23:23', '2022-05-25 17:23:25', '教室103', '教室103家长会', '张三', 6, 0, 1, NULL);

-- ----------------------------
-- Table structure for activity_teacher
-- ----------------------------
DROP TABLE IF EXISTS `activity_teacher`;
CREATE TABLE `activity_teacher`  (
  `activityid` int(0) NULL DEFAULT NULL,
  `userid` int(0) NULL DEFAULT NULL,
  `state` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity_teacher
-- ----------------------------
INSERT INTO `activity_teacher` VALUES (265, 5, 'A');
INSERT INTO `activity_teacher` VALUES (265, 7, 'B');
INSERT INTO `activity_teacher` VALUES (265, 8, 'A');
INSERT INTO `activity_teacher` VALUES (265, 9, 'B');
INSERT INTO `activity_teacher` VALUES (265, 10, 'A');
INSERT INTO `activity_teacher` VALUES (265, 4, 'A');
INSERT INTO `activity_teacher` VALUES (266, 5, 'B');
INSERT INTO `activity_teacher` VALUES (266, 7, 'B');
INSERT INTO `activity_teacher` VALUES (266, 8, 'B');
INSERT INTO `activity_teacher` VALUES (266, 9, 'B');
INSERT INTO `activity_teacher` VALUES (266, 10, 'B');
INSERT INTO `activity_teacher` VALUES (266, 4, 'B');
INSERT INTO `activity_teacher` VALUES (267, 5, 'B');
INSERT INTO `activity_teacher` VALUES (267, 7, 'A');
INSERT INTO `activity_teacher` VALUES (267, 9, 'A');
INSERT INTO `activity_teacher` VALUES (267, 10, 'A');
INSERT INTO `activity_teacher` VALUES (267, 4, 'A');
INSERT INTO `activity_teacher` VALUES (268, 36, 'A');
INSERT INTO `activity_teacher` VALUES (268, 37, 'A');
INSERT INTO `activity_teacher` VALUES (268, 38, 'A');
INSERT INTO `activity_teacher` VALUES (268, 39, 'A');
INSERT INTO `activity_teacher` VALUES (268, 40, 'A');
INSERT INTO `activity_teacher` VALUES (278, 7, 'B');
INSERT INTO `activity_teacher` VALUES (278, 8, 'B');
INSERT INTO `activity_teacher` VALUES (278, 9, 'B');
INSERT INTO `activity_teacher` VALUES (278, 10, 'B');
INSERT INTO `activity_teacher` VALUES (278, 4, 'A');
INSERT INTO `activity_teacher` VALUES (278, 7, 'B');

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `majorname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `craetetime` datetime(0) NULL DEFAULT NULL,
  `dean` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `userid` int(0) NULL DEFAULT NULL,
  `numbers` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE,
  CONSTRAINT `major_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES (1, '计算机科学与技术', '2022-02-26 13:23:57', '张三', 3, 7);
INSERT INTO `major` VALUES (2, '互联网工程系', '2022-02-26 13:45:09', '小明', 2, 6);
INSERT INTO `major` VALUES (3, '管理科学与工程系', '2022-02-26 13:46:41', '李老师', 6, 0);
INSERT INTO `major` VALUES (4, '电子商务系', '2022-03-01 13:23:19', '刘老师', 27, 5);
INSERT INTO `major` VALUES (5, '矿业工程系', '2022-03-01 13:31:38', '王老师', 28, 0);
INSERT INTO `major` VALUES (6, '经济与管理系', '2022-03-01 13:32:16', '张老师', 29, 0);
INSERT INTO `major` VALUES (7, '艺术与设计科学系', '2022-03-01 13:34:09', '钱钰钰', 30, 0);

-- ----------------------------
-- Table structure for major_teacher
-- ----------------------------
DROP TABLE IF EXISTS `major_teacher`;
CREATE TABLE `major_teacher`  (
  `majorid` int(0) NOT NULL,
  `userid` int(0) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of major_teacher
-- ----------------------------
INSERT INTO `major_teacher` VALUES (1, 5);
INSERT INTO `major_teacher` VALUES (1, 7);
INSERT INTO `major_teacher` VALUES (1, 8);
INSERT INTO `major_teacher` VALUES (1, 9);
INSERT INTO `major_teacher` VALUES (1, 10);
INSERT INTO `major_teacher` VALUES (2, 11);
INSERT INTO `major_teacher` VALUES (2, 12);
INSERT INTO `major_teacher` VALUES (2, 13);
INSERT INTO `major_teacher` VALUES (2, 14);
INSERT INTO `major_teacher` VALUES (2, 15);
INSERT INTO `major_teacher` VALUES (2, 16);
INSERT INTO `major_teacher` VALUES (3, 17);
INSERT INTO `major_teacher` VALUES (3, 18);
INSERT INTO `major_teacher` VALUES (3, 19);
INSERT INTO `major_teacher` VALUES (3, 20);
INSERT INTO `major_teacher` VALUES (3, 21);
INSERT INTO `major_teacher` VALUES (3, 22);
INSERT INTO `major_teacher` VALUES (1, 4);
INSERT INTO `major_teacher` VALUES (3, 23);
INSERT INTO `major_teacher` VALUES (4, 31);
INSERT INTO `major_teacher` VALUES (4, 32);
INSERT INTO `major_teacher` VALUES (4, 33);
INSERT INTO `major_teacher` VALUES (4, 34);
INSERT INTO `major_teacher` VALUES (4, 35);
INSERT INTO `major_teacher` VALUES (5, 36);
INSERT INTO `major_teacher` VALUES (5, 37);
INSERT INTO `major_teacher` VALUES (5, 38);
INSERT INTO `major_teacher` VALUES (5, 39);
INSERT INTO `major_teacher` VALUES (5, 40);
INSERT INTO `major_teacher` VALUES (6, 41);
INSERT INTO `major_teacher` VALUES (6, 42);
INSERT INTO `major_teacher` VALUES (6, 43);
INSERT INTO `major_teacher` VALUES (6, 44);
INSERT INTO `major_teacher` VALUES (6, 45);
INSERT INTO `major_teacher` VALUES (6, 46);
INSERT INTO `major_teacher` VALUES (7, 47);
INSERT INTO `major_teacher` VALUES (7, 48);
INSERT INTO `major_teacher` VALUES (7, 49);
INSERT INTO `major_teacher` VALUES (7, 50);
INSERT INTO `major_teacher` VALUES (7, 51);
INSERT INTO `major_teacher` VALUES (7, 53);
INSERT INTO `major_teacher` VALUES (2, 12);
INSERT INTO `major_teacher` VALUES (1, 7);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `accountname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rank` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, '小明', 'cxgg', '1009768549@qq.com', '123cxgg', 'C');
INSERT INTO `user` VALUES (3, '张三', 'wxq', NULL, 'wxq123', 'A');
INSERT INTO `user` VALUES (4, '王宇', 'wyu454', NULL, 'wyu123', 'B');
INSERT INTO `user` VALUES (5, '王青', 'wangqing', NULL, 'qwer123', 'B');
INSERT INTO `user` VALUES (6, '李老师', 'zhangsan', NULL, 'asdf45612', 'A');
INSERT INTO `user` VALUES (7, '刘靖宇', 'ljy456', NULL, 'ljy456', 'B');
INSERT INTO `user` VALUES (8, '王雪晴', 'wxq456', NULL, 'wxq456', 'B');
INSERT INTO `user` VALUES (9, '文卿', 'wq456', NULL, 'wq456789', 'B');
INSERT INTO `user` VALUES (10, '刘鑫', 'hhx45612', NULL, 'hhx45612', 'B');
INSERT INTO `user` VALUES (11, '刘裕兴', 'vvgvg1165165', NULL, 'vvgvg1165165', 'B');
INSERT INTO `user` VALUES (12, '王文婧', 'wqdsf', NULL, 'wqdsf', 'B');
INSERT INTO `user` VALUES (13, '刘文婧', 'sdzfg', NULL, 'sdzfg', 'B');
INSERT INTO `user` VALUES (14, '张文婧', 'fgddh24', NULL, 'fgddh24', 'B');
INSERT INTO `user` VALUES (15, '陌文婧', 'gdhfh451', NULL, 'gdhfh451', 'B');
INSERT INTO `user` VALUES (16, '钱舒晨', 'fghgfg', NULL, '416465', 'B');
INSERT INTO `user` VALUES (17, '王淑霞', 'ggdhfg', NULL, 'dgs64df464fsdgF', 'B');
INSERT INTO `user` VALUES (18, '王清初', 'sfgfgfd', NULL, 'faf1ad6f', 'B');
INSERT INTO `user` VALUES (19, '刘飒飒', 'dadfs64545', NULL, 'dadfs64545', 'B');
INSERT INTO `user` VALUES (20, '刘雨欣', 'fsdgfg1616', NULL, 'fs615615', 'B');
INSERT INTO `user` VALUES (21, '文初语', 'dfxg1615', NULL, 'dfds6f466', 'B');
INSERT INTO `user` VALUES (22, '文阿斯蒂芬', 'afd4f444666', NULL, 'afd4f64df', 'B');
INSERT INTO `user` VALUES (23, '赵倩', 'dsa1da1d4', NULL, 'das5f1a51f', 'B');
INSERT INTO `user` VALUES (24, '刘倩', 'ds5a1d615', NULL, 'add1a61', 'B');
INSERT INTO `user` VALUES (25, '刘卿鑫', 'asd6a51', NULL, 'fd1f6s5d1f65', 'B');
INSERT INTO `user` VALUES (26, '张三', 'fdf41d4', NULL, 'fsdf164645', 'B');
INSERT INTO `user` VALUES (27, '刘老师', 'yuyu', NULL, '123456', 'A');
INSERT INTO `user` VALUES (28, '王老师', 'wyuyu', NULL, '123456', 'A');
INSERT INTO `user` VALUES (29, '张钰钰', 'zyuyu', NULL, '123456', 'A');
INSERT INTO `user` VALUES (30, '钱老师', 'qyuyu', NULL, '123456', 'A');
INSERT INTO `user` VALUES (31, '刘宇', 'ldfsdf', NULL, '165165', 'B');
INSERT INTO `user` VALUES (32, '王宇', 'dsgsgyut', NULL, '165165', 'B');
INSERT INTO `user` VALUES (33, '张宇', 'f4s64g656', NULL, '16165', 'B');
INSERT INTO `user` VALUES (34, '李宇', 'fdf4676q4', NULL, 'a4aa1d6', 'B');
INSERT INTO `user` VALUES (35, '赵宇星', 'zyxuihiuh', NULL, '123456', 'B');
INSERT INTO `user` VALUES (36, '张涛琴', 'adaf651', NULL, 'c4dc44', 'B');
INSERT INTO `user` VALUES (37, '王涛琴', 'af651f15', NULL, '164141sa', 'B');
INSERT INTO `user` VALUES (38, '刘涛卿', 'asf6f15', NULL, 'df6dsf165', 'B');
INSERT INTO `user` VALUES (39, '李涛卿', 'dsa4d44', NULL, 'sad16a1', 'B');
INSERT INTO `user` VALUES (40, '赵涛卿', 'd16f565465', NULL, '123asda', 'B');
INSERT INTO `user` VALUES (41, '宋之余', 'adsa5d6151', NULL, 'ssdad5165', 'B');
INSERT INTO `user` VALUES (42, '王之余', 'fad6f516', NULL, 'aad56f65', 'B');
INSERT INTO `user` VALUES (43, '赵之余', 'da161115', NULL, 'cd14f', 'B');
INSERT INTO `user` VALUES (44, '李之余', 'ssd46664', NULL, 'sdaf46', 'B');
INSERT INTO `user` VALUES (45, '钱之余', 'sdf444dsd', NULL, 'af6ad1f', 'B');
INSERT INTO `user` VALUES (46, '孙之余', 'dsad444da77', NULL, 'das4a6f1', 'B');
INSERT INTO `user` VALUES (47, '李志宇', 'sda4659sdasd1', NULL, 'sda6d11', 'B');
INSERT INTO `user` VALUES (48, '王志程', 'd4sad46464', NULL, 'das6d6as5d65', 'B');
INSERT INTO `user` VALUES (49, '赵志程', 'd4sa6d461sd4', NULL, 's65d16d1', 'B');
INSERT INTO `user` VALUES (50, '李志程', 'asd6a5s65115', NULL, 'dsa4sd16a1sd', 'B');
INSERT INTO `user` VALUES (51, '张志程', 'afsaf5d4f4f', NULL, 'adf4adf4', 'B');
INSERT INTO `user` VALUES (53, '孙志程', 'da4f6af6a4f4', NULL, 'ada46a', 'B');
INSERT INTO `user` VALUES (57, '王初语', 'dfgdghjgf', NULL, 'af4d1g65sg', 'B');

SET FOREIGN_KEY_CHECKS = 1;
