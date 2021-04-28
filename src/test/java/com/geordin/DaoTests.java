package com.geordin;

import com.geordin.DAO.Imp.GormBankImp;
import com.geordin.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class DaoTests {

    private static GormBankImp gormDao;
    @BeforeAll
    public static void initGormDao(){
        gormDao = new GormBankImp();
    }
    @Test
    public void testCustomerLoginPW(){
        String pw = "pw123"; //existing data
        String user = "Tim"; //existing data
        Customer cust = new Customer();
        try {
            cust = gormDao.findCustomerByLogin(user, pw);
        }
        catch (SQLException | BusinessException e){

        }

        Assertions.assertEquals(pw, cust.getPassword());
    }
    @Test
    public void testCustomerLoginUser(){
        String pw = "pw123"; //existing data
        String user = "Tim"; //existing data
        Customer cust = new Customer();
        try {
            cust = gormDao.findCustomerByLogin(user, pw);
        }
        catch (SQLException | BusinessException e){
        }//what happens if exception?
        Assertions.assertEquals(user, cust.getUsername());
    }
    @Test
    public void testCustomerLoginException() throws SQLException, BusinessException {
        String pw = "pw12"; //existing data - wrong password
        String user = "Tim"; //existing data
        Customer cust = new Customer();
        Assertions.assertThrows(BusinessException.class, ()-> {gormDao.findCustomerByLogin(user, pw);});
       }
}
