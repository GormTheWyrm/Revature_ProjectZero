package com.geordin;
//currently, logger is breaking project

import com.geordin.DAO.Imp.GormBankImp;
import com.geordin.model.Customer;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Scanner;

public class BankApp {

    private static Logger log=Logger.getLogger(BankApp.class);
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        log.info("Loading gormbank user interface...");
        BankApp app = new BankApp();
        app.mainMenu(sc); //main logic, front end
        sc.close(); //close scanner
    }

    private void mainMenu(Scanner scan){    // menu will handle the outer loop
        int menuState = 0;
        do{
            try{
                log.info("\n\n\n\n\nMain Menu\nPlease select an option\n#1. sign in as a customer\n#2. sign up as a customer\n#3. sign in as employee\n#4. Quit");
                int answer = Integer.parseInt(scan.nextLine());
                        //scan.nextInt(); //parse this with ingeter wrapper from nextline
                //number format exception!
                menuState = answer;
            } catch (Exception e){ //need to do a bunch of different exceptions...
                log.info("Invalid entry");
                menuState = 0;
                // needs to actually deal with the exception...
            }
            switch (menuState){
                case 0: log.info("please enter an integer between 1 and 4");
                    break;
                case 1: this.signInCustomer(scan);      //
                    break;
                case 2: this.signUpNewCustomer(scan);   //create new customer and add to database
                    break;
                case 3:
                    this.employeeMenu(scan);
//                    this.signInEmployee(scan); //hardcoded employee for now
                    break;
                case 4: this.QuitApp(scan);
                    break;
                default: log.info("Please enter an integer between 1 and 4");
            }
        } while (menuState != 4);

    }
    private void signInCustomer(Scanner scan) {
        // this should check sql for a user with the correct pw and username
        boolean isSignedIn = false;
        String pw = null;
        String user = null;
        while (isSignedIn == false) {
//            CustomerImp customerDAO = new CustomerImp();    //different from sample code...
            GormBankImp bankDao = new GormBankImp();
            Customer customer = new Customer();
            log.info("\n\n\n\n\nWelcome returning customer. Please enter your username");
            log.info("Or type EXIT to return to the main menu");
            user = scan.nextLine();
            if (user.equals("exit") || user.equals("EXIT") || user.equals("Exit")) {

                log.info("Returning to main Menu");
                break;
            } else {

                log.info("\nThank You, ." + user + ". now please enter your password");
                pw = scan.nextLine();

                try{
                    // need to send username and pw to DB and get a Customer object back
                    // then send that Customer object to the customer menu
//                    customer = customerDAO.findCustomer(user,pw);//should return correct customer
                    customer = bankDao.findCustomerByLogin(user, pw);   //need to figure out casting...
                }
                catch(BusinessException | SQLException e){//sql exception
                log.info(e.getMessage());
                //need to figure out which exceptions refer to what so I can give user more info
                }

                log.trace("temporary response- attempting to sign in");
                isSignedIn = true; //fix this area...
                this.customerMenu(scan, customer);
            }
        }
    }

    private void signUpNewCustomer(Scanner scan){  //return employee
            log.info("this will create a new customer, add it to the database");    //check if this should require approval
//        return employee
    }
    private void QuitApp(Scanner scan){  //return employee
        //this should quit the app...
//        return employee
        log.info("Exiting Application");
        scan.close();
    }
    private void customerMenu(Scanner scan, Customer customer){    //need to pass in a customer object
        //this is where customer can interact with DB..
        //how to set up employees approving new accounts...
        long currentAccount;
        //while loop...
        log.info("\n\n\n\n\nCustomer Menu. Please select an option");
        log.info("#1. Account Info \n2. Quit");
        //options; 1. check balance (also checks account status...) 2. deposit  3.
        //  1. list accounts - checks status and balance
        //  2. deposit  - requires input
        //  2. withdrawal - must be less than amount in account
        //do i need to select account first in a nested loop... or can I sql search via customer id and return the associated accounts...
            //that uses the employee that should be sent to this function via parameter...
    }
    private void employeeMenu(Scanner scan){ //
        int menuState = 0;
        while (menuState != 4){
        try{
            log.info("\n\n\n\n\nEmployee Menu\nPlease select an option\n#1. See all applications\n#2. Search Accounts By Username\n#3. Approve Account By Account Number");
            log.info("Deny Account by Account Number");
            //should accounts with zero money send a notice to employee?...hmm... later maybe
            int answer = Integer.parseInt(scan.nextLine());
            //scan.nextInt(); //parse this with ingeter wrapper from nextline
            //number format exception!
            menuState = answer;

        } catch (Exception e){ //need to do a bunch of different exceptions...
            log.info("Invalid entry");
            menuState = 0;
            // needs to actually deal with the exception...
        }

            //viewAccountsByUsername - customerDao?
            //viewLogByDate
            //ViewLogByUser
            //ViewLogByAccount
            //ApproveAccountByAccount
                switch (menuState){
            case 0: log.info("please enter an integer between 1 and 4");
                break;
            case 1: //see applications
                break;
            case 2: //
                break;
            case 3: //
                break;
            case 4: log.info("Returning to Main Menu");
                break;
            default: log.info("Please enter an integer between 1 and 4");
        }
    }
    }//if need to implement multiple employees, will need to add employe object to the parameters of employeeMenu


}

/*
Bank App
customer can
    apply for an account
    view balance
    make withdrawals and deposits
EMPloyee can
    approve or deny accounts
    view balances...
        - sort/filter by
            + customer
            + account
            + day (stretch goal)
~~~~~
MAIN
    menu
        - sign in as employee
        - sign is as customer
        - sign up as customer
        - exit application
        subMenu
            + sign in as employee   {
                - see open applications
                - view customer (activity/balance plus applications)
                    -activity
                    -balance
                - view account (activity plus balance
                    -activity
                    -balance
            +sign in as customer
                - select account belonging to that customer
                - apply for new account
            + logout to main menu

    ~~~
Main calls menu, which can be a method in main or a separate class... probably doesnt need to be a separate class...
Menu handles most logic, keep it in main
Menu calls employee signin or customer signin methods
    these methods take in a person object?
...
//need to figure out logging first
// then write a basic menu...



transaction has account, accounts have user


friday, 46 min in, 2:30 video
 */