-- MySQL dump 10.13  Distrib 5.5.46, for osx10.8 (i386)
--
-- Host: localhost    Database: custome_new
-- ------------------------------------------------------
-- Server version	5.5.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bao_jian_shi`
--

DROP TABLE IF EXISTS `bao_jian_shi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bao_jian_shi` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '保健师号',
  `name` varchar(10) NOT NULL COMMENT '保健师姓名',
  `status` tinyint(4) NOT NULL COMMENT '1:正常,0:离开',
  `employe_no` varchar(10) NOT NULL COMMENT '雇员编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bao_jian_shi`
--

LOCK TABLES `bao_jian_shi` WRITE;
/*!40000 ALTER TABLE `bao_jian_shi` DISABLE KEYS */;
INSERT INTO `bao_jian_shi` VALUES (1,'11',1,'Y0001'),(2,'111',1,'Y0002'),(3,'ZHANGSNA',0,'Y0003'),(4,'小红',1,'Y004'),(5,'张三',1,'Y0005'),(9,'张三',1,'Y006');
/*!40000 ALTER TABLE `bao_jian_shi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_role`
--

DROP TABLE IF EXISTS `group_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_role` (
  `role_function_id` int(10) DEFAULT NULL,
  `group_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_role`
--

LOCK TABLES `group_role` WRITE;
/*!40000 ALTER TABLE `group_role` DISABLE KEYS */;
INSERT INTO `group_role` VALUES (4,4),(5,5),(6,6),(7,7),(8,8),(9,9),(10,10),(1,1),(2,1),(4,1),(6,1),(7,1),(54,1),(43,1),(50,1),(44,1),(48,1),(45,1),(49,1),(46,1),(51,1),(47,1),(55,1),(56,1),(57,1),(52,1),(53,1),(47,3),(55,3),(56,3),(57,3),(52,3),(53,3),(52,2),(53,2);
/*!40000 ALTER TABLE `group_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_product`
--

DROP TABLE IF EXISTS `order_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL COMMENT '订单号',
  `product_id` int(11) NOT NULL COMMENT '商品ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `num` int(11) NOT NULL,
  `price` decimal(8,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_product`
--

LOCK TABLES `order_product` WRITE;
/*!40000 ALTER TABLE `order_product` DISABLE KEYS */;
INSERT INTO `order_product` VALUES (109,28,1,'2015-10-24 16:44:04',3,10.00),(114,34,2,'2015-10-24 17:14:37',1,15.00),(115,34,1,'2015-10-24 17:14:37',2,10.00),(117,41,2,'2015-10-24 19:11:39',3,15.00),(119,42,1,'2015-10-25 16:18:53',10,10.00),(120,43,3,'2015-10-25 16:20:20',1,15.00),(121,44,3,'2015-10-25 16:25:43',1,15.00),(123,46,2,'2015-11-21 16:57:50',4,15.00);
/*!40000 ALTER TABLE `order_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` int(11) NOT NULL COMMENT '房间号',
  `persion_num` int(11) NOT NULL COMMENT '人数',
  `enter_time` datetime NOT NULL COMMENT '进入时间',
  `total_price` decimal(8,2) NOT NULL COMMENT '总价格',
  `jie_zhang` tinyint(4) NOT NULL COMMENT '是否结账 0否，1是',
  `leave_time` datetime DEFAULT NULL COMMENT '离开时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (28,1,2,'2015-10-24 16:42:05',108.00,1,'2015-10-24 16:44:35'),(32,3,1,'2015-10-24 16:50:12',0.00,1,NULL),(33,6,1,'2015-10-24 16:53:19',0.00,1,'2015-10-24 16:53:31'),(34,2,2,'2015-10-24 16:56:17',181.00,1,'2015-10-24 17:14:57'),(35,3,2,'2015-10-24 17:15:40',0.00,1,'2015-10-24 17:15:50'),(36,1,1,'2015-10-24 17:15:58',0.00,1,'2015-10-24 17:18:49'),(37,2,2,'2015-10-24 17:16:25',0.00,1,'2015-10-24 17:18:53'),(38,1,0,'2015-10-24 17:19:05',0.00,1,'2015-10-24 17:19:34'),(39,2,1,'2015-10-24 17:19:16',0.00,1,'2015-10-24 17:19:38'),(40,1,1,'2015-10-24 17:19:47',0.00,1,NULL),(41,1,2,'2015-10-24 17:20:08',113.00,1,'2015-10-24 19:12:48'),(42,1,3,'2015-10-25 16:18:27',236.00,1,'2015-10-25 16:20:49'),(43,2,2,'2015-10-25 16:19:39',151.00,1,'2015-10-25 16:20:53'),(44,1,2,'2015-10-25 16:25:10',151.00,1,'2015-10-25 16:26:23'),(45,1,1,'2015-10-25 16:51:28',0.00,1,'2015-10-25 16:53:26'),(46,1,3,'2015-11-21 16:51:43',176.00,0,NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '名称',
  `price` decimal(8,2) NOT NULL COMMENT '价格',
  `status` int(11) NOT NULL COMMENT '状态 0无效 1 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'中华烟',10.00,1),(2,'红星二锅头',15.00,1),(3,'好日子',15.00,0),(4,'饺子',20.00,1),(6,'水电费水电费 ',11.00,0);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_function`
--

DROP TABLE IF EXISTS `role_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_function` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `father_id` int(10) DEFAULT NULL COMMENT '如果不是0则寻找Value为ID的作为子节点',
  `name` varchar(100) DEFAULT NULL,
  `function_url` varchar(200) DEFAULT NULL COMMENT '如果点击节点不做任何反应只需默认为Null即可',
  `code` varchar(10) DEFAULT NULL COMMENT '根节点：0001标示',
  `status` int(1) DEFAULT NULL COMMENT '默认是0，不起作用吧',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 4096 kB';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_function`
--

LOCK TABLES `role_function` WRITE;
/*!40000 ALTER TABLE `role_function` DISABLE KEYS */;
INSERT INTO `role_function` VALUES (1,0,'系统管理','','0001',0),(2,1,'权限分组管理','./admin/role/rolegroup.jsp','0011',0),(4,1,'权限管理','./admin/role/rolefunction.jsp','0011',0),(6,1,'用户管理','./admin/role/roleuser.jsp','0011',0),(7,0,'雇员管理','','0001',0),(43,0,'客房管理','','0001',0),(44,0,'套餐管理','','0001',0),(45,0,'服务业务管理','','0001',0),(46,0,'消费商品管理','','0001',0),(47,0,'统计报表','','0001',0),(48,44,'套餐管理','./taocan/index.do','0011',0),(49,45,'服务项目管理','./service/index.do','0011',0),(50,43,'客房管理','./room/index.do','0011',0),(51,46,'商品管理','./product/index.do','0011',0),(52,0,'订单管理','','0001',0),(53,52,'客户订单管理','./order/index.do','0011',0),(54,7,'保健师管理','./baojianshi/index.do','0011',0),(55,47,'日统计图标','./report/index.do','0011',0),(56,47,'保健师服务对账表','./report/bjs_reckoning.do','0011',0),(57,47,'订单详细列表','./report/order.do','0011',0);
/*!40000 ALTER TABLE `role_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_group`
--

DROP TABLE IF EXISTS `role_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_group` (
  `status` int(1) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `id` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_group`
--

LOCK TABLES `role_group` WRITE;
/*!40000 ALTER TABLE `role_group` DISABLE KEYS */;
INSERT INTO `role_group` VALUES (0,'权限管理',1),(0,'操作员',2),(0,'高级操作员',3);
/*!40000 ALTER TABLE `role_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_user`
--

DROP TABLE IF EXISTS `role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(20) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `status` int(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_user`
--

LOCK TABLES `role_user` WRITE;
/*!40000 ALTER TABLE `role_user` DISABLE KEYS */;
INSERT INTO `role_user` VALUES (1,'管理员','admin','123456',1),(2,'测试','test','123456',1),(6,'张三','zhangsan','123456',1);
/*!40000 ALTER TABLE `role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '房间号',
  `name` varchar(10) NOT NULL COMMENT '房间名称',
  `persion_num` int(11) NOT NULL COMMENT '容纳人数',
  `status` int(11) NOT NULL COMMENT '状态0：空闲，1：使用中',
  `floor` tinyint(4) NOT NULL DEFAULT '1' COMMENT '楼层',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'1001 x3',3,1,1),(2,'1002 x3',3,0,1),(3,'1003 x1',1,0,1),(4,'1005 x3',1,0,1),(5,'2001 x2',1,0,2),(6,'3010',2,0,3),(7,'3011',1,0,2),(8,'1009',1,0,1),(9,'3008',3,0,3),(10,'2008',3,0,2);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '服务名称',
  `price` decimal(8,2) NOT NULL COMMENT '价格',
  `description` varchar(200) NOT NULL COMMENT '服务描述',
  `status` tinyint(4) NOT NULL COMMENT '状态 1有效 0 无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1,'足浴',10.00,'足浴足浴足浴足浴足浴足浴足浴足浴足浴足浴足浴足浴足浴足浴足浴足浴足浴足浴足浴足浴足浴足浴足浴',1),(2,'泰式足浴',20.00,'泰式足浴按摩\n泰式足浴按摩\n泰式足浴按摩\n泰式足浴按摩\n泰式足浴按摩',1),(3,'洗浴',10.00,'洗浴洗浴洗浴洗浴洗浴洗浴洗浴洗浴洗浴洗浴洗浴',1),(7,'泰式刮痧',50.00,'泰式刮痧泰式刮痧泰式刮痧泰式刮痧',0);
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_order`
--

DROP TABLE IF EXISTS `service_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `service_id` int(11) NOT NULL COMMENT '服务ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `bjs_id` int(11) NOT NULL COMMENT '保健师ID',
  `price` decimal(8,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=297 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_order`
--

LOCK TABLES `service_order` WRITE;
/*!40000 ALTER TABLE `service_order` DISABLE KEYS */;
INSERT INTO `service_order` VALUES (281,28,2,'2015-10-24 16:42:53',1,20.00),(283,34,2,'2015-10-24 17:14:08',1,20.00),(284,34,1,'2015-10-24 17:14:08',2,10.00),(285,41,1,'2015-10-24 19:11:17',1,10.00),(286,42,1,'2015-10-25 16:18:33',1,10.00),(287,42,1,'2015-10-25 16:18:33',2,10.00),(291,43,3,'2015-10-25 16:19:52',4,10.00),(292,43,3,'2015-10-25 16:19:52',4,10.00),(293,44,3,'2015-10-25 16:25:18',1,10.00),(294,44,3,'2015-10-25 16:25:18',1,10.00),(295,46,2,'2015-11-21 16:56:21',1,20.00),(296,46,2,'2015-11-21 16:56:21',1,20.00);
/*!40000 ALTER TABLE `service_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tao_can`
--

DROP TABLE IF EXISTS `tao_can`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tao_can` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '套餐名称',
  `price` decimal(8,2) NOT NULL COMMENT '价格',
  `description` varchar(200) NOT NULL COMMENT '描述',
  `status` tinyint(4) NOT NULL COMMENT '状态 0：无效 1：有效',
  `services_list` varchar(100) NOT NULL COMMENT '套餐服务项',
  `original_price` decimal(8,2) NOT NULL COMMENT '原价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tao_can`
--

LOCK TABLES `tao_can` WRITE;
/*!40000 ALTER TABLE `tao_can` DISABLE KEYS */;
INSERT INTO `tao_can` VALUES (1,'豪华套餐一',98.00,'111',1,'1',1.00),(2,'套餐一',58.00,'1',1,'1',88.00),(5,'实惠套餐',38.00,'1',0,'1',98.00),(8,'超级实惠套餐一',18.00,'dfsdfsdfsd',1,'1',99.00);
/*!40000 ALTER TABLE `tao_can` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taocan_order`
--

DROP TABLE IF EXISTS `taocan_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taocan_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `taocan_id` int(11) NOT NULL COMMENT '套餐ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `bjs_id` int(11) NOT NULL COMMENT '保健师ID',
  `price` decimal(8,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taocan_order`
--

LOCK TABLES `taocan_order` WRITE;
/*!40000 ALTER TABLE `taocan_order` DISABLE KEYS */;
INSERT INTO `taocan_order` VALUES (138,28,2,'2015-10-24 16:42:33',1,58.00),(140,34,2,'2015-10-24 17:14:12',1,58.00),(141,34,2,'2015-10-24 17:14:12',1,58.00),(142,41,2,'2015-10-24 19:11:22',1,58.00),(143,42,2,'2015-10-25 16:18:38',1,58.00),(144,42,2,'2015-10-25 16:18:38',2,58.00),(145,43,2,'2015-10-25 16:20:11',5,58.00),(146,43,2,'2015-10-25 16:20:11',5,58.00),(147,44,2,'2015-10-25 16:25:31',1,58.00),(148,44,2,'2015-10-25 16:25:31',1,58.00),(149,46,2,'2015-11-21 16:56:29',1,58.00),(150,46,8,'2015-11-21 16:56:29',1,18.00);
/*!40000 ALTER TABLE `taocan_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_id` int(10) DEFAULT NULL,
  `group_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(2,2),(5,1),(5,2),(6,3);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-27 21:37:56
