package com.company.controller;

import BusinessAPI.ListBranchOfficeApi;
import Configurations.Alerts;
import Configurations.DateAndHour;
import Configurations.LoadImage;
import BusinessDB.Queries;
import Models.BranchOffice;
import Models.Employee;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    Queries queries = new Queries();

    private final ObservableList<String> optionEmployee = FXCollections.observableArrayList( "Administrador", "Empleado");
    private final ObservableList<String> optionsBranchOffice = FXCollections.observableArrayList();
    public static Employee em = new Employee();
    public static BranchOffice branch_office = new BranchOffice();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        try {
            loadDataBranchOffice();
            rbSelectOptionMain.setItems(optionEmployee);
            rbSelectBranchOffice.setItems(optionsBranchOffice);
            DateAndHour.dateAndHour(this.txtDate);
            LoadImage.loadImageMain(this.imageMain);   
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToEmployee(ActionEvent event) throws SQLException {
        
        Object ev = event.getSource();
        
        if (ev.equals(this.btnLogin)) {
            
            String username = txtUserName.getText();
            String password = txtPassword.getText();
            String typeEmployee = rbSelectOptionMain.getSelectionModel().getSelectedItem();
            String branch = rbSelectBranchOffice.getSelectionModel().getSelectedItem();
            em.setUser(username);
            branch_office.setName(rbSelectBranchOffice.getSelectionModel().getSelectedItem());
            if (username.equals("")) {
                
                Alerts.alertWarning("Campo USUARIO vacio", "Escribe un usuario válido");
                
            } else if (password.equals("")) {
                
                Alerts.alertWarning("Campo CONTRASEÑA vacio", "Escribe una contraseña válida");
                
            } else if (rbSelectBranchOffice.selectionModelProperty().getValue().isEmpty()) {
                
                Alerts.alertWarning("Sucursal sin seleccionar", "Selecciona una sucursal del menú");
                
            } else  if(queries.validateUser(username, password, typeEmployee, branch)){
                switch (rbSelectOptionMain.getSelectionModel().getSelectedItem()) {
                    case "Administrador":
                        try {
                            App.setRoot("VistaAdministrador");
                            Alerts.alertInformation("Inicio de sesion", "Bienvenido: " + username);
                        } catch (IOException e) {    
                            System.out.println("Error: " + e.getMessage());
                        } break;
                    
                    case "Empleado":
                        try {
                            App.setRoot("VistaEmpleado");
                            Alerts.alertInformation("Inicio de sesion", "Bienvenido: " + username);
                        } catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                        } break;
                }
            }else {
                Alerts.alertWarning("Selección invalida", "Seleccione una opción válida");
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