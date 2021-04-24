package com.geordin.model;

public class Employee extends User {

    //constructor
    public Employee(){
        super("employee1", "password");
    } //constructor hardcodes employee password

//no class variables right now,

    @Override
    public boolean verifyUser(String user, String pw){  // using this instead of a login method because we are not enabling any sort of tracking method to ensure they remain logged in. Its just a loop.
        //connect to db then return true?
        //or just return true?
        return false;
        //returns true if login is successful
    } //this is designed so that employee can be expanded in the future.
    public boolean verifyUser(){  // using this instead of a login method because we are not enabling any sort of tracking method to ensure they remain logged in. Its just a loop.
        //connect to db then return true?
        //or just return true?
        return false;
        //returns true if login is successful
    } //this is default method to be used to sign in with employee

}
