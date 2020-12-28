package com.app.e_shopping.Model;

public class Users {

    private String email,name,password, image;
    public Users(){

    }

    public Users(String email, String name, String password, String image) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.image = image;
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
