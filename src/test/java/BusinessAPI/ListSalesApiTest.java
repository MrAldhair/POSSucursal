package BusinessAPI;

import io.restassured.RestAssured;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Assert;
import org.junit.Test;


public class ListSalesApiTest {

    @Test
    public void testConsultSales() throws Exception {
        ListSalesApi instance = new ListSalesApi();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        String bodyFromClass = instance.consultSales().toString();
        String pureBody = RestAssured
                .given().baseUri("http://localhost:9001/listar")
                .and().queryParam("format", "json")
                .when().get("/")
                .then().log().all()
                .and().assertThat().statusCode(is(equalTo(200)))
                .and()
                .body("id_sale", response -> notNullValue())
                .body("id_employee", response -> notNullValue())
                .body("id_branch_office", response -> notNullValue())
                .body("total_sale", response -> notNullValue())
                .body("description", response -> notNullValue())
                .body("date_sale", response -> notNullValue())
                .body("name_employee", response -> notNullValue())
                .body("name_branch_office", response -> notNullValue())
                .body("state_branch_office", response -> notNullValue())
                .body("city_branch_office", response -> notNullValue())
                .body("street_branch_office", response -> notNullValue())
                .body("number_branch_office", response -> notNullValue())
                .body("zip_code_branch_office", response -> notNullValue())
                .and().extract().body().asString();

        Assert.assertEquals(pureBody,bodyFromClass);
    }
}
