package com.company.controller;

import Configurations.Alerts;
import Configurations.CleanTextfield;
import Configurations.DataAndHour;
import Configurations.LoadImage;
import ConnectionDB.ConnDBH2;
import Models.Employee;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    // Generar la conexion
    private static final ConnDBH2 sql = new ConnDBH2();
    private static final Connection conn = sql.connectionDbH2(); // Query
    private static String querySql = "";
    
    // Declaracion de la alerta
    Alert alert = new Alert(Alert.AlertType.NONE);
    
    // Lista de textfield
    private List<TextField> listTextfield;
    private ObservableList<String> optionsBranchOffice = FXCollections.observableArrayList();
    
    Employee name_employee = new Employee();
    
    // conexion API
    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder;
    private String line;        	
    private URL url;
    
    public AgregarEmpleadoController() {
        
        // Inicializar la lista
        this.listTextfield = new ArrayList<>();
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        try {
            loadDataBranchOffice();
        } catch (IOException ex) {
            Logger.getLogger(AgregarEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Cargar la hora
        DataAndHour.dateAndHour(this.txtDate);
        
        // Cargar la imagen
        LoadImage.loadImageMain(this.imageMain);
        // cargar sucursales
        rbSelectBranchOffice.setItems(optionsBranchOffice);
        
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

    @FXML 
    private void insertToDB(ActionEvent event) throws SQLException { 
        
        // Saber que en que nodo se acciona el evento
        Object evt = event.getSource();
        
        if (evt.equals(this.btnRegistrarEmpleado)) {
            
            if (!this.txtContrasena.getText().isEmpty() && !this.txtContrasena2.getText().isEmpty() && !this.txtUsuario.getText().isEmpty()) {
                
                if (this.txtContrasena.getText().equals(this.txtContrasena2.getText())) { 

                    // Crear el query
                    querySql = "INSERT INTO useremployee(user, password, typeEmployee, branchName) VALUES(?,?,?,?)";
                    
                    try {

                        //PreparedStatment
                        //bucar para que sirve esto
                        PreparedStatement preparedStatement = conn.prepareStatement(querySql);
                        preparedStatement.setString(1, this.txtUsuario.getText());
                        preparedStatement.setString(2, this.txtContrasena.getText());
                        preparedStatement.setString(4, this.rbSelectBranchOffice.getSelectionModel().getSelectedItem());
                        
                        // Obtener el tipo de emplado (Administrador / Empleado)
                        if (this.radioAdmin.isSelected()) {
                            
                            preparedStatement.setString(3, "Administrador");
                            
                        } else {
                            
                            preparedStatement.setString(3, "Empleado");
                            
                        }
 
                        this.alert = new Alert(Alert.AlertType.CONFIRMATION);
                        this.alert.setTitle("Nuevo empleado");
                        this.alert.setContentText("¿Desea agregar un nuevo usuario al sistema?");
                        this.alert.setHeaderText(null);
                        
                        // Se agrega esto para obtener el resultado de la alerta de la confirmación
                        Optional<ButtonType> action = this.alert.showAndWait();
                        
                        
                        // confirmacion de la alerta
                        if (action.get() == ButtonType.OK) {
                            
                            Alerts.alertInformation("Nuevo empleado", "¡Usuario agregado con exito!"); 
                            
                            // Ejecutar el query
                            preparedStatement.execute();
                            
                            // Limpiar los campos
                            CleanTextfield.cleanAllTextfield(this.listTextfield);
                            this.radioAdmin.setSelected(true);
                            
                        } else {
                            
                            preparedStatement.cancel();
                            System.out.println("no execute");
                            
                        }
                        
                    } catch (SQLException e) {
                        
                        System.out.println("Algo fallo: " + e.toString());
                        //buscar para que sirve esto
                        Logger.getLogger(AgregarEmpleadoController.class.getName()).log(Level.SEVERE, null, e);
                        
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

    private void loadDataBranchOffice() throws IOException {

        try {
                // api para consumir los fatos
                url = new URL("http://localhost:9001/ListBranchOffice");
                //realiza la conexion
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");            
                connection.connect();

                if(connection.getResponseCode() == 200){
                    System.out.println("Response: OK");

                    //obtiene respuesta
                    bufferedReader  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    stringBuilder = new StringBuilder();

                    while ((line = bufferedReader.readLine()) != null) {
                        
                        stringBuilder.append(line);
                        
                    }

                    JSONArray dataArray  = new JSONArray(stringBuilder.toString());                 
                    
                    for(int i = 0 ; i < dataArray.length(); i++) {

                        JSONObject row = dataArray.getJSONObject(i); 
                        
                        optionsBranchOffice.add(row.getString("name"));
                        System.out.println();
                    }
                    
                }    
                
            } catch (MalformedURLException e) {

                    e.printStackTrace();
            }
    }
}