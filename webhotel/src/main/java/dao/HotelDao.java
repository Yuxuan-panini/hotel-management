package dao;

import entity.Hotel;
import global.Constants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HotelDao {

    // 从数据库查询结构中构建Hotel
    private static Hotel buildHotelFromResultSet(ResultSet rs) throws SQLException {
        int hotel_id = rs.getInt("hotel_id");
        String hotel_name = rs.getString("hotel_name");
        String hotel_area = rs.getString("hotel_area");
        String address = rs.getString("address");
        return new Hotel(hotel_id, hotel_name, hotel_area, address);
    }

    // 根据酒店ID，从数据库中找到符合的酒店，如果找不到，返回null
    public static Hotel findHotel(int id) {
        Hotel hotel = null;
        try {
            Connection conn = Conn.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "select * from " + Constants.Table.HOTEL + " where hotel_id=" + id + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                hotel = buildHotelFromResultSet(rs);
            }
            else {
                throw new SQLException("No hotel record matches hotel_id: " + id);
            }
        }
        catch (Exception e) {
            System.err.println("Unable to select customer from database, err: " + e.toString());
            e.printStackTrace();
        }
        return hotel;
    }
    //根据酒店名称，从数据库中找到符合的酒店，如果找不到，返回null
    public static Hotel findHotel(String hotelName) {
        Hotel hotel = null;
        try {
            Connection conn = Conn.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "select * from " + Constants.Table.HOTEL + " where hotel_name='" + hotelName + "';";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                hotel = buildHotelFromResultSet(rs);
            }
            else {
                throw new SQLException("No hotel record matches hotel_name: " + hotelName);
            }
        }
        catch (Exception e) {
            System.err.println("Unable to select customer from database, err: " + e.toString());
            e.printStackTrace();
        }
        return hotel;
    }
}
