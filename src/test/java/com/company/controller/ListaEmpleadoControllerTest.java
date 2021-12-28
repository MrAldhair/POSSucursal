package com.company.controller;

import ConnectionDB.ConnDBH2;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;

public class ListaEmpleadoControllerTest {

    /**
     * validate that the connection is not null
     */
    @Test
    public void validateSuccessfullConnectionDB(){

        System.out.println("*** Successfull connection to the data base");

        Connection connection = null;

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
     * validate that the connection is null
     */
    @Test
    public void validateUnsuccessfullConnectionDB(){

        System.out.println("*** Successfull connection to the data base");

        Connection connection = null;

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

        Connection connection = null;
        Statement statement = null;

        try {
            // 1. Obtener el objeto de conexión
            connection = ConnDBH2.connectionDbH2();
            // 2. Ejecute la instrucción SQL para obtener el resultado
            String sql_query = "DELETE FROM useremployee WHERE idEmployee = 9999";
            // 3. Obtenga la declaración de acuerdo con el objeto de conexión
            statement = connection.createStatement();
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
    public void consultEmployee() {
        System.out.println("*** Consult employees of H2 Data Base ***");

        Connection connection = null;
        Statement statement = null;
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