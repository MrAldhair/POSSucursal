package com.company.controller;


import Configurations.Alerts;
import Configurations.DataAndHour;
import Configurations.LoadImage;
import ConnectionDB.ConnDBH2;
import Models.BranchOffice;
import Models.Employee;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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

    private Boolean administrador = false;
    Alert alert = new Alert(Alert.AlertType.NONE);
    
    // Instancias la clase que hemos creado anteriormente
    private static ConnDBH2 SQL = new ConnDBH2();
    
    // Llamas al método que tiene la clase y te devuelve una conexión
    private static Connection conn;
    
    // Query que usarás para hacer lo que necesites
    private static String sSQL = "";
    
    // Resultado de un query
    private ResultSet rs;
    
    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder;
    private String line;        	
    private URL url;

    // Controlls
    @FXML private Label lblTitle;
    @FXML private Label lblDescription;
    @FXML private ComboBox<String> rbSelectOptionMain;
    @FXML private ComboBox<String> rbSelectBranchOffice;
    @FXML private TextField txtDate;
    @FXML private ImageView imageMain;
    @FXML private Label lblSelection;
    @FXML private TextField txtUserName;
    @FXML private TextField txtPassword;
    @FXML private Button btnLogin;
    
    

    private ObservableList<String> optionEmployee = FXCollections.observableArrayList( "Administrador", "Empleado");
    private ObservableList<String> optionsBranchOffice = FXCollections.observableArrayList();
    
    public static String bufferUserName;
    
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
        
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        
        String typeEmployee = rbSelectOptionMain.getSelectionModel().getSelectedItem();
        
        em.setUser(username);
        branch_office.setName(rbSelectBranchOffice.getSelectionModel().getSelectedItem());

        if (username.equals("")) {
            this.alert = new Alert(Alert.AlertType.WARNING);
            this.alert.setTitle("Campo USUARIO vacio");
            this.alert.setHeaderText(null);
            this.alert.setContentText("Escribe un usuario válido");
            this.alert.showAndWait();
            
        } else if (password.equals("")) {
            this.alert = new Alert(Alert.AlertType.WARNING);
            this.alert.setTitle("Campo CONTRASEÑA vacio");
            this.alert.setHeaderText(null);
            this.alert.setContentText("Escribe una contraseña válida");
            this.alert.showAndWait();
        } else if (rbSelectBranchOffice.selectionModelProperty().getValue().isEmpty()) {
            this.alert = new Alert(Alert.AlertType.WARNING);
            this.alert.setTitle("Sucursal sin seleccionar");
            this.alert.setHeaderText(null);
            this.alert.setContentText("Selecciona una sucursal del menú");
            this.alert.showAndWait();
        }  
        
        else  {
            
            conn = SQL.connectionDbH2();
            sSQL = "SELECT * FROM useremployee WHERE user=? AND password=? AND typeEmployee=? AND branchName=?";
            
            try {
                
                // PreparedStatement
                PreparedStatement pstm = conn.prepareStatement(sSQL);
                pstm.setString(1, username);
                pstm.setString(2, password);
                pstm.setString(3, typeEmployee);
                pstm.setString(4,rbSelectBranchOffice.getSelectionModel().getSelectedItem());
                rs = pstm.executeQuery();
                
                if (rs.next()) {

                    switch (rbSelectOptionMain.getSelectionModel().getSelectedItem()) {
                        case "Administrador":
                            administrador = true;
                            try {
                                
                                App.setRoot("VistaAdministrador");
                                
                                Alerts.alertInformation("Inicio de sesion", "Bienvenido: " + username);
                                conn.close();
                            } catch (IOException e) {
                                
                                System.out.println("Error: " + e.getMessage());
                                
                            }   break;
                        case "Empleado":
                            administrador = false;
                            try {
                                
                                App.setRoot("VistaEmpleado");
                                Alerts.alertInformation("Inicio de sesion", "Bienvenido: " + username);
                                
                            } catch (IOException e) {
                                
                                System.out.println("Error: " + e.getMessage());
                                
                            }   break;
                    }

                } else {
                    this.alert = new Alert(Alert.AlertType.WARNING);
                    this.alert.setTitle("Selección invalida");
                    this.alert.setHeaderText(null);
                    this.alert.setContentText("Selecciona un puesto válido");
                    this.alert.showAndWait();
                }
                
            } catch (SQLException ex) {
                
                System.out.println(ex.toString());
                
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
 
                    }
                    
                }    
                
            } catch (MalformedURLException e) {

                    e.printStackTrace();
            }
        
    }
    
}
