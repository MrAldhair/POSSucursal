/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;


public final class ListaEmpleadoController implements Initializable {

    @FXML private Label lblDescription;
    @FXML private Label lblTitle;
    @FXML private TableView<Employee> tableEmployees;
    @FXML private Button btnGenerateEmployee;
    @FXML private Button btnDelEmployee;
    @FXML private Button btnReturn;
    @FXML private TextField txtDate;
    @FXML private ImageView imageMain;
    @FXML private TableColumn<Employee, Integer> colIdEmploye;
    @FXML private TableColumn<Employee, String> colUser;
    @FXML private TableColumn<Employee, String> colPassword;
    @FXML private TableColumn<Employee, String> colTypeUser;
    @FXML private TableColumn<Employee, String> colDate;
    
    // Lista de empleados para llenar la tabla
    private ObservableList<Employee> empleyees;
    
    // Instancias la clase que hemos creado anteriormente
    private static ConnDBH2 SQL = new ConnDBH2();
    
    // Llamas al método que tiene la clase y te devuelve una conexión
    private static Connection conn;
    
    // Query que usarás para hacer lo que necesites
    private static String querySql = "";
    
    // Obtener el resuldo de una consulta
    private ResultSet rs;
    
    //Alerta
    Alert alert = new Alert(Alert.AlertType.NONE);
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        DataAndHour.dateAndHour(this.txtDate);
        
        LoadImage.loadImageMain(this.imageMain);
        
        this.empleyees = FXCollections.observableArrayList();
        
        this.colIdEmploye.setCellValueFactory(new PropertyValueFactory("id"));
        this.colUser.setCellValueFactory(new PropertyValueFactory("user"));
        this.colPassword.setCellValueFactory(new PropertyValueFactory("password"));
        this.colTypeUser.setCellValueFactory(new PropertyValueFactory("typeEmployee"));
        this.colDate.setCellValueFactory(new PropertyValueFactory("idBranch"));
 
        fillTable();
        
    }
    
    
    @FXML
    private void generateNewEmployee(ActionEvent event) {
        
        Object ev = event.getSource();
        
        if(ev.equals(this.btnGenerateEmployee)){
        
            try {
            
            App.setRoot("VistaAgregarEmpleado");
            
            } catch (IOException e) {

                System.out.println("Error: " + e.getMessage());

            }
            
        }
        
    }

    @FXML
    private void deleteEmployee(ActionEvent event) {
        
        Object ev = event.getSource();
        
        if(ev.equals(this.btnDelEmployee)){
        
            // Fila seleccionada
            Employee em = this.tableEmployees.getSelectionModel().getSelectedItem();



            if(em != null ) {

                conn = SQL.connectionDbH2();
                querySql = "DELETE FROM useremployee WHERE idEmployee = ?";

                try {

                    this.alert = new Alert(Alert.AlertType.CONFIRMATION);
                    this.alert.setTitle("Nuevo empleado");
                    this.alert.setContentText("¿Desea eliminar este usuario del sistema?");
                    this.alert.setHeaderText(null);

                    PreparedStatement preparedStatement = conn.prepareStatement(querySql);
                    preparedStatement.setString(1, em.getId().toString());

                    // Se agrega esto para obtener el resultado de la alerta de la confirmación
                    Optional<ButtonType> action = this.alert.showAndWait();

                    // confirmacion de la alerta
                    if (action.get() == ButtonType.OK) {

                        preparedStatement.execute();

                        this.empleyees.remove(em);

                        this.tableEmployees.refresh();

                        Alerts.alertInformation("Eliminar empleado", "Elemento eliminado con exito");

                    } else {

                        preparedStatement.cancel();
                        System.out.println("no execute");

                    }

                } catch (SQLException e) {

                    System.out.println(e.getMessage());

                }

            } else {

                Alerts.alertWarning("Eliminar empleado", "Debes seleccionar un elemento de la tabla apara poder eliminarlo");

            }
        
        } 

    }

    @FXML
    private void swichToAdministrador(ActionEvent event) {
        
        Object ev = event.getSource();
        
        if(ev.equals(this.btnReturn)){
        
            try {
            
            App.setRoot("VistaAdministrador");
            
            } catch (IOException e) {

                System.out.println("Error: " + e.getMessage());

            }
            
        }
    
    }
    
    public void fillTable(){
        
        conn = SQL.connectionDbH2();
        querySql = "SELECT * FROM useremployee";
        
        try {
  
            PreparedStatement pstm = conn.prepareStatement(querySql);
            rs = pstm.executeQuery(); 
            
            while(rs.next()){
            
                empleyees.add(new Employee(
                        rs.getInt("idEmployee"), 
                        rs.getString("user"), 
                        rs.getString("password"), 
                        rs.getString("typeEmployee"), 
                        rs.getInt("idBranch")));
            }
            
            tableEmployees.setItems(empleyees);
        

        } catch (SQLException e) {
            
            System.out.println(e.getMessage());
            
        }
    
    }

}