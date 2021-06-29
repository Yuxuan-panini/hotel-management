package dao;

import entity.Bill;
import global.Constants;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BillDao {

    // 从数据库查询结构中构建Bill
    private static Bill buildBillFromResultSet(ResultSet rs) throws SQLException {
        int bill_id = rs.getInt("bill_id");
        int customer_id = rs.getInt("customer_id");
        int hotel_id = rs.getInt("hotel_id");
        Date order_date = rs.getDate("order_date");
        System.out.println(order_date);
        Date date_in = rs.getDate("date_in");
        Date date_out = rs.getDate("date_out");
        int order_time = rs.getInt("order_time");
        String room_type = rs.getString("room_type");
        double bill_fee = rs.getDouble("bill_fee");
        boolean charge_state = rs.getBoolean("charge_state");
        boolean can_delete = rs.getBoolean("can_delete");
        boolean is_in = rs.getBoolean("is_in");
        boolean valid = rs.getBoolean("valid");
        return new Bill(bill_id, customer_id, hotel_id, order_date, date_in, date_out, order_time, room_type, bill_fee, charge_state, can_delete, is_in, valid);
    }

    // 根据顾客ID，从数据库中找到顾客的所有订单，如果找不到，返回一个空List
    public static List<Bill> findBillsByCustomerId(int id) {
        List<Bill> bills = new LinkedList<>();
        try {
            ResultSet rs =null;
            Connection conn = Conn.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "select * from " + Constants.Table.BILL + " where customer_id=" + id + " and valid=1 and can_delete=1 and is_in=0;";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                bills.add(buildBillFromResultSet(rs));
            }
        }
        catch (Exception e) {
            System.err.println("Unable to select customer from database, err: " + e.toString());
            e.printStackTrace();
        }
        return bills;
    }

    public static List<Bill> findBillsByCustomerIdOut(int id) {
        List<Bill> bills = new LinkedList<>();
        try {
            ResultSet rs =null;
            Connection conn = Conn.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "select * from " + Constants.Table.BILL + " where customer_id=" + id + " and valid=1 and can_delete=0 and is_in=1;";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                bills.add(buildBillFromResultSet(rs));
            }
        }
        catch (Exception e) {
            System.err.println("Unable to select customer from database, err: " + e.toString());
            e.printStackTrace();
        }
        return bills;
    }
//fixme:需求理解有误，要再写一个函数

    public static int addBill(Date date_in, int stay_time, String room_type, double bill_fee, boolean charge_state, int customer_id, int hotel_id) {
        int bill_id = -1;
        try {
            Connection conn = Conn.getConnection();
            String sql = "insert into " + Constants.Table.BILL + " (date_in, stay_time, room_type, bill_fee, charge_state, customer_id, hotel_id) values (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, date_in);
            stmt.setInt(2, stay_time);
            stmt.setString(3, room_type);
            stmt.setDouble(4, bill_fee);
            stmt.setBoolean(5, charge_state);
            stmt.setInt(6, customer_id);
            stmt.setInt(7, hotel_id);

            int affectedRows = stmt.executeUpdate(sql); //返回影响的条数
            if (affectedRows == 0) {
                throw new SQLException("None affected rows");
            }

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                bill_id = generatedKeys.getInt(1);
            }//获得自增长键
        }
        catch (Exception e) {
            System.err.println("Unable to insert new customer into database");
            e.printStackTrace();
        }

        // todo
        return bill_id;
    }
}

