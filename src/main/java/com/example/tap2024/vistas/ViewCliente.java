package com.example.tap2024.vistas;

import com.example.tap2024.models.CancionDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewCliente extends Stage {
    private TableView<CancionDAO> tbvCanciones;
    private VBox vBox;
    private Scene escena;
    private Button btnVerHistorial, btnVerDatosPersonales;

    public ViewCliente() {
        CrearUI();
        this.setTitle("Mi Música");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvCanciones = new TableView<>();
        CrearTable();

        btnVerHistorial = new Button("Ver Historial de Compras");
        btnVerDatosPersonales = new Button("Ver Datos Personales");

        btnVerHistorial.setOnAction(event -> verHistorialCompras());
        btnVerDatosPersonales.setOnAction(event -> verDatosPersonales());

        vBox = new VBox(10, tbvCanciones, btnVerHistorial, btnVerDatosPersonales);
        escena = new Scene(vBox, 600, 400);
        escena.getStylesheets().add(getClass().getResource("/styles/Listas.CSS").toExternalForm());

    }

    private void CrearTable() {
        CancionDAO objCan = new CancionDAO();

        TableColumn<CancionDAO, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<CancionDAO, Float> tbcCosto = new TableColumn<>("Costo Canción");
        tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costoCancion"));

        tbvCanciones.getColumns().addAll(tbcNombre, tbcCosto);
        tbvCanciones.setItems(objCan.SELECTALL());
    }

    private void verHistorialCompras() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Historial de Compras");
        alert.setHeaderText("Historial de Compras Realizadas");
        alert.setContentText("Aquí se mostrará el historial de compras del usuario.");
        alert.showAndWait();
    }

    private void verDatosPersonales() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Datos Personales");
        alert.setHeaderText("Datos del Usuario");
        alert.setContentText("Aquí se mostrarán los datos personales del usuario.");
        alert.showAndWait();
    }
}