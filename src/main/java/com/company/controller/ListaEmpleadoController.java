package com.company.controller;

import Configurations.Alerts;
import Configurations.DataAndHour;
import Configurations.LoadImage;
import BusinessDB.Queries;
import Models.Employee;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
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

public class ListaEmpleadoController implements Initializable {

    @FXML private Label lblDescription;
    @FXML private Label lblTitle;
    @FXML private Label lblNameUser;
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
    @FXML private TableColumn<Employee, String> colBranchName;

    //Alerta para obtener el resultado de OK o Cancel
    Alert alert = new Alert(Alert.AlertType.NONE);
    
    // Objeto que se utliza e varios metodos para obtener datos de otro clases
    Employee name_employee = new Employee();
    Queries queries = new Queries();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        DataAndHour.dateAndHour(this.txtDate);
        LoadImage.loadImageMain(this.imageMain);
        this.colIdEmploye.setCellValueFactory(new PropertyValueFactory("id"));
        this.colUser.setCellValueFactory(new PropertyValueFactory("user"));
        this.colPassword.setCellValueFactory(new PropertyValueFactory("password"));
        this.colTypeUser.setCellValueFactory(new PropertyValueFactory("typeEmployee"));
        this.colBranchName.setCellValueFactory(new PropertyValueFactory("branchName"));
        //this.tableEmployees.setItems(queries.getAllUsers());
        // Llenar tabla con los datos de los empleados
        this.tableEmployees.setItems(queries.getAllUsers());
        // Usuario que inicia sesion
        this.name_employee.setUser(PrincipalController.em.getUser());
        this.lblNameUser.setText("Usuario: " + name_employee.getUser());        
    }
    /*
     * Metodo para cambiar de vista cuando se quiere crear un nuevo empleado para el sistema
    */
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
    /*
    *  Metodo para eliminar un empleado de la base de datos
    */
    @FXML
    private void deleteEmployee(ActionEvent event) throws SQLException {
        
        Object ev = event.getSource();
        if(ev.equals(this.btnDelEmployee)){
            
            // Fila seleccionada
            Employee em = this.tableEmployees.getSelectionModel().getSelectedItem();
            if(em != null ) {
                
                try {
                    this.alert = new Alert(Alert.AlertType.CONFIRMATION);
                    this.alert.setTitle("Nuevo empleado");
                    this.alert.setContentText("¿Desea eliminar este usuario del sistema?");
                    this.alert.setHeaderText(null);
                    Optional<ButtonType> action = this.alert.showAndWait();
                    
                    // confirmacion de la alerta
                    if (action.get() == ButtonType.OK) {
                        //gets id from object Employee and delete it
                        this.queries.deleteUser(em.getId().toString());
                        //clear all the table
                        this.tableEmployees.getItems().clear();
                        //fill table with a new query from DB
                        this.tableEmployees.setItems(queries.getAllUsers());
                        Alerts.alertInformation("Eliminar empleado", "Elemento eliminado con exito");
                    } else {
                        this.queries.cancel();
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
}