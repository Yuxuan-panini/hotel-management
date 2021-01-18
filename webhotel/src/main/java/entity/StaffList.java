package entity;

import java.util.ArrayList;
import java.util.List;

public class StaffList {
    public List<StaffInfo> stafflist;

    public StaffList(){
        stafflist=new ArrayList<>();
    }

    public void add(StaffInfo e){
        stafflist.add(e);
    }
}
