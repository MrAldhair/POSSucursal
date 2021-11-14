
package Configurations;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoadImage {
    
    public static void loadImageMain(ImageView imageView){
        
        Image image = new Image("/images/image-main.png");
        imageView.setImage(image);
    
    }
    
}
