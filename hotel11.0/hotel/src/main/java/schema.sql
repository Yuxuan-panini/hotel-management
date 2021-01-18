CREATE DATABASE IF NOT EXISTS HotelDatabase;
USE HotelDatabase;

CREATE TABLE IF NOT EXISTS `customer`    -- 顾客信息
(
	`customer_id`   INTEGER         NOT NULL    AUTO_INCREMENT,			# 顾客id
    `customer_name` VARCHAR(10)     NOT NULL,          			        # 顾客姓名
    `customer_pwd`  VARCHAR(16)     NOT NULL,		   			        # 顾客密码
    `balance`       DECIMAL(10,2)   NOT NULL    DEFAULT 0,			    # 当前余额
    PRIMARY KEY(customer_id),
    UNIQUE KEY(customer_name)
);

CREATE TABLE IF NOT EXISTS hotel ( -- 各酒店
	hotel_id  INTEGER PRIMARY KEY,      			#酒店id
    hotel_name VARCHAR(20) UNIQUE KEY,  			#酒店名称
    hotel_area VARCHAR(10),			    			#酒店所在地区
    address VARCHAR(50)				    			#酒店所在地址
);

CREATE TABLE IF NOT EXISTS staff ( -- 酒店员工
	staff_id  INTEGER PRIMARY KEY,      			#员工id
    staff_name VARCHAR(10),  						#员工姓名
    staff_title VARCHAR(10),		    			#员工职位
    staff_pwd VARCHAR(16),			    			#员工密码
    staff_token VARCHAR(50)			    			#员工token(用于鉴别是否是存在的员工)
);

CREATE TABLE IF NOT EXISTS hotel_has_staff ( -- 酒店分店与酒店员工
	hotel_id  INTEGER,                  			#酒店id
    staff_id INTEGER,				   			    #员工id
    PRIMARY KEY (hotel_id,staff_id),
    FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS hotel_room ( -- 分店的某一种房型
    room_type VARCHAR(20),			    			#客房类型
    hotel_id INTEGER,				    			#酒店id
    total INTEGER,					    			#客房总数
    rest INTEGER,									#客房剩余数
    price DECIMAL(10,2),							#客房价格
    PRIMARY KEY(room_type ,hotel_id),
    FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CHECK(total>=rest)
);

CREATE TABLE IF NOT EXISTS deposit ( -- 充值订单
	deposit_id  INTEGER AUTO_INCREMENT,				#充值账单id
    deposit_fee DECIMAL(10,2),						#充值账单价格
	deposit_time DATETIME,							#成交时间
    customer_id  INTEGER,							#顾客id
    PRIMARY KEY (deposit_id)
);

CREATE TABLE IF NOT EXISTS bill ( -- 入住订单
	bill_id         INTEGER         NOT NULL    AUTO_INCREMENT,			# 账单id
	customer_id     INTEGER         NOT NULL,							# 顾客id
    hotel_id        INTEGER         NOT NULL,							# 酒店id
    order_date      DATETIME        NOT NULL,							# 预定时间
    date_in         DATETIME        NOT NULL,							# 入住时间
    date_out        DATETIME        NOT NULL,							# 离开时间
    order_time      INTEGER         NOT NULL,							# 预定居住时间
    room_type       VARCHAR(20)     NOT NULL,							# 居住房型
    bill_fee        DECIMAL(10,2)   NOT NULL,							# 账单价格
    charge_state    BOOLEAN         DEFAULT 0,					        # 是否付款
    can_delete      BOOLEAN         DEFAULT 1,					        # 是否可以取消
    is_in           BOOLEAN         DEFAULT 0, 						    # 是否处在居住状态
    valid           BOOLEAN         DEFAULT 1,                          # 是否有效（被删除后无效）
    PRIMARY KEY (bill_id)
);

