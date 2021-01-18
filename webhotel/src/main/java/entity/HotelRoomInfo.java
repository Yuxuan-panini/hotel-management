package entity;

import java.util.ArrayList;
import java.util.List;

public class HotelRoomInfo {

    private List<EachRoomInfo> hotellist;

    public  HotelRoomInfo()
    {
        this.hotellist=new ArrayList<>();
    }

    public void AddInfo(String s1,String s2,float rp,int tot)
    {
        EachRoomInfo each=new EachRoomInfo(s1,s2,rp,tot);
        hotellist.add(each);

    }

    public boolean isEmpty(){
        return hotellist.isEmpty();
    }
}
