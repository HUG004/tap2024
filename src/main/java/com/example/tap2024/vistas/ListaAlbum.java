package com.example.tap2024.vistas;

import com.example.tap2024.components.ButtonCell;
import com.example.tap2024.models.AlbumDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;

public class ListaAlbum extends Stage {
    private TableView<AlbumDAO> tbvAlbum;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaAlbum() {
        CrearUI();
        this.setTitle("Lista de Álbumes");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        tlbMenu = new ToolBar();
        Button btnAddAlbum = new Button("Agregar Álbum");
        btnAddAlbum.getStyleClass().add("button");  // Aplica estilo de botón
        btnAddAlbum.setOnAction(actionEvent -> new FormAlbum(tbvAlbum, null));
        tlbMenu.getItems().add(btnAddAlbum);

        CrearTable();
        vBox = new VBox(tlbMenu, tbvAlbum);
        escena = new Scene(vBox, 600, 400);
        escena.getStylesheets().add(getClass().getResource("/styles/Listas.CSS").toExternalForm());
    }

    private void CrearTable() {
        AlbumDAO objAlbum = new AlbumDAO();
        tbvAlbum = new TableView<>();
        tbvAlbum.getStyleClass().add("table");  // Aplica estilo de tabla

        TableColumn<AlbumDAO, String> tbcBanda = new TableColumn<>("Banda");
        tbcBanda.setCellValueFactory(new PropertyValueFactory<>("banda"));

        TableColumn<AlbumDAO, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<AlbumDAO, String> tbcAño = new TableColumn<>("Año de Salida");
        tbcAño.setCellValueFactory(new PropertyValueFactory<>("añoSalida"));

        TableColumn<AlbumDAO, byte[]> tbcImagen = new TableColumn<>("Imagen");
        tbcImagen.setCellValueFactory(new PropertyValueFactory<>("imagen"));

        tbcImagen.setCellFactory(col -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(byte[] imageData, boolean empty) {
                super.updateItem(imageData, empty);
                if (empty || imageData == null) {
                    setGraphic(null);
                } else {
                    Image img = new Image(new ByteArrayInputStream(imageData));
                    imageView.setImage(img);
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);
                    setGraphic(imageView);
                }
            }
        });

        TableColumn<AlbumDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(col -> new ButtonCell<>(
                "Editar",
                album -> new FormAlbum(tbvAlbum, album),
                null
        ));

        TableColumn<AlbumDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(col -> new ButtonCell<>(
                "Eliminar",
                null,
                album -> {
                    album.DELETE();
                    tbvAlbum.setItems(album.SELECTALL());
                }
        ));

        tbvAlbum.getColumns().addAll(tbcBanda, tbcNombre, tbcAño, tbcImagen, tbcEditar, tbcEliminar);
        tbvAlbum.setItems(objAlbum.SELECTALL());
    }

}