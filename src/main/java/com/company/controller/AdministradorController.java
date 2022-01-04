package com.company.controller;

import BusinessAPI.ListSalesApi;
import BusinessDB.Queries;
import Configurations.Alerts;
import Configurations.DataAndHour;
import Configurations.LoadImage;
import Models.Employee;
import Models.Sale;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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
    @FXML private TableColumn<Sale, String> colNameUser;
    @FXML private TableColumn<Sale, String> colNameBranchOffice;
    @FXML private Label lblNameUser;
    
    // Obtener el resultaddo de una consulta
    private ObservableList<Sale> listSales;
    private Double totalSales = 0.0;
    private Integer id_user = 0;
    Queries queries = new Queries();
    Alert alert = new Alert(Alert.AlertType.NONE);
    Employee name_employee = new Employee();

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
        this.colNameUser.setCellValueFactory(new PropertyValueFactory<>("name_employee"));
        this.colNameBranchOffice.setCellValueFactory(new PropertyValueFactory<>("name_branch_office"));        
        listSales = tbSalesAdmin.getItems();
        cBoxUsers.setItems(FXCollections.observableArrayList(queries.getUserName()));
        
        // Usuario que inicia sesión
        this.name_employee.setUser(PrincipalController.em.getUser());
        this.lblNameUser.setText("Usuario: " + name_employee.getUser());
    }    

    @FXML
    private void filterUsers(ActionEvent event) throws IOException, SQLException {
        Object ev = event.getSource();
        
        if(ev.equals(this.btnFilterUsers)){
            
            String selectedItem = cBoxUsers.getSelectionModel().getSelectedItem();
            
            if(selectedItem != null) {
                ListSalesApi listSalesApi = new ListSalesApi();
                JSONArray dataArray  = new JSONArray(listSalesApi.consultSales().toString());
                this.tbSalesAdmin.getItems().removeAll(listSales);
                //recorre el json
                for(int i = 0 ; i < dataArray.length(); i++){
                    JSONObject row = dataArray.getJSONObject(i);
                    if(row.getInt("id_employee") == queries.getUserId(this.cBoxUsers)){
                    listSales.add(
                            new Sale(
                                    row.getLong("id_sale"), 
                                    row.getInt("id_employee"), 
                                    row.getLong("id_branch_office"),
                                    row.getString("name_branch_office"),
                                    row.getDouble("total_sale"), 
                                    row.getString("description"), 
                                    row.getString("date_sale"),
                                    row.getString("name_employee"),
                                    row.getString("folio")));
                    }
                }
                
                if(listSales.isEmpty()){
                          Alerts.alertWarning("Filtrar ventas por usuario",
                                  "Este usuario no tiene ventas registradas");
                }
            }
        }
    }

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
            ListSalesApi listSalesApi = new ListSalesApi();
            JSONArray dataArray  = new JSONArray(listSalesApi.consultSales().toString());
            tbSalesAdmin.getItems().removeAll(listSales);
            
            for(int i = 0 ; i < dataArray.length(); i++) {
                JSONObject row = dataArray.getJSONObject(i); 
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
                totalSales += row.getDouble("total_sale");
                id_user = row.getInt("id_employee");
            }
            this.txtTotalSales.setText(totalSales.toString());
        }
    }
}