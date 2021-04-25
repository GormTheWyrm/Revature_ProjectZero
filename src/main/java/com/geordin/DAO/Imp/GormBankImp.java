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
            }
        }else {
            throw new BusinessException("Failure in registration... Please retry.....");
        }
        return customer; //this returns a customer even if it doesnt work...?
    }
    public void applyForAccount(Customer customer) throws SQLException, BusinessException //create account,set status to pending
    {
        System.out.println("temp function");
    }




    public Customer findCustomerByLogin(String username, String pw) throws SQLException, BusinessException{
        System.out.println("temp function");
//        Customer tempCustomer = new Customer();
//        return tempCustomer;
//~~~~~~~~~~~~~~~~~~~~~~~~
        Customer customer = new Customer();         //creates customer object that will get returned if it goes well
        //database connection
        Connection connection= PostgresConnection.getConnection();
        String sql="SELECT username, \"name\", \"password\" from gormbank.customers WHERE username = ? AND \"password\" = ?;";
        PreparedStatement preparedStatement=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,username);    //variables sent into DB
        preparedStatement.setString(2,pw);
        System.out.println("mark 1");
        int c=preparedStatement.executeUpdate();
        if(c==1){
            ResultSet resultSet=preparedStatement.getGeneratedKeys();
            if(resultSet.next()){ //this is needed to get a response
                customer.setUsername(resultSet.getString(1));
                customer.setName(resultSet.getString(2));
                customer.setPassword(resultSet.getString(3));
                System.out.println("mark2"); //this was not reached
                //need to set accounts fixme... wait, no I dont?
            }
        }else {
            throw new BusinessException("No User Found, Please Check Your Username and Password");
        }
        return customer;







    }
}   //https://www.softwaretestinghelp.com/jdbc-connection-steps/#:~:text=From%20Java%207%20onwards%2C%20we%20can%20close%20the,try%20block%2C%20it%20will%20automatically%20close%20the%20connection.

