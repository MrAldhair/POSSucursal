package com.company.controller;

import Business.RequestApiSales;
import Configurations.Alerts;
import Configurations.CleanTextfield;
import Configurations.DataAndHour;
import Configurations.LoadImage;
import ConnectionDB.ConnDBH2;
import Models.BranchOffice;
import Models.Employee;
import Models.Sale;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;



public class NuevaVentaController implements Initializable{

    @FXML private Label lblTitle;
    @FXML private Label lblDescription;
    @FXML private Button btnGenerateSale;
    @FXML private TextField txtDate;
    @FXML private ImageView imageMain;
    @FXML private TextField txtTotal;
    @FXML private TextField txtSaleDescription;
    @FXML private TextField txtSaleDate;
    @FXML private Button btnReturnEmployeeSale;
    @FXML private Label lblUserName;
    
    Alert alert = new Alert(Alert.AlertType.NONE);
    
    // Lista de textfield
    private final List<TextField> listTextfield;
    Employee name_employee = new Employee();
    BranchOffice new_branch_office = new BranchOffice();
    
    // Instancias la clase que hemos creado anteriormente
    private static final ConnDBH2 SQL = new ConnDBH2();
    // Llamas al método que tiene la clase y te devuelve una conexión
    private static Connection conn;
    // Query que usarás para hacer lo que necesites
    private static String sSQL = "";
    //ResultSet
    private ResultSet rs;
    
    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder;
    private String line;        	
    private URL url;
    
    public NuevaVentaController() {
        
        // Inicializar la lista
        this.listTextfield = new ArrayList<>();
        
    }
    
    public boolean validateTextField() throws IOException {
        
        try {
            
            Double.parseDouble(this.txtTotal.getText());
            
        } catch (NumberFormatException e) {
            
            Alerts.alertWarning("Campos invalidos", "Ingresa solamente números en TOTAL");
            CleanTextfield.cleanAllTextfield(this.listTextfield);
            return false;
            
        }
        
        return true;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Usuario que inicia sesion trasferido desde la clase PrincipalController
        this.name_employee.setUser(PrincipalController.em.getUser());
        this.new_branch_office.setName(PrincipalController.branch_office.getName());
              
        DataAndHour.dateAndHour(txtDate);
        LoadImage.loadImageMain(this.imageMain);
        
        this.listTextfield.add(this.txtTotal);
        this.listTextfield.add(this.txtSaleDescription);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        
        this.txtSaleDate.setText(dtf.format(now));

        // Usuario que inicia sesion
        this.name_employee.setUser(PrincipalController.em.getUser());
        this.lblUserName.setText("Usuario: " + name_employee.getUser());
    }

    @FXML 
    private void generateSale(ActionEvent event) throws SQLException, IOException { 
        
        Object ev = event.getSource();
        if(ev.equals(this.btnGenerateSale)){
            
            if(validateTextField() == true && !this.txtDate.getText().isEmpty() && !this.txtSaleDescription.getText().isEmpty()) {
                
                Sale new_sale = new Sale();
                new_sale.setTotal_sale(Double.parseDouble(this.txtTotal.getText()));
                new_sale.setDescription(this.txtSaleDescription.getText());
                new_sale.setDate_sale(this.txtDate.getText());
                new_sale.setId_branch_office(consultIdBranchOffice());
                // selecciona la sucursal desde la pantalla de inicio
                new_sale.setName_branch_office(new_branch_office.getName());
                new_sale.setId_employee(userLogin());
                new_sale.setName_employee(PrincipalController.em.getUser());

                ObjectMapper mapper = new ObjectMapper();
                String json;
                Response response;
                
                try {
                    
                    this.alert = new Alert(Alert.AlertType.CONFIRMATION);
                    this.alert.setTitle("Generación de venta");
                    this.alert.setContentText("¿Desea registrar una nueva venta?");
                    this.alert.setHeaderText(null);

                    // Se agrega esto para obtener el resultado de la alerta de la confirmación
                    Optional<ButtonType> action = this.alert.showAndWait();

                    // confirmacion de la alerta
                    if (action.get() == ButtonType.OK) {
                        
                        //Enviar datos de venta
                        json = mapper.writeValueAsString(new_sale);
                        response = RequestApiSales.doPostRequest(json, "http://localhost:9001/crear");
                        Alerts.alertInformation("Generación de venta", "Nueva venta generada con exito");
                        //System.out.println(this.txtDate.getText());

                        // Limpiar los campos
                        CleanTextfield.cleanAllTextfield(this.listTextfield);
                        
                        
                    } else {
                        
                        Alerts.alertInformation("Generación de venta", "Operación cancelada");  
                        
                    }
                    
                } catch (JsonProcessingException | NumberFormatException e) {
                    
                    System.out.println("Error:" + e.getMessage());
                    
                }
            } else {
                
                Alerts.alertWarning("Generación de nueva venta", "Existen campos vacios, por favor llene todo espacios en blanco");   
                
            }
            
        }
        
    }

    @FXML
    private void switchToEmployee(ActionEvent event) {
        
        Object ev = event.getSource();
        
        if (ev.equals(this.btnReturnEmployeeSale)) {
            
            try {
                
                App.setRoot("VistaEmpleado");
                
            } catch (IOException e) {
                
                System.out.println("Error: " + e.getMessage());
                
            }
            
        }
    }
    
    private int userLogin() throws SQLException {
        
        conn = SQL.connectionDbH2();
        sSQL = "SELECT idemployee FROM useremployee WHERE user=?";
        int idUser = 0;
        PreparedStatement preparedStatement = conn.prepareStatement(sSQL);
        preparedStatement.setString(1, name_employee.getUser());
        rs = preparedStatement.executeQuery();
        
        if(rs.next()){
            
            idUser = rs.getInt("idemployee");  
            
        }
        
        return idUser;     
    }
    
    private Long consultIdBranchOffice() throws IOException {
        
        Long id_branch_office = null;
        
        try {
                // api para consultar las sucursales disponibles
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
                        
                        if(new_branch_office.getName().equals(row.getString("name"))){

                            new_branch_office.setId_branch_office(row.getLong("id_branch_office"));
                            id_branch_office = new_branch_office.getId_branch_office();
                                           
                        } 

                    }

                }    
                
            } catch (MalformedURLException e) {

                    e.printStackTrace();
                    
            }

        
        return id_branch_office;
    }
    
}
