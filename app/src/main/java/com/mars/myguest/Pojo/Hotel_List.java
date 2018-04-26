package com.mars.myguest.Pojo;

public class Hotel_List {

    String hotel_id, hotel_name, hotel_address, hotel_photo, hotel_isactive, created, modified,
            hotel_user_id,hotel_user_name,hotel_user_mobile,hotel_user_login_pin,hotel_user_hotel_id, hotel_user_type_id,
            hotel_user_status,hotel_user_created,hotel_user_modified;

    public Hotel_List(String hotel_id, String hotel_name, String hotel_address, String hotel_photo,
                      String hotel_isactive, String created, String modified, String hotel_user_id,
                      String hotel_user_name, String hotel_user_mobile, String hotel_user_login_pin,
                      String hotel_user_hotel_id, String hotel_user_type_id, String hotel_user_status,
                      String hotel_user_created, String hotel_user_modified) {
        this.hotel_id=hotel_id;
        this.hotel_name=hotel_name;
        this.hotel_address=hotel_address;
        this.hotel_photo=hotel_photo;
        this.hotel_isactive=hotel_isactive;
        this.created=created;
        this.modified=modified;
        this.hotel_user_id=hotel_user_id;
        this.hotel_user_name=hotel_user_name;
        this.hotel_user_mobile=hotel_user_mobile;
        this.hotel_user_login_pin=hotel_user_login_pin;
        this.hotel_user_hotel_id=hotel_user_hotel_id;
        this.hotel_user_type_id=hotel_user_type_id;
        this.hotel_user_status=hotel_user_status;
        this.hotel_user_created=hotel_user_created;
        this.hotel_user_modified=hotel_user_modified;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_address() {
        return hotel_address;
    }

    public void setHotel_address(String hotel_address) {
        this.hotel_address = hotel_address;
    }

    public String getHotel_photo() {
        return hotel_photo;
    }

    public void setHotel_photo(String hotel_photo) {
        this.hotel_photo = hotel_photo;
    }

    public String getHotel_isactive() {
        return hotel_isactive;
    }

    public void setHotel_isactive(String hotel_isactive) {
        this.hotel_isactive = hotel_isactive;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getHotel_user_id() {
        return hotel_user_id;
    }

    public void setHotel_user_id(String hotel_user_id) {
        this.hotel_user_id = hotel_user_id;
    }

    public String getHotel_user_name() {
        return hotel_user_name;
    }

    public void setHotel_user_name(String hotel_user_name) {
        this.hotel_user_name = hotel_user_name;
    }

    public String getHotel_user_mobile() {
        return hotel_user_mobile;
    }

    public void setHotel_user_mobile(String hotel_user_mobile) {
        this.hotel_user_mobile = hotel_user_mobile;
    }

    public String getHotel_user_login_pin() {
        return hotel_user_login_pin;
    }

    public void setHotel_user_login_pin(String hotel_user_login_pin) {
        this.hotel_user_login_pin = hotel_user_login_pin;
    }

    public String getHotel_user_hotel_id() {
        return hotel_user_hotel_id;
    }

    public void setHotel_user_hotel_id(String hotel_user_hotel_id) {
        this.hotel_user_hotel_id = hotel_user_hotel_id;
    }

    public String getHotel_user_type_id() {
        return hotel_user_type_id;
    }

    public void setHotel_user_type_id(String hotel_user_type_id) {
        this.hotel_user_type_id = hotel_user_type_id;
    }

    public String getHotel_user_status() {
        return hotel_user_status;
    }

    public void setHotel_user_status(String hotel_user_status) {
        this.hotel_user_status = hotel_user_status;
    }

    public String getHotel_user_created() {
        return hotel_user_created;
    }

    public void setHotel_user_created(String hotel_user_created) {
        this.hotel_user_created = hotel_user_created;
    }

    public String getHotel_user_modified() {
        return hotel_user_modified;
    }

    public void setHotel_user_modified(String hotel_user_modified) {
        this.hotel_user_modified = hotel_user_modified;
    }
}
