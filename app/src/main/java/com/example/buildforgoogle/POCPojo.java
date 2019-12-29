package com.example.buildforgoogle;

public class POCPojo {
    String firstName,firstPhone,firstRelation,secondName,secondPhone,secondRelation;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstPhone() {
        return firstPhone;
    }

    public void setFirstPhone(String firstPhone) {
        this.firstPhone = firstPhone;
    }

    public String getFirstRelation() {
        return firstRelation;
    }

    public void setFirstRelation(String firstRelation) {
        this.firstRelation = firstRelation;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSecondPhone() {
        return secondPhone;
    }

    public void setSecondPhone(String secondPhone) {
        this.secondPhone = secondPhone;
    }

    public String getSecondRelation() {
        return secondRelation;
    }

    public void setSecondRelation(String secondRelation) {
        this.secondRelation = secondRelation;
    }

    public POCPojo(String firstName, String firstPhone, String firstRelation, String secondName, String secondPhone, String secondRelation) {
        this.firstName = firstName;
        this.firstPhone = firstPhone;
        this.firstRelation = firstRelation;
        this.secondName = secondName;
        this.secondPhone = secondPhone;
        this.secondRelation = secondRelation;
    }
}
