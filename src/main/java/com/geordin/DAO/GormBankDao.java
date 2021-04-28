package com.geordin.DAO;

import com.geordin.BusinessException;
import com.geordin.model.Account;
import com.geordin.model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface GormBankDao {

    //this will declare all of the methods that need to access the database!


    public Customer createNewCustomer(String username, String name, String password) throws SQLException, BusinessException;

//currently working - tentatively
    public void applyForAccount(Customer customer) throws SQLException, BusinessException; //create account,set status to pending
    public Customer findCustomerByLogin(String username, String pw) throws SQLException, BusinessException; //returns customer object when logging in with user and pw
        //result was returned where none was requested
    public void viewAllApplications() throws SQLException, BusinessException; //views all accounts with "pending" status
                    //is basically viewAllPendingApplications...should I change name? no
    public void viewAccountsByUsername(String username) throws SQLException, BusinessException;   //used by employee to view customers... without getting pw



//currently testing
public List<Account> findAccountsByUsername(String username) throws SQLException, BusinessException;   //used by employee to view customers... without getting pw
    //replacing viewAccountsByUsername  fixme currently working on this!

    public void viewAccountsByAccountNum(long accountNum) throws SQLException, BusinessException;   //used by ...
        // did not sout anything
    //not built
public void viewMyAccounts(Customer customer)throws SQLException, BusinessException; //shows user their own accounts

//public Customer findCustomerByLoginNoPW(String username, String pw) throws SQLException, BusinessException;
    //should be find account by customer!

//    public void viewAccountsById() //oops... did I need this?
    //need work
    //ApproveAccount()  //

    //findCustomerByUsername()
    //findCustomerByAccountNumber
    public void withdrawFunds(Customer customer, long accountNum, double amount) throws SQLException, BusinessException;
    public void depositFunds(Customer customer, long accountNum, double amount) throws SQLException, BusinessException;
    public void transferFunds(Customer customer, long accountNum, long accountNum2, double amount) throws SQLException, BusinessException;
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

//need to get a delete function to prove my CRUD
    /*
    currently:
        users can sign up for an account
        ...
        Users can log in to an account
        ...

     */

}
