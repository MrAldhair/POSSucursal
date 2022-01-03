package ConnectionDB;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ConnDBH2Test {

    @Test
    public void connectionDbH2() {

        System.out.println("*** Successful connection to the data base");

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
    public void closeConnection() throws SQLException {

        System.out.println("*** Unsuccessful close connection to the data base");

        Connection connection = null;

        try {

            System.out.println("No se ha creado ningun punto de conexion");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        } finally {

            ConnDBH2.closeConnection();
            
        }

        assertNull(connection);

    }
}