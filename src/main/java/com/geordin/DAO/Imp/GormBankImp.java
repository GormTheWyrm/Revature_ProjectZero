package com.geordin.DAO.Imp;

import com.geordin.BusinessException;
import com.geordin.DAO.GormBankDao;
import com.geordin.bdutil.PostgresConnection;
import com.geordin.model.Customer;

import java.sql.*;

public class GormBankImp implements GormBankDao {

    //example method from employee example
//    @Override
//    public Employee createEmployee(Employee employee) throws SQLException, BusinessException {
//
//        Connection connection= PostgresConnection.getConnection();
//        String sql="INSERT INTO employee_schema.employee\n" +
//                "(\"name\", age, contact, city, gender, deptid)\n" +
//                "VALUES(?, ?, ?, ?, ?, ?);\n";
//        PreparedStatement preparedStatement=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//        preparedStatement.setString(1,employee.getName());
//        preparedStatement.setInt(2,employee.getAge());
//        preparedStatement.setLong(3,employee.getContact());
//        preparedStatement.setString(4,employee.getCity());
//        preparedStatement.setString(5,employee.getGender());
//        preparedStatement.setInt(6,employee.getDepartment().getDeptid());
//
//        int c=preparedStatement.executeUpdate();
//        if(c==1){
//            ResultSet resultSet=preparedStatement.getGeneratedKeys();
//            if(resultSet.next()){
//                employee.setId(resultSet.getInt(1));
//            }
//        }else {
//            throw new BusinessException("Failure in registration... Please retry.....");
//        }
//        return employee;
//    }


    public Customer findCustomerByLogin(String username, String pw) throws SQLException, BusinessException{
        System.out.println("temp function");
        Customer tempCustomer = new Customer(234L, "Tommy", "1234", "Tom Ado");
        return tempCustomer;
    /*
    public Customer findCustomerByLogin(String username, String pw) throws SQLException, BusinessException{
        System.out.println("temporary login function needs to return real customer");
//        return new Customer(); //fixme!
        Customer customer = new Customer();
        Connection connection= PostgresConnection.getConnection();
        String sql="select userid, username, \"name\", \"password\" from gormbank.customers where username = ?, \"password\" = ?";

        PreparedStatement preparedStatement=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,pw;

        //...


        int c=preparedStatement.executeUpdate(); //shouldnt need this...
        if(c==1){
            ResultSet resultSet=preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                employee.setId(resultSet.getInt(1));
            }
        }else {
            throw new BusinessException("Failure in registration... Please retry.....");
        }
        return employee;

*/








    }
}
