package com.geordin.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BusinessTests {

private static BusinessLayer businessLayer;
@BeforeAll
public static void initBusinessLayer(){
    businessLayer = new BusinessLayer();
    }
    @Test
    public void testSignInEmployee(){
        String pw = "password";
        Assertions.assertTrue(businessLayer.signInEmployee(pw));
    }



}
