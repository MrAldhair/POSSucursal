package com.company.controller;

import ConnectionDB.ConnDBH2;
import Models.Employee;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;

public class PrincipalControllerTest {
    
    @Mock 
    Connection connection = null;
    @Mock
    Statement statement = null;
    @Mock
    Employee new_emp = new Employee();
    @Mock
    ResultSet resultSet = null;
    
    /**
     * Returns the user of the data base h2 equals Not null
     */
    @Test
    public void testGetUser() throws Exception {
       
        connection = ConnDBH2.connectionDbH2();

        String sql_query = "SELECT * FROM useremployee WHERE user='fulano' AND password='fula' AND typeEmployee='Empleado' AND branchName='Sucursal 2'";
        
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql_query);
       
        while(resultSet.next()){

            new_emp.setUser(resultSet.getString("user"));
            new_emp.setPassword(resultSet.getString("password"));
            new_emp.setTypeEmployee(resultSet.getString("typeemployee"));
            new_emp.setBranchName(resultSet.getString("branchname"));  
            

        }
        
        assertNotNull(new_emp.getUser());
        assertNotNull(new_emp.getPassword());
        assertNotNull(new_emp.getTypeEmployee());
        assertNotNull(new_emp.getBranchName());
   
    }
    
}
