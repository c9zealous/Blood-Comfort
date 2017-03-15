package com.bloodcomfort.www.bloodcomfort;

public class Contacts {

    private String name,email,mobile,bloodtype;

    public Contacts(String name,String email,String mobile,String bloodtype){

        this.setName(name);
        this.setEmail(email);
        this.setMobile(mobile);
        this.setBloodtype(bloodtype);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBloodtype() {
        return bloodtype;
    }

    public void setBloodtype(String mobile) {
        this.bloodtype = mobile;
    }
}
