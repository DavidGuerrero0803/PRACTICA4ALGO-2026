package uabc.david.practica4algo2026.Vista;

import uabc.david.practica4algo2026.Modelo.ZonaTablero;
import uabc.david.practica4algo2026.Modelo.Cascada;

import javafx.scene.layout.Pane;
import javafx.scene.Node;

/**
 * Esta clase dibuja una cascada completa, en donde
 * cada carta cuenta con su separación vertical,
 * la capa oscura sobre las cartas que todavía no se pueden tomar
 * y los contornos de resaltado.
 */
public class VistaCascada {
    private ZonaTablero zona;
    private VistaCarta cartas;
    private EightOffGUI gui;
    private Pane nodo;

    private int cartasDibujadas;
    private double desplazamiento;
    private double separacionCartas;
    private double alturaCascada;

    public VistaCascada(VistaCarta cartas, EightOffGUI gui, int indice) {
        this.cartas = cartas;
        this.gui = gui;
        this.zona = new ZonaTablero("CASCADA", indice);

        separacionCartas = 28;
        alturaCascada = 440;
        cartasDibujadas = 0;
        desplazamiento = separacionCartas;

        nodo = new Pane();
        nodo.setMinSize(cartas.getAnchoCarta(), alturaCascada);
        nodo.setPrefSize(cartas.getAnchoCarta(), alturaCascada);
        nodo.setMaxSize(cartas.getAnchoCarta(), alturaCascada);
        nodo.setPickOnBounds(true);

        nodo.setOnMouseClicked(e -> {

        });
    }

    /**
     * Borra y vuelve a dibujar la cascada, de la carta que está más al fondo hasta la visible.
     */
    public void dibujar(Cascada cascada, int cuantasMovibles) {
        nodo.getChildren().clear();

        cartasDibujadas = cascada.contarCartas();
        desplazamiento = separacionCartas;
        if (cartasDibujadas > 1) {
            double maximo = (alturaCascada - cartas.getAltoCarta()) / (cartasDibujadas - 1);
            desplazamiento = Math.min(separacionCartas, maximo);
        }

        if (cartasDibujadas == 0) {
            nodo.getChildren().add(cartas.crearContornoVacio());
            return;
        }

        for (int posicion = 0; posicion < cartasDibujadas; posicion++) {
            int profundidad = cartasDibujadas - posicion;
            double y = posicion * desplazamiento;

            Node carta = cartas.crearCarta(cascada.getCarta(posicion));
            carta.setLayoutY(y);
            carta.setOnMouseClicked(e -> {

            });
            nodo.getChildren().add(carta);

            if (profundidad > cuantasMovibles) {
                nodo.getChildren().add(cartas.crearCapaOscura(y));
            }
        }
    }

    /**
     * Se encarga de resaltar en un solo contorno
     * que rodea las últimas cartas de la cascada.
     */
    public void resaltarGrupo(int cantidad, String claseCss) {
        double y = (cartasDibujadas - cantidad) * desplazamiento;
        double alto = (cantidad - 1) * desplazamiento + cartas.getAltoCarta();
        nodo.getChildren().add(cartas.crearContorno(y, alto, claseCss));
    }

    /**
     * Resalta el contorno de la carta visible en donde caería un movimiento.
     */
    public void resaltarDestino(String claseCss) {
        double y = 0;
        if (cartasDibujadas > 0) {
            y = (cartasDibujadas - 1) * desplazamiento;
        }
        nodo.getChildren().add(cartas.crearContorno(y, cartas.getAltoCarta(), claseCss));
    }

    public Pane getNodo() {
        return nodo;
    }
}
