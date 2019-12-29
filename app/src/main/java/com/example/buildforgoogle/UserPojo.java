package com.example.buildforgoogle;

import java.io.Serializable;

public class UserPojo implements Serializable {
    private String name;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getComplexion() {
        return complexion;
    }

    public void setComplexion(String complexion) {
        this.complexion = complexion;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    private String dob;
    private String phone;
    private String imgUrl;

    public UserPojo(String name, String dob, String phone, String imgUrl, String complexion, String height, String weight) {
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.imgUrl = imgUrl;
        this.complexion = complexion;
        this.height = height;
        this.weight = weight;
    }

    private String complexion;
    private String height;
    private String weight;
}
