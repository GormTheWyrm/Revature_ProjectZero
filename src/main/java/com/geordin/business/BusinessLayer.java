package com.geordin.business;

import com.geordin.BusinessException;
import com.geordin.DAO.Imp.GormBankImp;
import com.geordin.model.Account;
import com.geordin.model.Customer;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;

public class BusinessLayer {
        private static Logger log=Logger.getLogger(BusinessLayer.class);
        //employeeSearch Service would be rough equivalent
        Customer customer = new Customer();
        GormBankImp bankImp = new GormBankImp();
    public Customer signInOldCustomer(Scanner scan, String user, String pw) throws BusinessException { //seems to work!
        try{
            customer = bankImp.findCustomerByLogin(user, pw); //if DB has that user, create it
            log.trace("loginOldCustomer from "+ customer.getUsername()); //this is not null... error is not here?
            //else throws business or sql exception!
            //should I compare java objects?...
        }
        catch (SQLException e){
            log.trace(e.getMessage()); //hopefully that logs the actual error for the developers to find
            throw new BusinessException("Could not find username or password. Please check your spelling and try again.");
        }
        catch (BusinessException e){
            //if found someone
            throw new BusinessException("Username already taken.");
        }
    return customer; //should not return null customer... I hope
    }

    public Customer signInNewCustomer(Scanner scan, String user, String name, String pw) throws BusinessException { //fixme working here
        try{
            customer = bankImp.createNewCustomer(user, name, pw);
            log.trace("business layer - signInNewCustomer: "+ customer.getUsername());
        }

        catch (BusinessException e){
            //if found someone
            throw new BusinessException("Username already taken.");
        }//order shouldnt matter?
        catch (SQLException e){
//            log.trace(e.getMessage()); //commented out because it breaks app...
            throw new BusinessException("Database Error, username may already be taken"); //this is also catching pw/user mismtch
        }//fixme need to validate before entering DB to prevent injection attacks!

        return customer; //should not return null customer... I hope
    }
    public boolean signInEmployee(String pw){ //seems to work
        if (pw.equals("password")){
            return true;
        }
        else {return false;}
    } //will need to edit this if real login required...

    public Vector<Account> findMyAccounts(Customer customer) throws BusinessException{  //works, but am not revalidating pw
        Vector<Account> accounts = new Vector<>();
        try{
        accounts = bankImp.findAccountsByUsername(customer.getUsername());
        log.trace("Business Layer, findMyAccounts: " + accounts); //this is not for the user - displays but ugly
        return accounts;
        }
        catch (BusinessException e){
            throw new BusinessException("No accounts found");
        }
        catch (SQLException e){
//            log.trace(e.getMessage()); //this remains commented because it might break code... but it seems important
            throw new BusinessException("Database Error, please inform IT department");
        }

    }
    public void applyForAccount(Customer customer) throws BusinessException{
        try {
            bankImp.applyForAccount(customer);
        }
        catch(BusinessException | SQLException e){
            throw new BusinessException("An error occurred. Account not created");
        }
    }
    public Vector<Account> viewPendingApplications() throws BusinessException {  //works, but am not revalidating pw
        Vector<Account> accounts = new Vector<>();
        try{
            accounts = bankImp.viewPendingApplications(); //fixme working
            log.trace("Business Layer,viewPendingAccounts: " + accounts); //this is not for the user - displays but ugly
            return accounts;
        }
        catch (BusinessException e){
            throw new BusinessException("No accounts found");
        }
        catch (SQLException e){
//            log.trace(e.getMessage()); //this remains commented because it might break code... but it seems important
            throw new BusinessException("Database Error, please inform IT department");
        }

    }//used by employee to see all accounts of status "pending"
    public void approveApplication(Long accountNum) throws BusinessException {
        try{
            bankImp.approveAccount(accountNum);
        }
        catch (SQLException | BusinessException e){
            throw new BusinessException("No accounts found");
        }


    }

    }

