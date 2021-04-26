package com.geordin.DAO.Imp;

import com.geordin.BusinessException;
import com.geordin.DAO.GormBankDao;
import com.geordin.bdutil.PostgresConnection;
import com.geordin.model.Customer;

import java.sql.*;

// resutset with prepared statement
public class GormBankImp implements GormBankDao {

    public Customer createNewCustomer( String username, String name, String password) throws SQLException, BusinessException{
        //this method is used to create a new customer in the database - when applying to be a customer
        //checks if the username (or user id) exists,
        //and only create the customer if the user does not exist
        Customer customer = new Customer();         //creates customer object that will get returned if it goes well
        //database connection
        Connection connection= PostgresConnection.getConnection();
        String sql="INSERT INTO gormbank.customers\n" +
                "(username, \"name\", \"password\")\n" +
                "VALUES(?, ?, ?);\n";
        PreparedStatement preparedStatement=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,username);    //variables sent into DB
        preparedStatement.setString(2,name);
        preparedStatement.setString(3,password);
        int c=preparedStatement.executeUpdate();
        //fixme - should this be executeQuery instead?
        if(c==1){
            ResultSet resultSet=preparedStatement.getGeneratedKeys();
            if(resultSet.next()){ //this is needed to get a response
            customer.setUsername(resultSet.getString(2)); //index 1 should be userid
            System.out.println("sysout username: "+ resultSet.getString(2));
            customer.setName(resultSet.getString(3));
            System.out.println("sysout name: "+ resultSet.getString(3));
            customer.setPassword(resultSet.getString(4));
            System.out.println("sysout PW: "+ resultSet.getString(4));
            //need to set accounts//fixme
                //check if these values were really set...

            }
        }else {
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
        System.out.println("Query executed");

        //Step 5 - Process Results  THIS WILL BE IMPORTANT~
        //        while (resultSet.next()){
        if (resultSet.next()) {
            System.out.print(" Name : " + resultSet.getString("username"));
            System.out.print(" Name : " + resultSet.getString("name"));
            System.out.print(" City : " + resultSet.getString("password"));
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
    public void applyForAccount (Customer customer) throws SQLException, BusinessException {

            System.out.println("temp function");
        }

//TRANSACTIONS

//        public void viewAllLogs () throws SQLException, BusinessException {
//            Customer customer = new Customer();
//
//            //step 2 connection
//            Connection connection = PostgresConnection.getConnection();
//            //Step 3- Create Statement
//            String sql = "SELECT * from gormbank.transactions ;";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql); //2nd par makes keys returnable...
//            //Step 4 - Execute Query
//            ResultSet resultSet = preparedStatement.executeQuery();
//            System.out.println("Query executed");
//
//            //Step 5 - Process Results  THIS WILL BE IMPORTANT~
//            //        while (resultSet.next()){
//            if (resultSet.next()) {
//                System.out.print(" Name : " + resultSet.getString("username"));
//                System.out.print(" Name : " + resultSet.getString("name"));
//                System.out.print(" City : " + resultSet.getString("password"));
//                //put variables in customer and return it!
//                //set name and password - but only if returned from DB
//                customer.setUsername(resultSet.getString("username"));
//                customer.setName(resultSet.getString("name"));
//                customer.setPassword(resultSet.getString("password"));
//            } //needs testing
//            else {
//                throw new BusinessException("No User Found, Please Check Your Username and Password");
//            } //if no results, throw exception
//            return customer;
//
//        }
    }
//~~~~~~~~~~~~~~~~~~~~~~~~
//        Customer customer = new Customer();         //creates customer object that will get returned if it goes well
//        //database connection
//        Connection connection = PostgresConnection.getConnection();
//        String sql = "SELECT username, \"name\", \"password\" from gormbank.customers WHERE username = ? AND \"password\" = ?;";
//        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//        preparedStatement.setString(1, username);    //variables sent into DB
//        preparedStatement.setString(2, pw);
//        System.out.println("mark 1");
//
//
//        boolean hasResult = preparedStatement.execute();
//        //exectueeUpdate only for DML, returns row count of rows changed
//        //executeQuery, returns ResultSet
//        //The execute method returns a boolean to indicate the form of the first result.
//        // ...You must call either the method getResultSet or getUpdateCount to retrieve the result;
//        // ...you must call getMoreResults to move to any subsequent result(s).
//
//        System.out.println("mark2"); //this was not reached
//        if (hasResult == true) {
//
//            System.out.println("mark3"); //this was not reached
//            ResultSet resultSet = preparedStatement.getGeneratedKeys();
//            if (resultSet.next()) { //this is needed to get a response
//                customer.setUsername(resultSet.getString(1));
//                customer.setName(resultSet.getString(2));
//                customer.setPassword(resultSet.getString(3));
//                System.out.println("mark2"); //this was not reached
//                //need to set accounts fixme... wait, no I dont?
//            }
//        }


//        public void viewAllLogs() throws SQLException, BusinessException{
//            Connection connection= PostgresConnection.getConnection();
//            Statement statement=connection.createStatement(); //this looks important...
//            String sql="select * from gormbank.transactions;";
//            //Step 4 - Execute Query
//            ResultSet resultSet=statement.executeQuery(sql);
//            System.out.println("Query executed");
//
//            //Step 5 - Process Results
//            while (resultSet.next()){
//                System.out.print("Id : "+resultSet.getInt("id"));
//                System.out.print(" Name : "+resultSet.getString("name"));
//                System.out.print(" City : "+resultSet.getString("city"));
//                System.out.print(" Gender : "+resultSet.getString("gender"));
//                System.out.print(" Age : "+resultSet.getInt("age"));
//                System.out.println(" Contact : "+resultSet.getLong("contact"));
//            }
    //fixme


//https://www.softwaretestinghelp.com/jdbc-connection-steps/#:~:text=From%20Java%207%20onwards%2C%20we%20can%20close%20the,try%20block%2C%20it%20will%20automatically%20close%20the%20connection.

