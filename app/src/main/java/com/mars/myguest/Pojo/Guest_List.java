package com.mars.myguest.Pojo;

public class Guest_List {

    String guest_id, first_name, last_name, mobile, address,
            city, photo, doc_1, doc_2, created,modified,signature,room_id,room_no,price;

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Guest_List(String guest_id, String first_name, String last_name, String mobile, String address, String city,
                      String photo, String doc_1, String doc_2, String created, String modified, String signature, String room_id, String room_no,
                      String price) {
        this.guest_id=guest_id;
        this.first_name=first_name;
        this.last_name=last_name;
        this.mobile=mobile;
        this.address=address;
        this.city=city;
        this.photo=photo;
        this.doc_1=doc_1;
        this.doc_2=doc_2;
        this.created=created;
        this.modified=modified;
        this.signature=signature;
        this.room_id=room_id;
        this.room_no=room_no;
        this.price=price;


    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(String guest_id) {
        this.guest_id = guest_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDoc_1() {
        return doc_1;
    }

    public void setDoc_1(String doc_1) {
        this.doc_1 = doc_1;
    }

    public String getDoc_2() {
        return doc_2;
    }

    public void setDoc_2(String doc_2) {
        this.doc_2 = doc_2;
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
}
