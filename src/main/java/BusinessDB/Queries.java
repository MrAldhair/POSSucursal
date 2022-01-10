package BusinessDB;

import Models.Employee;
import com.company.controller.App;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Queries {
    ConnDBH2 connDBH2 = new ConnDBH2();
    private static Connection conn;
    private static final String USERS = "SELECT user FROM useremployee";
    private static final String IDS = "SELECT idemployee FROM useremployee WHERE user=?";
    private static final String INSERT_USER = "INSERT INTO useremployee(user, password, typeEmployee, branchName) VALUES(?,?,?,?)";
    private static final String USER_EXIST = "SELECT user FROM useremployee WHERE user=?";
    private static final String DELETE_USER = "DELETE FROM useremployee WHERE idEmployee = ?";
    private static final String SELECT_ALL = "SELECT * FROM useremployee ";
    private static final String VALIDATE_DATA = "SELECT * FROM useremployee WHERE user=? AND password=? AND typeEmployee=? AND branchName=?";
    
    private ResultSet rs;
    private PreparedStatement pstm;
    
    public List<String> getUserName(){
        conn = connDBH2.connectionDbH2();
        List<String> options = new ArrayList<>();
        try {
            pstm = conn.prepareCall(USERS);
            rs=pstm.executeQuery();
            while (rs.next()){
                options.add(rs.getString("user"));
            }
            pstm.close();
            rs.close();
            conn.close();
            return options;
        } catch(SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public ObservableList<Employee> getAllUsers(){
        conn = connDBH2.connectionDbH2();
        ObservableList<Employee> listEmployees = FXCollections.observableArrayList();
        
        try {
            pstm = conn.prepareCall(SELECT_ALL);
            rs=pstm.executeQuery();
            while (rs.next()){
                listEmployees.add(new Employee(
                        rs.getInt("idEmployee"),
                        rs.getString("user"),
                        rs.getString("password"), 
                        rs.getString("typeEmployee"), 
                        rs.getString("branchName")));
            }
            pstm.close();
            rs.close();
            conn.close();
            return listEmployees;
        } catch(SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Integer getUserId (String cbox) throws SQLException {
        conn = connDBH2.connectionDbH2();
        int idEmployee = -1;
        String name_user = cbox;
        pstm = conn.prepareStatement(IDS);
        pstm.setString(1, name_user);
        rs = pstm.executeQuery();
        if(rs.next()){
            idEmployee = rs.getInt("idemployee");
        }
        conn.close();
        return idEmployee;
    }
    
    public Integer getUserIdLogin (String user) throws SQLException {
        conn = connDBH2.connectionDbH2();
        int idEmployee = -1;
        String name_user = user;
        pstm = conn.prepareStatement(IDS);
        pstm.setString(1, name_user);
        rs = pstm.executeQuery();
        if(rs.next()){
            idEmployee = rs.getInt("idemployee");
        }
        conn.close();
        return idEmployee;
    }
    public void insertUser (String usuario, String pass, String typeEmployee, String branch) throws SQLException{
        conn = connDBH2.connectionDbH2();
        pstm = conn.prepareStatement(INSERT_USER);
        pstm.setString(1, usuario);
        pstm.setString(2, pass);
        pstm.setString(3, typeEmployee);
        pstm.setString(4, branch);
        pstm.execute();
        conn.close();
    }
    
    public boolean validateUser (String usuario, String pass, String typeEmployee, String branch) throws SQLException{
        conn = connDBH2.connectionDbH2();
        pstm = conn.prepareStatement(VALIDATE_DATA);
        pstm.setString(1, usuario);
        pstm.setString(2, pass);
        pstm.setString(3, typeEmployee);
        pstm.setString(4, branch);
        rs = pstm.executeQuery();
        if(rs.next()){
            return true;
        }
        conn.close();
        return false;
    }
    
    public void deleteUser(String id) throws SQLException{
        conn = connDBH2.connectionDbH2();
        pstm = conn.prepareStatement(DELETE_USER);
        pstm.setString(1,id);
        pstm.execute();
        conn.close();
    }
    
    public Boolean userExist(String userName) throws SQLException {
        conn = connDBH2.connectionDbH2();
        String name = "";
        // Comprobar si el usuario existe en la base de datos
        pstm = conn.prepareStatement(USER_EXIST);
        pstm.setString(1, userName);
        rs = pstm.executeQuery();
        if(rs.next()) {
            name = rs.getString("user");
            conn.close();
            return true;
        }else{
            conn.close();
            return false;
        }

       // return name;
    }

    public void cancel() throws SQLException{
        pstm.cancel();
        conn.close();
    }

}