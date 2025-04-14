module com.example.atividade2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.atividade2 to javafx.fxml;
    exports com.example.atividade2;
}