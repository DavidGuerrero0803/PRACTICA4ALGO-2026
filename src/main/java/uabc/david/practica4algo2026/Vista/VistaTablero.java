package uabc.david.practica4algo2026.Vista;

import uabc.david.practica4algo2026.Modelo.Cascada;
import uabc.david.practica4algo2026.Modelo.CeldaLibre;
import uabc.david.practica4algo2026.Modelo.Fundacion;
import uabc.david.practica4algo2026.Modelo.ZonaTablero;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Esta clase representa el tablero completo, gestionando la distribución
 * de los componentes visuales (cada uno responsable de su zona).
 */
public class VistaTablero {
    private VistaCascada[] cascadas;
    private VistaFundacion[] fundaciones;
    private VistaCeldaLibre[] celdas;
    private HBox raiz;

    public VistaTablero(VistaCarta cartas, EightOffGUI gui) {
        double espacio = 10;

        cascadas = new VistaCascada[8];
        fundaciones = new VistaFundacion[4];
        celdas = new VistaCeldaLibre[8];

        VBox columnaFundaciones = new VBox(espacio);
        for (int i = 0; i < 4; i++) {
            fundaciones[i] = new VistaFundacion(cartas, gui, i);
            columnaFundaciones.getChildren().add(fundaciones[i].getNodo());
        }

        HBox filaCeldas = new HBox(espacio);
        for (int i = 0; i < 8; i++) {
            celdas[i] = new VistaCeldaLibre(cartas, gui, i);
            filaCeldas.getChildren().add(celdas[i].getNodo());
        }

        HBox filaCascadas = new HBox(espacio);
        for (int i = 0; i < 8; i++) {
            cascadas[i] = new VistaCascada(cartas, gui, i);
            filaCascadas.getChildren().add(cascadas[i].getNodo());
        }

        VBox centro = new VBox(espacio, filaCeldas, filaCascadas);
        raiz = new HBox(30, columnaFundaciones, centro);
        raiz.setPadding(new Insets(20, 0, 20, 20));
    }

    /**
     * Cada componente borra y vuelve a dibujar su zona según el estado del juego.
     */
    public void dibujar(Cascada[] cascadasModelo,
                        CeldaLibre[] celdasModelo,
                        Fundacion[] fundacionesModelo,
                        int[] movibles) {
        for (int i = 0; i < 8; i++) {
            celdas[i].dibujar(celdasModelo[i]);
            cascadas[i].dibujar(cascadasModelo[i], movibles[i]);
        }
        for (int i = 0; i < 4; i++) {
            fundaciones[i].dibujar(fundacionesModelo[i]);
        }
    }

    /**
     * Contorno que rodea "cantidad" cartas desde la ubicación indicada (selección o pista).
     */
    public void resaltarGrupo(ZonaTablero origen, int cantidad, String claseCss) {
        if (origen.esCascada()) {
            cascadas[origen.getIndice()].resaltarGrupo(cantidad, claseCss);
        } else if (origen.esCelda()) {
            celdas[origen.getIndice()].resaltar(claseCss);
        } else {
            fundaciones[origen.getIndice()].resaltar(claseCss);
        }
    }

    /**
     * Contorno de la carta (o celda vacía) donde caería el movimiento de una pista.
     */
    public void resaltarDestino(ZonaTablero destino, String claseCss) {
        if (destino.esCascada()) {
            cascadas[destino.getIndice()].resaltarDestino(claseCss);
        } else if (destino.esCelda()) {
            celdas[destino.getIndice()].resaltar(claseCss);
        } else {
            fundaciones[destino.getIndice()].resaltar(claseCss);
        }
    }

    public Node getNodo() {
        return raiz;
    }
}
