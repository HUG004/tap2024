package com.example.tap2024.vistas;

import com.example.tap2024.models.CancionDAO;
import com.example.tap2024.models.GeneroDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormCancion extends Stage {
    private Scene scene;
    private TextField txtNombre;
    private TextField txtCostoCancion;
    private ComboBox<GeneroDAO> comboGenero;  // ComboBox para seleccionar el género
    private Button btnGuardar;
    private VBox vbox;
    private CancionDAO objCan;
    private TableView<CancionDAO> tbvCancion;

    public FormCancion(TableView<CancionDAO> tbv, CancionDAO objC) {
        this.tbvCancion = tbv;
        CrearUI();
        if (objC != null) {
            this.objCan = objC;
            txtNombre.setText(objCan.getNombre());
            txtCostoCancion.setText(String.valueOf(objCan.getCostoCancion()));

            GeneroDAO generoSeleccionado = objCan.getGenero();
            comboGenero.getSelectionModel().select(generoSeleccionado);

            this.setTitle("Editar Canción");
        } else {
            this.objCan = new CancionDAO();
            this.setTitle("Agregar Canción");
        }
        this.setScene(scene);
        this.show();
    }



    private void CrearUI() {
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre de la canción");

        txtCostoCancion = new TextField();
        txtCostoCancion.setPromptText("Costo de la canción");

        // Crear ComboBox para géneros y cargarlo con los géneros disponibles
        comboGenero = new ComboBox<>();
        comboGenero.setItems(FXCollections.observableArrayList(new GeneroDAO().SELECTALL()));
        comboGenero.setPromptText("Seleccione un género");

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> GuardarCancion());

        vbox = new VBox(txtNombre, txtCostoCancion, comboGenero, btnGuardar);  // Agregar ComboBox a la vista
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        scene = new Scene(vbox, 300, 200);  // Aumentar tamaño de la ventana para acomodar el ComboBox
    }

    private void GuardarCancion() {
        objCan.setNombre(txtNombre.getText());
        objCan.setCostoCancion(Float.parseFloat(txtCostoCancion.getText()));

        // Asignar el género seleccionado a la canción
        GeneroDAO generoSeleccionado = comboGenero.getSelectionModel().getSelectedItem();
        if (generoSeleccionado != null) {
            objCan.setGeneroID(generoSeleccionado.getTipoGenero());  // Almacena Tipo_Genero como String
        }

        String msj;
        Alert.AlertType type;

        if (!objCan.esNuevo()) {
            objCan.UPDATE();
            msj = "Canción actualizada correctamente";
            type = Alert.AlertType.INFORMATION;
        } else {
            if (objCan.INSERT() > 0) {
                msj = "Canción insertada correctamente";
                type = Alert.AlertType.INFORMATION;
            } else {
                msj = "Ocurrió un error al insertar, intente de nuevo";
                type = Alert.AlertType.ERROR;
            }
        }

        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setContentText(msj);
        alerta.showAndWait();

        tbvCancion.setItems(objCan.SELECTALL());
        tbvCancion.refresh();
    }

}

