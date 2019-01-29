package com.mars.myguest.Pojo;

public class Guest_List {

    String guest_id, first_name, last_name, mobile, address,
            city, photo, doc_1, doc_2, created,modified,signature,room_id,room_no,price, state, country;
    String tr_id,checkin_time,checkout_time,advance_amonut,total_amount,discount,admin_discount,no_of_days,
            payable_amount,guest_status,dob;

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

    public String getTr_id() {
        return tr_id;
    }

    public void setTr_id(String tr_id) {
        this.tr_id = tr_id;
    }

    public String getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(String checkin_time) {
        this.checkin_time = checkin_time;
    }

    public String getCheckout_time() {
        return checkout_time;
    }

    public void setCheckout_time(String checkout_time) {
        this.checkout_time = checkout_time;
    }

    public String getAdvance_amonut() {
        return advance_amonut;
    }

    public void setAdvance_amonut(String advance_amonut) {
        this.advance_amonut = advance_amonut;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAdmin_discount() {
        return admin_discount;
    }

    public void setAdmin_discount(String admin_discount) {
        this.admin_discount = admin_discount;
    }

    public String getNo_of_days() {
        return no_of_days;
    }

    public void setNo_of_days(String no_of_days) {
        this.no_of_days = no_of_days;
    }

    public String getPayable_amount() {
        return payable_amount;
    }

    public void setPayable_amount(String payable_amount) {
        this.payable_amount = payable_amount;
    }

    public String getGuest_status() {
        return guest_status;
    }

    public void setGuest_status(String guest_status) {
        this.guest_status = guest_status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Guest_List(String guest_id, String first_name, String last_name, String mobile, String address, String city,
                      String photo, String doc_1, String doc_2, String created, String modified, String signature, String room_id, String room_no,
                      String price, String tr_id, String checkin_time, String checkout_time, String advance_amonut, String total_amount,
                      String discount, String admin_discount, String no_of_days, String payable_amount, String guest_status, String state,
                      String country, String dob) {
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
        this.tr_id=tr_id;
        this.checkin_time=checkin_time;
        this.checkout_time=checkout_time;
        this.advance_amonut=advance_amonut;
        this.total_amount=total_amount;
        this.discount=discount;
        this.admin_discount=admin_discount;
        this.no_of_days=no_of_days;
        this.payable_amount=payable_amount;
        this.guest_status=guest_status;
        this.country=country;
        this.state=state;
        this.dob=dob;



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
