package com.example.tap2024.vistas;

import com.example.tap2024.components.ReportePDF;
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
    private Button btn_Clt, btn_Art, btn_Alb, btn_Ven,btn_Gen, btn_Can,btn_AxC,btnAlb_C,btnV_C, btnEstadisticas, btnRepTotal;
    private Button btnCerrarSesion;

    public Spotify() {
        CrearUI();
        this.setTitle("Spotify");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        btnCerrarSesion = new Button("Cerrar Sesión");
        btnCerrarSesion.setOnAction(event -> cerrarSesion());

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

        btnEstadisticas = new Button("Estadisticas");
        btnEstadisticas.setOnAction(actionEvent -> new EstadisticasVentas());
        btnRepTotal = new Button("Reporte total de artistas y canciones");
        btnRepTotal.setOnAction(actionEvent -> new ReportePDF());

        lbl_spoti = new Label("Tablas");

        hbox = new HBox(10); // Espacio de 10 entre botones
        hbox.getChildren().addAll(btn_Clt, btn_Art, btn_Ven,btn_Gen,btn_Alb,btn_Can,btn_AxC,btnAlb_C,btnV_C,btnEstadisticas, btnRepTotal);

        vbox = new VBox(20); // Espacio de 20 entre componentes del VBox
        vbox.getChildren().addAll(lbl_spoti, hbox, btnCerrarSesion);

        escena = new Scene(vbox, 1150, 200); // Ajustamos el tamaño de la ventana
        escena.getStylesheets().add(getClass().getResource("/styles/Listas.CSS").toExternalForm());
    }
    private void cerrarSesion() {
        new Login(); // Abrir la ventana de Login
        this.close(); // Cerrar la ventana actual
    }
}