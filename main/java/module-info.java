module com.di.examendi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires javax.mail;
    requires javafx.web;


    opens com.di.ClienteRedesSociales to javafx.fxml;
    exports com.di.ClienteRedesSociales;
}