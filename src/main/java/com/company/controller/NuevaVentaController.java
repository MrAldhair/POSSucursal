package com.company.controller;

import Configurations.Alerts;
import Configurations.CleanTextfield;
import Configurations.DateAndHour;
import Configurations.LoadImage;
import BusinessAPI.ListBranchOfficeApi;
import BusinessAPI.PostApi;
import BusinessDB.Queries;
import Models.BranchOffice;
import Models.Employee;
import Models.Sale;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
    Queries queries = new Queries();
    
    public NuevaVentaController() {
        // Inicializar la lista
        this.listTextfield = new ArrayList<>();
    }

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Usuario que inicia sesion trasferido desde la clase PrincipalController
        this.name_employee.setUser(PrincipalController.em.getUser());
        this.new_branch_office.setName(PrincipalController.branch_office.getName());
        // Usuario que inicia sesion en pantalla
        this.lblUserName.setText("Usuario: " + name_employee.getUser());
        //Establecer la hora en la pantalla
        DateAndHour.dateAndHour(txtDate);
        // Cargar logo en la pantalla
        LoadImage.loadImageMain(this.imageMain);
        this.listTextfield.add(this.txtTotal);
        this.listTextfield.add(this.txtSaleDescription);
        //Establecer la hora de la venta cuando se genere una nueva
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        this.txtSaleDate.setText(dtf.format(now));
  
    }
    
    @FXML 
    private void generateSale(ActionEvent event) throws SQLException, IOException {
        
        Object ev = event.getSource();
        
        if(ev.equals(this.btnGenerateSale)){
            
            if(validateTextField() == true && 
                    !this.txtDate.getText().isEmpty() && 
                    !this.txtSaleDescription.getText().isEmpty()) {
                
                Sale new_sale = new Sale();
                new_sale.setTotal_sale(Double.parseDouble(this.txtTotal.getText()));
                new_sale.setDescription(this.txtSaleDescription.getText());
                new_sale.setDate_sale(this.txtDate.getText());
                new_sale.setId_branch_office(consumeBranchOffice());
                
                // selecciona la sucursal desde la pantalla de inicio
                new_sale.setName_branch_office(new_branch_office.getName());
                //asign the user id from db using the employee name from object Employee
                new_sale.setId_employee(queries.getUserIdLogin(name_employee.getUser()));
                new_sale.setName_employee(PrincipalController.em.getUser());
                new_sale.setFolio(
                        "sucursal: "+new_sale.getId_branch_office().toString()+" - "+
                        "No. Empleado: "+ new_sale.getId_employee().toString()+" - "+
                        "Empleado: "+new_sale.getName_employee()+" - "+
                        "Fecha: "+new_sale.getDate_sale()+" - "+
                        "Total: "+new_sale.getTotal_sale().toString());

                //mapper convierte objeto a String en formato json.
                ObjectMapper mapper = new ObjectMapper();
                String json;
                
                try {
                    PostApi postApi = new PostApi();
                    this.alert = new Alert(Alert.AlertType.CONFIRMATION);
                    this.alert.setTitle("Generación de venta");
                    this.alert.setContentText("Confirmar venta");
                    this.alert.setHeaderText(null);
                    // Se agrega esto para obtener el resultado de la alerta de la confirmación
                    Optional<ButtonType> action = this.alert.showAndWait();
                    // confirmacion de la alerta
                    if (action.get() == ButtonType.OK) {
                        //Enviar datos de venta
                        json = mapper.writeValueAsString(new_sale);
                        postApi.postJson(json);
                        System.out.println("¡¡venta enviada!!");
                        Alerts.alertInformation("Generación de venta", "Nueva venta generada con exito");
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
    
    /*
    * Cambiar a la pantalla de principal del empleado de ventas
    */
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
    
    private Long consumeBranchOffice() throws IOException {    
        Long id_branch_office = null;
        
        try {
            ListBranchOfficeApi branch = new ListBranchOfficeApi();
            JSONArray dataArray  = new JSONArray(branch.consultIdBranchOffice().toString());
            
            for(int i = 0 ; i < dataArray.length(); i++) {        
                JSONObject row = dataArray.getJSONObject(i);
                
                if(new_branch_office.getName().equals(row.getString("name"))){    
                    new_branch_office.setId_branch_office(row.getLong("id_branch_office"));
                    id_branch_office = new_branch_office.getId_branch_office();
                }
            }
            } catch (MalformedURLException e) {}        
        return id_branch_office;
    }

    /*
     * Validar que el campo de txtTotal se un numero y no otro tipos de caracteres
     */
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
}