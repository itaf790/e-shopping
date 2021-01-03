package com.app.e_shopping.Model;

public class Users {

    private String email,name,password,phone, address,  image, lastname;
    public Users(){

    }



    public Users(String email, String phone, String address, String name, String password, String image, String lastname) {
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.image = image;
        this.phone = phone;
        this.address= address;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Users(String email, String name, String password) {



    }
}
