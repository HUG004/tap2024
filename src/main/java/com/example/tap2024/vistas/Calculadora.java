package com.example.tap2024.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {

    private Button[][] arBtns;
    private Button btnClear;
    private TextField txtPantalla;
    private GridPane gdpTeclado;
    private VBox vBox;
    private Scene escena;
    private String[] strTeclas = {"7", "8", "9", "*", "4", "5", "6", "/", "1", "2", "3", "+", "0", ".", "=", "-"};
    private double num1 = 0; // Guardará el primer número de la operación
    private String operador = ""; // Guardará el operador (+, -, *, /)
    private boolean start = true; // Indica si se está iniciando una nueva operación
    private boolean resultadoMostrado  = false;// para que el resultado no se pueda ultilizar como valor para otra operacion

    // Constructor de la clase
    public Calculadora() {
        CrearUI();
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.show();
    }

    // Método para crear la interfaz de usuario
    private void CrearUI() {
        arBtns = new Button[4][4];
        txtPantalla = new TextField("0");
        txtPantalla.setAlignment(Pos.CENTER_RIGHT);
        txtPantalla.setEditable(false);
        gdpTeclado = new GridPane();
        CrearTeclado();

        btnClear = new Button("Clear");
        btnClear.setId("front-button");
        btnClear.setOnAction(event -> clearPantalla()); // Limpia la pantalla cuando se presiona 'Clear'

        vBox = new VBox(txtPantalla, gdpTeclado, btnClear);
        escena = new Scene(vBox, 200, 200);
        escena.getStylesheets().add(getClass().getResource("/styles/cal.css").toString());
    }

    // Método para crear el teclado de la calculadora
    private void CrearTeclado() {
        for (int i = 0; i < arBtns.length; i++) {
            for (int j = 0; j < arBtns.length; j++) {
                arBtns[j][i] = new Button(strTeclas[4 * i + j]);
                arBtns[j][i].setPrefSize(50, 50);
                arBtns[j][i].getStyleClass().add("button");
                int finalI = i;
                int finalJ = j;
                arBtns[j][i].setOnAction(event -> detectarTecla(strTeclas[4 * finalI + finalJ]));
                gdpTeclado.add(arBtns[j][i], j, i);
            }
        }
    }

    // Método para manejar la lógica cuando se presiona una tecla
    private void detectarTecla(String tecla) {
        if (tecla.matches("[0-9\\.]")) { // Si es un número o un punto
            if (start) {
                txtPantalla.clear(); // Limpia la pantalla si es el inicio de una nueva entrada
                start = false; // Indica que se ha comenzado a escribir un número
            }
            txtPantalla.appendText(tecla); // Añade el número o el punto a la pantalla
        } else if (tecla.matches("[\\+\\-\\*/]")) { // Si es un operador (+, -, *, /)
            if (!operador.isEmpty() && !start) { // Realiza la operación intermedia si ya hay un operador
                double num2 = Double.parseDouble(txtPantalla.getText());
                num1 = calcular(num1, num2, operador); // Realiza la operación intermedia
                txtPantalla.setText(String.valueOf(num1)); // Muestra el resultado intermedio
            } else {
                num1 = Double.parseDouble(txtPantalla.getText()); // Convierte el texto en la pantalla a un número y lo guarda en `num1`
            }
            operador = tecla; // Almacena el operador que se presionó
            start = true; // Indica que se espera la entrada del segundo número
        } else if (tecla.equals("=")) { // Si es el símbolo igual (=)
            if (!operador.isEmpty()) { // Solo realiza la operación si hay un operador definido
                double num2 = Double.parseDouble(txtPantalla.getText()); // Convierte el texto en la pantalla al segundo número (`num2`)
                double resultado = calcular(num1, num2, operador); // Llama al método `calcular` para obtener el resultado
                if (Double.isNaN(resultado)) { // Si el resultado es NaN, muestra un error
                    txtPantalla.setText("Error");
                } else {
                    txtPantalla.setText(String.valueOf(resultado)); // Muestra el resultado en la pantalla
                }
                start = true; // Prepara para una nueva operación
                operador = ""; // Resetea el operador después de la operación
            }
        }
    }


    // Método para realizar la operación matemática
    private double calcular(double num1, double num2, String operador) {
        switch (operador) {
            case "+":
                return num1 + num2; // Suma
            case "-":
                return num1 - num2; // Resta
            case "*":
                return num1 * num2; // Multiplicación
            case "/":
                if (num2 != 0) {
                    return num1 / num2; // División (si `num2` no es 0)
                } else {
                    return Double.NaN; // Retorna NaN para indicar un error en la división por cero
                }
            default:
                return Double.NaN; // Retorna NaN si el operador es desconocido
        }
    }

    // Método para limpiar la pantalla y reiniciar la calculadora
    private void clearPantalla() {
        txtPantalla.setText("0"); // Reinicia la pantalla a "0"
        start = true; // Indica que se está listo para una nueva entrada
        operador = ""; // Limpia el operador almacenado
        num1 = 0; // Reinicia el valor de `num1`
    }
}