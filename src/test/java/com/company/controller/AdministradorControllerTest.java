/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.controller;

import BusinessDB.ConnDBH2;
import java.sql.Connection;

import static org.hamcrest.Matchers.not;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;





public class AdministradorControllerTest {
    /*
    @Mock
    AdministradorController controller;

@BeforeEach
void setUp() {
//    MockitoAnnotations.openMocks(this);
    MockitoAnnotations.initMocks(this);
}

    
    
    /**
     * validate that the connection is not null
    
    @Test
    public void validateSuccessfullConnectionDB(){
        Connection connection = null;
        try {
            // Obtener el objeto de conexión
            connection = ConnDBH2.connectionDbH2();
            //assertNotNull(connection);
            System.out.println("*** Successfull connection to the data base");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            ConnDBH2.closeConnection();
            System.out.println("Closed connection to the data base");
        }
    }

    @Test
    public void getDataTest() {
        //System.out.println("*** Consult employees of H2 Data Base ***");

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<String> options= new ArrayList<>();
        try {
            // 1. Obtener el objeto de conexión
            connection = ConnDBH2.connectionDbH2();
            // 3. Ejecute la instrucción SQL para obtener el resultado
            String sql_query = "SELECT user FROM USEREMPLOYEE ";
            // 2. Obtenga la declaración de acuerdo con el objeto de conexión
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql_query);
            // 4. Recorrer el conjunto de resultados
            while(resultSet.next()){
                //agrega resultados a lista
                options.add(resultSet.getString("user"));
            }
            statement.close();
            resultSet.close();
            
            assertThat(options, not(IsEmptyCollection.empty()));
//            assertThat(new ArrayList<>(), IsEmptyCollection.empty());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnDBH2.closeConnection();
        }
    }
    
    @Test
    public void getDataTest(){
        verify(controller);
        //when(FXCollections.observableArrayList(getData()).thenReturn());
    }
*/

}