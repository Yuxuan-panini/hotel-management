package db_groupwork.hotel.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {

    public static HashMap<String,Integer> fee=new HashMap<String,Integer>();
        static {
            fee.put("单间",2);
            fee.put("标间",3);
            fee.put("大床房",4);
            fee.put("总统房",5);
        }

}
