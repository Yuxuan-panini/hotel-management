package db_groupwork.hotel.entity;


import java.sql.Timestamp;

public class Deposit {
    private int    depositId;
    private double  depositFee;
    private Timestamp depositTime;

    private int    customerId;

    Deposit(){}

    public Deposit(int customerId, double depositFee){
        this.customerId = customerId;
        this.depositFee = depositFee;
        this.depositTime = new Timestamp(System.currentTimeMillis());
    }


    public double getDepositFee() {
        return depositFee;
    }

    public void setDepositFee(double depositFee) {
        this.depositFee = depositFee;
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Timestamp getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(Timestamp depositTime) {
        this.depositTime = depositTime;
    }

    public int getDepositId() {
        return depositId;
    }

    public void setDepositId(int depositId) {
        this.depositId = depositId;
    }
}
