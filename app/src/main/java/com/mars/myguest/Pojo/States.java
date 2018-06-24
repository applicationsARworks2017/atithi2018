package com.mars.myguest.Pojo;

/**
 * Created by Amaresh on 6/24/18.
 */

public class States {
    String state_id,state_name,is_active,country_id,country_name;

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public States(String state_id, String state_name, String is_active, String country_id, String country_name) {
        this.state_id=state_id;
        this.state_name=state_name;
        this.is_active=is_active;
        this.country_id=country_id;
        this.country_name=country_name;


    }
}
