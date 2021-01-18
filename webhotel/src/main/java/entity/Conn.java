package entity;

import java.sql.*;

//访问数据库的工具类
public class Conn {

	//获取数据库连接，无密码
	public static Connection getConnection() throws Exception {
		Connection con = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("数据库驱动加载成功");
		// 用这个语句连接数据库
		con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hoteldatabase?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai", "root",
				"newpassword");
		//con.setAutoCommit(false);
		System.out.println("数据库连接成功");
		return con;
	}

	//关闭连接
	public static void close(Connection con, Statement sm, ResultSet rs) throws SQLException {
		if (con != null) {
			con.close();
		}
		if (sm != null) {
			sm.close();
		}
		if (rs != null) {
			rs.close();
		}
	}
}
