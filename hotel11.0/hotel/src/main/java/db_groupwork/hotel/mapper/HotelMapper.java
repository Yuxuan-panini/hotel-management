package db_groupwork.hotel.mapper;

import db_groupwork.hotel.entity.Room;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelMapper {

    //通过酒店名称返回酒店id
    //@Select("select hotel_id from hotel where hotel_name=#{hotelName}")
    int getHotelId(String hotelName);

    //获取所有酒店的名称
    List<String> getHotelNames();

    //获取某个酒店的所有房间
    List<String> getRoomType(int hotelId);
}
