package com.geordin.business;

import com.geordin.BusinessException;
import com.geordin.DAO.Imp.GormBankImp;
import com.geordin.model.Customer;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Scanner;

public class BusinessLayer {
        private static Logger log=Logger.getLogger(BusinessLayer.class);
        //employeeSearch Service would be rough equivalent
        Customer customer = new Customer();
        GormBankImp bankImp = new GormBankImp();
    public Customer signInOldCustomer(Scanner scan, String user, String pw) throws BusinessException { //
        try{
            Customer customer = bankImp.findCustomerByLogin(user, pw); //if DB has that user, create it
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



    //I need to implement a business layer...

    /*need to know
        1. customer or employee
        2. current user object (employee or cutomer)
            - accounts...
         3... what to do with the above info...



     */




}
