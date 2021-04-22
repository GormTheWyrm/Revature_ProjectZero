package com.geordin;

class Person {
    String username;
    String password;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    protected String retreivePassword(String pw){
        this.password = pw;
        return pw;
    }
    protected String retreiveUsername(String un){
        this.password = un;
        return un;
    }




}
//no arg constructor gets pw and username from sql?


//need to figure out login before filling out this class...
//will employe and customer logins work the same?
//username and password stored in DB?
//so need a get username and getpw functions!

