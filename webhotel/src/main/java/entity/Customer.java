package entity;

public class Customer {
    private int customer_id;
    private String customer_name;
    private String customer_pwd;
    private boolean membership;
    private float balance;

    public Customer(int id, String name, String code, boolean membership, float balance) {
        this.customer_id = id;
        this.customer_name = name;
        this.customer_pwd = code;
        this.membership = membership;
        this.balance = balance;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_pwd() {
        return customer_pwd;
    }

    public void setCustomer_pwd(String customer_pwd) {
        this.customer_pwd = customer_pwd;
    }

    public boolean getMembership() {
        return membership;
    }

    public void setMembership(boolean membership) {
        this.membership = membership;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
