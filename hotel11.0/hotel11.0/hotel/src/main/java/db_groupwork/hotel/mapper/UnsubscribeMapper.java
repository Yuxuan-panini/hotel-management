package db_groupwork.hotel.mapper;

import db_groupwork.hotel.entity.Bill;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UnsubscribeMapper {
    //用户退订，检查订单是否符合条件
 //   @Select("select count(bill_id) from bill where bill_id=#{billId} and can_delete=1")
    int isDeleteable(int billId,int customerId);

    //用户退订，解除房间占据
  //  @Update("update hotel_room set rest=rest+1 where hotel_id=#{hotelId} and room_type=#{roomType}")
    void releaseRoom(int hotelId,String roomType);

    //用户退订退款
 //   @Update("update customer set charge=charge+#{cost} where customer_id=#{customerId}")
    int backMoney(int customerId,double cost);

    //订单无效化
 //   @Delete("update bill set valid=0 where bill_id=#{billId}")
    void deleteBill(int billId);
}
