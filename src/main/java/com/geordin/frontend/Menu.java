package com.geordin.frontend;

import com.geordin.BusinessException;
import com.geordin.business.BusinessLayer;
import com.geordin.model.Account;
import com.geordin.model.Customer;
import org.apache.log4j.Logger;

import java.util.Scanner;
import java.util.Vector;

public class Menu { //may change name to app...
    private static Logger log=Logger.getLogger(Menu.class);
    //sign in as employee or customer
        //includes sign up
        // will pass forward a user object... cast as employee/customer?

    //next menu; accounts...no...    //alternatively...emplyee can manage customers and such as well...

    public static void main(String[] args) {
        //not sure if main is needed... might call this from something else...
        Scanner sc = new Scanner(System.in);
        Menu bankApplication = new Menu();
        bankApplication.loginMenu(sc);   //calls main Menu

    }
    public void loginMenu(Scanner scan){
        BusinessLayer businessLayer = new BusinessLayer();
        boolean userLoggedIn = false;
        int menuState = 0;
        while (!userLoggedIn || menuState != 4){  //while user is not logged in, or not quite, keep trying menu
            //first layer
            String user;
            String pw;
                try{
                    log.info("\n\n\n\n\nMain Menu\nPlease select an option\n#1. sign in as a customer\n#2. sign up as a customer\n#3. sign in as employee\n#4. Quit");
                    menuState  = Integer.parseInt(scan.nextLine());
                } catch (NumberFormatException e){
                    log.info("Invalid entry");
                    menuState = 0;
                    userLoggedIn = false;
                }
                switch (menuState){
                    case 0: log.info("please enter an integer between 1 and 4");
                        break;
                    case 1: //returning user logging in
                        log.info("\n\n\nWelcome returning customer. Please enter your username");
                        log.info("Or type EXIT to return to the main menu");
                        user = scan.nextLine();
                        log.info("\n\n\nWelcome returning customer. Please enter your password");
                        log.info("Or type EXIT to return to the main menu");
                        pw = scan.nextLine();
                        if (pw.equals("exit") || pw.equals("EXIT") || pw.equals("Exit") || user.equals("exit") || user.equals("EXIT") || user.equals("Exit")){
                            menuState = 0; //goes back to login menu
                            userLoggedIn = false;
                        }
                        else{
                            try{
                                Customer customer = businessLayer.signInOldCustomer(scan, user, pw);
                                log.info("Redirecting to Customer Menu: "+ customer.getUsername());
                                log.trace(businessLayer.signInOldCustomer(scan, user, pw));
                                this.customerMenu(scan, customer);
                            }
                            catch (BusinessException e){
                            }
                        }
                        break;
                    case 2: //new customer
                        log.info("\n\n\nWelcome New customer. Please enter your username");
                        log.info("Or type EXIT to return to the main menu");
                        user = scan.nextLine();
                        log.info("\n\n\nWelcome New customer. Please enter your password");
                        log.info("Or type EXIT to return to the main menu");
                        pw = scan.nextLine();

                        if (pw.equals("exit") || pw.equals("EXIT") || pw.equals("Exit") || user.equals("exit") || user.equals("EXIT") || user.equals("Exit")){
                            menuState = 0; //goes back to login menu
                            userLoggedIn = false;
                            log.info("Returning to Main Menu");
                        }
                        else{
                            try{
                                log.info("For Our Records, please enter your real name");
                                String name = scan.nextLine();
                                Customer customer = businessLayer.signInNewCustomer(scan, user,name, pw);
                                this.customerMenu(scan, customer);
                            }
                            catch (BusinessException e){
                                log.info(e.getMessage());
                                menuState = 0;
                            }
                        }
                        break;
                    case 3://login as employee - currently hardcoded
                        log.info("\n\n\nWelcome Employee. Please enter your password");
                        log.info("Or type EXIT to return to the main menu");
                        pw = scan.nextLine();
                        userLoggedIn = businessLayer.signInEmployee(pw);
                            log.trace("employeeLoggedIn - Menu: "+ userLoggedIn);
                        if (userLoggedIn == true){
                            this.EmployeeMenu(scan);
                        }
                        break;
                    case 4: this.quitApp(scan);
                        break;
                    default: log.info("Please enter an integer between 1 and 4");
                } //end switch
        }
    }
    public void customerMenu(Scanner scan, Customer customer) throws BusinessException {
        int menuState = 0;
        BusinessLayer businessLayer = new BusinessLayer(); //there is probably a better way to do this than creating one for each menu...
        while (menuState != 6){
            log.info("\n\n\nSigned in as "+customer.getUsername());
            try {
                log.info("Customer Menu. Please select an option");
                log.info("1. See My Accounts \n2. Apply For New Account \n3. Make A Withdrawal\n4. Make A Deposit\n5. Transfer Money\n6. Return to Main Menu");
                menuState = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e){
                log.info("Invalid entry");
                menuState = 0;
            }
            //fixme; finish implementing cases
                switch (menuState) {
                    case 0:
                        log.info("please enter an integer between 1 and 6"); //redirected by catch block
                        break;
                    case 1: //should display customers own accounts   -including status   //
                        try {//this needs to set accounts, but for nwo it can just display them fixme
                            Vector<Account> accounts = new Vector<>();
                            accounts = businessLayer.findMyAccounts(customer); //seems to work!
                            accounts.forEach(acc -> log.info(acc.toString())); //SUCCESS!! displays each account
                        }
                        catch (BusinessException e) {
                            log.info(e.getMessage());
                            menuState = 0;
                        }
                        //should return account collection and then log all accounts
//                        bankImp.viewMyAccounts(customer);
//                        log.warn("this should display all accounts for the user");
                        break;
                    case 2:// apply for new account
                        try{
                            businessLayer.applyForAccount(customer);
                            log.info("Account Created. It may take up to 48 hours for the account to be approved.");
                        }
                        catch (BusinessException e){
                            log.info(e.getMessage());
                            menuState = 0;
                        }
                        break;
                    case 3: //withdrawal
                        log.info("Withdrawal method not yet implemented");
                        break;
                    case 4: //deposit
                    log.info("Deposit method not yet implemented");
                        break;
                    case 5: //transfer
                        break;
                    case 6: //returning to main menu
                        log.info("Returning to main menu");
                    default:
                        log.info("Please enter an integer between 1 and 6");
                }

        }
    }

