/*
MySQL Data Transfer
Source Host: localhost
Source Database: custome
Target Host: localhost
Target Database: custome
Date: 2010-11-8 16:40:16
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for catalogue
-- ----------------------------
DROP TABLE IF EXISTS `catalogue`;
CREATE TABLE `catalogue` (
  `channel_id` int(11) default NULL COMMENT '频道编号',
  `cata_name` varchar(50) default '' COMMENT '收藏夹名称',
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for channelinfo
-- ----------------------------
DROP TABLE IF EXISTS `channelinfo`;
CREATE TABLE `channelinfo` (
  `status` int(11) default '0' COMMENT '频道是否可用,0表示可用,1表示不可用',
  `name` varchar(50) default '' COMMENT '频道名称',
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fastreply
-- ----------------------------
DROP TABLE IF EXISTS `fastreply`;
CREATE TABLE `fastreply` (
  `fast_content` varchar(255) default '' COMMENT '快速回复内容',
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*
MySQL Data Transfer
Source Host: localhost
Source Database: custome
Target Host: localhost
Target Database: custome
Date: 2010-11-8 17:48:09
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for role_group
-- ----------------------------
DROP TABLE IF EXISTS `role_group`;
CREATE TABLE `role_group` (
  `status` int(1) default NULL,
  `name` varchar(50) default NULL,
  `id` int(10) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------

-- ----------------------------
-- Table structure for group_role
-- ----------------------------
DROP TABLE IF EXISTS `group_role`;
CREATE TABLE `group_role` (
  `role_function_id` int(10) default NULL,
  `group_id` int(10) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for replyinfo
-- ----------------------------
DROP TABLE IF EXISTS `replyinfo`;
CREATE TABLE `replyinfo` (
  `user_id` int(11) default NULL COMMENT '用户id',
  `reply_content` varchar(255) default '' COMMENT '反馈内容',
  `reply_time` datetime default NULL COMMENT '反馈时间',
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role_function
-- ----------------------------
DROP TABLE IF EXISTS `role_function`;
CREATE TABLE `role_function` (
  `id` int(10) NOT NULL auto_increment,
  `father_id` int(10) default NULL,
  `name` varchar(100) default NULL,
  `function_url` varchar(200) default NULL,
  `code` varchar(10) default NULL,
  `status` int(1) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 4096 kB';

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user` (
  `id` int(11) NOT NULL auto_increment,
  `nick_name` varchar(20) default NULL,
  `user_name` varchar(20) default NULL,
  `password` varchar(20) default NULL,
  `status` int(1) default '1',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for undoinfo
-- ----------------------------
/*
MySQL Data Transfer
Source Host: localhost
Source Database: custome
Target Host: localhost
Target Database: custome
Date: 2010-11-8 17:28:10
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for undoinfo
-- ----------------------------

CREATE TABLE `undoinfo` (
  `user_id` int(10) default NULL,
  `catalogue_id` int(10) default NULL COMMENT '收藏表的id,默认为0表示未收藏',
  `channel_id` int(11) default NULL COMMENT '频道信息的编号',
  `type` int(11) default NULL COMMENT '未处理信息所属的类别,0为投诉类,1为建议类,2为其他',
  `delete_type` int(11) default '0' COMMENT '对未处理信息的删除状态,0为未删除,1表示已经删除',
  `reply_type` int(11) default '0' COMMENT '对于未处理信息的回复状态,0为未回复,1为已回复',
  `undo_content` text COMMENT '未处理信息内容',
  `create_time` datetime default NULL COMMENT '未处理信息的创建时间',
  `phone_num` bigint(11) default NULL COMMENT '手机号码',
  `fetion_id` bigint(20) default NULL COMMENT '飞信ID',
  `ua` varchar(50) default '' COMMENT '手机类型',
  `nick_name` varchar(50) default '' COMMENT '户用昵称',
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(10) default NULL,
  `group_id` int(10) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

