package db_groupwork.hotel.mapper;

import db_groupwork.hotel.entity.Bill;
import db_groupwork.hotel.entity.Room;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderMapper {

    //查询该酒店有无此房型
    int isHaveRoomType(int hotelId,String roomType);

    //根据酒店id和房间类型查询Room
    Room findRoomById(int hotelId,String roomType);

    //查询空闲房位
    //int getRestRoom(int hotelId,String roomType);

    //订房占据空闲房位，成功返回1，失败返回0
    //@Update("update hotel_room set rest=rest-1 where hotel_id=#{hotel_id} and room_type=#{roomType}")
    int orderRoom(int hotelId,String roomType);

    //将订单设置为已付费
    //@Update("update bill set charge_state=1 where bill_id=#{billId}")
    //void changeBill(int billId);

    //返回客户的余额
    int getBalance(int id);

    //订房扣费，成功返回1，失败返回0
    //@Update("update customer set charge=charge-#{cost} where customer_name=#{name}")
    int purchase(int id,double cost);

    //生成（订房）订单，成功返回id，失败SQLException
    //@Insert("insert into bill(customer_id,hotel_id,order_date,date_in,date_out,order_time,room_type,bill_fee) values(#{customerId},#{hotelId},#bookTime},#{checkIn},#{checkOut},#{expectedStayDays},#{roomType},#{billFee})")
    //@Options(useGeneratedKeys = true,keyProperty="billId")
    int genBill(Bill bill);

    //获取一个客户的所有历史订单
    List<Bill> getHistoryBill(int customerId);

    //找到订单
    //   @Select("select * from bill where bill_id=#{billId}")
    Bill findBillById(int billId);

}