    public void EmployeeMenu(Scanner scan) {
        int menuState = 0;
        BusinessLayer businessLayer = new BusinessLayer(); //there is probably a better way to do this than creating one for each menu...
        while (menuState != 10) {
            log.info("\n\n\nSigned in as employee");
            try {
                log.info("Employee Menu. Please select an option\n1. See all pending applications\n2. Search Accounts By Username");
                log.info("3. Search Account by Account Number\n4. Approve Account By Account Number\n5. Deny Account by Account Number");
                log.info("6. View Logs by Account\n7. View Logs By User\n8. View Logs by Date\n9. View All Logs\n10. Return to Main Menu");
                menuState = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                log.info("Invalid entry");
                menuState = 0;
            }
            //fixme; not all cases implemented
            switch (menuState) {
                case 0: //catch exceptions
                    log.info("please enter an integer between 1 and 10"); //redirected by catch block
                    break;
                case 1: //see all pending accounts
                    Vector<Account> accounts = new Vector<>();
                    try {
                        accounts = businessLayer.viewPendingApplications();
                        ; //seems to work!
                        accounts.forEach(acc -> log.info(acc.toString())); //SUCCESS!! displays each account
                    } catch (BusinessException e) {
                        log.info(e.getMessage());
                        menuState = 0;
                    }
                    break;
                case 2://search by username
                    log.info("search by username not yet implemented");
//                    log.info("Search By Username; Please enter Username.");
//                    username = scan.nextLine();
//                    bankDao.viewAccountsByUsername(username);//displays accounts that match query username
                    break;
                case 3: //search by account number
                    log.info("Search By Account Number not yet implemented");
//                    log.info("Search By Account Number; Please enter Account Number.");
//                    accountNumber = Long.parseLong(scan.nextLine());
//                    bankDao.viewAccountsByAccountNum(accountNumber);//displays accounts that match query username
                    break;
                case 4: //approve account by number
                    log.info("Approve By Account Number not yet implemented"); //fixme next method to implement!
//                    log.info("Please Enter Account To Approve.");
//                    accountNumber = Long.parseLong(scan.nextLine());
//                    //must catch arithmetic exception
//                    //approveAccountByAccountNumber
                    break;
                case 5: //deny account by number
//                    log.info("Please Enter Account To Deny.");
                    log.info("Deny By Account Number not yet implemented");
//                    accountNumber = Long.parseLong(scan.nextLine());
//                    //must catch arithmetic exception
//                    // denyAccountByAccountNumber
                    break;
                case 6: //view logs by account
//                    log.info("Please Enter Account To View Logs.");
                    log.info("View All Logs not yet implemented");
//                    accountNumber = Long.parseLong(scan.nextLine());
                    //must catch arithmetic exception
                    //viewLogsByAccountNumber
                    break;
                case 7: //view logs by user
                    log.info("View Logs By User not yet implemented");
//                    log.info("Please Enter User To View Logs.");
//                    username = scan.nextLine();
                    //viewLogsByUser
                    break;
                case 8: //view logs by date
                    log.warn("View By Date Not Implemented");
                    //viewLogsByDate
//                String date = Long.parseLong(scan.nextLine()); //what type to use for date?String?
                    break;
                case 9: //viewAllLogs
                    log.info("view All Logs not yet implemented");
                    break;
                case 10:
                    log.info("Returning to Main Menu");
                    break;

                default:
                    log.info("Please enter an integer between 1 and 10");
            }
        }
    }



    private void quitApp(Scanner scan){  //exits application
        log.info("Exiting Application");
        scan.close();
        System.exit(0);
        //close database? I think java handles that automatically, and forcing it closed can be problematic
    }


}
//NumberFormatException | SQLException | BusinessException e