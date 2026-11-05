package uabc.david.practica4algo2026.Vista;

import uabc.david.practica4algo2026.Modelo.CartaInglesa;
import uabc.david.practica4algo2026.Modelo.Fundacion;
import uabc.david.practica4algo2026.Modelo.ZonaTablero;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * Esta clase dibuja una fundación, es decir,
 * una celda vacía o únicamente su carta más alta.
 */
public class VistaFundacion {
    private VistaCarta cartas;
    private ZonaTablero zona;
    private Pane nodo;

    public VistaFundacion(VistaCarta cartas, EightOffGUI gui, int indice) {
        this.cartas = cartas;
        this.zona = new ZonaTablero("FUNDACION", indice);

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
     * Borra y vuelve a dibujar la fundación (solo se ve la carta de arriba).
     */
    public void dibujar(Fundacion fundacion) {
        nodo.getChildren().clear();
        CartaInglesa tope = fundacion.getCartaSuperior();
        if (tope == null) {
            nodo.getChildren().add(cartas.crearContornoVacio());
            Text marca = new Text(" ");
            marca.setStyle("-fx-fill: rgba(255,255,255,0.35); -fx-font-size: 34px; -fx-font-weight: bold;");
            StackPane panelMarca = new StackPane(marca);
            panelMarca.setMinSize(cartas.getAnchoCarta(), cartas.getAltoCarta());
            panelMarca.setPrefSize(cartas.getAnchoCarta(), cartas.getAltoCarta());
            panelMarca.setMouseTransparent(true);
            nodo.getChildren().add(panelMarca);
        } else {
            nodo.getChildren().add(cartas.crearCarta(tope));
        }
    }

    /**
     * Agrega un contorno de resaltado alrededor de la fundación (selección o destino de pista).
     */
    public void resaltar(String estiloCss) {
        nodo.getChildren().add(cartas.crearContorno(0, cartas.getAltoCarta(), estiloCss));
    }

    public Pane getNodo() {
        return nodo;
    }
}
