package uabc.david.practica4algo2026.Vista;

import uabc.david.practica4algo2026.Modelo.CartaInglesa;
import uabc.david.practica4algo2026.Modelo.Palo;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.Node;

import java.io.InputStream;

/**
 * Esta clase se encarga de cargar las 52 imágenes una vez,
 * en el constructor, y las guarda en un arreglo. Además de
 * crear los nodos que comparten todas las zonas.
 */
public class VistaCarta {
    private Image[] imagenes;
    private double anchoCarta;
    private double altoCarta;
    private int cartasCargadas;

    public VistaCarta() {
        anchoCarta = 80;
        altoCarta = 112;
        imagenes = new Image[52];
        cartasCargadas = 0;
        cargarCartas();
    }

    /**
     * Carga las imágenes que están en la ruta de archivo.
     */
    private void cargarCartas() {
        for (Palo palo : Palo.values()) {
            for (int valor = 2; valor <= 14; valor++) {
                String ruta = "/cartas/" + construirArchivo(valor, palo);
                InputStream entrada = getClass().getResourceAsStream(ruta);
                if (entrada == null) {
                    System.err.println("No se encontró la imagen: " + ruta);
                    continue;
                }
                Image imagen = new Image(entrada, anchoCarta * 2,
                        altoCarta * 2, false, true);
                imagenes[indice(valor, palo)] = imagen;
                cartasCargadas++;
            }
        }
    }

    /**
     * Auxiliar para regresar la posición de la carta en el arreglo.
     */
    private int indice(int valor, Palo palo) {
        return palo.ordinal() * 13 + (valor - 2);
    }

    /**
     * Construye el nombre del archivo.
     */
    private String construirArchivo(int valor, Palo palo) {
        String rango = switch (valor) {
            case 11 -> "Jack";
            case 12 -> "Reina";
            case 13 -> "Rey";
            case 14 -> "As";
            default -> String.valueOf(valor);
        };

        String nombrePalo = switch (palo) {
            case TREBOL -> "Treboles";
            case DIAMANTE -> "Diamantes";
            case CORAZON -> "Corazones";
            case PICA -> "Picas";
        };
        return rango + nombrePalo + ".png";
    }

    /**
     * Crea una carta con la imagen que ya está en memoria.
     */
    public Node crearCarta(CartaInglesa carta) {
        Image imagen = imagenes[indice(carta.getValor(), carta.getPalo())];
        Node nodo;
        ImageView vista = new ImageView(imagen);
        vista.setFitWidth(anchoCarta);
        vista.setFitHeight(altoCarta);
        vista.setSmooth(true);
        nodo = vista;
        return nodo;
    }
}
