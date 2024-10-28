package com.example.tap2024.vistas;

import com.example.tap2024.models.ClienteDAO;
import com.example.tap2024.models.VentaDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaVentas extends Stage {

    private TableView<VentaDAO> tbvVentas;
    private VBox vBox;
    private Scene escena;

    public ListaVentas() {
        CrearUI();
        this.setTitle("Lista de Ventas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvVentas = new TableView<>();
        CrearTable();
        vBox = new VBox(tbvVentas);
        escena = new Scene(vBox, 500, 250);
        escena.getStylesheets().add(getClass().getResource("/styles/Listas.CSS").toExternalForm());

    }

    private void CrearTable() {
        VentaDAO objVenta = new VentaDAO();

        TableColumn<VentaDAO, Integer> tbcIdVenta = new TableColumn<>("ID Venta");
        tbcIdVenta.setCellValueFactory(new PropertyValueFactory<>("idVenta"));

        TableColumn<VentaDAO, Float> tbcPrecio = new TableColumn<>("Precio");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<VentaDAO, Integer> tbcIdCliente = new TableColumn<>("ID Cliente");
        tbcIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));

        tbvVentas.getColumns().addAll(tbcIdVenta, tbcPrecio, tbcIdCliente);
        tbvVentas.setItems(objVenta.SELECTALL());
    }
}