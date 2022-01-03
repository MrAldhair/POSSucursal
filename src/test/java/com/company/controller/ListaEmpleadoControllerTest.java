package com.company.controller;

import ConnectionDB.ConnDBH2;
import org.junit.Test;
import java.sql.*;
import static org.junit.Assert.*;
import org.mockito.Mock;

public class ListaEmpleadoControllerTest {
    
    @Mock 
    Connection connection = null;
    @Mock
    Statement statement = null;

    /**
     * validate that the connection is not null
     */
    @Test
    public void validateSuccessfullConnectionDB(){

        System.out.println("*** Successfull connection to the data base");

        try {

            // Obtener el objeto de conexión
            connection = ConnDBH2.connectionDbH2();
            assertNotNull(connection);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        } finally {

            ConnDBH2.closeConnection();

        }

    }

    /**
     * Validate that the connection is null
    */
    @Test
    public void validateUnsuccessfullConnectionDB(){

        System.out.println("*** Successfull connection to the data base");

        try {

            assertNull(connection);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        } finally {

            ConnDBH2.closeConnection();

        }

    }

    /**
     * Test of deleteEmployee query sql, of class ListaEmpleadoController.
     */
    @Test
    public void deleteEmployeeFromDataBaseH2() {

        System.out.println("*** Delete employees of H2 Data Base ***");
      
        try {
            
            // 1. Obtener el objeto de conexión
            connection = ConnDBH2.connectionDbH2();
            // 2. Ejecute la instrucción SQL para obtener el resultado
            String sql_query = "DELETE FROM useremployee WHERE idEmployee = 9999";
            // 3. Se obtyiene la declaración de acuerdo con el objeto de conexión
            statement = connection.createStatement();
            // 4. Ejecutar el query
            statement.execute(sql_query);

            assertNotNull(connection);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        } finally {

            ConnDBH2.closeConnection();

        }

    }

    /**
     * Test of consultEmployee query sql, of class ListaEmpleadoController.
     */
    @Test
    public void consultEmployeeFromDataBaseH2() {
        
        System.out.println("*** Consult employees of H2 Data Base ***");
        ResultSet resultSet = null;

        try {
            // 1. Obtener el objeto de conexión
            connection = ConnDBH2.connectionDbH2();
            // 3. Ejecute la instrucción SQL para obtener el resultado
            String sql_query = "SELECT * FROM USEREMPLOYEE ";
            // 2. Obtenga la declaración de acuerdo con el objeto de conexión
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql_query);
            // 4. Recorrer el conjunto de resultados
            while(resultSet.next()){

                String name = resultSet.getString("user");

                int age = resultSet.getInt("idemployee");

                System.out.println("User= "+name+",Id employee= "+age);
            }
            
        } catch (Exception e) {

            System.out.println(e.getMessage());

        } finally {

            ConnDBH2.closeConnection();

        }

    }

}