package com.example.tap2024.vistas;

import com.example.tap2024.components.ButtonCell;
import com.example.tap2024.models.ClienteDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaClientes extends Stage {

    private TableView<ClienteDAO> tbvClientes;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;
    public ListaClientes(){
        CrearUI();
        this.setTitle("Lista de clientes :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        tlbMenu = new ToolBar();
        Button btnAddCte = new Button("Agregar Cliente");
        btnAddCte.setOnAction(actionEvent -> new FormCliente(tbvClientes,null));
        tlbMenu.getItems().add(btnAddCte);

        CrearTable();
        vBox = new VBox(tlbMenu,tbvClientes);
        escena = new Scene(vBox,500,250);
    }

    private void CrearTable() {
        ClienteDAO objClt = new ClienteDAO();
        tbvClientes = new TableView<>();

        TableColumn<ClienteDAO, String> tbcNom = new TableColumn<>("Nombre");
        tbcNom.setCellValueFactory(new PropertyValueFactory<>("nomClt"));

        TableColumn<ClienteDAO, String> tbcTel = new TableColumn<>("Telefono");
        tbcTel.setCellValueFactory(new PropertyValueFactory<>("telClt"));

        TableColumn<ClienteDAO, String> tbcEmail = new TableColumn<>("Email");
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("emailClt"));

        TableColumn<ClienteDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(col -> new ButtonCell<>(
                "Editar",
                cliente -> new FormCliente(tbvClientes, cliente),  // Acción de edición
                cliente -> {
                    cliente.DELETE();  // Acción de eliminación
                    tbvClientes.setItems(cliente.SELECTALL());
                }
        ));

        TableColumn<ClienteDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(col -> new ButtonCell<>(
                "Eliminar",
                cliente -> new FormCliente(tbvClientes, cliente),  // Reusar acción de edición si quieres
                cliente -> {
                    cliente.DELETE();  // Acción de eliminación
                    tbvClientes.setItems(cliente.SELECTALL());
                }
        ));


        tbvClientes.getColumns().addAll(tbcNom,tbcTel,tbcEmail,tbcEditar,tbcEliminar);
        tbvClientes.setItems(objClt.SELECTALL());
    }
}