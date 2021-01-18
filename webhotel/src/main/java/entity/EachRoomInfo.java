package entity;


public class EachRoomInfo{
    public String hotel_id;
    public String room_type;
    public float room_price;
    public int total;

    public EachRoomInfo(String s1,String s2,float rp,int tot){
        this.hotel_id=s1;
        this.room_type=s2;
        this.room_price=rp;
        this.total=tot;
    }
}