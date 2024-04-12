package com.di.ClienteRedesSociales;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import oovv.ConectaBD;
import oovv.DatosConexion;

import java.io.IOException;
import java.sql.*;


public class LoginController {
    @FXML
    private Button CancelButton;
    @FXML
    private Button LoginButton;
    @FXML
    private Label LoginMessageLabel;
    @FXML
    private TextField UsernameTextField;
    @FXML
    private PasswordField PasswordTextField;
    private int numCoincidencias = 0;
    private Stage stage;


    public void LoginButtonOnAction(ActionEvent event) {

        if (!PasswordTextField.getText().isBlank() && !UsernameTextField.getText().isBlank()) {
            ValidateLogin();
            if (numCoincidencias == 1) {
                DashBoardOnAction();

            } else {
                LoginMessageLabel.setText("No existe el usuario o Contraseña");
            }

        } else {
            LoginMessageLabel.setText(("intenta iniciar Sesion/algun campo vacio"));

        }
    }

    public void CancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    public void ValidateLogin() {
        //conexion a bd
        DatosConexion datos = new DatosConexion();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConectaBD.getConnection(datos.getDRIVER(), datos.getURL(), datos.getUSERNAME(), datos.getPASSWORD(), datos.getBD());
            if (con != null) {
                String sql = "SELECT COUNT(*) AS num_coincidencias FROM usuarios WHERE usuario LIKE ? AND contraseña LIKE ?";
                pstmt = (PreparedStatement) con.prepareStatement(sql);
                if (pstmt != null) {
                    pstmt.setString(1, UsernameTextField.getText());
                    pstmt.setString(2, PasswordTextField.getText());
                    rs = pstmt.executeQuery();
                    if (rs != null) {
                        if (rs.next()) {
                            numCoincidencias = rs.getInt("num_coincidencias");
                        }
                        rs.close();
                    }
                    pstmt.close();
                }
                con.close();
            }

        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void CreateAccountFormOnAction() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
            Stage Register = new Stage();
            Register.initStyle(StageStyle.UNDECORATED);
            Register.setScene(new Scene(root, 600, 700));
            Register.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void DashBoardOnAction() {
        try {

            String username = UsernameTextField.getText();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent root = loader.load();
            DashboardController dashboardController = loader.getController();
            dashboardController.initData(username);
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            stage.close();
            Stage dashboardStage = new Stage();
            dashboardStage.initStyle(StageStyle.UNDECORATED);
            dashboardStage.setScene(new Scene(root, 965, 690));
            dashboardStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}