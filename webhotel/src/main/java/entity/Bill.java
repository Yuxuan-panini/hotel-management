package entity;

import java.sql.Date;

// 入住订单
public class Bill {
    private int bill_id;
    private int customer_id;
    private int hotel_id;
    private Date order_date;
    private Date date_in;
    private Date date_out;
    private int order_time;
    private String room_type;
    private double bill_fee;
    private boolean charge_state;
    private boolean can_delete;
    private boolean is_in;
    private boolean valid;

    public Bill(int bill_id, int customer_id, int hotel_id, Date order_date, Date date_in, Date date_out, int order_time, String room_type, double bill_fee, boolean charge_state, boolean can_delete, boolean is_in, boolean valid) {
        this.bill_id = bill_id;
        this.customer_id = customer_id;
        this.hotel_id = hotel_id;
        this.order_date = order_date;
        this.date_in = date_in;
        this.date_out = date_out;
        this.order_time = order_time;
        this.room_type = room_type;
        this.bill_fee = bill_fee;
        this.charge_state = charge_state;
        this.can_delete = can_delete;
        this.is_in = is_in;
        this.valid = valid;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public Date getDate_in() {
        return date_in;
    }

    public void setDate_in(Date date_in) {
        this.date_in = date_in;
    }

    public Date getDate_out() {
        return date_out;
    }

    public void setDate_out(Date date_out) {
        this.date_out = date_out;
    }

    public int getOrder_time() {
        return order_time;
    }

    public void setOrder_time(int order_time) {
        this.order_time = order_time;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public double getBill_fee() {
        return bill_fee;
    }

    public void setBill_fee(double bill_fee) {
        this.bill_fee = bill_fee;
    }

    public boolean isCharge_state() {
        return charge_state;
    }

    public void setCharge_state(boolean charge_state) {
        this.charge_state = charge_state;
    }

    public boolean isCan_delete() {
        return can_delete;
    }

    public void setCan_delete(boolean can_delete) {
        this.can_delete = can_delete;
    }

    public boolean isIs_in() {
        return is_in;
    }

    public void setIs_in(boolean is_in) {
        this.is_in = is_in;
    }

    public boolean isvalid() { return valid; }

    public void setvalid(boolean valid) {
        this.valid = valid;
    }
}
