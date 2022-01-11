package BusinessDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnDBH2 {

    Connection connection = null;
    // Metodo de conexion
    public Connection connectionDbH2(){
        try {
            Connection conn = null;
            // Libreria de MySQL
            String driver = "org.h2.Driver";
            //Ruta de la bd
            String url = "jdbc:h2:tcp://localhost/~/test";
            // Nombre de usuario
            String userName ="sa";
            //password de usuario en db
            String password ="";
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
            connection = conn;
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return null;
        }

    }
/*
    public Connection closeConnection() throws SQLException{
        //Connection conn = null;
        conn = this.connectionDbH2();
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnDBH2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

*/
    //clase exclusivamente para test sql.


}

