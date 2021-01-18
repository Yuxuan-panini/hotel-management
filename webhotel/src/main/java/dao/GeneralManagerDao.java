package dao;

import entity.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class GeneralManagerDao {
	

	
	// 打印所有酒店
	public static HotelList_rename printHotel() {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		EachHotel_NoIncome eh=null;
		HotelList_rename hotel_list=new HotelList_rename();
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			res = stmt.executeQuery(String.format("select * from hotel"));
			while (res.next()) {
				eh=new EachHotel_NoIncome(res.getString("hotel_id"),res.getString("hotel_name"),res.getString("hotel_area"),res.getString("address"));
				hotel_list.add(eh);
			}
			Conn.close(con, stmt, res);
			return hotel_list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return hotel_list;
	}

	// 添加分店，若id已存在则弹出SQLException
	public static void addHotel(int hotelId, String hotel_name,String hotelArea, String hotelAddress) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(
					String.format("insert into hotel values(%d,'%s','%s','%s')", hotelId,hotel_name, hotelArea, hotelAddress));
			Conn.close(con, stmt, res);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
	}

	// 更新分店信息，若不存在则什么也不做
	public static void updateHotel(int hotelId, String hotelName,String hotelArea, String hotelAddress) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(String.format("update hotel set hotel_name='%s', hotel_area='%s',address='%s' where hotel_id=%d",
					hotelName, hotelArea, hotelAddress, hotelId));
			Conn.close(con, stmt, res);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
	}

	// 关闭分店，并删除对应的房型
	public static void removeHotel(int hotelId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(String.format("delete from hotel where hotel_id=%d", hotelId));
			// 数据库中hotel_room.hotel_id有级联删除关系，主表属性hotel.hotel_id删除则从表属性也会删除。
			Conn.close(con, stmt, res);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
	}

	// 获取除总经理外的所有职员
	public static StaffList showAllStaff() {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		StaffList sl=null;
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			res = stmt.executeQuery(String.format("select * from staff where staff_title!='总经理'"));
			sl=new StaffList();
			while (res.next()) {
				// 输出信息
				StaffInfo si=new StaffInfo(res.getString(1),res.getString(2),res.getString(3),res.getString(4));
				sl.add(si);
			}
			Conn.close(con, stmt, res);
			return sl;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return sl;
	}

	//获取某酒店的所有职员
	public static StaffList showHotelStaff(int hotelId){
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		StaffList staffs=new StaffList();
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			res = stmt.executeQuery(String.format("select * from staff natural join hotel_has_staff where hotel_id=%d",hotelId));
			while (res.next()) {
				staffs.add(new StaffInfo(res.getString("staff_id"),res.getString("staff_name"),res.getString("staff_title"),res.getString("staff_pwd")));
			}
			Conn.close(con, stmt, res);
			return staffs;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return null;
	}

	// 添加员工，若id重复则抛出SQLException
	public static boolean addStaff(int staffId, String staffName, String staffTitle, String staffPwd) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			if (!staffTitle.equals("总经理")) {// 不是总经理
				con = Conn.getConnection();
				stmt = con.createStatement();
				stmt.executeUpdate(String.format(
						"insert into staff(staff_id,staff_name,staff_title,staff_pwd) values (%d,'%s','%s','%s')",
						staffId, staffName, staffTitle, staffPwd));
				Conn.close(con, stmt, res);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return false;
	}

	// 更改信息，id不能修改，title不能为GManager
	public static boolean updateStaff(int staffId, String staffName, String staffTitle, String staffPwd) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			if (!staffTitle.equals("总经理")) {
				con = Conn.getConnection();
				stmt = con.createStatement();
				stmt.executeUpdate(String.format(
						"update staff set staff_name='%s',staff_title='%s',staff_pwd='%s' where staff_id=%d", staffName,
						staffTitle, staffPwd, staffId));
				Conn.close(con, stmt, res);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return false;
	}

	// 解雇员工，并令其与所有酒店分店解约
	public static boolean fireStaff(int staffId, String staffTitle) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			if (!staffTitle.equals("总经理")) {
				con = Conn.getConnection();
				stmt = con.createStatement();
				stmt.executeUpdate(String.format("delete from staff where staff_id=%d", staffId));
				// 数据库中hotel_has_staff.staff_id有级联删除关系，staff.staff_id被删除，hotel_has_staff.staff_id也会被删除。
				Conn.close(con, stmt, res);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return false;
	}

	// 令一个非总经理员工与某一酒店分店签约，酒店在前端就已确认存在
	// 如果已签约，则弹出SQLException
	public static boolean attachHotel(int hotelId, int staffId) {

		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			res = stmt.executeQuery(String.format("select staff_id from staff where staff_id=%d", staffId));
			if (res == null) {
				return false;
			} else {
				stmt.executeUpdate(String.format("insert into hotel_has_staff values (%d,%d)", hotelId, staffId));
			}
			// 其实不需要判断，如果员工不存在的话，那么他自然会弹出SQLException
			Conn.close(con, stmt, res);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return false;
	}

	// 令一名非总经理员工与某酒店解约，前端已确认酒店和员工的契约关系存在
	public static boolean detachHotel(int hotelId, int staffId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(
					String.format("delete from hotel_has_staff where hotel_id=%d and staff_id=%d", hotelId, staffId));
			Conn.close(con, stmt, res);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return false;
	}

	// 以片区为单位打印报表，不算未付款的
	public static AreaReport printAreaReport() {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		AreaReport ap=new AreaReport();
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			res = stmt.executeQuery(String.format(
					"select sum(bill_fee),hotel_area from bill natural join hotel where charge_state=1 and valid=1 group by hotel_area"));
			while (res.next()) {
				ap.add(res.getString(2),res.getFloat(1));
				// 输出每个area的收入情况
			}
			Conn.close(con, stmt, res);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return ap;
	}

	// 给定地区，以地址为单位打印本地区报表，不算未付款的
	public static AddressReport printAddressReport(String area) {
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		AddressReport addressReport = new AddressReport();
		try {
			con = Conn.getConnection();
			stmt = con.createStatement();
			res = stmt.executeQuery(String.format(
					"select hotel_id,address,sum(bill_fee) from bill natural join hotel where charge_state=1 and hotel_area='%s' and valid=1 group by address",area));
			while (res.next()) {
				addressReport.add(res.getString(1),res.getString(2),res.getFloat(3));
			}
			Conn.close(con, stmt, res);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("非法的数据库操作！");
		}
		return addressReport;
	}
}
