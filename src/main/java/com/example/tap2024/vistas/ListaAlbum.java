package com.example.tap2024.vistas;

import com.example.tap2024.components.ButtonCell;
import com.example.tap2024.models.AlbumDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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
        escena = new Scene(new VBox(), 600, 400);
        escena.getStylesheets().add(getClass().getResource("/styles/ListaAlbum.CSS").toExternalForm());

        tlbMenu = new ToolBar();
        Button btnAddAlbum = new Button("Agregar Álbum");
        Button btnCargarImagen = new Button("Cargar Imagen");
        btnCargarImagen.setOnAction(actionEvent -> cargarImagen());

        tlbMenu.getItems().addAll(btnAddAlbum, btnCargarImagen);

        CrearTable();
        vBox = new VBox(tlbMenu, tbvAlbum);
        vBox.getStyleClass().add("root"); // Aplicar estilo raíz
        escena.setRoot(vBox); // Establecer el contenedor como raíz de la escena
    }

    private AlbumDAO albumSeleccionado;

    private void cargarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(this); // Abrir diálogo para seleccionar imagen

        if (file != null) {
            String rutaImagen = file.getAbsolutePath(); // Obtener la ruta completa
            if (albumSeleccionado != null) { // Verifica que un álbum esté seleccionado
                albumSeleccionado.setFoto(rutaImagen); // Almacenar la ruta en el álbum
                albumSeleccionado.UPDATE(); // Actualizar la base de datos

                // Refrescar la tabla para mostrar el álbum con la nueva imagen
                tbvAlbum.setItems(new AlbumDAO().SELECTALL());
            } else {
                System.out.println("No se ha seleccionado ningún álbum.");
            }
        } else {
            System.out.println("No se seleccionó ninguna imagen.");
        }
    }



    private void CrearTable() {
        AlbumDAO objAlbum = new AlbumDAO();
        tbvAlbum = new TableView<>();
        tbvAlbum.getStyleClass().add("table");


        TableColumn<AlbumDAO, String> tbcBanda = new TableColumn<>("Banda");
        tbcBanda.setCellValueFactory(new PropertyValueFactory<>("banda"));

        TableColumn<AlbumDAO, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<AlbumDAO, String> tbcAño = new TableColumn<>("Año de Salida");
        tbcAño.setCellValueFactory(new PropertyValueFactory<>("añoSalida"));

        TableColumn<AlbumDAO, String> tbcImagen = new TableColumn<>("Imagen");
        tbcImagen.setCellValueFactory(new PropertyValueFactory<>("foto"));
        tbcImagen.setCellFactory(col -> new TableCell<AlbumDAO, String>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    try {
                        Image image = new Image(new File(item).toURI().toString()); // Cargar imagen desde la ruta correcta
                        imageView.setImage(image);
                    } catch (Exception e) {
                        System.out.println("Error al cargar la imagen: " + e.getMessage());
                        imageView.setImage(null); // En caso de error, dejar la imagen vacía.
                    }
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    setGraphic(imageView);
                }

            }
        });

        // Column for the Edit button
        TableColumn<AlbumDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(col -> new ButtonCell<>(
                "Editar",
                album -> {
                    albumSeleccionado = album; // Guardar el álbum seleccionado
                    new FormAlbum(tbvAlbum, album); // Abrir formulario de edición
                },
                null // No hay acción de eliminar aquí
        ));


        // Column for the Delete button
        TableColumn<AlbumDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(col -> new ButtonCell<>(
                "Eliminar",
                null, // No edit action in the delete column
                album -> {
                    album.DELETE();  // Delete action
                    tbvAlbum.setItems(album.SELECTALL());
                }
        ));

        tbvAlbum.getColumns().addAll(tbcBanda, tbcNombre, tbcAño,tbcImagen, tbcEditar, tbcEliminar);
        tbvAlbum.setItems(objAlbum.SELECTALL());
    }

}

