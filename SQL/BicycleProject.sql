
USE bicycleproject


SELECT * FROM `user`
SELECT * FROM shoppingCart
SELECT * FROM SaleList
SELECT * FROM commodity
SELECT * FROM repairorders
SELECT * FROM repairItems


/*用户部分*/
/*用户表*/
CREATE TABLE `user`(
	userid INT(2) PRIMARY KEY AUTO_INCREMENT, /*用户id，并且从 1 开始自增*/
	username VARCHAR(20) NOT NULL UNIQUE, /*用户名，不为空，不可以重复*/
	`password` VARCHAR(20) NOT NULL, /*密码，不为空*/
	phoneNumber CHAR(11) NOT NULL,/*电话号码*/
	`role` INT(1) DEFAULT(0) NOT NULL, /*角色类型 0：顾客  1：管理员  2：最高管理员  默认值为0 */
	`status` INT(1) DEFAULT(1) NOT NULL, /*是否启用 0：未启用   1：已启用   默认为1   （启用中的账户用户名不可以重复）*/
)


/*维修功能部分*/
/*维修单实体类*/
CREATE TABLE repairorders(
	repairid INT(2) PRIMARY KEY AUTO_INCREMENT, /*维修单id，并且从1开始自增*/
	userid1 INT(2) NOT NULL, /*申请维修的用户id*/
	username1 VARCHAR(20) NOT NULL, /*申请维修的用户名 */
	userid2 INT(2) NOT NULL, /*受理维修的管理员的id */
	username2 VARCHAR(20) NOT NULL, /*受理维修的管理员的用户名 */
	`date` DATE NOT NULL, /*维修单申请日期*/
	sumprice FLOAT(7,2) NOT NULL, /*维修单总价*/
	repairstatus INT(1) DEFAULT(0) NOT NULL, /*维修单状态 0：申请中   1：已撤销   2：已受理    3：维修中    4：待领取   5.已领取完成  默认为0*/
	CONSTRAINT `userid1` FOREIGN KEY (userid1) REFERENCES `user`(userid),
	CONSTRAINT `userid2` FOREIGN KEY (userid2) REFERENCES `user`(userid)
)

/*维修项目实体类 */
CREATE TABLE repairItems(
	itemsId INT(2) PRIMARY KEY AUTO_INCREMENT,/*维修项目id,，并且从1开始自增*/
	itemsName VARCHAR(40) NOT NULL,/*维修项目名*/
	itemsPrice FLOAT(6,2) NOT NULL, /*维修项目价目*/
	`status` INT(1) DEFAULT(1) NOT NULL   /*是否启用 0:未启用   1：已启用（默认已经启用）*/
)


/*维修单明细实体类（已经弃用）*/
CREATE TABLE repairOrdersDetails(
	repairOrdersDetailsId INT(2) PRIMARY KEY AUTO_INCREMENT, /*维修单明细id*/
	repairId INT(2) NOT NULL, /*对应维修单id*/
	itemsId INT(2) NOT NULL, /*维修项目id*/
	CONSTRAINT `repairId1` FOREIGN KEY (repairId) REFERENCES repairorders(repairId),
	CONSTRAINT `itemsId1` FOREIGN KEY (itemsId) REFERENCES repairItems(itemsId)
)



/*商品部分*/
/*商品实体类*/
CREATE TABLE commodity(
	commodityId INT(2) PRIMARY KEY AUTO_INCREMENT,/*商品实体类*/
	commodityName VARCHAR(60) NOT NULL, /*商品名*/
	commodityType INT(2) NOT NULL, /* 商品类型（未确定有哪些，到时候用数字表示） */
	pictureId VARCHAR(100) NOT NULL, /* 商品图片Id（存放在 mongodb 中） */
	introduce VARCHAR(400) NOT NULL,/* 商品介绍 */
	stock INT(2) NOT NULL, /* 商品存量 */
	sold INT(2) NOT NULL DEFAULT(2), /* 是否在售  0：下架   1：销售中   2:即将销售    默认为2 */
	price FLOAT(6,2) NOT NULL /*售价*/
)

/*销售清单实体类*/
CREATE TABLE SaleList(
	saleId INT(2) PRIMARY KEY AUTO_INCREMENT,/*销售清单id*/
	userId INT(2) NOT NULL,/*消费者id*/
	username VARCHAR(20) NOT NULL,/*消费者用户名*/
	detailsId VARCHAR(200) UNIQUE NOT NULL,/*对应消费明细数据id*/
	sumPrice FLOAT(7,2) NOT NULL,/*总价*/
	saleTime DATE NOT NULL,/*消费日期*/
	listStatus INT(1) NOT NULL DEFAULT(0),/* 清单状态  0：申请中  1：备货中   2：待领取    3：已完成    4:已撤销  (默认为0) */
	CONSTRAINT `userid` FOREIGN KEY (userid) REFERENCES `user`(userid)
)

/*销售清单明细实体类（弃用）*/
CREATE TABLE SaleListDetails(
	saleLiostDetailsId INT(2) PRIMARY KEY AUTO_INCREMENT,/*销售清单明细id*/
	saleId INT(2) NOT NULL,/*销售清单id*/
	commodityId INT(2) NOT NULL,/*商品id*/
	`sum` INT(2) NOT NULL,/*商品销售数量*/
	CONSTRAINT `saleId1` FOREIGN KEY (saleId) REFERENCES saleList(`saleId`),
	CONSTRAINT `commodityId` FOREIGN KEY (commodityId) REFERENCES commodity(`commodityId`)
)

/*购物车实体类*/
CREATE TABLE ShoppingCart(
	userId INT(2) PRIMARY KEY,/*用户id*/
	detailsId VARCHAR(200) UNIQUE NOT NULL /*对应消费明细数据id*/
)










