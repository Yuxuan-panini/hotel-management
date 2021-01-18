package entity;

public class CustomerBill {
    public String order_id;
    public String room_type;
    public String pay;
    public String state;

    public CustomerBill(String s1,String s2,String s3,String s4){
        order_id=s1;
        room_type=s2;
        pay=s3;
        state=s4;
    }
}
