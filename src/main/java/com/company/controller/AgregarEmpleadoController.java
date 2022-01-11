package com.company.controller;

import BusinessAPI.ListBranchOfficeApi;
import BusinessDB.Queries;
import Configurations.Alerts;
import Configurations.CleanTextfield;
import Configurations.DateAndHour;
import Configurations.LoadImage;
import Models.Employee;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;

public class AgregarEmpleadoController implements Initializable {

    @FXML private Label lblTitle1;
    @FXML private Label lblDescription1;
    @FXML private TextField txtDate;
    @FXML private ImageView imageMain;
    @FXML private TextField txtUsuario;
    @FXML private TextField txtContrasena;
    @FXML private PasswordField txtContrasena2;
    @FXML private RadioButton radioAdmin;
    @FXML private RadioButton radioEmpleado;
    @FXML private Button btnRegistrarEmpleado;
    @FXML private Button btnCancelarRegistro;
    @FXML private Label lblNameUser;
    @FXML private ComboBox<String> rbSelectBranchOffice;
    
    // Agrupar radio buttons
    ToggleGroup tg = new ToggleGroup(); 
    
    // Declaracion de la alerta
    Alert alert = new Alert(Alert.AlertType.NONE);
    
    // Lista de textfield
    private List<TextField> listTextfield = new ArrayList<>();
    private ObservableList<String> optionsBranchOffice = FXCollections.observableArrayList();
    private String userName = "";
    private String pass ="";
    private String pass2 ="";
    Employee name_employee = new Employee();
    Queries queries = new Queries();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadDataBranchOffice();
        } catch (IOException ex) {
            Logger.getLogger(AgregarEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Cargar la hora
        DateAndHour.dateAndHour(this.txtDate);
        // Cargar la imagen
        LoadImage.loadImageMain(this.imageMain);
        // cargar sucursales
        rbSelectBranchOffice.setItems(optionsBranchOffice);
        //radioButton
        this.radioAdmin.setToggleGroup(this.tg);
        this.radioEmpleado.setToggleGroup(this.tg);
        this.radioAdmin.setSelected(true);

        // Lista para limpiar los TextField
        this.listTextfield.add(this.txtUsuario);
        this.listTextfield.add(this.txtContrasena);
        this.listTextfield.add(this.txtContrasena2);
        // Usuario que inicia sesion
        this.name_employee.setUser(PrincipalController.em.getUser());
        this.lblNameUser.setText("Usuario: " + name_employee.getUser());
    }
    //insert employee to db
    @FXML 
    private void insertToDB(ActionEvent event) throws SQLException {
        // Saber que en que nodo se acciona el evento
        Object evt = event.getSource();
        
        if (evt.equals(this.btnRegistrarEmpleado)) {
            userName = this.txtUsuario.getText();
            pass = this.txtContrasena.getText();
            pass2 = this.txtContrasena2.getText();            
            if (!this.txtContrasena.getText().isEmpty() &&
                    !this.txtContrasena2.getText().isEmpty() && 
                    !this.txtUsuario.getText().isEmpty()) {

                if (pass.equals(pass2)) {
                    
                    this.alert = new Alert(Alert.AlertType.CONFIRMATION);
                    this.alert.setTitle("Nuevo empleado");
                    this.alert.setContentText("¿Desea agregar un nuevo usuario al sistema?");
                    this.alert.setHeaderText(null);
                        
                    // Se agrega esto para obtener el resultado de la alerta de la confirmación
                    Optional<ButtonType> action = this.alert.showAndWait();
                        
                    // confirmacion de la alerta
                    if (action.get() == ButtonType.OK) {
                        
                        // Validar si el usuario existe en la base de datos (h2)
                        if(this.queries.userExist(userName)==false) {
                            ((RadioButton)this.tg.getSelectedToggle()).getText();
                            // Ejecutar el query
                            this.queries.insertUser(userName,pass,
                                    ((RadioButton)this.tg.getSelectedToggle()).getText(),
                                        this.rbSelectBranchOffice.getSelectionModel().getSelectedItem()
                                    );
                            Alerts.alertInformation("Nuevo empleado", "¡Usuario agregado con exito!");
                            
                            // Limpiar los campos
                            CleanTextfield.cleanAllTextfield(this.listTextfield);
                        } else {
                            // Cancelar la ejecucion del query

                            Alerts.alertWarning("Nuevo empleado", "El nombre: " + this.txtUsuario.getText() + " ya se encuentra registrado en el sistema.");
                        }                                                          
                    } else {
                        this.queries.cancel();
                        System.out.println("no execute");
                    }
                    
                } else {
                    Alerts.alertWarning("Nuevo empleado", "La contraseña no coinciden, verificalo por favor");
                }
            } else {
                Alerts.alertWarning("Nuevo empleado", "Existen campos vacios");        
            }
        }
    }

    @FXML 
    private void switchToAdministrador(ActionEvent event) {
        Object ev = event.getSource();
        
        if(ev.equals(this.btnCancelarRegistro)) {
            try {
            App.setRoot("VistaListaEmpleado");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
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