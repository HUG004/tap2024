package com.example.tap2024.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Loteria extends Stage {

    private HBox hBoxMain, hBoxButtons;
    private VBox vBoxTablilla, vBoxMazo;
    private Button btnAnt, btnSig, btnIni, btnFin;
    private Label lbTimer;
    private GridPane gdTab;
    private ImageView imMazo;
    private Scene escena;
    private String[] arrImages = {
            "/images/l1.png", "/images/l2.png", "/images/l3.png", "/images/l4.png",
            "/images/l5.png", "/images/l6.png", "/images/l7.png", "/images/l8.png",
            "/images/l9.png", "/images/l10.png", "/images/l11.png", "/images/l12.png",
            "/images/l13.png", "/images/l14.png", "/images/l15.png", "/images/l16.png",
            "/images/l17.png", "/images/l18.png", "/images/l19.png", "/images/l20.png",
            "/images/l21.png", "/images/l22.png", "/images/l23.png", "/images/l24.png",
            "/images/l25.png", "/images/l26.png", "/images/l27.png", "/images/l28.png",
            "/images/l29.png", "/images/l30.png", "/images/l31.png", "/images/l32.png",
            "/images/l33.png", "/images/l34.png", "/images/l35.png", "/images/l36.png",
            "/images/l37.png", "/images/l38.png", "/images/l39.png", "/images/l40.png",
            "/images/l41.png", "/images/l42.png", "/images/l43.png", "/images/l44.png",
            "/images/l45.png", "/images/l46.png", "/images/l47.png", "/images/l48.png",
            "/images/l49.png", "/images/l50.png", "/images/l51.png", "/images/l52.png",
            "/images/l53.png", "/images/l54.png"
    };
    private Button[][] arTab;
    private Panel pnlMain;
    private int currentTab = 0;
    private boolean juegoActivo = false;
    private Timeline timer; // Cambiado a variable de instancia
    private int tiempoRestante; // Tiempo en segundos

    public Loteria() {
        CrearUI();
        this.setTitle("Lotería");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView imAnt = new ImageView(new Image(getClass().getResource("/images/last.png").toString()));
        imAnt.setFitWidth(50);
        imAnt.setFitHeight(50);

        ImageView imSig = new ImageView(new Image(getClass().getResource("/images/NEXT.png").toString()));
        imSig.setFitWidth(50);
        imSig.setFitHeight(50);

        arTab = new Button[5][16]; // 5 tablillas, 4x4 buttons each
        for (int k = 0; k < 5; k++) {
            GridPane gdTabk = new GridPane();
            CrearTablilla(gdTabk, k);
            if (k == 0) {
                gdTab = gdTabk;
            }
        }

        btnAnt = new Button();
        btnAnt.setGraphic(imAnt);
        btnAnt.setOnAction(e -> {
            currentTab = (currentTab - 1 + 5) % 5;
            actualizarTab();
        });

        btnSig = new Button();
        btnSig.setGraphic(imSig);
        btnSig.setOnAction(e -> {
            currentTab = (currentTab + 1) % 5;
            actualizarTab();
        });

        btnFin = new Button("Finalizar juego");
        btnFin.setOnAction(e -> reiniciarJuego());

        btnIni = new Button("Iniciar juego");
        btnIni.getStyleClass().addAll("btn-sm", "btn-danger");
        btnIni.setOnAction(e -> iniciarJuego());

        hBoxButtons = new HBox(btnAnt, btnSig, btnFin, btnIni);
        vBoxTablilla = new VBox(gdTab, hBoxButtons);

        CrearMazo();

        hBoxMain = new HBox(vBoxTablilla, vBoxMazo);
        pnlMain = new Panel("Lotería Mexicana");
        pnlMain.getStyleClass().add("panel-success");
        pnlMain.setBody(hBoxMain);
        hBoxMain.setSpacing(80);
        hBoxMain.setPadding(new Insets(30));
        escena = new Scene(pnlMain, 900, 800);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        escena.getStylesheets().add(getClass().getResource("/styles/Loteria.css").toString());
    }

    private void CrearMazo() {
        Image imgMazo = new Image(getClass().getResource("/images/dorso.png").toString());
        lbTimer = new Label("00:00");
        imMazo = new ImageView(imgMazo);
        imMazo.setFitHeight(450);
        imMazo.setFitWidth(300);
        vBoxMazo = new VBox(lbTimer, imMazo);
    }

    private void iniciarJuego() {
        if (juegoActivo) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Juego ya iniciado");
            alert.setHeaderText("El juego ya está en progreso");
            alert.setContentText("No puedes iniciar un nuevo juego mientras otro está en progreso.");
            alert.showAndWait();
            return;
        }

        juegoActivo = true;
        tiempoRestante = 5; // Reiniciar el tiempo a 5 segundos
        lbTimer.setText("00:05");

        List<String> cartas = new ArrayList<>(Arrays.asList(arrImages));
        Collections.shuffle(cartas);

        new Thread(() -> {
            for (String carta : cartas) {
                if (!juegoActivo) return; // Si el juego no está activo, salir

                mostrarCarta(carta);
                try {
                    Thread.sleep(5000); // Esperar 5 segundos
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            verificarGanador();
        }).start();

        btnAnt.setDisable(true);
        btnSig.setDisable(true);

        // Iniciar el temporizador
        startTimer();
    }

    private void startTimer() {
        // Detener cualquier temporizador anterior
        if (timer != null) {
            timer.stop();
        }

        // Configurar el temporizador
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            tiempoRestante--;
            lbTimer.setText(String.format("00:%02d", tiempoRestante));

            if (tiempoRestante <= 0) {
                timer.stop();
                // Acciones al finalizar el tiempo, por ejemplo, mostrar un mensaje
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "¡Tiempo terminado!");
                alert.setTitle("Fin del tiempo");
                alert.showAndWait();
                reiniciarJuego(); // Reiniciar el juego al terminar el tiempo
            }
        }));

        timer.setCycleCount(5); // Contar hasta 5 segundos
        timer.play();
    }

    private void mostrarCarta(String carta) {
        // Convertir la carta a un índice
        int index = Integer.parseInt(carta.replaceAll("[^0-9]", "")) - 1; // Asegúrate de que carta sea el número de la carta

        // Verificar que el índice esté dentro de los límites
        if (index >= 0 && index < arrImages.length) {
            // Cargar la imagen
            Image image = new Image(getClass().getResourceAsStream(arrImages[index]));
            // Establecer la imagen en imMazo
            imMazo.setImage(image);
        } else {
            System.out.println("Error: Índice de carta fuera de límites: " + carta);
        }
    }

    private void verificarGanador() {
        if (!juegoActivo) return;

        boolean todasMarcadas = Arrays.stream(arTab[currentTab]).allMatch(btn -> btn.isDisabled());

        Platform.runLater(() -> {
            String mensaje = todasMarcadas ? "¡Ganaste!" : "¡Perdiste!";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, mensaje);
            alert.setTitle("Resultado");
            alert.showAndWait();
        });

        // Reiniciar el juego al finalizar
        reiniciarJuego();
    }

    private void reiniciarJuego() {
        juegoActivo = false; // Terminar el juego actual
        for (int k = 0; k < 5; k++) {
            for (Button btn : arTab[k]) {
                btn.setDisable(false); // Habilitar todos los botones
                btn.setVisible(true); // Asegurarse de que sean visibles
            }
        }
        lbTimer.setText("00:00"); // Reiniciar el temporizador
        if (timer != null) {
            timer.stop(); // Detener el temporizador si está activo
        }
        btnAnt.setDisable(false); // Habilitar botones de navegación
        btnSig.setDisable(false);
    }

    private void CrearTablilla(GridPane gdTab, int k) {
        // Lista de imágenes que se utilizarán en la tablilla
        List<String> imageList = new ArrayList<>(Arrays.asList(arrImages));
        Collections.shuffle(imageList); // Barajar las imágenes

        for (int i = 0; i < 4; i++) { // Iterar sobre las filas
            for (int j = 0; j < 4; j++) { // Iterar sobre las columnas
                // Crear el botón
                Button btn = new Button();

                // Crear el ImageView con la imagen de la carta
                ImageView imageView = new ImageView(new Image(getClass().getResource(imageList.get(i * 4 + j)).toString()));

                // Ajustar el tamaño de la imagen
                imageView.setFitHeight(80); // Cambia este valor según lo necesites
                imageView.setFitWidth(60);   // Cambia este valor según lo necesites

                // Establecer la imagen en el botón
                btn.setGraphic(imageView);
                btn.setOnAction(e -> {
                    btn.setDisable(true); // Deshabilitar el botón al ser seleccionado
                });

                // Guardar el botón en el array
                arTab[k][i * 4 + j] = btn;
                // Añadir botón al GridPane en la posición correspondiente
                gdTab.add(btn, j, i);
            }
        }
    }


    private void actualizarTab() {
        // Actualizar la tablilla visible
        for (int k = 0; k < 5; k++) {
            arTab[k][0].setVisible(k == currentTab);
        }
    }
}
