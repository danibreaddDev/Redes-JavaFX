package com.di.ClienteRedesSociales;

import javax.mail.*;
import javax.mail.internet.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oovv.ConectaBD;
import oovv.DatosConexion;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RegisterController {
    @FXML
    private Button CloseButton;
    @FXML
    private Label RegistrationMessageLabel;
    @FXML
    private Label PasswordMatchLabel;
    @FXML
    private TextField PasswordTextField;
    @FXML
    private TextField ConfirmPasswordTextField;
    @FXML
    private TextField UsernameTextField;
    @FXML
    private TextField LastnameTextField;
    @FXML
    private TextField FirstnameTextField;
    @FXML
    private TextField EmailTextField;
    @FXML
    private TextField PhoneTextField;
    private String emailFrom = "";
    private String contraseña = "";
    private String emailTo;
    private String subject;
    private String content;
    private MimeMessage correo;
    private Properties properties;
    private Session session;

    public void CloseRegister(ActionEvent event) {

        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }

    public void RegisterButtonOnAction(ActionEvent event) {
        if (PasswordTextField.getText().equals(ConfirmPasswordTextField.getText())) {
            RegisterUser();
            GeneratePDF();
            CreateEmail();
            EnviarEmail();
            PasswordMatchLabel.setText("");
        } else {
            PasswordMatchLabel.setText("Password does not match");
        }
    }

    private void RegisterUser() {
        //conexion a bd
        DatosConexion datos = new DatosConexion();
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConectaBD.getConnection(datos.getDRIVER(), datos.getURL(), datos.getUSERNAME(), datos.getPASSWORD(), datos.getBD());
            if (con != null) {
                System.out.println("Conexion Establecida");
                String sql = "INSERT INTO usuarios (nombre,apellido,email,telefono,usuario,contraseña) VALUES(?,?,?,?,?,?)";
                pstmt = (PreparedStatement) con.prepareStatement(sql);
                pstmt.setString(1, FirstnameTextField.getText());
                pstmt.setString(2, LastnameTextField.getText());
                pstmt.setString(3, EmailTextField.getText());
                pstmt.setString(4, PhoneTextField.getText());
                pstmt.setString(5, UsernameTextField.getText());
                pstmt.setString(6, PasswordTextField.getText());
            }
            int filasInsertadas = pstmt.executeUpdate();
            if (filasInsertadas > 0) {
                RegistrationMessageLabel.setText("User has been registered successfully!");
            } else {
                RegistrationMessageLabel.setText("User has not been registered");
            }
            pstmt.close();
            con.close();

        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GeneratePDF() {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            String fontFile = "src/main/resources/assets/Roboto_Condensed/static/RobotoCondensed-Black.ttf";
            PDType0Font font = PDType0Font.load(document, new File(fontFile));
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(font, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("AQUI ESTA EL USUARIO: ");
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Nombre: " + FirstnameTextField.getText() + " Apellido: " + LastnameTextField.getText());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Usuario: " + UsernameTextField.getText() + " Contraseña: " + PasswordTextField.getText());
            contentStream.endText();
            contentStream.close();
            document.save("usuarioEstablecido.pdf");
            document.close();
            System.out.println("PDF generado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al generar el PDF: " + e.getMessage());
        }
    }

    private void CreateEmail() {
        properties = new Properties();
        emailTo = EmailTextField.getText();
        content = "Usuario/Contraseña";
        subject = "Usuario/Contraseña para acceder a LlamaTic (examenDI)";
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.ssl.trust", "smtp.office365.com");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.user", emailFrom);
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.setProperty("mail.smtp.auth", "true");
        session = Session.getDefaultInstance(properties);

        try {
            correo = new MimeMessage(session);
            correo.setFrom(new InternetAddress(emailFrom));
            correo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            correo.setSubject(subject);
            correo.setText(content, "ISO-8859-1");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void EnviarEmail() {
        try {
            // Create MimeBodyPart for the email content
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File("D:\\DAM\\ExamenDi\\usuarioEstablecido.pdf"));

            // Combinar las partes del mensaje
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachmentPart);

            // Establecer el contenido del mensaje
            correo.setContent(multipart);

            // Set Multipart as the content of the message
            correo.setContent(multipart);
            Transport transport = session.getTransport("smtp");
            transport.connect(emailFrom, contraseña);
            transport.sendMessage(correo, correo.getRecipients(Message.RecipientType.TO));
            transport.close();
            System.out.println("se ha enviado el correo");
        } catch (NoSuchProviderException e) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, e);
        } catch (MessagingException ez) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ez);
        } catch (IOException ex) {

        }

    }

}






