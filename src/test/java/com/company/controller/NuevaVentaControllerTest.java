package com.company.controller;

import BusinessDB.ConnDBH2;
import Models.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;

public class NuevaVentaControllerTest {
    
    @Mock 
    Connection connection = null;
    @Mock
    Statement statement = null;
    @Mock
    Employee new_emp = new Employee();
    @Mock
    ResultSet resultSet = null;
    
    /**
     * Returns the id of the connected user
     */
    @Test
    public void testUserId() throws Exception {
       
        connection = ConnDBH2.connectionDbH2();
        int idUser = 0;
        
        String sql_query = "SELECT idemployee FROM useremployee WHERE user='fulano'";
        
         statement = connection.createStatement();
            resultSet = statement.executeQuery(sql_query);
            // 4. Recorrer el conjunto de resultados
            while(resultSet.next()){

                idUser = resultSet.getInt("idemployee");

            }
        
        assertEquals(9, idUser);
   
    }
    
}
