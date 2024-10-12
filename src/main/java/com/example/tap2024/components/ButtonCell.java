package com.example.tap2024.components;

import com.example.tap2024.models.ClienteDAO;
import com.example.tap2024.vistas.FormCliente;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import java.util.Optional;

public class ButtonCell  extends TableCell<ClienteDAO, String> {
    Button btnCelda;
    public ButtonCell(String str){
        btnCelda = new Button(str);
        btnCelda.setOnAction(event ->EventoBoton(str) );
    }

    private void EventoBoton(String str) {
        ClienteDAO objCte = this.getTableView().getItems().get(this.getIndex()); //get regrea un objeto de una tabla en clientesDAO

        if (str.equals("Editar")) {
            new FormCliente(this.getTableView(), objCte);
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setContentText("Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> opcion = alert.showAndWait();
            if (opcion.get()==ButtonType.OK){
                objCte.DELETE();
                this.getTableView().setItems(objCte.SELECTALL());
                this.getTableView().refresh();
            }
        }
    }

    @Override
    protected void updateItem(String s, boolean b) {
        super.updateItem(s, b);
        if (!b ){
            this.setGraphic(btnCelda);
        }
    }
}
