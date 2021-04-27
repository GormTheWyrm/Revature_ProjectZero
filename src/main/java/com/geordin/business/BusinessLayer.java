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
    public Customer signInOldCustomer(Scanner scan, String user, String pw) throws BusinessException {
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
    return customer; //should not return null customer... I hope
    }

    public Customer signInNewCustomer(Scanner scan, String user, String name, String pw) throws BusinessException { //fixme working here
        try{
            Customer customer = bankImp.createNewCustomer(user, name, pw);
            log.trace("business layer - signInNewCustomer: "+ customer.getUsername());
        }
        catch (BusinessException e){
            //if found someone
            throw new BusinessException("Username already taken.");
        }
        catch (SQLException e){
            log.trace(e.getMessage()); //hopefully that logs the actual error for the developers to find
            throw new BusinessException("Database Error, please inform IT department");
        }
        return customer; //should not return null customer... I hope
    }
    public boolean signInEmployee(String pw){
        if (pw.equals("password")){
            return true;
        }
        else {return false;}
    } //will need to edit this if real login required...

    public Vector<Account> findMyAccounts(Customer customer) throws BusinessException{
        //pass in customer, should return a collection of accounts...
        //set list to findAccountsByUsername(customer.getUsername()) throws business exception; fixme here
//        public void viewAccountsByUsername(customer.getUsername(), customer.getPassword()); //not revalidating password... thats an issue
        //need to pass this into a collection instead of simply logging it...
        Vector<Account> accounts = new Vector<>();
        try{
        accounts = bankImp.findAccountsByUsername(customer.getUsername());
        return accounts;
        //needs testing fixme might need to be iterated...
        }
        catch (BusinessException e){
            //if found someone
            throw new BusinessException("No accounts found");
        }
        catch (SQLException e){
            log.trace(e.getMessage()); //hopefully that logs the actual error for the developers to find
            throw new BusinessException("Database Error, please inform IT department");
        }

    }


    //I need to implement a business layer...

    /*need to know
        1. customer or employee
        2. current user object (employee or cutomer)
            - accounts...
         3... what to do with the above info...



     */




}
