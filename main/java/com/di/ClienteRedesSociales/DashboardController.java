package com.di.ClienteRedesSociales;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.web.*;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class DashboardController {
    @FXML
    private Button InstagramButton;
    @FXML
    private Button XButton;
    @FXML
    private Button FacebookButton;
    @FXML
    private Button YoutubeButton;
    @FXML
    private Button CerrarSesionButton;
    @FXML
    private Label UserLabel;
    @FXML
    private WebView webView;
    @FXML
    private Label LabelPanel;
    private WebEngine engine;

    public void initData(String username) {
        // Concatenar los valores y establecer el texto del Label
        UserLabel.setText(username);
        engine = webView.getEngine();
    }
    public void AbrirInstagram(ActionEvent event){
        LabelPanel.setText("Instagram");
        engine.load("https://www.instagram.com/?hl=es");
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI("https://www.instagram.com/?hl=es"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public void AbrirX(ActionEvent event){
        LabelPanel.setText("X");
        engine.load("https://twitter.com/home");
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI("https://twitter.com/home"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
    public void AbrirFacebook(ActionEvent event){
        LabelPanel.setText("Facebook");
        engine.load("https://www.facebook.com/");
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI("https://www.facebook.com/"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
    public void AbrirYoutube(ActionEvent event){
        LabelPanel.setText("Youtube");
        engine.load("https://www.youtube.com/");
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI("https://www.youtube.com/"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    public void CerrarSession() {
        Stage stage = (Stage) CerrarSesionButton.getScene().getWindow();
        stage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage Register = new Stage();
            Register.initStyle(StageStyle.UNDECORATED);
            Register.setScene(new Scene(root, 520, 400));
            Register.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
