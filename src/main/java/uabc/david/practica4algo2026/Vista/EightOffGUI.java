package uabc.david.practica4algo2026.Vista;

import uabc.david.practica4algo2026.Controlador.JuegoEightOff;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class EightOffGUI extends Application {
    private JuegoEightOff controlador;

    private Button botonNuevo;
    private Button botonDeshacer;
    private Button botonPista;
    private Label mensaje;

    @Override
    public void start(Stage escenario) {
        controlador = new JuegoEightOff();

        botonNuevo = new Button("Nuevo juego");
        botonNuevo.getStyleClass().add("boton-menu");
        botonDeshacer = new Button("Deshacer");
        botonDeshacer.getStyleClass().add("boton-menu");
        botonPista = new Button("Pista");
        botonPista.getStyleClass().add("boton-menu");

        mensaje = new Label();
        mensaje.setWrapText(true);
        mensaje.setMaxWidth(160);

        botonNuevo.setOnAction(e -> {

        });
        botonDeshacer.setOnAction(e -> {

        });
        botonPista.setOnAction(e -> {

        });

        VBox panelDerecho = new VBox(10, botonNuevo, botonDeshacer,
                botonPista, mensaje);
        panelDerecho.setPadding(new Insets(20, 20, 20, 20));

        BorderPane raiz = new BorderPane();
        raiz.setRight(panelDerecho);
        raiz.getStyleClass().add("fondo");

        Scene escena = new Scene(raiz, 1040, 620);
        URL estilo = getClass().getResource("/estilos.css");
        if (estilo != null) {
            escena.getStylesheets().add(estilo.toExternalForm());
        } else {
            System.err.println("No se encontró estilo.css en los recursos.");
        }

        escenario.setTitle("Eight Off");
        escenario.setScene(escena);
        escenario.setResizable(false);
        escenario.show();
    }
}
