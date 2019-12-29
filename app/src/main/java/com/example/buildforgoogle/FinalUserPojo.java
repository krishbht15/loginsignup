package com.example.buildforgoogle;

public class FinalUserPojo {
    UserPojo userPojo;
    POCPojo pocPojo;

    public UserPojo getUserPojo() {
        return userPojo;
    }

    public void setUserPojo(UserPojo userPojo) {
        this.userPojo = userPojo;
    }

    public POCPojo getPocPojo() {
        return pocPojo;
    }

    public void setPocPojo(POCPojo pocPojo) {
        this.pocPojo = pocPojo;
    }

    public FinalUserPojo(UserPojo userPojo, POCPojo pocPojo) {
        this.userPojo = userPojo;
        this.pocPojo = pocPojo;
    }
}
