/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 8.0.26 : Database - bicycleproject
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bicycleproject` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `bicycleproject`;

/*Table structure for table `commodity` */

DROP TABLE IF EXISTS `commodity`;

CREATE TABLE `commodity` (
  `commodityId` int NOT NULL AUTO_INCREMENT,
  `commodityName` varchar(60) NOT NULL,
  `commodityType` int NOT NULL,
  `pictureId` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `introduce` varchar(400) NOT NULL,
  `stock` int NOT NULL,
  `sold` int NOT NULL DEFAULT (2),
  `price` float(8,2) NOT NULL,
  PRIMARY KEY (`commodityId`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `commodity` */

insert  into `commodity`(`commodityId`,`commodityName`,`commodityType`,`pictureId`,`introduce`,`stock`,`sold`,`price`) values 
(10,'ATX20-S',0,'646c70e369f2e9256d03d56d','突破自我 经典重塑，专为青少年定制的轻量化铝合金车架，强度和重量比进一步优化，同时采用传统管件成型与热处理工艺，保证骑行品质',20,0,1298.00),
(11,'ATX610',0,'646c717269f2e9256d03d56f','GIANT ALUXX轻量化铝合金技术打造， 坚固耐用的车架能够达到长久的陪伴。专为青少年设计的车架，符合青少年的骑乘几何。转把方便青少年使用，变速快速、精准。专用青少年坐垫更加适合青少年骑行姿势，在单车上挥洒汗水。',20,1,1598.00),
(12,'ATX620',0,'646c716c69f2e9256d03d56e','Giant ALUXX铝合金车架，全方位运动竞速等级铝合金科技，强度重量比进一步优化，同时采用传统管件与热处理及焊接科技，保证骑乘品质，广大骑友在骑乘ATX620感受到轻快的同时，带来速度上的畅快感受，优秀的整车刚性为安全加码。',20,0,1598.00),
(13,'ATX660',0,'646ae9f8e648cc5e345e80e8','突破自我 经典重塑,运动竞速等级铝合金车架,强度与重量比进一步优化,同时采用传统管件成型与热处理工艺,保证骑行品质。崎岖路面 任我穿行,安全可靠的高碳钢避震前叉,良好的避震性能让你无惧震感,平稳骑行。',20,2,1898.00),
(14,'Geek × EVA',0,'646aea96e648cc5e345e80e9','联名设计 闪耀来袭，GEEK与新世纪福音战士联名合作，并设计全新涂装，整车配色采用福音战士主打色，并加入EVA元素。',20,2,2598.00),
(15,'Revolt F 1',0,'646aeb30e648cc5e345e80ea','舒适性 类GRAVEL车型的舒适几何，满足城市通勤和短距离郊游宽胎设计，通过性、稳定性大大提高。减震D性坐垫杆，充分吸收骑行过程中的震动。',20,2,4998.00),
(16,'Revolt F 2',0,'646aebc9e648cc5e345e80eb','舒适性 类GRAVEL车型的舒适集合，满足城市通勤和短距离交由宽胎设计，通过性，稳定性大大提高，减震D型坐垫杆，充分吸收骑行过程中的震动\n油压碟刹 不论是晴天、雨天，还是面对爬坡之后的下坡，油压碟刹都能提供安全、稳定的操控力和制动性',20,2,3998.00),
(17,'STP 20',0,'646aeca5e648cc5e345e80ec','STP (Street Trail Park) 20\" 前避震童车，这是吉安特转为孩童设计的一款童车，轮圈大小为20英寸',20,2,2198.00),
(18,'STP 24',0,'646aeceee648cc5e345e80ed','STP (Street Trail Park) 24\" 前避震童车，这是吉安特转为山地骑行者设计的一款自行车，轮圈大小为24英寸',20,2,2198.00),
(19,'STP 26',0,'646aedc7e648cc5e345e80f0','STP (Street Trail Park) 26\" 前避震童车，这是吉安特转为山地骑行者设计的一款自行车，是STP系列中轮圈最大的一款自行车，轮圈大小为26英寸',20,2,2198.00),
(20,'TCR Advanced SL 1 Disc-Di2',0,'646b4995a867152376d9cd0d','TCR Advanced SL 0 Disc以更轻量的车架提升爬坡效率，更强的踩踏刚性与更具空气力学的整合式设计，让您的竞赛表现提升至全新境界。油压碟刹在晴雨天气均能保持一致的制动力，高刚性前叉让您在下坡和压弯时自信控。',20,2,5300.00),
(21,'TCR Advanced SL Disc Team',0,'646b54baa867152376d9cd20','产品特色：碟刹+轻量满足更多爬坡与下坡的挑战维持compact road压缩设计，追求轻量和高刚性的极致平衡采用AI碳纤叠层技术与镭射切割技术油压刹车提供全天候一致的制动性',20,2,59800.00),
(22,'TCR Advanced SL 0 Disc',0,'646b5552a867152376d9cd21','TCR Advanced SL 0 Disc以更轻量的车架提升爬坡效率，更强的踩踏刚性与更具空气力学的整合式设计，让您的竞赛表现提升至全新境界。油压碟刹在晴雨天气均能保持一致的制动力，高刚性前叉让您在下坡和压弯时自信操控。',20,2,73800.00),
(23,'TCR Advanced Pro 0 Disc-AXS',0,'646b5593a867152376d9cd22','TCR Advanced Pro 0 Disc-AXS以更轻量的车架提升爬坡效率，更强的踩踏刚性与更具空气力学的整合式设计，让您的竞赛表现提升至全新境界。油压碟刹在晴雨天气均能保持一致的制动力，高刚性前叉让您在下坡和压弯时自信操控。',20,2,37800.00),
(24,'TCR Advanced Disc 3',0,'646b55baa867152376d9cd23','TCR Advanced Disc 3以更轻量的车架 提升爬坡效率，更强的踩踏刚性与更具空 气力学的整合式设计，让您的竞赛表现提 升至全新境界。油压碟刹在晴雨天气均能 保持一致的制动力，高刚性前叉让您在下 坡和压弯时自信操控。',20,2,12800.00),
(25,'TCR Advanced Disc 1 KOM',0,'646b5689a867152376d9cd24','该车在每次过弯和进攻时保持领先，前插刚性教上一代提高百分之三十五，加宽叉肩设计，最大支持32C外胎，无钩轮组与12mm桶轴设计提升过弯稳定性，人体工学车把设计，提升长时间握持时的舒适性',20,2,19800.00),
(26,'TCR Advanced Pro 0 Disc-Di2',0,'646b56eda867152376d9cd25','该车在每次过弯和进攻时保持领先，前插刚性教上一代提高百分之三十五，加宽叉肩设计，最大支持32C外胎，无钩轮组与12mm桶轴设计提升过弯稳定性，人体工学车把设计，提升长时间握持时的舒适性',20,2,35800.00),
(27,'TCR Advanced Pro 2 Disc',0,'646b5729a867152376d9cd26','该车在每次过弯和进攻时保持领先，前插刚性教上一代提高百分之三十五，加宽叉肩设计，最大支持32C外胎，无钩轮组与12mm桶轴设计提升过弯稳定性，人体工学车把设计，提升长时间握持时的舒适性',20,2,19800.00),
(28,'TCR Advanced Disc 2-KOM',0,'646b5762a867152376d9cd27','TCR Advanced SL 0 Disc以更轻量的车架提升爬坡效率，更强的踩踏刚性与更具空气力学的整合式设计，让您的竞赛表现提升至全新境界。油压碟刹在晴雨天气均能保持一致的制动力，高刚性前叉让您在下坡和压弯时自信控。',20,2,15800.00),
(29,'TCX Espoir 24',0,'646b57cca867152376d9cd28','青少年公路几何，专为青少年设计的压缩车架结构，具有较低的跨高，让小骑手拥有信心和控制力，车架内走线设计，整车简洁美观。',20,2,3398.00);

/*Table structure for table `repairitems` */

DROP TABLE IF EXISTS `repairitems`;

CREATE TABLE `repairitems` (
  `itemsId` int NOT NULL AUTO_INCREMENT,
  `itemsName` varchar(40) NOT NULL,
  `itemsPrice` float(6,2) NOT NULL,
  `status` int NOT NULL DEFAULT (1),
  PRIMARY KEY (`itemsId`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `repairitems` */

insert  into `repairitems`(`itemsId`,`itemsName`,`itemsPrice`,`status`) values 
(23,'补胎',45.00,1),
(24,'修复链条',40.00,1),
(25,'更换刹车片',30.00,1),
(26,'更换刹车钢丝',25.00,1),
(27,'更换车灯',40.00,1),
(28,'调整变速器',90.00,1),
(29,'更换轮胎',300.00,1),
(30,'更换坐垫',30.00,1),
(31,'更换车锁',50.00,1);

/*Table structure for table `repairorders` */

DROP TABLE IF EXISTS `repairorders`;

CREATE TABLE `repairorders` (
  `repairid` int NOT NULL AUTO_INCREMENT,
  `userid1` int NOT NULL,
  `username1` varchar(20) NOT NULL,
  `userid2` int DEFAULT NULL,
  `username2` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `date` date NOT NULL,
  `sumprice` float(7,2) NOT NULL,
  `repairstatus` int NOT NULL DEFAULT (0),
  `repairOrdersDetailsId` varchar(100) NOT NULL,
  PRIMARY KEY (`repairid`),
  KEY `userid1` (`userid1`),
  KEY `userid2` (`userid2`),
  CONSTRAINT `userid1` FOREIGN KEY (`userid1`) REFERENCES `user` (`userid`),
  CONSTRAINT `userid2` FOREIGN KEY (`userid2`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `repairorders` */

insert  into `repairorders`(`repairid`,`userid1`,`username1`,`userid2`,`username2`,`date`,`sumprice`,`repairstatus`,`repairOrdersDetailsId`) values 
(3,10,'测试用户1',11,'测试用户2','2023-05-08',700.55,5,'6458fd97cf58652be95df3de'),
(4,10,'测试用户1',11,'测试用户2','2023-05-08',700.55,5,'6458fdb6cf58652be95df3df'),
(5,10,'测试用户1',11,'测试用户2','2023-05-08',700.55,1,'6458fe94cf58652be95df3e0'),
(6,10,'测试用户1',11,'测试用户2','2023-05-20',85.44,4,'646844d8a4afc51811409b90'),
(7,10,'测试用户1',20,'测试用户0','2023-05-20',96.69,4,'64684549a4afc51811409b91'),
(8,10,'测试用户1',NULL,NULL,'2023-05-20',39.99,0,'64684610a4afc51811409b92'),
(9,10,'测试用户1',11,'测试用户2','2023-05-20',56.70,4,'646869e0a4afc51811409b93'),
(10,10,'测试用户1',11,'测试用户2','2023-05-20',184.20,4,'646869e4a4afc51811409b94'),
(11,20,'测试用户0',NULL,NULL,'2023-05-22',144.21,1,'646ac69e5bb5c2577e24fee6'),
(12,20,'测试用户0',11,'测试用户2','2023-05-22',370.00,5,'646b58f5a867152376d9cd29');

/*Table structure for table `repairordersdetails` */

DROP TABLE IF EXISTS `repairordersdetails`;

CREATE TABLE `repairordersdetails` (
  `repairOrdersDetailsId` int NOT NULL AUTO_INCREMENT,
  `repairId` int NOT NULL,
  `itemsId` int NOT NULL,
  PRIMARY KEY (`repairOrdersDetailsId`),
  KEY `repairId1` (`repairId`),
  KEY `itemsId1` (`itemsId`),
  CONSTRAINT `itemsId1` FOREIGN KEY (`itemsId`) REFERENCES `repairitems` (`itemsId`),
  CONSTRAINT `repairId1` FOREIGN KEY (`repairId`) REFERENCES `repairorders` (`repairid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `repairordersdetails` */

