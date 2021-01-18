package db_groupwork.hotel.entity;

import java.sql.Timestamp;

public class Bill {
    private int billId;
    private int customerId;
    private int hotelId;

    private Timestamp bookTime;
    private Timestamp checkin;
    private Timestamp checkout;
    private int expectedStayDays;

    private String  roomType;
    private double  billFee;

    private boolean chargeState;
    private boolean canDelete;
    private boolean isIn;
    private boolean valid;

    Bill(){}

    public Bill(int customerId, int hotelId, Timestamp bookTime, Timestamp checkin, Timestamp checkout, int expectedStayDays, String roomType,double billFee){
        this.customerId = customerId;
        this.hotelId = hotelId;

        this.bookTime = bookTime;
        this.checkin = checkin;
        this.checkout = checkout;
        this.expectedStayDays=expectedStayDays;

        this.roomType = roomType;
        this.billFee = billFee;

        this.chargeState = false;
        this.canDelete = false;
        this.isIn = false;
        this.valid = true;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public Timestamp getBookTime() {
        return bookTime;
    }

    public void setBookTime(Timestamp bookTime) {
        this.bookTime = bookTime;
    }

    public Timestamp getCheckin() {
        return checkin;
    }

    public void setCheckin(Timestamp checkin) {
        this.checkin = checkin;
    }

    public Timestamp getCheckout() {
        return checkout;
    }

    public void setCheckout(Timestamp checkout) {
        this.checkout = checkout;
    }

    public double getBillFee() {
        return billFee;
    }

    public void setBillFee(double billFee) {
        this.billFee = billFee;
    }

    public boolean isChargeState() {
        return chargeState;
    }

    public void setChargeState(boolean chargeState) {
        this.chargeState = chargeState;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public boolean isIn() {
        return isIn;
    }

    public void setIn(boolean in) {
        isIn = in;
    }

    public int getExpectedStayDays() {
        return expectedStayDays;
    }

    public void setExpectedStayDays(int expectedStayDays) {
        this.expectedStayDays = expectedStayDays;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
