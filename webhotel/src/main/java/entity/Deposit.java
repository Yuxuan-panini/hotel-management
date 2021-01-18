package entity;

import java.sql.Date;

public class Deposit {
    private int deposit_id;
    private double deposit_fee;
    private Date deposit_time;
    private int customer_id;

    public Deposit(int deposit_id, double deposit_fee, Date deposit_time, int customer_id) {
        this.deposit_id = deposit_id;
        this.deposit_fee = deposit_fee;
        this.deposit_time = deposit_time;
        this.customer_id = customer_id;
    }

    public int getDeposit_id() {
        return deposit_id;
    }

    public void setDeposit_id(int deposit_id) {
        this.deposit_id = deposit_id;
    }

    public double getDeposit_fee() {
        return deposit_fee;
    }

    public void setDeposit_fee(double deposit_fee) {
        this.deposit_fee = deposit_fee;
    }

    public Date getDeposit_time() {
        return deposit_time;
    }

    public void setDeposit_time(Date deposit_time) {
        this.deposit_time = deposit_time;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
}
