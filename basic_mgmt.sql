/*
 Navicat Premium Data Transfer

 Source Server         : 本地MySQL
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : basic_mgmt

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 31/10/2023 16:32:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父ID',
  `dict_sort` int(4) NULL DEFAULT 1 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型(key)',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值(value)',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标记(0存在,1删除)',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'fa-dot-circle-o' COMMENT '图标',
  `create_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (100, NULL, 1, '标签名称', 'logo_title', 'BasicMgmtBoot', '0', 'fa-desktop', 'admin', '2023-10-11 17:33:19', '标签名称');
INSERT INTO `sys_dict_data` VALUES (101, NULL, 2, '系统标题', 'sys_title', '基础后台管理系统', '0', 'fa-bell', 'admin', '2023-10-11 17:33:07', '系统标题');
INSERT INTO `sys_dict_data` VALUES (102, NULL, 3, '性别标签', 'sys_user_gender', '', '0', 'fa-venus-mars', 'admin', '2023-10-10 23:14:20', '系统性别');
INSERT INTO `sys_dict_data` VALUES (103, 102, 1, '性别标签', 'sys_user_gender', '0', '0', 'fa-dot-circle-o', 'admin', '2023-09-30 23:20:27', '系统性别_男');
INSERT INTO `sys_dict_data` VALUES (104, 102, 2, '性别标签', 'sys_user_gender', '1', '0', 'fa-dot-circle-o', 'admin', '2023-09-30 23:20:28', '系统性别_女');
INSERT INTO `sys_dict_data` VALUES (105, 102, 3, '性别标签', 'sys_user_gender', '2', '0', 'fa-dot-circle-o', 'admin', '2023-09-30 23:20:29', '系统性别_保密');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 1 COMMENT '显示权重',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '路由地址',
  `is_frame` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 213 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '首页', 0, 1, '/home.html', '1', 'C', '1', NULL, 'fa-home', 'admin', '2023-09-23 15:36:30', 'admin', '2023-09-23 15:36:50', '首页');
INSERT INTO `sys_menu` VALUES (2, '系统管理', 0, 2, '#', '1', 'M', '1', NULL, 'fa-dot-circle-o', 'admin', '2023-09-23 15:38:30', 'admin', '2023-09-23 15:38:38', '系统管理');
INSERT INTO `sys_menu` VALUES (3, '系统监控', 0, 3, '#', '1', 'M', '1', NULL, 'fa-dot-circle-o', 'admin', '2023-09-23 15:40:08', 'admin', '2023-09-23 15:40:15', '系统监控');
INSERT INTO `sys_menu` VALUES (101, '用户管理', 2, 1, '/sys_user/user', '1', 'C', '1', NULL, 'fa-dot-circle-o', 'admin', '2023-09-23 15:45:47', 'admin', '2023-10-29 14:53:47', '用户管理');
INSERT INTO `sys_menu` VALUES (102, '角色管理', 2, 2, '/sys_role/role', '1', 'C', '1', NULL, 'fa-dot-circle-o', 'admin', '2023-09-23 15:47:00', 'admin', '2023-09-23 15:47:10', '角色管理');
INSERT INTO `sys_menu` VALUES (103, '字典管理', 2, 3, '/sys_dict_data/dict', '1', 'C', '1', NULL, 'fa-dot-circle-o', 'admin', '2023-09-23 15:47:00', 'admin', '2023-09-23 15:47:10', '字典管理');
INSERT INTO `sys_menu` VALUES (104, '菜单管理', 2, 4, '/sys_menu/menu', '1', 'C', '1', NULL, 'fa-dot-circle-o', 'admin', '2023-09-23 15:47:00', 'admin', '2023-09-23 15:47:10', '菜单管理');
INSERT INTO `sys_menu` VALUES (201, '在线用户', 3, 1, '/monitor/online', '1', 'C', '1', NULL, 'fa-dot-circle-o', 'admin', '2023-09-23 15:53:10', 'admin', '2023-10-30 15:03:41', '在线用户');
INSERT INTO `sys_menu` VALUES (203, '服务管理', 3, 2, '/monitor/server', '1', 'C', '1', NULL, 'fa-dot-circle-o', 'admin', '2023-09-23 15:53:10', 'admin', '2023-10-30 15:10:04', '服务管理');
INSERT INTO `sys_menu` VALUES (204, '外链测试', 0, 4, '#', '1', 'M', '1', NULL, 'fa-dot-circle-o', 'admin', '2023-09-23 16:05:39', 'admin', '2023-10-30 16:15:39', '外链测试');
INSERT INTO `sys_menu` VALUES (211, '百度外链', 204, 1, 'https://www.baidu.com/', '0', 'C', '1', NULL, 'fa-link', 'admin', '2023-09-23 16:05:39', 'admin', '2023-10-30 14:58:07', '百度外链');
INSERT INTO `sys_menu` VALUES (212, 'Layui外链', 204, 2, 'https://layui.dev/2.7/', '0', 'C', '1', NULL, 'fa-link', 'admin', '2023-09-23 16:05:39', 'admin', '2023-10-30 14:58:13', 'Layui开发页面外链');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (100, '超级管理员', 'administrator', '1', '0', '0', 'admin', '2023-06-14 22:37:23', 'admin', '2023-06-14 22:37:23', '超级管理员');
INSERT INTO `sys_role` VALUES (101, '管理员', 'manager', '1', '0', '0', 'admin', '2023-06-14 22:37:23', 'admin', '2023-06-14 22:37:23', '管理员');
INSERT INTO `sys_role` VALUES (102, '普通用户', 'common', '1', '0', '0', 'admin', '2023-06-14 22:37:23', 'admin', '2023-06-14 22:37:23', '普通用户');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表(1-N)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户昵称',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2保密）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像路径',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_name`(`user_name`) USING BTREE COMMENT 'user_id和user_name的值都唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic STATS_PERSISTENT = 0;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (100, 'admin', '测管理', 'test001@163.com', '15888888888', '0', '/assets/images/default-avatar-male.png', 'F865B53623B121FD34EE5426C792E5C33AF8C227', '0', '0', '192.168.34.2', '2023-10-31 08:03:44', 'admin', '2023-10-31 16:03:44', 'admin', '2023-10-31 16:03:44', '管理号');
INSERT INTO `sys_user` VALUES (101, 'manager', '测试号', 'test002@163.com', '15888888887', '0', '/assets/images/default-avatar-female.png', 'F865B53623B121FD34EE5426C792E5C33AF8C227', '0', '0', '0:0:0:0:0:0:0:1', '2023-10-30 13:46:39', 'admin', '2023-10-30 21:46:39', 'admin', '2023-10-30 21:46:39', '测试号');
INSERT INTO `sys_user` VALUES (102, 'admin111', 'admin111', 'abcd@qq.com', '17777777777', '0', '/upload/image/6ebc4a3f-009d-4037-a03e-35a34990bccf-loginbg.png', '03F6EFBFCEB6D61EB7BAC71BF9FE47ACB7867910', '0', '0', '', NULL, 'admin', '2023-09-29 20:49:45', 'admin', '2023-09-29 20:49:45', '');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`) USING BTREE COMMENT '一个用户只能有一个权限;'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表(N-1)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (100, 100);
INSERT INTO `sys_user_role` VALUES (101, 101);
INSERT INTO `sys_user_role` VALUES (102, 100);

SET FOREIGN_KEY_CHECKS = 1;
