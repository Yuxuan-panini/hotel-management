package dao;

import entity.Customer;
import entity.CustomerBill;
import entity.CustomerBillList;
import global.Constants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDao {
    // 从数据库查询结构中构建Customer
    private static Customer buildCustomerFromResultSet(ResultSet rs) throws SQLException {
        int customer_id = rs.getInt("customer_id");
        String customer_name = rs.getString("customer_name");
        String customer_pwd = rs.getString("customer_pwd");
        boolean membership = rs.getBoolean("membership");
        double balance = rs.getDouble("balance");
        return new Customer(customer_id, customer_name, customer_pwd, membership, (float) balance);
    }

    // 根据顾客ID，从数据库中找到符合的顾客，如果找不到，返回null
    public static Customer findCustomer(int id) {
        Customer customer = null;
        try {
            Connection conn = Conn.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "select * from " + Constants.Table.CUSTOMER + " where customer_id=" + id + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {//是否存在数据
                customer = buildCustomerFromResultSet(rs);
            }
            else {
                throw new SQLException("No customer record matches customer_id: " + id);
            }
        }
        catch (SQLException e) {//处理异常块
            System.err.println("Unable to select customer from database, err: " + e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    // 根据顾客姓名和密码，从数据库中找到符合的顾客，如果找不到，返回null
    public static Customer findCustomer(String name, String pwd) {
        Customer customer = null;
        try {
            Connection conn = Conn.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "select * from " + Constants.Table.CUSTOMER + " where customer_name='" + name + "' and customer_pwd='" + pwd + "';";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                customer = buildCustomerFromResultSet(rs);
            }
            else {
                throw new SQLException("No customer record matches customer_name: " + name + " and customer_pwd: " + pwd);
            }
        }
        catch (SQLException e) {
            System.err.println("Unable to select customer from database, err: " + e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }


    // 向数据库中添加顾客，指定顾客姓名、ID和密码，成功添加返回true，否则返回false
    public static boolean addCustomer(String name, int id, String pwd) {
        try {
            Connection conn = Conn.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "insert into " + Constants.Table.CUSTOMER + " (customer_id, customer_name, customer_pwd, membership, balance) values  (" + id + ", '" + name + "', '" + pwd + "', TRUE, 0);";
            int affectedRows = stmt.executeUpdate(sql);
            if (affectedRows == 0) {
                throw new SQLException("None affected rows");
            }
            return true;
        }
        catch (SQLException e) {
            System.err.println("Unable to insert new customer into database");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 修改数据库中顾客的余额，指定顾客ID和更改后的余额，成功修改返回true，否则返回false
    public static boolean setBalance(int id, double newBalance) {
        try {
            Connection conn = Conn.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "update " + Constants.Table.CUSTOMER + " set balance=" + newBalance + " where customer_id=" + id + ";";
            int affectedRows = stmt.executeUpdate(sql);
            if (affectedRows == 0) {
                throw new SQLException("None affected rows");
            }
            return true;
        }
        catch (SQLException e) {
            System.err.println("Unable to update balance of customer " + id + " in database");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static CustomerBillList getBillList(int customer_id) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        ResultSet res = null;

        CustomerBillList cbl=null;

        try {
            conn = Conn.getConnection();
            stmt = conn.createStatement();
            res = stmt.executeQuery(String.format("select bill_id,room_type,bill_fee,charge_state from bill where customer_id=%d and valid=1", customer_id));
            cbl=new CustomerBillList();
            while(res.next()){
                CustomerBill cb=new CustomerBill(res.getString(1),res.getString(2),res.getString(3),res.getString(4));
                cbl.element.add(cb);
            }
            res.close();
            Conn.close(conn, stmt, res);
            return cbl;
        } catch (SQLException e) {
            System.err.println("Unable to insert new customer into database");
            e.printStackTrace();
        }
        return cbl;
    }

    public static boolean Unsubscribe(int order_id){
        Connection conn = null;
        Statement stmt = null;
        ResultSet res = null;
        try {
            conn = Conn.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(String.format("delete from bill where bill_id=%d", order_id));
            Conn.close(conn, stmt, res);
            return true;
        } catch (SQLException e) {
            System.err.println("Unable to insert new customer into database");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
