package com.example.tap2024;
import com.example.tap2024.models.Conexion;
import com.example.tap2024.vistas.Calculadora;
import com.example.tap2024.vistas.ListaClientes;
import com.example.tap2024.vistas.Loteria;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {

private BorderPane bdpPrincipal;
private MenuBar mnbPrincipal;
private Menu menCompetencia1, menCompetencia2, menSalir;
private MenuItem mitCalculadora, mitLoteria, mitSpotify;

public void CrearUI(){
mitCalculadora = new MenuItem("Calculadora");
mitCalculadora.setOnAction(event -> new Calculadora());

mitLoteria = new MenuItem("Loteria");
mitLoteria.setOnAction(event -> new Loteria());

mitSpotify = new MenuItem("Spotify");
mitSpotify.setOnAction(actionEvent -> new ListaClientes());

menCompetencia1 = new Menu("Competencia 1");
menCompetencia1.getItems().addAll(mitCalculadora, mitLoteria, mitSpotify);
mnbPrincipal = new MenuBar(menCompetencia1);
bdpPrincipal = new BorderPane();
bdpPrincipal.setTop(mnbPrincipal);
}

@Override
public void start(Stage stage) throws IOException {
//FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
CrearUI();
Scene scene = new Scene(bdpPrincipal, 320, 240);
scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
stage.setTitle("Hello!");
stage.setScene(scene);
stage.setMaximized(true);
stage.show();

    Conexion.CrearConexion();
}

public static void main(String... args) {
launch();
}
}