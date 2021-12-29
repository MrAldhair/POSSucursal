 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessAPI;

import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

public class ListBranchOfficeApiTest {    
    public ListBranchOfficeApiTest() {
    }
    /**
     * valida conexión a API.
     * /
    @Test
    public void testConecctionConsultIdBranchOffice() throws Exception{
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .queryParam("format", "json")
                .when()
                .get("http://localhost:9001/ListBranchOffice")
                .then()
                .assertThat().statusCode(is(equalTo(200)));
    }
    /**
     * valida que extrae y muestra los datos con metodo get en caso de existir 
     * registros en API.
    */
    @Test
    public void testConsultIdBranchOffice() throws Exception {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        String body = RestAssured
                .given().baseUri("http://localhost:9001/ListBranchOffice")
                .and().queryParam("format", "json")
                .when().get("/")
                .then().log().all()
                .and().assertThat().statusCode(is(equalTo(200)))
                .and()
                .body("id_branch_office", response -> notNullValue())
                .body("name", response -> notNullValue())
                .body("state", response -> notNullValue())
                .body("city", response -> notNullValue())
                .body("street", response -> notNullValue())
                .body("number", response -> notNullValue())
                .body("zip_code", response -> notNullValue())
                .and().extract().body().asString();
    }
}