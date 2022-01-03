package BusinessAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ListSalesApi {
    
    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder;
    private String line;   
    
    public StringBuilder consultSales()throws IOException{
        
        try{
            
            // api para consumir los datos
                URL url = new URL("http://localhost:9001/listar");
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
