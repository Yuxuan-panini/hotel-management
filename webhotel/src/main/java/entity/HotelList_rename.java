
package entity;

import java.util.ArrayList;
import java.util.List;

public class HotelList_rename {
    public List<EachHotel_NoIncome> hotellist;

    public HotelList_rename(){
        hotellist=new ArrayList<>();
    }

    public void add(EachHotel_NoIncome each){
        hotellist.add(each);
    }
}
