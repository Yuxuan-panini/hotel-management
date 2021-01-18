package entity;

public class EachHotelInfo extends EachHotel_NoIncome {
    public float income;

    public EachHotelInfo(String s1,String s2,String s3,float ft){
        this.hotel_id=s1;this.area=s2;this.address=s3;this.income =ft;
    }
}
