package com.geordin.DAO;

import com.geordin.BusinessException;
import com.geordin.model.Account;
import com.geordin.model.Customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public interface GormBankDao {

    //this will declare all of the methods that need to access the database!
    Customer createNewCustomer(String username, String name, String password) throws SQLException, BusinessException;
    void applyForAccount(Customer customer) throws SQLException, BusinessException; //create account,set status to pending
    Customer findCustomerByLogin(String username, String pw) throws SQLException, BusinessException; //returns customer object when logging in with user and pw
    List<Account> findAccountsByUsername(String username) throws SQLException, BusinessException;   //used by employee to view customers... without getting pw
    Vector<Account> viewPendingApplications() throws SQLException, BusinessException;

    public void viewAccountsByUsername(String username) throws SQLException, BusinessException;   //used by employee to view customers... without getting pw
    public void viewAccountsByAccountNum(long accountNum) throws SQLException, BusinessException;   //used by ...
    public void withdrawFunds(Customer customer, long accountNum, double amount) throws SQLException, BusinessException;
    public void depositFunds(Customer customer, long accountNum, double amount) throws SQLException, BusinessException;
    public void transferFunds(Customer customer, long accountNum, long accountNum2, double amount) throws SQLException, BusinessException;
    //viewLogByDate
    //ViewLogByUser
    //ViewLogByAccount
//    public void viewAllLogs() throws SQLException, BusinessException;
    //rejectApplication -delete
//    public void viewAllLogs() throws SQLException, BusinessException;
    //createTransaction //used to create record of any activity...





}
