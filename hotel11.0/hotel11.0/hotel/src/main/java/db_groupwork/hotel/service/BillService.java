package db_groupwork.hotel.service;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import db_groupwork.hotel.entity.Bill;
import db_groupwork.hotel.entity.Customer;
import db_groupwork.hotel.entity.Deposit;
import db_groupwork.hotel.entity.Room;
import db_groupwork.hotel.mapper.HotelMapper;
import db_groupwork.hotel.mapper.OrderMapper;
import db_groupwork.hotel.mapper.UnsubscribeMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class BillService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UnsubscribeMapper unsubscribeMapper;
    @Autowired
    HotelMapper hotelMapper;

    public List<String> getHotelList(){
        List<String> all_hotel_names = hotelMapper.getHotelNames();
        return all_hotel_names;
    }

    public int getHotelid(String hotel_name){
        int hotelid = hotelMapper.getHotelId(hotel_name);
        return hotelid;
    }

    public List<String> getRoomType(int hotelId){
        return  hotelMapper.getRoomType(hotelId);
    }

    public boolean makeOrder(int customerId, int hotelId, Date checkin, Date checkout, String roomType){

        if((orderMapper.isHaveRoomType(hotelId,roomType)>0)&&(orderMapper.findRoomById(hotelId,roomType).getRest()>0)){
            orderMapper.orderRoom(hotelId, roomType);
            Room room = orderMapper.findRoomById(hotelId,roomType);
            Timestamp in = new Timestamp(checkin.getTime());
            Timestamp out = new Timestamp(checkout.getTime());
            int differentDays = (int)(checkout.getTime()-checkin.getTime())/(1000*3600*24);
            double cost=(room.getRoomPrice())*differentDays;
            if(orderMapper.getBalance(customerId)>cost){
                orderMapper.purchase(customerId,cost);
                orderMapper.genBill(new Bill(customerId,hotelId,new Timestamp(System.currentTimeMillis()),in,out,differentDays,roomType,cost));
                return true;
            }
        }
        return false;
    }

    public JsonArray getHisoryBill(int customerId) {
        List<Bill> bill = orderMapper.getHistoryBill(customerId);
        JsonArray jsona = new JsonArray();
        JsonObject jsono = new JsonObject();
        JsonObject object = new JsonObject();
        for (Bill b : bill) {
            jsono.addProperty("order_id", b.getBillId());
            jsono.addProperty("room_type", b.getRoomType());
            jsono.addProperty("except_days", b.getExpectedStayDays());
            jsono.addProperty("pay", b.getBillFee());
            if(b.isValid()&&b.isCanDelete()){
                jsono.addProperty("state",1);
            }else if(b.isIn()){
                jsono.addProperty("state",0);
            }else{
                jsono.addProperty("state",2);
            }
            jsona.add(jsono.deepCopy());

        }
        return jsona;
    }

    public boolean cancel(int bill_id, int customer_id){
        if(unsubscribeMapper.isDeleteable(bill_id,customer_id)==1){
            Bill b = orderMapper.findBillById(bill_id);
            unsubscribeMapper.releaseRoom(b.getHotelId(),b.getRoomType());
            unsubscribeMapper.backMoney(customer_id,b.getBillFee());
            unsubscribeMapper.deleteBill(bill_id);
            return true;
        }
        return false;
    }

}
