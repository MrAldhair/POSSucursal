
package Configurations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.TextField;


public class DataAndHour {
    
    public static void dateAndHour(TextField txtdate){
 
        // Time and date
        try {
            
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");// 
                    LocalDateTime now = LocalDateTime.now();
                    txtdate.setText(dtf.format(now));
                }
            };
            
            timer.schedule(task, 0, 1000);
            
        } catch (Exception e) {
            
            System.out.println("Error: " + e.getMessage());
            
        }
        
    }

}
