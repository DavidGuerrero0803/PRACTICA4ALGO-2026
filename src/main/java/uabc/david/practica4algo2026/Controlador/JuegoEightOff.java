package uabc.david.practica4algo2026.Controlador;

import uabc.david.practica4algo2026.Modelo.*;

public class JuegoEightOff {
    private Cascada[] cascadas = new Cascada[8];
    private Fundacion[] fundaciones = new Fundacion[4];
    private CeldaLibre[] celdas = new CeldaLibre[8];
    private ListaSimple<Movimiento> historial;
    private Mazo mazo;

    private boolean reyesEnCeldaVacia;

    public JuegoEightOff() {
        iniciarJuego();
    }

    /**
     * Reparte una partida nueva: 4 cartas a las primeras 4 celdas y
     * 6 cartas a cada una de las 8 cascadas (4 + 48 = 52). Todas boca arriba.
     */
    public void iniciarJuego() {
        mazo = new Mazo();
        historial = new ListaSimple<>();

    }

}
