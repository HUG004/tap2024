package com.example.tap2024.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Loteria extends Stage {

    private HBox hBoxMain, hBoxButtons;
    private VBox vbxTablilla, vbxMazo;
    private Button btnAnterior, btnSiguiente, btnIniciar;
    private Label lblTimer;
    private GridPane gdpTablilla;
    private ImageView imvMazo;
    private Scene escena;
    private String[] arImages = {"loteria1.jpg", "loteria2.jpg", "loteria3.jpg", "loteria4.jpg", "loteria5.jpg", "loteria6.jpg", "loteria7.jpg", "loteria8.jpg", "loteria9.jpg", "loteria10.jpg",
            "loteria11.jpg", "loteria12.jpg", "loteria13.jpg", "loteria14.jpg", "loteria15.jpg", "loteria16.jpg", "loteria17.jpg", "loteria18.jpg", "loteria19.jpg", "loteria20.jpg", "loteria21.jpg",
            "loteria22.jpg", "loteria23.jpg", "loteria24.jpg", "loteria25.jpg", "loteria26.jpg", "loteria27.jpg", "loteria28.jpg", "loteria29.jpg", "loteria30.jpg", "loteria31.jpg", "loteria32.jpg",
            "loteria33.jpg", "loteria34.jpg", "loteria35.jpg", "loteria36.jpg", "loteria37.jpg", "loteria38.jpg", "loteria39.jpg", "loteria40.jpg", "loteria41.jpg", "loteria42.jpg", "loteria43.jpg",
            "loteria44.jpg", "loteria45.jpg", "loteria46.jpg", "loteria47.jpg", "loteria48.jpg", "loteria49.jpg", "loteria50.jpg", "loteria51.jpg", "loteria52.jpg", "loteria53.jpg", "loteria54.jpg"};
    private Button[][] arBtnTab;
    private Panel pnlPrincipal;
    private int currentTemplateIndex = 0;

    public Loteria() {
        CrearUI();
        this.setTitle("Loteria Mexicana :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView imvAnt, imvSig;
        imvAnt = new ImageView(new Image(getClass().getResource("/images/last.png").toString()));
        imvAnt.setFitHeight(50);
        imvAnt.setFitWidth(50);
        imvSig = new ImageView(new Image(getClass().getResource("/images/next.png").toString()));
        imvSig.setFitWidth(50);
        imvSig.setFitHeight(50);

        gdpTablilla = new GridPane();
        CrearTablilla();

        btnAnterior = new Button();
        btnAnterior.setGraphic(imvAnt);
        btnSiguiente = new Button();
        btnSiguiente.setGraphic(imvSig);

        btnAnterior.setOnAction(event -> mostrarPlantillaAnterior());
        btnSiguiente.setOnAction(event -> mostrarPlantillaSiguiente());

        hBoxButtons = new HBox(btnAnterior, btnSiguiente);
        vbxTablilla = new VBox(gdpTablilla, hBoxButtons);

        CrearMazo();

        hBoxMain = new HBox(vbxTablilla, vbxMazo);
        pnlPrincipal = new Panel("Loteria Mexicana :)");
        pnlPrincipal.getStyleClass().add("panel-success");
        pnlPrincipal.setBody(hBoxMain);
        hBoxMain.setSpacing(20);
        hBoxMain.setPadding(new Insets(20));
        escena = new Scene(pnlPrincipal, 800, 600);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        escena.getStylesheets().add(getClass().getResource("/styles/loteria.css").toExternalForm());
    }

    private void CrearMazo() {
        lblTimer = new Label("00:00");
        lblTimer.getStyleClass().add("timer-label");
        imvMazo = new ImageView(getClass().getResource("/images/dorso.jpg").toString());
        imvMazo.setFitHeight(450);
        imvMazo.setFitWidth(300);
        btnIniciar = new Button("Iniciar Juego");
        btnIniciar.getStyleClass().setAll("btn-sm", "btn-danger");
        vbxMazo = new VBox(lblTimer, imvMazo, btnIniciar);
        vbxMazo.setSpacing(10);

        btnIniciar.setOnAction(event -> iniciarTemporizador());
    }

    private void CrearTablilla() {
        arBtnTab = new Button[4][4];
        List<String> shuffledImages = Arrays.asList(arImages);
        Collections.shuffle(shuffledImages);

        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Image img = new Image(getClass().getResource("/images/" + shuffledImages.get(index)).toString());
                ImageView imv = new ImageView(img);
                imv.setFitWidth(70);
                imv.setFitHeight(120);
                arBtnTab[j][i] = new Button();
                arBtnTab[j][i].setGraphic(imv);
                arBtnTab[j][i].getStyleClass().add("card-button");

                // Acción para que el usuario marque la carta manualmente
                arBtnTab[j][i].setOnAction(event -> {
                    Button btn = (Button) event.getSource();
                    btn.getStyleClass().add("marked");
                    verificarVictoria(); // Verifica si ya ganó después de marcar una carta
                });

                gdpTablilla.add(arBtnTab[j][i], j, i);
                index++;
            }
        }
    }

    private void mostrarPlantillaAnterior() {
        currentTemplateIndex = (currentTemplateIndex - 1 + 5) % 5; // Controla que el índice no salga del rango
        actualizarTablilla();
    }

    private void mostrarPlantillaSiguiente() {
        currentTemplateIndex = (currentTemplateIndex + 1) % 5;
        actualizarTablilla();
    }

    private void actualizarTablilla() {
        CrearTablilla();
    }

    private void iniciarTemporizador() {
        btnIniciar.setDisable(true); // Deshabilitar botón al iniciar el juego
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private int segundos = 0;

            @Override
            public void run() {
                segundos++;
                int minutos = segundos / 60;
                int restoSegundos = segundos % 60;
                lblTimer.setText(String.format("%02d:%02d", minutos, restoSegundos));

                if (segundos % 5 == 0) {
                    javafx.application.Platform.runLater(() -> mostrarSiguienteCarta());
                }
            }
        }, 0, 1000); // Ejecuta cada 1 segundo
    }

    private void mostrarSiguienteCarta() {
        List<String> shuffledImages = Arrays.asList(arImages);
        Collections.shuffle(shuffledImages);
        String nextCard = shuffledImages.get(0);
        Image img = new Image(getClass().getResource("/images/" + nextCard).toString());
        imvMazo.setImage(img);
    }

    private void verificarVictoria() {
        boolean haGanado = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!arBtnTab[j][i].getStyleClass().contains("marked")) {
                    haGanado = false;
                    break;
                }
            }
        }
        if (haGanado) {
            javafx.application.Platform.runLater(() -> {
                new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "¡Has ganado!").showAndWait();
            });
        }
    }
}