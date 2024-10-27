package com.example.tap2024.vistas;

import com.example.tap2024.models.AlbumDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FormAlbum extends Stage {

    private Scene scene;
    private TextField txtBanda, txtNombre, txtAño;
    private Button btnGuardar, btnSeleccionarImagen;
    private Label lblRutaImagen;
    private VBox vbox;
    private AlbumDAO objAlbum;
    private TableView<AlbumDAO> tbvAlbum;
    private String rutaImagen; // Ruta de la imagen seleccionada

    public FormAlbum(TableView<AlbumDAO> tbv, AlbumDAO objA) {
        this.tbvAlbum = tbv;
        CrearUI();

        // Verificar si es edición o creación de un nuevo álbum
        if (objA != null) {
            this.objAlbum = objA;
            txtBanda.setText(objAlbum.getBanda());
            txtNombre.setText(objAlbum.getNombre());
            txtAño.setText(objAlbum.getAñoSalida());
            lblRutaImagen.setText("Imagen seleccionada: " + objAlbum.getFoto()); // Mostrar ruta si existe
            this.setTitle("Editar Álbum");
        } else {
            this.objAlbum = new AlbumDAO();
            this.setTitle("Agregar Álbum");
        }

        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        txtBanda = new TextField();
        txtBanda.setPromptText("Banda");

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del Álbum");

        txtAño = new TextField();
        txtAño.setPromptText("Año de Salida");

        lblRutaImagen = new Label("Imagen no seleccionada");

        btnSeleccionarImagen = new Button("Seleccionar Imagen");
        btnSeleccionarImagen.setOnAction(event -> seleccionarImagen());

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> guardarAlbum());

        vbox = new VBox(10, txtBanda, txtNombre, txtAño, lblRutaImagen, btnSeleccionarImagen, btnGuardar);
        vbox.setPadding(new Insets(10));

        scene = new Scene(vbox, 300, 300);
    }

    private void seleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(this);

        if (file != null) {
            rutaImagen = file.toURI().toString();
            lblRutaImagen.setText("Imagen seleccionada: " + file.getName());
        }
    }

    private void guardarAlbum() {
        objAlbum.setBanda(txtBanda.getText());
        objAlbum.setNombre(txtNombre.getText());
        objAlbum.setAñoSalida(txtAño.getText());
        objAlbum.setFoto(rutaImagen); // Guardar la ruta de la imagen

        String msj;
        Alert.AlertType type;

        if (!objAlbum.esNuevo()) {
            objAlbum.UPDATE();
            msj = "Álbum actualizado correctamente";
            type = Alert.AlertType.INFORMATION;
        } else {
            if (objAlbum.INSERT() > 0) {
                msj = "Álbum insertado correctamente";
                type = Alert.AlertType.INFORMATION;
            } else {
                msj = "Error al insertar, intente de nuevo";
                type = Alert.AlertType.ERROR;
            }
        }

        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setContentText(msj);
        alerta.showAndWait();

        // Actualizar el TableView
        tbvAlbum.setItems(objAlbum.SELECTALL());
        tbvAlbum.refresh();
        this.close(); // Cerrar el formulario
    }
}
