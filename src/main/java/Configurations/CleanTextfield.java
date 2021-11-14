
package Configurations;

import java.util.List;
import javafx.scene.control.TextField;

public class CleanTextfield {
    
    public static void cleanAllTextfield(List<TextField> listTextField) {
        
        /*
        for(TextField item: listTextField) {
        
            item.setText("");
        
        }
        */
        
        listTextField.forEach(item -> {
            
            item.setText("");
            
        });
        
    }
    
}
