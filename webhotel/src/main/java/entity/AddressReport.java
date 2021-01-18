package entity;

import java.util.ArrayList;
import java.util.List;

public class AddressReport {
    private class Each{
        public String hotel_id;
        public String address;
        public float income;

        public Each(String s1,String s,float f){
            hotel_id=s1;address=s;income=f;
        }
    }

    List<Each> areahotellist;

    public AddressReport(){
        areahotellist=new ArrayList<>();
    }

    public void add(String s1,String ad,float f){
        Each each=new Each(s1,ad,f);
        areahotellist.add(each);
    }
}
