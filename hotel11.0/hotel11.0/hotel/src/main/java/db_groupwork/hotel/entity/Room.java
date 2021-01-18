package db_groupwork.hotel.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {

    private String roomType;
    private int hotelId;
    private int total;
    private int rest;
    private double roomPrice;

    Room(){}
    public Room(String roomType,int hotelId,int total,int rest,double price){
        this.hotelId=hotelId;
        this.rest=rest;
        this.roomPrice=price;
        this.roomType=roomType;
        this.total=total;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }
//    public static HashMap<String,Integer> fee=new HashMap<String,Integer>();
//        static {
//            fee.put("单间",2);
//            fee.put("标间",3);
//            fee.put("大床房",4);
//            fee.put("总统房",5);
//        }

}
