package com.geordin.model;

import java.util.Objects;

public class Account {
    long accountNumber;
    double balance;
    String userId;
    String status;

    public long getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.accountNumber);
    }
    @Override
    public String toString(){
        return ("Account Number: "+ this.accountNumber + " Balance: "+ this.balance + " UserId: " + this.userId); //may need to change this to userid?
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Account)){ return false;}
        Account other = (Account) o;
        return this.accountNumber == other.accountNumber;
        // special thanks to https://mkyong.com/java/java-how-to-overrides-equals-and-hashcode/
    }
}
// https://docs.oracle.com/javase/8/docs/api/java/util/Hashtable.html
//adding a dateCreated would be good practice