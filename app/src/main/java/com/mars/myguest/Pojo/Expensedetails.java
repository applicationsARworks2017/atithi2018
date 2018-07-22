package com.mars.myguest.Pojo;

/**
 * Created by Amaresh on 7/2/18.
 */

public class Expensedetails {
    String id;
    String details;
    String amount;

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    String total_amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    String created;


    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", details = " + details +
                ", amount = " + amount +", total_amount = " + total_amount + ",  created = " + created+"]";
    }
}
