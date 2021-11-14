
package Configurations;

import javafx.scene.control.Alert;

public class Alerts {
    
    private static Alert alert = new Alert(Alert.AlertType.NONE);
    
    public static void alertWarning(String title, String contentText) {
    
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.setHeaderText(null);
        alert.showAndWait();
        
    }
    
    public static void alertConfirmation(String title, String contentText) {
    
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.setHeaderText(null);
        alert.showAndWait();
        
    }
    
    public static void alertInformation(String title, String contentText) {
    
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.setHeaderText(null);
        alert.showAndWait();
        
    }
    
    public static void alertError(String title, String contentText) {
    
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.setHeaderText(null);
        alert.showAndWait();
        
    }
    
    
}
