package com.example.tap2024.vistas;

import com.example.tap2024.components.ButtonCell;
import com.example.tap2024.models.CancionDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaCancion extends Stage {
    private TableView<CancionDAO> tbvCanciones;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaCancion() {
        CrearUI();
        this.setTitle("Lista de Canciones");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        Button btnAddCancion = new Button("Agregar Canción");
        btnAddCancion.getStyleClass().add("button");
        btnAddCancion.setOnAction(actionEvent -> new FormCancion(tbvCanciones, null));
        tlbMenu.getItems().add(btnAddCancion);

        CrearTable();
        vBox = new VBox(tlbMenu, tbvCanciones);
        escena = new Scene(vBox, 600, 300);
        escena.getStylesheets().add(getClass().getResource("/styles/Listas.CSS").toExternalForm());
    }

    private void CrearTable() {
        CancionDAO objCan = new CancionDAO();
        tbvCanciones = new TableView<>();

        TableColumn<CancionDAO, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<CancionDAO, Float> tbcCosto = new TableColumn<>("Costo Canción");
        tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costoCancion"));

        TableColumn<CancionDAO, String> tbcGenero = new TableColumn<>("Género");
        tbcGenero.setCellValueFactory(cellData -> {
            CancionDAO cancion = cellData.getValue();
            return cancion.getTipoGenero() != null ? new SimpleStringProperty(cancion.getTipoGenero()) : null;
        });

        TableColumn<CancionDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(col -> new ButtonCell<>(
                "Editar",
                cancion -> new FormCancion(tbvCanciones, cancion),
                cancion -> {
                    cancion.DELETE();
                    tbvCanciones.setItems(cancion.SELECTALL());
                }
        ));

        tbvCanciones.getColumns().addAll(tbcNombre, tbcCosto, tbcGenero, tbcEditar);
        tbvCanciones.setItems(objCan.SELECTALL());
    }

}