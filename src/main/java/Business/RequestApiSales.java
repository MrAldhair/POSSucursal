
package Business;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

public class RequestApiSales {
    
    public static Response doGetRequest(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;
        return
            given().
                headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                when().get(endpoint).
                then().contentType(ContentType.JSON).extract().response();
    }
    
    //    Esta es mi metodo POST
    public static Response doPostRequest(String requestBody, String uri) {
//      Recuerda que maneje toda mi info con java streams para retornar la respuesta de forma sencilla
        return given()
//                Los headers es una sección adicional al payload de una solicitud, la cual no puede ser vista a simple vista, si no que requiere de un analizador HTTP para poderlos ver, sin embargo, todas las solicitudes llevan por default una serie de headers, entonces es sobre como mandar tu info sobre de las columnas, aqui te recomiendo que uses lo mismo, realmente este es un codigo que genere de forma generica, ahora si que solo lo adaptas a lo que use
                .header("Content-type", "application/json")
                .and()
//                Esto nos permite recuperar el cuerpo de la solicitud
                .body(requestBody)
                .when()
//                Hace post cuando obtiene el endpoint
                .post(uri)
                .then()
                .extract().response();
    }
    
}
