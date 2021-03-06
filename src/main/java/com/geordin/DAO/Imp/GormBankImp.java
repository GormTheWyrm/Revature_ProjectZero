package com.geordin.DAO.Imp;

import com.geordin.BusinessException;
import com.geordin.DAO.GormBankDao;
import com.geordin.bdutil.PostgresConnection;
import com.geordin.model.Account;
import com.geordin.model.Customer;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Vector;

public class GormBankImp implements GormBankDao {
    private static Logger log=Logger.getLogger(GormBankImp.class);
//CUSTOMERS
    public Customer createNewCustomer(String username, String name, String password) throws SQLException, BusinessException {
        //creates new customer if password, name and username exist in database
        Customer customer = new Customer();
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
        log.trace("Inserted "+ c + " records");
        if (c == 1) {
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) { //this is needed to get a response
                customer.setUsername(resultSet.getString(2)); //index 1 should be userid
                customer.setName(resultSet.getString(3));
                customer.setPassword(resultSet.getString(4));
                log.trace(resultSet.getString(2)+ "added to user database");
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
        log.trace("DAO-findCustomerByLogin");
        //Step 5 - Process Results  THIS WILL BE IMPORTANT~
        //        while (resultSet.next()){
        if (resultSet.next()) {
            customer.setUsername(resultSet.getString("username"));
            customer.setName(resultSet.getString("name"));
            customer.setPassword(resultSet.getString("password"));
            log.trace("DAO loginOldCustomer: " + customer.getUsername());
        }
        else {
            throw new BusinessException("No User Found");
        } //if no results, throw exception
        return customer;
    }
    //ACCOUNTS
    public Vector<Account> findAccountsByUsername(String username) throws SQLException, BusinessException {   //used by employee and customer to view employees...
        //old function...
        Connection connection = PostgresConnection.getConnection();
        String sql = "select customers.userid, customers.username, customers.name, " +
                "accounts.account_number, accounts.balance, accounts.status " +
                "from gormbank.customers RIGHT join gormbank.accounts on accounts.userid = customers.userid " +
                "WHERE username = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);    //variables sent into sql/preparedStatement
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("Query executed - replace with trace");
        Vector<Account> accounts = new Vector<>();

        while (resultSet.next()) {
            Account account = new Account();
            account.setAccountNumber(resultSet.getLong("account_number"));
            account.setBalance(resultSet.getDouble("balance"));
            account.setUsername(resultSet.getString("username"));
            account.setStatus(resultSet.getString("status"));

            System.out.println("DAO RESULTS");
            System.out.print(" Account: " + resultSet.getLong("account_number"));
            System.out.print(" Balance : " + resultSet.getDouble("balance")); //wrong type, fixme
            System.out.print(" User: " + resultSet.getString("username"));
            System.out.print(" Name: " + resultSet.getString("name"));
            System.out.print(" User: " + resultSet.getString("name"));
            System.out.print(" Status: " + resultSet.getString("status"));
            System.out.println("\n");
            accounts.add(account);
        }
        return accounts;
    }

    public Vector<Account> viewPendingApplications() throws SQLException, BusinessException {
        Connection connection = PostgresConnection.getConnection();
        String sql = "select customers.userid, customers.username, customers.name, " +
                "accounts.account_number, accounts.balance, accounts.status " +
                "from gormbank.customers RIGHT join gormbank.accounts on accounts.userid = customers.userid " +
                "WHERE status = 'pending';";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
log.trace("DAO viewPendingApplications");
        Vector<Account> accounts = new Vector<>();
        while (resultSet.next()) {
            Account account = new Account();
            account.setAccountNumber(resultSet.getLong("account_number"));
            account.setBalance(resultSet.getDouble("balance"));
            account.setUsername(resultSet.getString("username"));
            account.setStatus(resultSet.getString("status"));
            accounts.add(account);
        }
        return accounts;
    }

    public void applyForAccount(Customer customer) throws SQLException, BusinessException { //fixme current
        Connection connection = PostgresConnection.getConnection();
        String sql="INSERT INTO gormbank.accounts (balance, status, userid) VALUES(0, 'pending', " +
                "(select userid from gormbank.customers c where username = ?));";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1, customer.getUsername());
        int executeUpdate=preparedStatement.executeUpdate();
    }

    public void viewAccountsByAccountNum(long accountNum) throws SQLException, BusinessException {   //used by employee and customer to view employees...
        System.out.println("temp function");
        Connection connection = PostgresConnection.getConnection();
        String sql = "select customers.userid, customers.username, customers.name, " +
                "accounts.account_number, accounts.balance, accounts.status " +
                "from gormbank.customers RIGHT join gormbank.accounts on accounts.userid = customers.userid " +
                "WHERE account_number = 1;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
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

        } //no errors, just no results, fixme
    }
    public Customer findCustomerByLoginNoPW(String username, String pw) throws SQLException, BusinessException{
        System.out.println("temp function");
        return new Customer();
    }
    public void viewAccountsByUsername(String username) throws SQLException, BusinessException {   //used by employee and customer to view employees...
        //fixme
        Connection connection = PostgresConnection.getConnection();
        String sql = "select customers.userid, customers.username, customers.name, " +
                "accounts.account_number, accounts.balance, accounts.status " +
                "from gormbank.customers RIGHT join gormbank.accounts on accounts.userid = customers.userid " +
                "WHERE username = ?;";
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
            System.out.println("\n"); //not log, but put into list!
        }
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
public void approveAccount(Long accountNum) throws SQLException, BusinessException{
        //fixme next step - might work,
    Connection connection = PostgresConnection.getConnection();
    String sql = "update gormbank.accounts set balance = 25, status ='active' " +
            "where account_number = ?;";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setLong(1, accountNum);    //variables sent into sql/preparedStatement
    int executeUpdate=preparedStatement.executeUpdate(); //need to actually run the sql...
    //I think I'm missing a step where I test results
    log.trace("updated " + executeUpdate);
}//still need to test to make sure this does not reset balance if applied to wrong account
//TRANSACTIONS

//        public void viewAllLogs () throws SQLException, BusinessException {}


}
