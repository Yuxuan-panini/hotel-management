package dao;

import entity.CheckMsg;
import entity.EachHotelInfo;
import entity.HotelListInfo;
import entity.HotelRoomInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ManagerDao {


	// 传入经理ID查看所辖分店的房间情况
	public static HotelRoomInfo printRoom(int staffId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		HotelRoomInfo hotel_room_info=null;
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			// 显示酒店的房间
			res = stmt.executeQuery(String.format("select hotel_id,room_type,price,total from hotel_has_staff natural join hotel_room where staff_id=%d", staffId));
			hotel_room_info=new HotelRoomInfo();
			while (res.next()) {
					hotel_room_info.AddInfo(res.getString(1),res.getString(2),res.getFloat(3),res.getInt(4));
				// 输出
			}
			Conn.close(con, stmt, res);
			return hotel_room_info;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return hotel_room_info;
	}

	// 添加酒店房型
	public static void addHotelRoom(int hotelId, String roomType, int total, int rest, float price) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(String.format("insert into hotel_room values('%s',%d,%d,%d,%f) ", roomType, hotelId,
					total, rest, price));
			Conn.close(con, stmt, res);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");

		}
	}

	// 修改酒店房间状态，不能修改hotelid和roomtype
	public static void updateHotelRoom(int hotelId, String roomType, int total, int rest, float price) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(String.format(
					"update hotel_room set total=%d,rest=%d,price=%f where hotel_id=%d and room_type='%s'", total, rest,
					price, hotelId, roomType));
			Conn.close(con, stmt, res);
		} catch (Exception e) {
			e.printStackTrace();

			System.out.println("非法的数据库操作！");

		}
	}

	// 删除酒店房型
	public static void removeHotelRoom(int hotelId, String roomType) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(
					String.format("delete from hotel_room where hotel_id=%d and room_type='%s'", hotelId, roomType));
			Conn.close(con, stmt, res);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");

		}
	}

	// 打印所辖范围的报表
	public static HotelListInfo printReport(int staffId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		HotelListInfo hotel_list=null;
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			res = stmt.executeQuery(String.format("select hotel_id,hotel_area,address,sum(bill_fee) from hotel_has_staff natural join bill natural join hotel where staff_id=%d and charge_state=1 and valid=1 group by hotel_id", staffId));
			while (res.next()) {
				hotel_list = new HotelListInfo();

				EachHotelInfo each = new EachHotelInfo(res.getString(1), res.getString(2), res.getString(3), res.getFloat(4));
				hotel_list.add(each);
			}
			Conn.close(con, stmt, res);
			return hotel_list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return hotel_list;
	}

	// 打印报表，查看某分店的账单情况
	public static CheckMsg printBill(int hotelId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		CheckMsg ckm=null;

		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			res = stmt.executeQuery(String.format("select * from bill where hotel_id=%d and charge_state=1 and valid=1", hotelId));
			ckm=new CheckMsg();
			while (res.next()) {
				List<String> list=new ArrayList<>();
				list.add(res.getString("bill_id"));
				list.add(res.getString("order_time"));
				list.add(res.getString("date_in"));
				list.add(res.getString("bill_fee"));
				list.add(res.getString("room_type"));
				list.add(res.getString("date_out"));
				list.add(res.getString("order_date"));
				ckm.add(list);
			}
			Conn.close(con, stmt, res);
			return ckm;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return ckm;
	}
}
