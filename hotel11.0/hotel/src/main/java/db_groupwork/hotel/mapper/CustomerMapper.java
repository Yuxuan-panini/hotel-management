package db_groupwork.hotel.mapper;

import db_groupwork.hotel.entity.Bill;
import db_groupwork.hotel.entity.Customer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CustomerMapper {

    //检查用户名是否存在
    int ifExist(String customerName);

    //注册，成功返回id，失败SQLException
//    @Insert("insert into customer(customer_name,customer_pwd) values(#{customerName},#{password})")
//    @Options(useGeneratedKeys = true,keyProperty = "customerId")
    int register(Customer customer);

    //登录时通过名字和密码获取用户数
//    @Select("select count(customer_name) from customer where customer_name=#{name} and pwd=#{password}")
    int getCustomerCount(String name,String password);

    //查询用户情况
//    @Select("select * from customer where customer_name=#{customerName}")
    Customer getCustomer(String customerName);
}
