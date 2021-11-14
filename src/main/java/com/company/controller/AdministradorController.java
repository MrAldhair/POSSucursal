
package com.company.controller;

import Configurations.Alerts;
import Configurations.DataAndHour;
import Configurations.LoadImage;
import ConnectivityAPI.Connectivity;
import Models.Sale;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
import javax.xml.ws.http.HTTPException;
import org.json.JSONArray;
import org.json.JSONObject;


public class AdministradorController implements Initializable {

    @FXML private TableView<Sale> tbSalesAdmin;
    @FXML private Button btnFilterUsers;
    @FXML private ComboBox<String> cBoxUsers;
    @FXML private Button btnSignOutAdmin;
    @FXML private Button btnLoadJson;
    @FXML private Button btnAddModUser;
    @FXML private Label lblTitle1;
    @FXML private Label lblDescription1;
    @FXML private TextField txtDate;
    @FXML private TextField txtTotalSales;
    @FXML private ImageView imageMain;
    @FXML private TableColumn<Sale, Long> colIdSale;
    @FXML private TableColumn<Sale, Integer> colIdEmployee;
    @FXML private TableColumn<Sale, Integer> colIdBranchOffice;
    @FXML private TableColumn<Sale, Double> colIdTotalSale;
    @FXML private TableColumn<Sale, String> coldescription;
    @FXML private TableColumn<Sale, String> colDate;
    
    private ObservableList<Sale> listSales;
    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder;
    private String line;        	
    private URL url;
    
    Alert alert = new Alert(Alert.AlertType.NONE);
    
    private Double totalSales = 0.0;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        DataAndHour.dateAndHour(this.txtDate);
        LoadImage.loadImageMain(this.imageMain);
        
        this.colIdSale.setCellValueFactory(new PropertyValueFactory<>("id_sale"));
        this.colIdEmployee.setCellValueFactory(new PropertyValueFactory<>("id_employee"));
        this.colIdBranchOffice.setCellValueFactory(new PropertyValueFactory<>("id_branch_office"));
        this.colIdTotalSale.setCellValueFactory(new PropertyValueFactory<>("total_sale"));
        this.coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.colDate.setCellValueFactory(new PropertyValueFactory<>("date_sale"));
        
        listSales = tbSalesAdmin.getItems();
        
    }    

    @FXML
    private void filterUsers(ActionEvent event) { }

    @FXML
    private void signOutAdmin(ActionEvent event) {
        
        Object ev = event.getSource();
        
        if(ev.equals(this.btnSignOutAdmin)){
            
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

    @FXML
    private void addModUser(ActionEvent event) {
        try {
            
            App.setRoot("VistaListaEmpleado");
            
        } catch (IOException e) {
            
            System.out.println("Error: " + e.getMessage());
            
        }
        
    }

    @FXML
    private void loadDataApi(ActionEvent event) throws IOException {
        
        Object ev = event.getSource();
        
        if(ev.equals(this.btnLoadJson)){
            
            this.totalSales = 0.0;
            
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
                tbSalesAdmin.getItems().removeAll(listSales);

                for(int i = 0 ; i < dataArray.length(); i++){
                    
                    JSONObject row = dataArray.getJSONObject(i); 
                    listSales.add(new Sale(row.getLong("id_sale"), row.getInt("id_employee"), row.getInt("id_branch_office"), row.getDouble("total_sale"), row.getString("description"), row.getString("date_sale")));

                    totalSales += row.getDouble("total_sale");

                }
                
                this.txtTotalSales.setText(totalSales.toString());

            }

            } catch (MalformedURLException e) {

                    e.printStackTrace();

            }
            
        }
        
    }
    
}
