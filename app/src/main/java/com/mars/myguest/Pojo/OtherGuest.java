package com.mars.myguest.Pojo;

/**
 * Created by Amaresh on 7/31/18.
 */

public class OtherGuest {

    String id,name,photo,guest_id,relation,document;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(String guest_id) {
        this.guest_id = guest_id;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", name = " + name +
                "photo = " + photo + ", guest_id = " + guest_id + ", relation = " + relation +
                ", document = " + document+"]";
    }
}
