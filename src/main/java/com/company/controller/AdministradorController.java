package com.company.controller;

import Configurations.DataAndHour;
import Configurations.LoadImage;
import ConnectionDB.ConnDBH2;
import ConnectivityAPI.Connectivity;
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
import java.util.ArrayList;
import java.util.List;
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
   // private ObservableList<Employee> listUsers;
    @FXML private Button btnSignOutAdmin;
    @FXML private Button btnLoadJson;
    @FXML private TextField txtSales;
    @FXML private Button btnAddModUser;
    @FXML private Label lblTitle1;
    @FXML private Label lblDescription1;
    @FXML private TextField txtDate;
    @FXML private ImageView imageMain;
    @FXML private TableColumn<Sale, Long> colIdSale;
    @FXML private TableColumn<Sale, Integer> colIdEmployee;
    @FXML private TableColumn<Sale, Integer> colIdBranchOffice;
    @FXML private TableColumn<Sale, Double> colIdTotalSale;
    @FXML private TableColumn<Sale, String> coldescription;
    @FXML private TableColumn<Sale, String> colDate;
    // Instancias la clase que hemos creado anteriormente
    private static ConnDBH2 SQL = new ConnDBH2();
     // Llamas al método que tiene la clase y te devuelve una conexión
    private static Connection conn;
    // Query que usarás para hacer lo que necesites
    private static String querySql = "";
    // Obtener el resuldo de una consulta
    private ResultSet rs;
    private ObservableList<Sale> listSales;
    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder;
    private String line;
    private URL url;
//
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
        cBoxUsers.setItems(FXCollections.observableArrayList(getData()));
//        this.listUsers = FXCollections.observableArrayList();
        //loadCombobox();
    }

    @FXML
    private void filterUsers(ActionEvent event) {
        
    }

    @FXML
    private void signOutAdmin(ActionEvent event) {
        Object ev = event.getSource();
        if (ev.equals(this.btnSignOutAdmin)) {
            try {
                App.setRoot("VistaPrincipal");
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
        if (ev.equals(this.btnLoadJson)) {
            try {
                url = new URL("http://localhost:9001/listar");
                //realiza la conexion
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                if (connection.getResponseCode() == 200) {
                    
                    System.out.println("Response: OK");
                    //obtiene respuesta
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    stringBuilder = new StringBuilder();

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    JSONArray dataArray = new JSONArray(stringBuilder.toString());
                    tbSalesAdmin.getItems().removeAll(listSales);

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject row = dataArray.getJSONObject(i);
                        listSales.add(
                                new Sale(
                                        row.getLong("id_sale"), 
                                        row.getInt("id_employee"), 
                                        row.getInt("id_branch_office"), 
                                        row.getDouble("total_sale"), 
                                        row.getString("description"), 
                                        row.getString("date_sale")));
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private List<String> getData(){
        conn = SQL.connectionDbH2();
        querySql = "SELECT user FROM useremployee";
        List<String> options = new ArrayList<>();
        try{
            PreparedStatement pstm = conn.prepareCall(querySql);
            rs=pstm.executeQuery();
            while (rs.next()){
                options.add(rs.getString("user"));
            }
            pstm.close();
            rs.close();
            return options;
        }catch(SQLException ex){
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
