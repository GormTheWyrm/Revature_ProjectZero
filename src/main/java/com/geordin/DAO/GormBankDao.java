package com.geordin.DAO;

import com.geordin.BusinessException;
import com.geordin.model.Customer;

import java.sql.SQLException;

public interface GormBankDao {
    //this will declare all of the methods that need to access the database!


//    public Customer createNewCustomer(Long id, String username, String name, String password) throws SQLException, BusinessException;
//    public void applyForAccount(Customer customer) throws SQLException, BusinessException; //create account,set status to pending
        //should use customer object to validate signin... no, creates a new account with status pending under

    //ApproveAccount()  //
    //viewAccountsByUsername
    //viewAllPendingApplications
    //findCustomerByUsername()
    //findCustomerByAccountNumber
    //withdrawFunds
    //depositFunds(
    //viewLogByDate
    //ViewLogByUser
    //ViewLogByAccount
    //rejectApplication -delete
    //transferFunds
    //viewAllLogs()
    public Customer findCustomerByLogin(String username, String pw) throws SQLException, BusinessException;


    //NOTES
    //"as a user, I can login", does that mean employees must login too?
    //input customer or employee objects -user object?) to ensure that the correct person is logged in!
    //need to deal with negative balances
    //!!need to implement viewing pending transfers from other accounts... and accepting them...stretch goal?
    //...do I need a findCustomer method?

    //see applications  -   select * from account where status = 'pending'; - accountDAO
    //viewAccountsByUsername - customerDao?



}
