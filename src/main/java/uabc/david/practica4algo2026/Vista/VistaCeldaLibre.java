package uabc.david.practica4algo2026.Vista;

import uabc.david.practica4algo2026.Modelo.CartaInglesa;
import uabc.david.practica4algo2026.Modelo.CeldaLibre;
import uabc.david.practica4algo2026.Modelo.ZonaTablero;

import javafx.scene.layout.Pane;

/**
 * Esta clase dibuja una celda libre en donde se ponen
 * las cartas a manera de reserva.
 */
public class VistaCeldaLibre {
    private VistaCarta cartas;
    private ZonaTablero zona;
    private Pane nodo;

    public VistaCeldaLibre(VistaCarta cartas, EightOffGUI gui, int indice) {
        this.cartas = cartas;
        this.zona = new ZonaTablero("CELDA", indice);

        nodo = new Pane();
        nodo.setMinSize(cartas.getAnchoCarta(), cartas.getAltoCarta());
        nodo.setPrefSize(cartas.getAnchoCarta(), cartas.getAltoCarta());
        nodo.setMaxSize(cartas.getAnchoCarta(), cartas.getAltoCarta());
        nodo.setPickOnBounds(true);
        nodo.setOnMouseClicked(e -> {
            gui.manejarClic(zona, 1);
        });
    }

    /**
     * Borra y vuelve a dibujar la celda según su estado actual.
     */
    public void dibujar(CeldaLibre celda) {
        nodo.getChildren().clear();
        CartaInglesa carta = celda.getCarta();
        if (carta == null) {
            nodo.getChildren().add(cartas.crearContornoVacio());
        } else {
            nodo.getChildren().add(cartas.crearCarta(carta));
        }
    }

    /**
     * Agrega un contorno de resaltado alrededor de la celda
     * (se usa para la selección o destino de pista).
     */
    public void resaltar(String estiloCss) {
        nodo.getChildren().add(cartas.crearContorno(0, cartas.getAltoCarta(), estiloCss));
    }

    public Pane getNodo() {
        return nodo;
    }
}
