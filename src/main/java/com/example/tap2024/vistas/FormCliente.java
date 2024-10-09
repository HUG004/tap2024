package com.example.tap2024.vistas;

import com.example.tap2024.models.ClienteDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.security.PrivateKey;

public class FormCliente extends Stage {
    private TextField txtNomCte;
    private TextField txtEmailCte;
    private TextField txtTelCte;
    private Button btnGuardar;
    private VBox vBox;
    private ClienteDAO objCte;

    private Scene escena;
    public FormCliente(){
        objCte = new ClienteDAO();
        CrearUI();
        this.setTitle("Agregar Cliente");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        txtNomCte = new TextField();
        txtNomCte.setPromptText("Nombre del cliente");
        txtEmailCte = new TextField();
        txtEmailCte.setPromptText("Email del cliente");
        txtTelCte = new TextField();
        txtTelCte.setPromptText("Telefono del cliente");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> GuardarCliente());
        vBox = new VBox(txtNomCte,txtEmailCte,txtTelCte,btnGuardar);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        escena = new Scene(vBox,350,150);
    }

    private void GuardarCliente() {
        objCte.setEmailCte(txtEmailCte.getText());
        objCte.setNomCte(txtNomCte.getText());
        objCte.setTelCte(txtTelCte.getText());
        String msj;
        Alert.AlertType type;
        if (objCte.INSERT()>0){
            msj = "registro";
            type = Alert.AlertType.INFORMATION;
        }else {
            msj = "Ocurrio un error. Intente otra vez";
            type = Alert.AlertType.INFORMATION;
        }
        Alert alert = new Alert(type);
        alert.setTitle("Mensaje del sistema");
        alert.setContentText(msj);
        alert.showAndWait();
    }

}
