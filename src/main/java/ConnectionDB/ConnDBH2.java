package ConnectionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnDBH2 {
    
    // Libreria de MySQL
    public String driver = "org.h2.Driver";
    //Ruta de la bd
    public String url = "jdbc:h2:tcp://localhost/~/test";
    // Nombre de usuario
    public String userName = "sa";
    // Contraseña
    public String password = "";
    
    // Metodo de conexion
    public Connection connectionDbH2(){
        
        Connection conn = null;
        
        try {
            
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
            
        } catch (ClassNotFoundException | SQLException e) {
            
            System.out.println(e.toString());
            e.printStackTrace();
            
        }
        
        return conn;
    
    }
    
}
