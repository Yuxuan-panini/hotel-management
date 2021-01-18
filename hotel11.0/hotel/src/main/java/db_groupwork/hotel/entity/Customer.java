package db_groupwork.hotel.entity;

public class Customer {
    private int    customerId;
    private String  customerName;
    private String  password;
    private double  balance;

    Customer(){}

    public Customer(String customerName, String password){
        this.customerName = customerName;
        this.password = password;
        this.balance = 0;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
