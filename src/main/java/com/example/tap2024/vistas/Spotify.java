package com.example.tap2024.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Spotify extends Stage {

    private Scene escena;
    private VBox vbox;
    private HBox hbox;
    private Label lbl_spoti;
    private Button btn_Clt, btn_Art, btn_Alb, btn_Ven,btn_Gen, btn_Can,btn_AxC,btnAlb_C,btnV_C;

    public Spotify() {
        CrearUI();
        this.setTitle("Spotify");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        btn_Clt = new Button("Cliente");
        btn_Clt.setOnAction(actionEvent -> new ListaClientes());

        btn_Art = new Button("Artista");
        btn_Art.setOnAction(actionEvent -> new ListaArt());

        btn_Ven = new Button("Ventas");
        btn_Ven.setOnAction(actionEvent -> new ListaVentas());

        btn_Gen = new Button("Genero");
        btn_Gen.setOnAction(actionEvent -> new ListaGenero());

        btn_Alb = new Button("Album");
        btn_Alb.setOnAction(actionEvent -> new ListaAlbum());

        btn_Can = new Button("Canción");
        btn_Can.setOnAction(actionEvent -> new ListaCancion());

        btn_AxC = new Button("Relacion Artista - Cancion");
        btn_AxC.setOnAction(actionEvent -> new ListaArtistaCancion());

        btnAlb_C = new Button ("Relacion Album - Cancion");
        btnAlb_C.setOnAction(actionEvent -> new ListaAlbumCancion());

        btnV_C= new Button ("Relacion Venta - Cancion");
        btnV_C.setOnAction(actionEvent -> new ListaVentaCancion());

        lbl_spoti = new Label("Tablas");

        hbox = new HBox(10); // Espacio de 10 entre botones
        hbox.getChildren().addAll(btn_Clt, btn_Art, btn_Ven,btn_Gen,btn_Alb,btn_Can,btn_AxC,btnAlb_C,btnV_C);

        vbox = new VBox(20); // Espacio de 20 entre componentes del VBox
        vbox.getChildren().addAll(lbl_spoti, hbox);

        escena = new Scene(vbox, 900, 200); // Ajustamos el tamaño de la ventana
    }
}
