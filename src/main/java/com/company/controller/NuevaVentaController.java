package com.company.controller;

import Business.RequestApiSales;
import Configurations.Alerts;
import Configurations.CleanTextfield;
import Configurations.DataAndHour;
import Configurations.LoadImage;
import Models.Sale;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class NuevaVentaController implements Initializable {

    @FXML private Label lblTitle;
    @FXML private Label lblDescription;
    @FXML private Button btnGenerateSale;
    @FXML private TextField txtDate;
    @FXML private ImageView imageMain;
    @FXML private TextField txtTotal;
    @FXML private TextField txtSaleDescription;
    @FXML private TextField txtSaleDate;
    @FXML private Button btnReturnEmployeeSale;

    Alert alert = new Alert(Alert.AlertType.NONE);

    // Lista de textfield
    private final List<TextField> listTextfield;

    public NuevaVentaController() {

        // Inicializar la lista
        this.listTextfield = new ArrayList<>();

    }

    public boolean validateTextField() throws IOException {
        try {
            Double.parseDouble(this.txtTotal.getText());
        } catch (NumberFormatException e) {
            System.out.println("ingresa solo numeros");
            CleanTextfield.cleanAllTextfield(this.listTextfield);
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataAndHour.dateAndHour(txtDate);
        this.listTextfield.add(this.txtTotal);
        this.listTextfield.add(this.txtSaleDescription);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        this.txtSaleDate.setText(dtf.format(now));
        LoadImage.loadImageMain(this.imageMain);
    }

    @FXML
    private void generateSale(ActionEvent event) throws IOException {
        Object ev = event.getSource();

        if (ev.equals(this.btnGenerateSale)) {
            Sale new_sale = new Sale();
            new_sale.setTotal_sale(Double.parseDouble(this.txtTotal.getText()));
            if ( validateTextField() == true && 
                !this.txtDate.getText().isEmpty() && 
                !this.txtSaleDescription.getText().isEmpty()) {
                new_sale.setDescription(this.txtSaleDescription.getText());
                new_sale.setDate_sale(this.txtDate.getText());
                
                new_sale.setId_branch_office(1);
                new_sale.setId_employee(32);

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
                
                        //System.out.println("¡¡exito!!");

                        Alerts.alertInformation("Generación de venta", "Nueva venta generada con exito");
                        System.out.println(this.txtDate.getText());

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
}
