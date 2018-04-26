package com.mars.myguest.Pojo;

public class Room_List {

  String  room_id, hotel_id, room_no, room_price,room_isactive,
    created, modified,rooml_user_id,room_user_name, room_user_address,
    room_user_photo,room_user_created,room_user_modified;

    public Room_List(String room_id, String hotel_id, String room_no, String room_price, String room_isactive,
                     String created, String modified, String rooml_user_id, String room_user_name,
                     String room_user_address, String room_user_photo, String room_user_created, String room_user_modified) {
      this.room_id=room_id;
      this.hotel_id=hotel_id;
      this.room_no=room_no;
      this.room_price=room_price;
      this.room_isactive=room_isactive;
      this.created=created;
      this.modified=modified;
      this.rooml_user_id=rooml_user_id;
      this.room_user_name=room_user_name;
      this.room_user_address=room_user_address;
      this.room_user_photo=room_user_photo;
      this.room_user_created=room_user_created;
      this.room_user_modified=room_user_modified;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getRoom_price() {
        return room_price;
    }

    public void setRoom_price(String room_price) {
        this.room_price = room_price;
    }

    public String getRoom_isactive() {
        return room_isactive;
    }

    public void setRoom_isactive(String room_isactive) {
        this.room_isactive = room_isactive;
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

    public String getRooml_user_id() {
        return rooml_user_id;
    }

    public void setRooml_user_id(String rooml_user_id) {
        this.rooml_user_id = rooml_user_id;
    }

    public String getRoom_user_name() {
        return room_user_name;
    }

    public void setRoom_user_name(String room_user_name) {
        this.room_user_name = room_user_name;
    }

    public String getRoom_user_address() {
        return room_user_address;
    }

    public void setRoom_user_address(String room_user_address) {
        this.room_user_address = room_user_address;
    }

    public String getRoom_user_photo() {
        return room_user_photo;
    }

    public void setRoom_user_photo(String room_user_photo) {
        this.room_user_photo = room_user_photo;
    }

    public String getRoom_user_created() {
        return room_user_created;
    }

    public void setRoom_user_created(String room_user_created) {
        this.room_user_created = room_user_created;
    }

    public String getRoom_user_modified() {
        return room_user_modified;
    }

    public void setRoom_user_modified(String room_user_modified) {
        this.room_user_modified = room_user_modified;
    }
}
