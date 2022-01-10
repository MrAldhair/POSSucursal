
package com.company.controller;

import BusinessAPI.ListSalesApi;
import Configurations.Alerts;
import Configurations.DataAndHour;
import Configurations.LoadImage;
import BusinessDB.Queries;
import Models.Employee;
import Models.Sale;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;


public class EmpleadoController implements Initializable{

    @FXML private TextField txtDate;
    @FXML private ImageView imageMain;
    @FXML private TableView<?> tbSales;
    @FXML private Button btnNewSale;
    @FXML private Button btnSignOutEmployee;
    @FXML private TableColumn<Sale, Long> colIdSale;
    @FXML private TableColumn<Sale, Integer> colIdBranchOffice;
    @FXML private TableColumn<Sale, Double> colTotalSale;
    @FXML private TableColumn<Sale, String> colDescriptionSale;
    @FXML private TableColumn<Sale, String> colDateSale;
    @FXML private TableColumn<Sale, String> colNameBranchOffice;
    @FXML private Label lblUserName;
    
    private ObservableList<Sale> listSales;
    
    Alert alert = new Alert(Alert.AlertType.NONE);
    Employee em = new Employee();
    Queries queries = new Queries();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Usuario que inicia sesion trasferido desde la clase PrincipalController
        this.em.setUser(PrincipalController.em.getUser()); 
        //System.out.println(em.getUser());
        DataAndHour.dateAndHour(this.txtDate);
        LoadImage.loadImageMain(this.imageMain);
        
        this.colIdSale.setCellValueFactory(new PropertyValueFactory<>("id_sale"));
        this.colIdBranchOffice.setCellValueFactory(new PropertyValueFactory<>("id_branch_office"));
        this.colTotalSale.setCellValueFactory(new PropertyValueFactory<>("total_sale"));
        this.colDescriptionSale.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.colDateSale.setCellValueFactory(new PropertyValueFactory<>("date_sale"));
        this.colNameBranchOffice.setCellValueFactory(new PropertyValueFactory<>("name_branch_office"));
        listSales = (ObservableList<Sale>) tbSales.getItems();
        // Usuario que inicia sesion
        this.lblUserName.setText("Usuario: " + em.getUser());
                try {
            loadDataApi();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }

    @FXML 
    private void newSale(ActionEvent event) {
        Object ev = event.getSource();
        if(ev.equals(this.btnNewSale)){ 
            try {
            App.setRoot("VistaNuevaVenta");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    @FXML 
    private void signOutEmployee(ActionEvent event) {
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
    
    //match the user id logged with all the sales and show the inner join from API
    private void loadDataApi() throws IOException, SQLException {
        this.tbSales.getItems().removeAll(listSales);
        //get all the sales in api
        ListSalesApi listSalesApi = new ListSalesApi();
        //map sales
        JSONArray dataArray  = new JSONArray(listSalesApi.consultSales().toString());
        //get logged user
        String userEmployee = em.getUser();
        //search the id from the logged user
        int idUser = queries.getUserIdLogin(userEmployee);

        for(int i = 0 ; i < dataArray.length(); i++){
            JSONObject row = dataArray.getJSONObject(i); 
            //insert to the local listsales the sales realized by user logged    
            if(row.getInt("id_employee") == idUser){
                listSales.add(
                        new Sale(
                                row.getLong("id_sale"), 
                                row.getInt("id_employee"), 
                                row.getLong("id_branch_office"), 
                                row.getString("name_branch_office"), 
                                row.getDouble("total_sale"), 
                                row.getString("description"), 
                                row.getString("date_sale"), 
                                row.getString("name_employee")));
            }else{
                listSales.clear();
            }    
        }             
    }
}