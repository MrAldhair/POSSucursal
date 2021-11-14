/* doesn't work with source level 1.8:
module com.company.controller {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.company.controller to javafx.fxml;
    exports com.company.controller;
}
*/
