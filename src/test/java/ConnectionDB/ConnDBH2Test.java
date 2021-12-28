package ConnectionDB;

import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ConnDBH2Test {

    @Test
    public void connectionDbH2() {

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

    @Test
    public void closeConnection() {

        System.out.println("*** Successfull close connection to the data base");

        Connection connection = null;

        try {

            // Obtener el objeto de conexión
            connection = ConnDBH2.connectionDbH2();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        } finally {

            ConnDBH2.closeConnection();
            assertNotNull(connection);

        }



    }
}