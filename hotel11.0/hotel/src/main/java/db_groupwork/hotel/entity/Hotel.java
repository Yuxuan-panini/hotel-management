package db_groupwork.hotel.entity;

public class Hotel {
    private int    hotelId;
    private String  hotelName;
    private String  area;
    private String  address;

    Hotel(){}


    public Hotel(int hotelId, String hotelName){
        this.hotelId = hotelId;
        this.hotelName = hotelName;
    }

    public int getHotelId(){
        return this.hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public void setHotelName(String  hotelName) {
        this.hotelName = hotelName;
    }
}

