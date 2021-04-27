package com.geordin.frontend;

import com.geordin.BusinessException;
import com.geordin.business.BusinessLayer;
import com.geordin.model.Customer;
import com.geordin.model.Employee;
import org.apache.log4j.Logger;

import java.util.Scanner;

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


            // choose type to log in
            //then type in credentials or details - separate minimenu for all 3 options!
            // should end in a customer or employee object... cast as user?
            //send to a new menu!!! ...not return anything but pass in the user!

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
                        log.info("For Our Records, please enter your real name");
                        String name = scan.nextLine();
                        if (pw.equals("exit") || pw.equals("EXIT") || pw.equals("Exit") || user.equals("exit") || user.equals("EXIT") || user.equals("Exit")){
                            menuState = 0; //goes back to login menu
                            userLoggedIn = false;
                        }
                        else{
                            try{
                                Customer customer = businessLayer.signInNewCustomer(scan, user,name, pw);
                                this.customerMenu(scan, customer);
                            }
                            catch (BusinessException e){
                            }
                        }
                        break;
                    case 3://login as employee - currently hardcoded
                        log.info("\n\n\nWelcome Employee. Please enter your password");
                        log.info("Or type EXIT to return to the main menu");
                        pw = scan.nextLine();
                        userLoggedIn = businessLayer.signInEmployee(pw);   //fixme
//                    this.employeeMenu(scan);
//                        this.signInEmployee(scan); //hardcoded employee for now
                        if (userLoggedIn == true){
                            this.EmployeeMenu(scan);
                        }
                        break;
                    case 4: this.quitApp(scan);
                        break;
                    default: log.info("Please enter an integer between 1 and 4");
                } //end switch

        }   //mainmenu should be done.
        //I AM HERE fixme

    }
    public void customerMenu(Scanner scan, Customer customer){
        //goes here when customer is logged in

    }
    public void EmployeeMenu(Scanner scan, Employee employee){
        //goes here when Employee is logged in
    }//empty method for when employee login connected to DB
    public void EmployeeMenu(Scanner scan){
        //goes here when Employee is logged in
    } //hardcoded version of this function
    private void quitApp(Scanner scan){  //return employee
        //this should quit the app...
//        return employee
        log.info("Exiting Application");
        scan.close();
        System.exit(0);
        //close database? I think java handles that automatically, and forcing it closed can be problematic
    }


}
//NumberFormatException | SQLException | BusinessException e