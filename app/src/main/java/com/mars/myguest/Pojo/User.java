package com.mars.myguest.Pojo;

/**
 * Created by Amaresh on 4/15/18.
 */

public class User {

    String id,name,mobile,login_pin,hotel_id,user_type_id,created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLogin_pin() {
        return login_pin;
    }

    public void setLogin_pin(String login_pin) {
        this.login_pin = login_pin;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getUser_type_id() {
        return user_type_id;
    }

    public void setUser_type_id(String user_type_id) {
        this.user_type_id = user_type_id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", name = " + name + ", mobile = " + mobile + ", " +
                "login_pin = " + login_pin + ", hotel_id = " + hotel_id + ", user_type_id = " + user_type_id +
                ", created = " + created+"]";
    }
}
