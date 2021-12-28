/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author e-emgarza
 */
public class ListBranchOfficeApi {

    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder;
    private String line;        
    
    public StringBuilder consultIdBranchOffice() throws IOException {
        try {
                // api para consumir los fatos
                URL url = new URL("http://localhost:9001/ListBranchOffice");
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
                }
            } catch (MalformedURLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        return stringBuilder;
    }
}
