package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoginDao {
    public static List<String> login(int id, String password) {
        Connection con = null;
        Statement stmt = null;
        ResultSet res = null;
        List<String> list=new ArrayList<String>();
        String permission=null;
        try {
            con = Conn.getConnection();
            stmt = con.createStatement();
            res = stmt.executeQuery(String.format(
                    "select staff_name,staff_title from staff where staff_id=%d and staff_pwd='%s'", id, password));
            while (res.next()) {
                String title=res.getString("staff_title");
                System.out.println(title);
                if(title.equals("前台")){
                    permission="1";
                }else if(title.equals("经理")){
                    permission="2";
                }else if(title.equals("总经理")){
                    permission="3";//总经理
                }
                String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
                stmt.executeUpdate(String.format("update staff set staff_token='%s' where staff_id=%d", token, id));

                Conn.close(con, stmt, res);
                list.add(token);
                list.add(permission);
                return list;
            }
            // 返回code0
            Conn.close(con, stmt, res);
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("非法的数据库操作！");
        }
        return list;
    }
}
