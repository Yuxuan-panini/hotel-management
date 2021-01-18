package dao;

import entity.RoomList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReceptionDao {


	// 调用此方法传入要查看的酒店id
	public static RoomList watchRooms(int hotelId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		RoomList rl=null;
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			res = stmt.executeQuery(String.format("select * from hotel_room where hotel_id=%d", hotelId));
			if (res != null) {
				rl=new RoomList();
				while (res.next()) {
					// 返回房间列表
					rl.add(res.getString("room_type"),
							res.getFloat("price"),
							res.getInt("total"),
							res.getInt("rest")
					);
				}
			}
			Conn.close(con, stmt, res);
			return rl;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return rl;
	}

	// 调用此方法传入要查看的客户id订单
	public static List<String> watchCheckinBill(int customerId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		List<String> list=new ArrayList<String>();
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			res = stmt.executeQuery(String.format("select * from bill where customer_id=%d and is_in=0 and can_delete=1 and valid=1", customerId));
			while (res.next()) {
				// 返回订单列表（其实只有一个）
				list.add(res.getString(1));
				list.add(res.getString(3));
				list.add(res.getString(2));
				list.add(res.getString(5));
				list.add(res.getString(4));
				Conn.close(con, stmt, res);
				return list;
			}
			Conn.close(con, stmt, res);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return list;
	}

	// 调用此方法传入要修改的账单id（需保证用户在这之前付款）
	public static boolean checkIn(int billId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			Date date = new Date();
			con = Conn.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(String.format("update bill set date_in='%s',can_delete=0,is_in=1 where bill_id=%d and valid=1",
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date), billId));
			// 返回code1
			Conn.close(con, stmt, res);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return false;
	}

	// 调用此方法传入要查看的客户id订单
	public static List<String> watchCheckoutBill(int customerId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		List<String> list=new ArrayList<String>();
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			res = stmt.executeQuery(String.format("select * from bill where customer_id=%d and is_in=1 and can_delete=0 and valid=1", customerId));
			while (res.next()) {
				// 返回订单列表（其实只有一个）
				list.add(res.getString(1));
				list.add(res.getString(3));
				list.add(res.getString(2));
				list.add(res.getString(5));
				list.add(res.getString(4));
				Conn.close(con, stmt, res);
				return list;
			}
			Conn.close(con, stmt, res);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");

		}
		return list;
	}

	// 前端调用此方法传入要退的订单id
	public static boolean checkOut(int billId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		String rt="";
		int id=0;
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			res = stmt.executeQuery(String.format("select hotel_id,room_type from bill where bill_id=%d and valid=1", billId));
			while (res.next())
			{
				rt=res.getString("room_type");
				id=res.getInt("hotel_id");
			}
			stmt.executeUpdate(String.format("update hotel_room set rest=rest+1 where room_type='%s' and hotel_id=%d", rt,id ));
			// 前端显示“客户已成功退房”
			stmt.executeUpdate(String.format("update bill set is_in=0 where bill_id=%d and valid=1",billId));
			Conn.close(con, stmt, res);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return false;
	}
	public static int getHotelId(int staffId){
		if(staffId==-1){
			return -1;
		}
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		Integer hotelId=null;
		try{
			con = Conn.getConnection();
			stmt = con.createStatement();
			res=stmt.executeQuery(String.format("select hotel_id from hotel_has_staff where staff_id='%s'",staffId));
			while(res.next()){
				hotelId=res.getInt("hotel_id");
			}
			return hotelId;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return -1;
	}
}
