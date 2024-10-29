package com.example.tap2024.vistas;

import com.example.tap2024.models.CancionDAO;
import com.example.tap2024.models.ClienteDAO;
import com.example.tap2024.models.VentaDAO;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewCliente extends Stage {
    private TableView<CancionDAO> tbvCanciones;
    private VBox vBox;
    private Scene escena;
    private Button btnVerHistorial, btnVerDatosPersonales, btnAgregarVenta;
    private ClienteDAO clienteActual;

    public ViewCliente(ClienteDAO cliente) {
        this.clienteActual = cliente;  // Guardamos el cliente que inició sesión
        CrearUI();
        this.setTitle("Mi Música - Bienvenido " + cliente.getNomClt());
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvCanciones = new TableView<>();
        CrearTable();

        btnAgregarVenta = new Button("Agregar Compra");
        btnAgregarVenta.setOnAction(event -> mostrarDialogoCompra());

        btnVerHistorial = new Button("Ver Historial de Compras");
        btnVerDatosPersonales = new Button("Ver Datos Personales");

        btnVerHistorial.setOnAction(event -> verHistorialCompras());
        btnVerDatosPersonales.setOnAction(event -> verDatosPersonales());

        vBox = new VBox(10, btnAgregarVenta, tbvCanciones, btnVerHistorial, btnVerDatosPersonales);
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Historial de compras del usuario.");
        alert.showAndWait();
    }

    private void verDatosPersonales() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Nombre: " + clienteActual.getNomClt() +
                        "\nCorreo: " + clienteActual.getEmailClt());
        alert.showAndWait();
    }

    private void mostrarDialogoCompra() {
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.setTitle("Selecciona una Canción");

        ComboBox<CancionDAO> cbCanciones = new ComboBox<>(new CancionDAO().SELECTALL());

        Button btnConfirmarCompra = new Button("Confirmar Compra");
        btnConfirmarCompra.setOnAction(event -> {
            CancionDAO cancionSeleccionada = cbCanciones.getSelectionModel().getSelectedItem();
            if (cancionSeleccionada != null) {
                realizarCompra(cancionSeleccionada);
                dialogo.close();
            } else {
                new Alert(Alert.AlertType.WARNING, "Selecciona una canción.").showAndWait();
            }
        });

        VBox vboxDialogo = new VBox(10, new Label("Elige una canción:"), cbCanciones, btnConfirmarCompra);
        dialogo.setScene(new Scene(vboxDialogo, 300, 150));
        dialogo.showAndWait();
    }

    private void realizarCompra(CancionDAO cancionSeleccionada) {
        VentaDAO nuevaVenta = new VentaDAO();
        nuevaVenta.setIdCliente(clienteActual.getIdClt());
        nuevaVenta.setPrecio(cancionSeleccionada.getCostoCancion());

        if (nuevaVenta.INSERT() > 0) {
            new Alert(Alert.AlertType.INFORMATION, "Compra realizada con éxito.").showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error al realizar la compra.").showAndWait();
        }
    }
}