package com.company.controller;

import BusinessAPI.ListBranchOfficeApi;
import Configurations.Alerts;
import Configurations.DataAndHour;
import Configurations.LoadImage;
import ConnectionDB.ConnDBH2;
import Models.BranchOffice;
import Models.Employee;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;

public class PrincipalController implements Initializable {

    // Controlls
    @FXML private Label lblTitle;
    @FXML private Label lblDescription;   
    @FXML private Label lblSelection;
    @FXML private ComboBox<String> rbSelectOptionMain;
    @FXML private ComboBox<String> rbSelectBranchOffice;
    @FXML private TextField txtDate;
    @FXML private ImageView imageMain;
    @FXML private TextField txtUserName;
    @FXML private TextField txtPassword;
    @FXML private Button btnLogin;
    
    Alert alert = new Alert(Alert.AlertType.NONE);    
    // Instancias la clase que hemos creado anteriormente
    private static final ConnDBH2 SQL = new ConnDBH2();
    // Llamas al método que tiene la clase y te devuelve una conexión
    private static Connection conn;
    // Query que usarás para hacer lo que necesites
    private static String sSQL = "";
    // Resultado de un query
    private ResultSet rs;
    
    private final ObservableList<String> optionEmployee = FXCollections.observableArrayList( "Administrador", "Empleado");
    private ObservableList<String> optionsBranchOffice = FXCollections.observableArrayList();
    public static Employee em = new Employee();
    public static BranchOffice branch_office = new BranchOffice();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        try {
            
            loadDataBranchOffice();
            rbSelectOptionMain.setItems(optionEmployee);
            rbSelectBranchOffice.setItems(optionsBranchOffice);
            DataAndHour.dateAndHour(this.txtDate);
            LoadImage.loadImageMain(this.imageMain);
            
        } catch (IOException ex) {
            
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }

    @FXML
    private void switchToEmployee(ActionEvent event) {
        
        Object ev = event.getSource();
        
        if (ev.equals(this.btnLogin)) {
            
            String username = txtUserName.getText();
            String password = txtPassword.getText();
            String typeEmployee = rbSelectOptionMain.getSelectionModel().getSelectedItem();
            em.setUser(username);
            branch_office.setName(rbSelectBranchOffice.getSelectionModel().getSelectedItem());

            if (username.equals("")) {
                
                Alerts.alertWarning("Campo USUARIO vacio", "Escribe un usuario válido");
                
            } else if (password.equals("")) {
                
                Alerts.alertWarning("Campo CONTRASEÑA vacio", "Escribe una contraseña válida");
                
            } else if (rbSelectBranchOffice.selectionModelProperty().getValue().isEmpty()) {
                
                Alerts.alertWarning("Sucursal sin seleccionar", "Selecciona una sucursal del menú");
                
            } else {
                
                conn = SQL.connectionDbH2();
                sSQL = "SELECT * FROM useremployee WHERE user=? AND password=? AND typeEmployee=? AND branchName=?";
                
                try {
                    
                    // PreparedStatement
                    PreparedStatement pstm = conn.prepareStatement(sSQL);
                    pstm.setString(1, username);
                    pstm.setString(2, password);
                    pstm.setString(3, typeEmployee);
                    pstm.setString(4, rbSelectBranchOffice.getSelectionModel().getSelectedItem());
                    rs = pstm.executeQuery();

                    if (rs.next()) {
                        
                        switch (rbSelectOptionMain.getSelectionModel().getSelectedItem()) {
                            
                            case "Administrador":
                                try {
                                    
                                    App.setRoot("VistaAdministrador");
                                    Alerts.alertInformation("Inicio de sesion", "Bienvenido: " + username);
                                    conn.close();
                                    
                                } catch (IOException e) {
                                    
                                    System.out.println("Error: " + e.getMessage());
                                }   break;
                                
                                
                            case "Empleado":
                                
                                try {
                                    
                                    App.setRoot("VistaEmpleado");
                                    Alerts.alertInformation("Inicio de sesion", "Bienvenido: " + username);
                                    
                                } catch (IOException e) {
                                    
                                    System.out.println("Error: " + e.getMessage());
                                }   break;
                                
                        }
                        
                    } else {
                        
                        Alerts.alertWarning("Selección invalida", "Seleccione una opción válida");
                        
                    }
                    
                } catch (SQLException ex) {
                    
                    System.out.println(ex.toString());
                    
                }
                
            }
            
        }
        
    }
    
    private void loadDataBranchOffice() throws IOException{
        
        ListBranchOfficeApi branch = new ListBranchOfficeApi();
        JSONArray dataArray  = new JSONArray(branch.consultIdBranchOffice().toString());
        
        for(int i = 0 ; i < dataArray.length(); i++) {
            
            JSONObject row = dataArray.getJSONObject(i); 
            optionsBranchOffice.add(row.getString("name"));
            
        }
        
    }
    
}