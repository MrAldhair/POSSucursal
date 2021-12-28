/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessAPI;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PostApi {
    private static HttpURLConnection http;
	
    public static void postJson(String json){
        try {
                URL url = new URL("http://localhost:9001/crear");
                http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoOutput(true);
                http.setRequestProperty("Accept","application/json");
                http.setRequestProperty("Content-Type","application/json");
                String data = json;
                byte[] out = data.getBytes(StandardCharsets.UTF_8);
                OutputStream stream = http.getOutputStream();
                stream.write(out);
                http.disconnect();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}