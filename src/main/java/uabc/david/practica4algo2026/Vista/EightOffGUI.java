package uabc.david.practica4algo2026.Vista;

import uabc.david.practica4algo2026.Controlador.JuegoEightOff;
import uabc.david.practica4algo2026.Modelo.ZonaTablero;
import uabc.david.practica4algo2026.Modelo.Movimiento;

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
    private ZonaTablero origenSeleccion;
    private VistaTablero tablero;
    private Movimiento pista;

    private Button botonNuevo;
    private Button botonDeshacer;
    private Button botonPista;
    private Label mensaje;

    private int cantidadSeleccion;

    @Override
    public void start(Stage escenario) {
        controlador = new JuegoEightOff();

        VistaCarta cartas = new VistaCarta();
        tablero = new VistaTablero(cartas, this);

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
        raiz.setCenter(tablero.getNodo());
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

        actualizarVista();
    }

    public void manejarClic(ZonaTablero zona, int profundidad) {
        if (controlador.isTerminado()) {
            return;
        }

        if (origenSeleccion != null) {
            ZonaTablero origen = origenSeleccion;
            int cantidad = cantidadSeleccion;

            if (zona.equals(origen) && profundidad == cantidad) {
                limpiarSeleccion();
                actualizarVista();
                return;
            }

            if (controlador.moverCartas(origen, zona, cantidad)) {
                limpiarSeleccion();
                limpiarPista();
                actualizarVista();
                return;
            }
        }

        if (controlador.puedeSeleccionar(zona, profundidad)) {
            origenSeleccion = zona;
            cantidadSeleccion = profundidad;
        } else {
            limpiarSeleccion();
        }
        actualizarVista();
    }

    private void limpiarSeleccion() {
        origenSeleccion = null;
        cantidadSeleccion = 0;
    }

    private void limpiarPista() {
        pista = null;
        mensaje.setText("");
    }

    /**
     * Vuelve a dibujar el tablero y refresca los botones y el mensaje de fin de partida.
     */
    private void actualizarVista() {
        int[] movibles = new int[8];
        for (int i = 0; i < 8; i++) {
            movibles[i] = controlador.cartasMovibles(i);
        }
        tablero.dibujar(controlador.getCascadas(), controlador.getCeldasLibres(), controlador.getFundaciones(), movibles);

        if (pista != null) {
            tablero.resaltarGrupo(pista.getOrigen(), pista.getCantidad(), "contorno-pista");
            tablero.resaltarDestino(pista.getDestino(), "contorno-pista");
        }
        if (origenSeleccion != null) {
            tablero.resaltarGrupo(origenSeleccion, cantidadSeleccion, "contorno-seleccion");
        }

        botonDeshacer.setDisable(!controlador.puedeDeshacer());
        botonPista.setDisable(!controlador.puedePedirPista());

        if (controlador.isTerminado()) {
            if (controlador.isVictoria()) {
                mensaje.setText("¡Ganaste! Presiona Nuevo juego para jugar otra vez.");
            } else {
                mensaje.setText("No hay más movimientos posibles. Presiona Nuevo juego para intentarlo otra vez.");
            }
        }
    }
}
