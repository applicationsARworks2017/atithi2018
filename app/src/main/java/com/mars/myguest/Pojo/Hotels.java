package com.mars.myguest.Pojo;

/**
 * Created by Amaresh on 4/18/18.
 */

public class Hotels {
    /*
    *
    *  "id": 1,
            "name": "gfdsgfdsg",
            "address": "gfdgfdggfdg",
            "photo": "http://a2r.in/atithi/files/profile/file15236756231976992687.jpg",
            "is_active": "Y",
            "created": "2018-04-14T03:13:43+00:00",
            "modified": "2018-04-14T03:13:43+00:00"
    * */
    String id,name,address,photo,is_active,created;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", name = " + name +
                "photo = " + photo + ", address = " + address + ", is_active = " + is_active +
                ", created = " + created+"]";
    }
}
