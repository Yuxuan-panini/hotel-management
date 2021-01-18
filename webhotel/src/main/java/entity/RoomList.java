package entity;

import java.util.ArrayList;
import java.util.List;

public class RoomList {
    public List<EachRoom_for_List> roomlist;


    public RoomList(){
        roomlist=new ArrayList<>();
    }

    public void add(String s1,float rp,int tot,int re){
        EachRoom_for_List med=new EachRoom_for_List(s1,rp,tot,re);
        roomlist.add(med);
    }
}