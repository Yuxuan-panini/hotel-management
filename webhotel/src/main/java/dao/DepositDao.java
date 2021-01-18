package dao;

import entity.Deposit;
import global.Constants;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DepositDao {

    // 从数据库查询结构中构建Deposit
    private static Deposit buildDepositFromResultSet(ResultSet rs) throws SQLException {
        int deposit_id = rs.getInt("deposit_id");
        double deposit_fee = rs.getDouble("deposit_fee");
        Date deposit_time = rs.getDate("deposit_time");
        int customer_id = rs.getInt("customer_id");
        return new Deposit(deposit_id, deposit_fee, deposit_time, customer_id);
    }

    public static Deposit findDeposit(int depositId) {
        Deposit deposit = null;
        try {
            Connection conn = Conn.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "select * from " + Constants.Table.DEPOSIT + " where deposit_id=" + depositId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                deposit = rs.getObject(1, Deposit.class);
            }
            else {
                throw new SQLException("No deposit record matches deposit_id: " + depositId);
            }
        }
        catch (Exception e) {
            System.err.println("Unable to select deposit from database, err: " + e.toString());
            e.printStackTrace();
        }
        return deposit;
    }

    public static int addDeposit(int customerId, double fee) {
        Date date = new Date(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(+8)));
        String sql = "insert into " + Constants.Table.DEPOSIT + " (deposit_fee, deposit_time, customer_id) values (?, ?, ?);";
        int depositId = -1;
        try {
            Connection connection = Conn.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, fee);
            statement.setDate(2, date);
            statement.setInt(3, customerId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting a new deposit failed, no rows affected.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                depositId = generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Inserting a new deposit failed, no ID obtained.");
            }
        }
        catch (SQLException e) {
            System.err.println(e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return depositId;
    }
}
