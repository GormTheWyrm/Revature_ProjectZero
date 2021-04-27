package com.geordin.DAO.Imp;

import com.geordin.BusinessException;
import com.geordin.DAO.GormBankDao;
import com.geordin.bdutil.PostgresConnection;
import com.geordin.model.Customer;

import java.sql.*;

// resutset with prepared statement
public class GormBankImp implements GormBankDao {

    public Customer createNewCustomer(String username, String name, String password) throws SQLException, BusinessException {
        //this method is used to create a new customer in the database - when applying to be a customer
        //checks if the username (or user id) exists,
        //and only create the customer if the user does not exist
        Customer customer = new Customer();         //creates customer object that will get returned if it goes well
        //database connection
        Connection connection = PostgresConnection.getConnection();
        String sql = "INSERT INTO gormbank.customers\n" +
                "(username, \"name\", \"password\")\n" +
                "VALUES(?, ?, ?);\n";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, username);    //variables sent into DB
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, password);
        int c = preparedStatement.executeUpdate();
        //fixme - should this be executeQuery instead?
        if (c == 1) {
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) { //this is needed to get a response
                customer.setUsername(resultSet.getString(2)); //index 1 should be userid
                System.out.println("sysout username: " + resultSet.getString(2));
                customer.setName(resultSet.getString(3));
                System.out.println("sysout name: " + resultSet.getString(3));
                customer.setPassword(resultSet.getString(4));
                System.out.println("sysout PW: " + resultSet.getString(4));
                //need to set accounts//fixme
                //check if these values were really set...

            }
        } else {
            throw new BusinessException("Failure in registration... Please retry.....");
        }
        return customer; //this returns a customer even if it doesnt work...?
    }

    public Customer findCustomerByLogin(String username, String pw) throws SQLException, BusinessException {
        Customer customer = new Customer();

        //step 2 connection
        Connection connection = PostgresConnection.getConnection();
        //Step 3- Create Statement
        String sql = "SELECT username, name, password from gormbank.customers WHERE username = ? AND password = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(sql); //2nd par makes keys returnable...
        preparedStatement.setString(1, username);    //variables sent into DB
        preparedStatement.setString(2, pw);

        //Step 4 - Execute Query
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("Query executed - replace with trace");

        //Step 5 - Process Results  THIS WILL BE IMPORTANT~
        //        while (resultSet.next()){
        if (resultSet.next()) {
            System.out.println("Login Successful, welcome "+ resultSet.getString("username")); //just realized I dont need to display the records for this

            //put variables in customer and return it!
            //set name and password - but only if returned from DB
            customer.setUsername(resultSet.getString("username"));
            customer.setName(resultSet.getString("name"));
            customer.setPassword(resultSet.getString("password"));
        } //needs testing
        else {
            throw new BusinessException("No User Found, Please Check Your Username and Password");
        } //if no results, throw exception
        return customer;
    }


    //ACCOUNTS
    public void viewAllApplications() throws SQLException, BusinessException { //fixme wrong method, but useful for getMyAccounts and getaccbyuser...
        Connection connection = PostgresConnection.getConnection();
//        String sql = "SELECT * from gormbank.accounts;";
        String sql = "select customers.userid, customers.username, customers.name, " +
                "accounts.account_number, accounts.balance, accounts.status " +
                "from gormbank.customers RIGHT join gormbank.accounts on accounts.userid = customers.userid " +
                "WHERE status = 'pending';";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("Query executed - replace with trace");
        System.out.println("RESULTS\n");
        while (resultSet.next()) {
            System.out.print(" Account: " + resultSet.getLong("account_number"));
            System.out.print(" Balance : " + resultSet.getDouble("balance")); //wrong type, fixme
            System.out.print(" User: " + resultSet.getString("username"));
            System.out.print(" Name: " + resultSet.getString("name"));
            System.out.print(" User: " + resultSet.getString("name"));
            System.out.print(" Status: " + resultSet.getString("status"));
            System.out.println("\n");

        } //dont actually need the balance...

    }

    public void applyForAccount(Customer customer) throws SQLException, BusinessException {

        System.out.println("temp function");
    }

    public void viewAccountsByAccountNum(long accountNum) throws SQLException, BusinessException {   //used by employee and customer to view employees...
        System.out.println("temp function");
        Connection connection = PostgresConnection.getConnection();
//        String sql = "SELECT * from gormbank.accounts;";
        String sql = "select customers.userid, customers.username, customers.name, " +
                "accounts.account_number, accounts.balance, accounts.status " +
                "from gormbank.customers RIGHT join gormbank.accounts on accounts.userid = customers.userid " +
                "WHERE account_number = 1;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
//        preparedStatement.setLong(4, accountNum);    //issue!?
        System.out.println("Query executed - replace with trace");
        System.out.println("RESULTS\n");
        while (resultSet.next()) {
            System.out.print(" Account: " + resultSet.getLong("account_number"));
            System.out.print(" Balance: " + resultSet.getDouble("balance")); //wrong type, fixme
            System.out.print(" User: " + resultSet.getString("username"));
            System.out.print(" Name: " + resultSet.getString("name"));
            System.out.print(" User: " + resultSet.getString("name"));
            System.out.print(" Status: " + resultSet.getString("status"));
            System.out.println("\n");

        } //why is this not returning any results? with or without setting long in prepared statement
        //no errors, just no results

















    }
    public Customer findCustomerByLoginNoPW(String username, String pw) throws SQLException, BusinessException{
        System.out.println("temp function");
        return new Customer();
    }
    public void viewAccountsByUsername(String username) throws SQLException, BusinessException {   //used by employee and customer to view employees...
        Connection connection = PostgresConnection.getConnection();
//        String sql = "SELECT * from gormbank.accounts;";
        String sql = "select customers.userid, customers.username, customers.name, " +
                "accounts.account_number, accounts.balance, accounts.status " +
                "from gormbank.customers RIGHT join gormbank.accounts on accounts.userid = customers.userid " +
                "WHERE username = ? ;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);    //variables sent into sql/preparedStatement
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("Query executed - replace with trace");
        System.out.println("RESULTS\n");
        while (resultSet.next()) {
            System.out.print(" Account: " + resultSet.getLong("account_number"));
            System.out.print(" Balance : " + resultSet.getDouble("balance")); //wrong type, fixme
            System.out.print(" User: " + resultSet.getString("username"));
            System.out.print(" Name: " + resultSet.getString("name"));
            System.out.print(" User: " + resultSet.getString("name"));
            System.out.print(" Status: " + resultSet.getString("status"));
            System.out.println("\n");

        } //dont actually need the balance...
    }

    public void withdrawFunds(Customer customer, long accountNum, double amount) throws SQLException, BusinessException {
        System.out.println("temp function");
    }

    public void depositFunds(Customer customer, long accountNum, double amount) throws SQLException, BusinessException {
        System.out.println("temp function");
    }

    public void transferFunds(Customer customer, long accountNum, long accountNum2, double amount) throws SQLException, BusinessException {
        System.out.println("temp function");
    }
    public void viewMyAccounts(Customer customer)throws SQLException, BusinessException { //shows user their own accounts
        System.out.println("temp function");
        //
    }

//TRANSACTIONS

//        public void viewAllLogs () throws SQLException, BusinessException {}


}
