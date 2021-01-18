package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TokenDao {
    public static int getId(String token){
        Connection con = null;
        Statement stmt = null;
        ResultSet res = null;
        int staffId=-1;
        try{
            con = Conn.getConnection();
            stmt = con.createStatement();
            res=stmt.executeQuery(String.format("select staff_id,staff_title from staff where staff_token='%s'",token));
            while(res.next()){
                staffId=res.getInt("staff_id");
            }
            return staffId;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("非法的数据库操作！");
        }
        return -1;
    }

}