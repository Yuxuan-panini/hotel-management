package entity;

// 酒店
public class Hotel {
    private int hotel_id;
    private String hotel_name;
    private String hotel_area;
    private String address;

    public Hotel(int hotel_id, String hotel_name, String hotel_area, String address) {
        this.hotel_id = hotel_id;
        this.hotel_name = hotel_name;
        this.hotel_area = hotel_area;
        this.address = address;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_area() {
        return hotel_area;
    }

    public void setHotel_area(String hotel_area) {
        this.hotel_area = hotel_area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
