package entity;

import java.util.List;

public class Order {
    public String id;
    public int Stay_days;
    public String Date_in;
    public float fee;
    public String room_type;
    public String Date_out;
    public String Order_day;

    public Order(List<String> result){
        String[] strArray=new String[result.size()];
        result.toArray(strArray);

        this.id=strArray[0];
        this.Stay_days=Integer.parseInt(strArray[1]);
        this.Date_in=strArray[2];
        this.fee=Float.parseFloat(strArray[3]);
        this.room_type=strArray[4];
        this.Date_out = strArray[5];
        this.Order_day = strArray[6];
    }
};