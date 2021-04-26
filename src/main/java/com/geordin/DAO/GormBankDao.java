package com.geordin.DAO;

import com.geordin.BusinessException;
import com.geordin.model.Customer;

import java.sql.SQLException;

public interface GormBankDao {
    //this will declare all of the methods that need to access the database!


    public Customer createNewCustomer(String username, String name, String password) throws SQLException, BusinessException;

//currently working - tentatively
    public void applyForAccount(Customer customer) throws SQLException, BusinessException; //create account,set status to pending
        //should use customer object to validate signin... no, creates a new account with status pending under
    public Customer findCustomerByLogin(String username, String pw) throws SQLException, BusinessException;
        //result was returned where none was requested
    
//currently testing
    //need work
    //ApproveAccount()  //
    //viewAccountsByUsername    //used by employee and cutomer to view employees...
    //viewAllPendingApplications
    //findCustomerByUsername()
    //findCustomerByAccountNumber
    //withdrawFunds
    //depositFunds(
    //viewLogByDate
    //ViewLogByUser
    //ViewLogByAccount
//    public void viewAllLogs() throws SQLException, BusinessException;
    //rejectApplication -delete
    //transferFunds
//    public void viewAllLogs() throws SQLException, BusinessException;
    //createTransaction //used to create record of any activity...

    //NOTES
    //"as a user, I can login", does that mean employees must login too?
    //input customer or employee objects -user object?) to ensure that the correct person is logged in!
    //need to deal with negative balances
    //!!need to implement viewing pending transfers from other accounts... and accepting them...stretch goal?
    //...do I need a findCustomer method?

    //see applications  -   select * from account where status = 'pending'; - accountDAO
    //viewAccountsByUsername - customerDao?


    /*
    currently:
        users can sign up for an account
        ...
        Users can log in to an account
        ...

     */

}
