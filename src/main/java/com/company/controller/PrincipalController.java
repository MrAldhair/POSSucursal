package com.company.controller;


import Configurations.Alerts;
import Configurations.DataAndHour;
import Configurations.LoadImage;
import ConnectionDB.ConnDBH2;
import Models.Employee;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class PrincipalController implements Initializable {

    private Boolean administrador = false;
    private Alert alert = new Alert(AlertType.WARNING);
    private Alert alertE = new Alert(AlertType.ERROR);
    private Alert alertC = new Alert(AlertType.CONFIRMATION);
    
    // Instancias la clase que hemos creado anteriormente
    private static ConnDBH2 SQL = new ConnDBH2();
    
    // Llamas al método que tiene la clase y te devuelve una conexión
    private static Connection conn;
    
    // Query que usarás para hacer lo que necesites
    private static String sSQL = "";
    
    private ResultSet rs;

    // Controlls
    @FXML private Label lblTitle;
    @FXML private Label lblDescription;
    @FXML private ComboBox<String> rbSelectOptionMain;
    @FXML private TextField txtDate;
    @FXML private ImageView imageMain;
    @FXML private Label lblSelection;
    @FXML private TextField txtUserName;
    @FXML private TextField txtPassword;
    @FXML private Button btnLogin;

    ObservableList<String> optionEmployee = FXCollections.observableArrayList(
            "Administrador",
            "Empleado");
    
    public static String bufferUserName;
    
    public static Employee em = new Employee();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        rbSelectOptionMain.setItems(optionEmployee);
        
        DataAndHour.dateAndHour(this.txtDate);
        
        LoadImage.loadImageMain(this.imageMain);
        
    }

    @FXML
    private void switchToEmployee(ActionEvent event) {
        
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        String typeEmployee = rbSelectOptionMain.getSelectionModel().getSelectedItem();
        em.setUser(username);

        if (username.equals("")) {
            
            alert.setHeaderText("Campo USUARIO vacio");
            alert.setContentText("Escribe un usuario válido");
            alert.showAndWait();
            
        } else if (password.equals("")) {
            
            alert.setHeaderText("Campo CONTRASEÑA vacio");
            alert.setContentText("Escribe una contraseña válida");
            alert.showAndWait();
            
        } else {
            
            conn = SQL.connectionDbH2();
            sSQL = "SELECT * FROM useremployee WHERE user=? AND password=? AND typeEmployee=?";
            
            try {
                
                // PreparedStatement
                PreparedStatement pstm = conn.prepareStatement(sSQL);
                pstm.setString(1, username);
                pstm.setString(2, password);
                pstm.setString(3, typeEmployee);
                rs = pstm.executeQuery();
                
                if (rs.next()) {

                    if (rbSelectOptionMain.getSelectionModel().getSelectedItem() == "Administrador") {
                        
                        administrador = true;
                        
                        try {
                            
                            App.setRoot("VistaAdministrador");
                            Alerts.alertInformation("Inicio de sesion", "Bienvenido: " + username);
                            
                        } catch (IOException e) {
                            
                            System.out.println("Error: " + e.getMessage());
                            
                        }
                        
                    } else if (rbSelectOptionMain.getSelectionModel().getSelectedItem() == "Empleado") {
                        
                        administrador = false;
                        
                        try {
                            
                            App.setRoot("VistaEmpleado");
                            Alerts.alertInformation("Inicio de sesion", "Bienvenido: " + username);
                            
                        } catch (IOException e) {
                            
                            System.out.println("Error: " + e.getMessage());
                            
                        }
                        
                    } else {

                        alert.setHeaderText("Selección invalida");
                        alert.setContentText("Selecciona un puesto válido");
                        alert.showAndWait();
                        
                    }

                } else {
                    
                    alertE.setHeaderText("Error en Login");
                    alertE.setContentText("Ingrese usuario y contraseña válidos");
                    alertE.showAndWait();
                    
                }
                
            } catch (SQLException ex) {
                
                System.out.println(ex.toString());
                
            }
            
        }
        
    }
    
}
