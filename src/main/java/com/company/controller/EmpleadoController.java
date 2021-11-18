
package com.company.controller;

import Configurations.Alerts;
import Configurations.DataAndHour;
import Configurations.LoadImage;
import ConnectionDB.ConnDBH2;
import Models.Employee;
import Models.Sale;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;


public class EmpleadoController implements Initializable{

    @FXML private Label lblTitle;
    @FXML private Label lblDescription;
    @FXML private TextField txtDate;
    @FXML private ImageView imageMain;
    @FXML private TableView<?> tbSales;
    @FXML private Button btnFilter;
    @FXML private ComboBox<?> cbFilter;
    @FXML private Button btnNewSale;
    @FXML private Button btnSignOutEmployee;
    @FXML private TableColumn<Sale, Long> colIdSale;
    @FXML private TableColumn<Sale, Integer> colIdBranchOffice;
    @FXML private TableColumn<Sale, Double> colTotalSale;
    @FXML private TableColumn<Sale, String> colDescriptionSale;
    @FXML private TableColumn<Sale, String> colDateSale;
    @FXML private Label lblUserName;
    
    private ObservableList<Sale> listSales;
    
    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder;
    private String line;        	
    private URL url;
    
    Alert alert = new Alert(Alert.AlertType.NONE);
    
    Employee em = new Employee();
    
     // Instancias la clase que hemos creado anteriormente
    private static ConnDBH2 SQL = new ConnDBH2();
    // Llamas al método que tiene la clase y te devuelve una conexión
    private static Connection conn;
    // Query que usarás para hacer lo que necesites
    private static String sSQL = "";
    private ResultSet rs;
    
    Employee name_employee = new Employee();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

       // Usuario que inicia sesion trasferido desde la clase PrincipalController
       this.em.setUser(PrincipalController.em.getUser()); 
       System.out.println(em.getUser());
  
        DataAndHour.dateAndHour(this.txtDate);
        
        LoadImage.loadImageMain(this.imageMain);
        
        System.out.println();
        
        this.colIdSale.setCellValueFactory(new PropertyValueFactory<>("id_sale"));
        this.colIdBranchOffice.setCellValueFactory(new PropertyValueFactory<>("id_branch_office"));
        this.colTotalSale.setCellValueFactory(new PropertyValueFactory<>("total_sale"));
        this.colDescriptionSale.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.colDateSale.setCellValueFactory(new PropertyValueFactory<>("date_sale"));
        
        listSales = (ObservableList<Sale>) tbSales.getItems();
        
        
        // intentar ejecutar desde el constructor de esta clase
        try {
            
            loadDataApi();
            
        } catch (IOException | SQLException ex) {
            
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        // Usuario que inicia sesion
        this.name_employee.setUser(PrincipalController.em.getUser());
        this.lblUserName.setText("Usuario: " + name_employee.getUser());
        
    }

    @FXML private void newSale(ActionEvent event) {
        
        Object ev = event.getSource();
        
        if(ev.equals(this.btnNewSale)){ 
        
            try {
            
            App.setRoot("VistaNuevaVenta");
            
            } catch (IOException e) {

                System.out.println("Error: " + e.getMessage());

            }
        
        }
 
    }

    @FXML private void signOutEmployee(ActionEvent event) {
        
        Object ev = event.getSource();
        
        if(ev.equals(this.btnSignOutEmployee)){
        
            try {
                
                this.alert = new Alert(Alert.AlertType.CONFIRMATION);
                this.alert.setTitle("Cerrar sesión");
                this.alert.setContentText("¿Esta seguro de salir?");
                this.alert.setHeaderText(null);
                        
                // Se agrega esto para obtener el resultado de la alerta de la confirmación
                Optional<ButtonType> action = this.alert.showAndWait();


                // confirmacion de la alerta
                if (action.get() == ButtonType.OK) {
                
                    App.setRoot("VistaPrincipal");
                    
                } else {
                    
                    Alerts.alertInformation("Cerrar sesión", "Puede seguir trabajando...");
                    
                }
            
            } catch (IOException e) {

                System.out.println("Error: " + e.getMessage());

            }
            
        }
        
    }
    
   
    private void loadDataApi() throws IOException, SQLException {
            
        try { 

        url = new URL("http://localhost:9001/listar");

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
            this.tbSales.getItems().removeAll(listSales);

            for(int i = 0 ; i < dataArray.length(); i++){

                JSONObject row = dataArray.getJSONObject(i); 
                
                if(row.getInt("id_employee") == userLogin()){
                    
                    listSales.add(new Sale(row.getLong("id_sale"), row.getInt("id_employee"), row.getInt("id_branch_office"), row.getDouble("total_sale"), row.getString("description"), row.getString("date_sale")));
                    
                }
                
            }

        }

        } catch (MalformedURLException e) {

                e.printStackTrace();

        }
             
        
    }
    
    private int userLogin() throws SQLException {
    
        conn = SQL.connectionDbH2();
        sSQL = "SELECT idemployee FROM useremployee WHERE user=?";
        
        int idUser = 32;
        
        PreparedStatement preparedStatement = conn.prepareStatement(sSQL);
        preparedStatement.setString(1, em.getUser());
        rs = preparedStatement.executeQuery();
       
        if(rs.next()){
            
            idUser = rs.getInt("idemployee");
            
        }
    
        return idUser;
        
    }
    
}
