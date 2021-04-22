package com.geordin;
//currently, logger is breaking project
import org.apache.log4j.Logger;

import java.util.Scanner;

public class BankApp {
    //declare class variables


    private static Logger log=Logger.getLogger(BankApp.class);
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        //log hello message
        log.info("Loading...");
        //call menu
        BankApp app = new BankApp();
        app.mainMenu(sc);

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
                case 1: this.signInCustomer(scan);
                    break;
                case 2: //call function
                    break;
                case 3: //call function
                    break;
                case 4: this.QuitApp(scan);
                    break;
                default: log.info("Please enter an integer between 1 and 4");
            }
        } while (menuState != 4);

    }
    private void signInCustomer(Scanner scan){ //return customer
        // this should check sql for a user with the correct pw and username
        //not validating password right now - do after MVP
        String pw = null;
        String user = null;
        log.info("\n\n\n\n\nWelcome returning customer. Please enter your username");
        log.info("Or type EXIT to return to the main menu");
        user = scan.nextLine();
        if (user.equals("exit") || user.equals("EXIT") || user.equals("Exit")){

            log.info("Returning to main Menu");
        }
        else {
            log.info("\nThank You, ." + user + ". now please enter your password");
            pw = scan.nextLine();
            log.trace("temporary response- attempting to sign in");

            //I would send call to database to verify existence of this username and pw combo,
            // and once verified, let user continue to user menu...
            //select account from a list...
            //we should end up with... creatign a customer, and sending that to the customer menu
            //customer menu would verify accounts...

            //customerMenu(customer myCustomer);

//        return customer

        } //exits else statement, leaving signinCustomer
    }
    private void signInEmployee(){  //return employee

//        return employee
    }
    private void signUpCustomer(){  //return employee

//        return employee
    }
    private void QuitApp(Scanner scan){  //return employee
        //this should quit the app...
//        return employee
        log.info("Exiting Application");
        scan.close();
    }



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
            + day (stretch goal
~~~~~
MAIN
    menu
        - sign in as employee or customer
        - sign up as customer
        - exit application
        subMenu
            + sign in as employee
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



uses a scanner to determine if


friday, 46 min in, 2:30 video
 */