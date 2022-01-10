
package Configurations;

import java.util.List;
import javafx.scene.control.TextField;

public class CleanTextfield {
    
    public static void cleanAllTextfield(List<TextField> listTextField) {
        
        listTextField.forEach(item -> {
            
            item.setText("");
            
        });
        
        /*
        for(TextField item: listTextField) {
        
            item.setText("");
        
        }
        */
        
    }
    
}
