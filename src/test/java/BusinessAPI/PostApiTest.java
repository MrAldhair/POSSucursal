/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessAPI;

import Models.Sale;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.*;

public class PostApiTest {

    PostApi instance = new PostApi();



    @Test
    public void testPostJson() throws IOException {
        Sale sale = new Sale();
        String json = null;
        ObjectMapper mapper = new ObjectMapper();

        sale.setId_sale(0L);
        sale.setId_employee(0);
        sale.setId_branch_office(0L);
        sale.setTotal_sale(1.0);
        sale.setDescription("sale");
        sale.setDate_sale("20/11/2021 00:00:00");
        sale.setName_employee("alvaro");
        sale.setName_branch_office("branch");
        sale.setFolio("sucursal: " + sale.getId_branch_office()+" - " + 
                "No. Empleado: "+ sale.getId_employee()+ " - " +
                "Empleado: "+ sale.getName_employee()+ " - " +
                "Fecha: "+ sale.getDate_sale()+ " - " +
                "Total: "+ sale.getTotal_sale());
        json = mapper.writeValueAsString(sale);
        int responseCode = instance.postJson(json).getResponseCode();
        /*
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        ValidatableResponse responseCodeMock= RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .queryParam("format", "json")
                .and()
                .body(json)
                .when()
                .post("http://localhost:9001/crear")
                .then().extract().response()
                .then()
                .assertThat().statusCode(is(equalTo(201)));
                */
        Assert.assertEquals(201,responseCode);
    }
    
    @Test
    public void testPostJsonNoBody() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .queryParam("format", "json")
                .when()
                .post("http://localhost:9001/crear")
                .then()
                .assertThat().statusCode(is(equalTo(400)));
    }


}