/*Table structure for table `salelist` */

DROP TABLE IF EXISTS `salelist`;

CREATE TABLE `salelist` (
  `saleId` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `username` varchar(20) NOT NULL,
  `detailsId` varchar(200) NOT NULL,
  `sumPrice` float(7,2) NOT NULL,
  `saleTime` date NOT NULL,
  `listStatus` int NOT NULL DEFAULT (0),
  PRIMARY KEY (`saleId`),
  UNIQUE KEY `detailsId` (`detailsId`),
  KEY `userid` (`userId`),
  CONSTRAINT `userid` FOREIGN KEY (`userId`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `salelist` */

insert  into `salelist`(`saleId`,`userId`,`username`,`detailsId`,`sumPrice`,`saleTime`,`listStatus`) values 
(3,20,'测试用户0','646b417aa867152376d9cd0b',990.00,'2023-05-22',4),
(4,20,'测试用户0','646b41eba867152376d9cd0c',990.00,'2023-05-22',3),
(5,20,'测试用户0','646c8dbcc8476603ffbf0506',6392.00,'2023-05-23',2);

/*Table structure for table `salelistdetails` */

DROP TABLE IF EXISTS `salelistdetails`;

CREATE TABLE `salelistdetails` (
  `saleLiostDetailsId` int NOT NULL AUTO_INCREMENT,
  `saleId` int NOT NULL,
  `commodityId` int NOT NULL,
  `sum` int NOT NULL,
  PRIMARY KEY (`saleLiostDetailsId`),
  KEY `saleId1` (`saleId`),
  KEY `commodityId` (`commodityId`),
  CONSTRAINT `commodityId` FOREIGN KEY (`commodityId`) REFERENCES `commodity` (`commodityId`),
  CONSTRAINT `saleId1` FOREIGN KEY (`saleId`) REFERENCES `salelist` (`saleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `salelistdetails` */

/*Table structure for table `shoppingcart` */

DROP TABLE IF EXISTS `shoppingcart`;

CREATE TABLE `shoppingcart` (
  `userId` int NOT NULL,
  `detailsId` varchar(200) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `detailsId` (`detailsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `shoppingcart` */

insert  into `shoppingcart`(`userId`,`detailsId`) values 
(9,'6456605b160fd816389f68b2'),
(10,'6458fbda300a7e593d2e421b'),
(12,'645b5287d1d6250dd9bc9a32'),
(14,'645b5349d1d6250dd9bc9a34'),
(15,'645b5398d1d6250dd9bc9a35'),
(16,'645b53e8d1d6250dd9bc9a36'),
(17,'645b5400d1d6250dd9bc9a37'),
(18,'645b5426d1d6250dd9bc9a38'),
(13,'645b576b2ef66b05902c98b0'),
(20,'646a1bd17fb43d4e78c169a2'),
(11,'646adbc7e648cc5e345e80e4');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `phoneNumber` char(11) NOT NULL,
  `role` int NOT NULL DEFAULT (0),
  `status` int NOT NULL DEFAULT (1),
  PRIMARY KEY (`userid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`userid`,`username`,`password`,`phoneNumber`,`role`,`status`) values 
(9,'测试用户789','123456','11100103333',0,0),
(10,'测试用户1','123456','11100101111',0,1),
(11,'测试用户2','123456','11100101111',1,1),
(13,'测试用户3','123456','15800304916',2,1),
(20,'测试用户0','123456','15800304916',0,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
