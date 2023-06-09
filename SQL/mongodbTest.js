

use mongodbTest

db

db.createCollection("myTest")

db.myTest.find()


db.myTest.insert({
		"username":"LW1",
		"stuNum":"22102120103",
		"age":22
})

db.myTest.insertMany(
	[
		{
			"username":"LW2",
			"stuNum":"11111",
			"age":18
		},
		{
			"username":"LW3",
			"stuNum":"22222",
			"age":16
		}
	]
)


db.myTest.insert({
		"username":"LW1",
		"stuNum":"22102120103",
		"age":22,
		"moreTest":"Test"
})

db.show

db.myTest.find()
db.myTest.remove()

db.myTest.remove({"username":"LW2"})

db.myTest2.find()

show collections




use BicycleDB

// 自行车项目信息表
db.createCollection("BicycleInfo")

//消费清单明细表
db.createCollection("SaleListDetails")

//维修清单明细表
db.createCollection("RepairOrdersDetails")

//购物车明细表
db.createCollection("ShoppingCart")


db.BicycleInfo.find()
db.SaleListDetails.find()
db.ShoppingCart.find({"_id":ObjectId("6455b2b3d61b901f49e0e74a")})



db.ShoppingCart.find()

db.ShoppingCart.drop()

db.RepairOrdersDetails.find()







































