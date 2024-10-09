package com.example.tap2024.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Random;

public class BuscaMinas {
    private int gridSize = 10; // es el tamaño de la cuadrícula
    private int[][] grid; // matriz para las bombas
    private Button[][] btns; // botones para el campo minado
    private int bombCount;
    private int celdasDescubiertas;

    public BuscaMinas() {
        Stage stage = new Stage();
        VBox vbox = new VBox();
        TextField txtBombCount = new TextField();
        Button btnCreate = new Button("Crear campo minado");
        btnCreate.setOnAction(actionEvent -> {
            bombCount = Integer.parseInt(txtBombCount.getText());
            celdasDescubiertas = 0; // reiniciar el contador de celdas descubiertas
            createMineField(bombCount);
            stage.setScene(createGameScene());
        });
        vbox.getChildren().addAll(txtBombCount, btnCreate);
        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void createMineField(int bombCount) { // crear campo de minas
        grid = new int[gridSize][gridSize];
        btns = new Button[gridSize][gridSize];
        Random random = new Random();

        for (int i = 0; i < bombCount; i++) { // colocar bombas aleatorias en el campo
            int fila, columna;
            do {
                fila = random.nextInt(gridSize);
                columna = random.nextInt(gridSize);
            } while (grid[fila][columna] == 1); // para que no se coloquen más de una bomba en la misma casilla
            grid[fila][columna] = 1; // indica una bomba
        }
    }

    private Scene createGameScene() {
        GridPane gridPane = new GridPane();

        for (int fila = 0; fila < gridSize; fila++) {
            for (int columna = 0; columna < gridSize; columna++) {
                Button button = new Button();
                button.setMinSize(40, 40);
                btns[fila][columna] = button;

                final int filaActual = fila;
                final int columnaActual = columna;

                // Con el clic con el botón izquierdo (evento) descubre la casilla
                button.setOnAction(event -> ClickIzquierdo(filaActual, columnaActual));

                button.setOnMouseClicked(event -> { // Evento con el clic derecho para marcar la casilla
                    if (event.isSecondaryButtonDown()) {
                        ClickDerecho(button);
                    }
                });

                gridPane.add(button, columna, fila); // respetar la posición correcta en la cuadrícula
            }
        }
        return new Scene(gridPane);
    }

    private void ClickIzquierdo(int fila, int columna) {
        if (grid[fila][columna] == 1) {
            btns[fila][columna].setText("B"); // lógica para terminar el juego como derrota
            showAlert("Has perdido ):","Descubriste una bomba.");
            endgame();
        } else {
            int bombasAdjacentes = countBombasAdjacentes(fila, columna);
            btns[fila][columna].setText(String.valueOf(bombasAdjacentes)); // lógica para manejar el caso cuando hay 0 bombas adyacentes
            btns[fila][columna].setDisable(true); // desactiva el botón ya descubierto
            celdasDescubiertas++;
            checarCondicionVictoria();
        }
    }

    private int countBombasAdjacentes(int fila, int columna) {
        int count = 0;
        // contar bombas adyacentes en las 8 casillas posibles
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newFila = fila + i;
                int newCol = columna + j;
                if (newFila >= 0 && newFila < gridSize && newCol >= 0 && newCol < gridSize) {
                    if (grid[newFila][newCol] == 1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private void ClickDerecho(Button button) {
        if (button.getText().equals("M")) {
            button.setText(""); // desmarca casilla
        } else {
            button.setText("M"); // marcar casilla
        }
    }

    private void checarCondicionVictoria() {
        // método para verificar victoria cuando se descubren todas las casillas donde no hay bombas
        int totalCells = gridSize * gridSize;
        if (celdasDescubiertas == totalCells - bombCount) { // quitar punto y coma aquí
            showAlert("¡Felicidades!", "Has ganado el juego");
            endgame();
        }
    }

    private void showAlert(String Titulo, String msj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Titulo);
        alert.setHeaderText(null);
        alert.setContentText(msj);
        alert.showAndWait();
    }

    private void endgame() { // desactiva todos los botones para terminar el juego
        for (int fila = 0; fila < gridSize; fila++) {
            for (int columnas = 0; columnas < gridSize; columnas++) {
                btns[fila][columnas].setDisable(true); // desactiva botones
            }
        }
    }
}