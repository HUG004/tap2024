package com.example.tap2024.vistas;

import com.example.tap2024.models.ClienteDAO;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IngresoDatosCliente extends Stage {
    private TextField txtNombre, txtCorreo;
    private Button btnContinuar;
    private VBox vbox;
    private Scene escena;

    public IngresoDatosCliente() {
        crearUI();
        this.setTitle("Ingreso de Datos - Cliente");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");

        txtCorreo = new TextField();
        txtCorreo.setPromptText("Correo electrónico");

        btnContinuar = new Button("Continuar");
        btnContinuar.setOnAction(event -> abrirInterfazCliente());

        vbox = new VBox(10, new Label("Ingresa tus datos:"), txtNombre, txtCorreo, btnContinuar);
        escena = new Scene(vbox, 300, 200);
    }

    private void abrirInterfazCliente() {
        ClienteDAO cliente = buscarCliente(txtNombre.getText(), txtCorreo.getText());

        if (cliente != null) {
            new ViewCliente(cliente);  // Pasamos el cliente a la vista
            this.close();
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Cliente no encontrado");
            alerta.setContentText("No se encontró el cliente. Intenta de nuevo.");
            alerta.showAndWait();
        }
    }

    private ClienteDAO buscarCliente(String nombre, String correo) {
        ClienteDAO clienteDAO = new ClienteDAO();
        ObservableList<ClienteDAO> listaClientes = clienteDAO.SELECTALL();

        for (ClienteDAO cliente : listaClientes) {
            if (cliente.getNomClt().equalsIgnoreCase(nombre) && cliente.getEmailClt().equalsIgnoreCase(correo)) {
                return cliente;
            }
        }
        return null;
    }
}
