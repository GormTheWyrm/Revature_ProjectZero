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
                menuState  = Integer.parseInt(scan.nextLine());
                        //scan.nextInt(); //parse this with ingeter wrapper from nextline
                //number format exception!

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
//                    this.employeeMenu(scan);
                    this.signInEmployee(scan); //hardcoded employee for now
                    break;
                case 4: this.quitApp(scan);
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
            Customer customer = new Customer("exit", "exit", "exit");
            log.info("\n\n\n\n\nWelcome returning customer. Please enter your username");
            log.info("Or type EXIT to return to the main menu");
            user = scan.nextLine();
            if (user.equals("exit") || user.equals("EXIT") || user.equals("Exit")) {

                log.info("Returning to main Menu");
                break;
            } else {

                log.info("\nThank You, ." + user + ". now please enter your password");
                pw = scan.nextLine();
                if (pw.equals("exit") || pw.equals("EXIT") || pw.equals("Exit")) {

                    log.info("Returning to main Menu");
                    break;
                } else {

                    try {
                        log.trace("customer sign in try block");
                        // need to send username and pw to DB and get a Customer object back
                        // then send that Customer object to the customer menu
//                    customer = customerDAO.findCustomer(user,pw);//should return correct customer
                        customer = bankDao.findCustomerByLogin(user, pw);   //need to figure out casting...
                    } catch (BusinessException | SQLException e) {
                        log.info(e.getMessage());
                        //need to figure out which exceptions refer to what so I can give user more info
                    }
                    //check is customer.passord == password && user == custumer.user


                if ((customer.getPassword().equals(pw)) && (customer.getUsername().equals(user))){  //fixme error null pointer exception
                    //null caused issues so I put exit as pw and username and name... should not be able ot equal that- should break above
                        isSignedIn = true;
                    log.info("\nSigned In, Redirecting to Customer Menu");  //may need to replace \n fixme
                    isSignedIn = true; //fix this area...
                    this.customerMenu(scan, customer);
                }
                else { isSignedIn = false;} //should send back to loop
                }
            }
        }
    } //fixme login broken now!

    private void signUpNewCustomer(Scanner scan) {  //return employee
        int menuState = 0;
        GormBankImp bankImp = new GormBankImp();
        while (menuState != 4) {
            log.trace(menuState);
            log.info("\n\n\n\n\nNew Customer Sign up. Please Create a Unique Username.");
            log.info("Or type EXIT to return to the main menu");
            String user = scan.nextLine();
            //need to catch exception?
            if (user.equals("exit") || user.equals("EXIT") || user.equals("Exit")) {

                log.info("Returning to main Menu");
                break;
            } else {
                log.info("Create a Password.");
                String pw = scan.nextLine();
                //need to catch exception?
                log.info("For Our Records, Please Enter Your Real Name");
                String name = scan.nextLine();

                log.info("Username: " + user + " Name: " + name + " Password: " + pw + ". Is this correct? Y/N");
                String answer = scan.nextLine();
                if (answer.equals("y") || answer.equals("Y") || answer.equals("YES") || answer.equals("Yes") || answer.equals("yes")) {
                    //attempt to put info into database
                    try {
                        bankImp.createNewCustomer(user, name, pw);
                        log.info("User created!");
                        log.info("Redirecting to Customer Menu. New Customers Must Still Apply For A New Account");
                        Customer customer = new Customer(user, pw, name); //id is temporary... may remove all ids from customers...
                        //send to user login!
                        menuState = 0; //skips while loop when customer exits customerMenu -> goes back to main menu
                        //didnt work...why?
                        this.customerMenu(scan, customer);//sends user to the customer menu so they can apply for a new account
                    }
                    catch(NumberFormatException | SQLException | BusinessException e){
                        menuState = 0;
                        log.trace(e);
                        log.info("An Error Occurred. Username might be taken, please try again");
                    }

                }
            }
        }
    }
    private void quitApp(Scanner scan){  //return employee
        //this should quit the app...
//        return employee
        log.info("Exiting Application");
        scan.close();
        System.exit(0);
        //close database? I think java handles that automatically, and forcing it closed can be problematic
    }
    private void customerMenu(Scanner scan, Customer customer){    //need to pass in a customer object
        int menuState = 0;
        GormBankImp bankImp = new GormBankImp();
        while (menuState != 4){
            log.info("\n\n\nSigned in as "+customer.getUsername());
            try {
                log.info("Customer Menu. Please select an option");
                log.info("1. See My Accounts \n2. Apply For New Account \n3. Select An Account \n4. Return to Main Menu");
                    menuState = Integer.parseInt(scan.nextLine());
                    //scan.nextInt(); //parse this with ingeter wrapper from nextline
                    //number format exception!

                switch (menuState) {
                    case 0:
                        log.info("please enter an integer between 1 and 4"); //redirected by catch block
                        break;
                    case 1: //should display customers own accounts   -including status   //
                        bankImp.viewMyAccounts(customer);
                        log.warn("this should display all accounts for the user");
                        break;
                    case 2:
                        //creates a new account and says something about waiting up to 48 hours
                        bankImp.applyForAccount(customer);
                        log.warn("this should create a new account with pending status");
                        //it would be great if users could only have 1 pending account at a time...
                        break;
                    case 3:
                        this.customerAccountMenu(scan, customer); //sends to a new menu
                        break;
                    case 4:
                        log.info("returning to main menu...");
                        this.mainMenu(scan);
                        break;
                    default:
                        log.info("Please enter an integer between 1 and 4");
                }
            } catch (NumberFormatException | SQLException | BusinessException e){
                log.info("invalid response");
                log.trace(e.getMessage());
                menuState = 0;}
        }
    }

    private void customerAccountMenu(Scanner scan, Customer customer) { //customer interacts with a specific account option...
        int menuState = 0;
        long accountNum;
        double amount;
        GormBankImp bankImp = new GormBankImp();
        while (menuState != 6) {
            try {

                log.info("\n\n\n\n\nCustomer Account Menu. Please select an option");
                log.info("1. See Accounts \n2. Withdrawal\n3. Deposit\n4. Transfer Money\n5. Apply For New Application\n6. Return to Customer Menu");
                menuState = Integer.parseInt(scan.nextLine());
                switch (menuState) {
                    case 0:
                        log.info("please enter an integer between 1 and 4");//catch statement redirects here
                        break;
                    case 1: //should display accounts   -including status   //
                        bankImp.viewMyAccounts(customer);
                        log.warn("this should display all accounts for the user");
                        //call function to display all accounts for that customer, customer as parameter
                        break;
                    case 2:
                        log.info("Enter Account Number to Withdraw From");
                        accountNum = Long.parseLong(scan.nextLine());
                        //deal with exception
                        log.info("How much do you want to withdraw?");
                        amount = Double.parseDouble(scan.nextLine());
                        //call function to withdraw
                        log.info("Withdraw Successful");
                        log.warn("not implemented yet!!");
                        bankImp.withdrawFunds(customer, accountNum, amount);
                        break;
                    case 3:
                        log.info("Enter Account Number to Deposit into");
                        accountNum = Long.parseLong(scan.nextLine());
                        //deal with exception
                        log.info("How much do you want to Deposit?");
                        amount = Long.parseLong(scan.nextLine());
                        //call function to deposit
                        log.info("Deposit Successful");
                        log.warn("not implemented yet!!");
                        bankImp.depositFunds(customer, accountNum, amount);
                        break;
                    case 4:
                        log.info("Enter Account Number to Transfer From");
                        accountNum = Long.parseLong(scan.nextLine());
                        //deal with exception
                        log.info("How much do you want to Transfer?");
                        amount = Long.parseLong(scan.nextLine());
                        log.info("Enter Account Number to Transfer Money Into");
                        Long accountNum2 = Long.parseLong(scan.nextLine());
                        log.info("Transfered $" + amount + " from Account Number " + accountNum + " to account number " + accountNum2);
                        log.warn("not implemented yet!!");
                        bankImp.transferFunds(customer, accountNum, accountNum2, amount);
                        break;
                    case 5: //apply for account
                        bankImp.applyForAccount(customer);
                        log.info("Applied for new account under user:" + customer.getUsername());
                        break;
                    case 6:
                        log.info("returning...");
                        break;
                    default:
                        log.info("Please enter an integer between 1 and 6");
                }
            }catch(NumberFormatException | SQLException | BusinessException e){
                menuState = 0;
            }
        }
    }

    private void employeeMenu(Scanner scan){ //fixme
        int menuState = 0;
        GormBankImp bankDao = new GormBankImp();
        while (menuState != 10){
            String username;
            Long accountNumber;
        try{
            log.info("\n\n\n\n\nEmployee Menu\nPlease select an option\n1. See all applications\n2. Search Accounts By Username");
            log.info("3. Search Account by Account Number\n4. Approve Account By Account Number\n5. Deny Account by Account Number");
            log.info("6. View Logs by Account\n7. View Logs By User\n8. View Logs by Date\n9. View All Logs\n10. Return to Main Menu");
            //should accounts with zero money send a notice to employee?...hmm... later maybe
            menuState = Integer.parseInt(scan.nextLine());
            //scan.nextInt(); //parse this with ingeter wrapper from nextline
            //number format exception!

                switch (menuState){
            case 0: log.info("please enter an integer between 1 and 9");
                break;
            case 1:  bankDao.viewAllApplications(); //displays pending applications
                break;
            case 2:
                log.info("Search By Username; Please enter Username.");
                username = scan.nextLine();
                bankDao.viewAccountsByUsername(username);//displays accounts that match query username
                break;
            case 3:
                log.info("Search By Account Number; Please enter Account Number.");
                accountNumber = Long.parseLong(scan.nextLine());
                bankDao.viewAccountsByAccountNum(accountNumber);//displays accounts that match query username
                break;
            case 4:
                log.info("Please Enter Account To Approve.");
                accountNumber = Long.parseLong(scan.nextLine());
                //must catch arithmetic exception
                //approveAccountByAccountNumber
                break;
            case 5:
                log.info("Please Enter Account To Deny.");
                accountNumber = Long.parseLong(scan.nextLine());
                //must catch arithmetic exception
                // denyAccountByAccountNumber
                break;
            case 6:
                log.info("Please Enter Account To View Logs.");
                accountNumber = Long.parseLong(scan.nextLine());
                //must catch arithmetic exception
                //viewLogsByAccountNumber
                break;
            case 7:
                log.info("Please Enter User To View Logs.");
                username = scan.nextLine();
                //viewLogsByUser
                break;
            case 8:
                log.warn("View By Date Not Implemented");
                //viewLogsByDate
//                String date = Long.parseLong(scan.nextLine()); //what type to use for date?String?
                break;
            case 9: //viewAllLogs
                log.warn("view all longs not yet implemented");
                break;
            case 10: log.info("Returning to Main Menu");
                break;

            default: log.info("Please enter an integer between 1 and 9");
        }
        } catch (NumberFormatException | SQLException | BusinessException e){ //need to do a bunch of different exceptions...
            log.info("Invalid entry");
            log.warn(e.getMessage());
            menuState = 0;
        }
    }
    }//if need to implement multiple employees, will need to add employe object to the parameters of employeeMenu
    //fixme
    private void signInEmployee(Scanner scan){
        // this should check sql for a user with the correct pw and username
        boolean isSignedIn = false;
        String pw = null;

        while (isSignedIn == false) {
//            GormBankImp bankDao = new GormBankImp(); //will need this if employee credentials in DB
            //would create employe object here if needed to access DB
            log.info("\n\n\nWelcome Employee. Please enter your Password");
            log.info("Or type EXIT to return to the main menu");
            pw = scan.nextLine();
            if  (pw.equals("exit") || pw.equals("EXIT") || pw.equals("Exit")) {

                    log.info("Returning to main Menu");
                    break;
            }
            else if (pw.equals("password")) {
                log.info("\nSigned In, Redirecting to Employee Menu");  //may need to replace \n fixme
                isSignedIn = true; //fix this area...
                this.employeeMenu(scan); //will want to pass an employee object into this when implementing credentials...
            }
            //else isSignedIn is still false and loops
            }

    }

}


//bigdecimal instead of long... longs are ints!...numeric
//arraylist!?
