package com.example.tap2024.vistas;

import com.example.tap2024.components.ButtonCell;
import com.example.tap2024.models.ArtistaDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaArt extends Stage {

    private TableView<ArtistaDAO> tbvArtistas;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaArt() {
        CrearUI();
        this.setTitle("Lista de Artistas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        Button btnAddArt = new Button("Agregar Artista");
        btnAddArt.getStyleClass().add("button");  // Aplica estilo de botón
        btnAddArt.setOnAction(actionEvent -> new FormArt(tbvArtistas, null));
        tlbMenu.getItems().add(btnAddArt);

        CrearTable();
        vBox = new VBox(tlbMenu, tbvArtistas);
        escena = new Scene(vBox, 500, 250);
        escena.getStylesheets().add(getClass().getResource("/styles/Listas.CSS").toExternalForm());
    }

    private void CrearTable() {
        ArtistaDAO objArt = new ArtistaDAO();
        tbvArtistas = new TableView<>();
        tbvArtistas.getStyleClass().add("table");

        TableColumn<ArtistaDAO, String> tbcNom = new TableColumn<>("Nombre");
        tbcNom.setCellValueFactory(new PropertyValueFactory<>("nomArt"));

        TableColumn<ArtistaDAO, String> tbcCancion = new TableColumn<>("Canción");
        tbcCancion.setCellValueFactory(new PropertyValueFactory<>("cancion"));

        TableColumn<ArtistaDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(col -> new ButtonCell<>(
                "Editar",
                artista -> new FormArt(tbvArtistas, artista),
                artista -> {
                    artista.DELETE();
                    tbvArtistas.setItems(artista.SELECTALL());
                }
        ));

        TableColumn<ArtistaDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(col -> new ButtonCell<>(
                "Eliminar",
                artista -> new FormArt(tbvArtistas, artista),
                artista -> {
                    artista.DELETE();
                    tbvArtistas.setItems(artista.SELECTALL());
                }
        ));

        tbvArtistas.getColumns().addAll(tbcNom, tbcCancion, tbcEditar, tbcEliminar);
        tbvArtistas.setItems(objArt.SELECTALL());
    }
}