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
        botonDeshacer = new Button("Deshacer");
        botonPista = new Button("Pista");

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

        Scene escena = new Scene(raiz, 1040, 620);

        escenario.setTitle("Eight Off");
        escenario.setScene(escena);
        escenario.setResizable(false);
        escenario.show();
    }
}
