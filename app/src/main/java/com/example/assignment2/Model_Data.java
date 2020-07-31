package com.example.assignment2;

public class Model_Data {
    public String name,gmail,mobile;

    public Model_Data() {
    }

    public Model_Data(String name, String gmail, String mobile) {
        this.name = name;
        this.gmail = gmail;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
