package entity;

import java.util.ArrayList;
import java.util.List;

public class HotelListInfo {
    public List<EachHotelInfo> hotelreportList;

    public HotelListInfo(){
        hotelreportList=new ArrayList<>();
    }

    public void add(EachHotelInfo each){
        hotelreportList.add(each);
    }
}
