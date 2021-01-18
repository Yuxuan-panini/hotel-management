package entity;

import java.util.ArrayList;
import java.util.List;

public class CheckMsg  {

    public List<Order> orderlist;

    public CheckMsg(List<String> result) {
        Order med=new Order(result);
        orderlist=new ArrayList<>();
        orderlist.add(med);
    }

    public CheckMsg(){
        orderlist=new ArrayList<>();
    }

    public void add(List<String> result){
        Order med=new Order(result);
        orderlist.add(med);
    }
}