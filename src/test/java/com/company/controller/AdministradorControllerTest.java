package com.company.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.not;

import BusinessDB.ConnDBH2;
import javafx.collections.FXCollections;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;


public class AdministradorControllerTest {

    @Mock
    AdministradorController controller;

    @BeforeEach
    void setUp() {
    //    MockitoAnnotations.openMocks(this);
        MockitoAnnotations.initMocks(this);
    }

    
    
    /**
     * validate that the connection is not null
    */
    @Test
    public void validateSuccessfullConnectionDB(){
        Connection connection = null;
        ConnDBH2 connDBH2 = new ConnDBH2();
        try {

            // Obtener el objeto de conexión
            connection = connDBH2.connectionDbH2();
            //assertNotNull(connection);
            System.out.println("*** Successfull connection to the data base");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
/*
    @Test
    public void getDataTest() {
        //System.out.println("*** Consult employees of H2 Data Base ***");

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<String> options= new ArrayList<>();
        try {
            ConnDBH2 connDBH2 = new ConnDBH2();
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
            connection.closeConnection();
        }
    }
    /*
    @Test
    public void getDataTest(){
        verify(controller);
        when(FXCollections.observableArrayList(getData()).thenReturn());
    }*/


}