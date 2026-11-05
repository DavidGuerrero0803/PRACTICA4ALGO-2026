package uabc.david.practica4algo2026.Controlador;

import uabc.david.practica4algo2026.Modelo.*;

public class JuegoEightOff {
    private Cascada[] cascadas = new Cascada[8];
    private Fundacion[] fundaciones = new Fundacion[4];
    private CeldaLibre[] celdas = new CeldaLibre[8];
    private ListaSimple<Movimiento> historial;
    private Mazo mazo;

    private boolean reyesEnCeldaVacia;
    private boolean terminado;
    private boolean victoria;

    public JuegoEightOff() {
        reyesEnCeldaVacia = true;
        iniciarJuego();
    }

    /**
     * Se inicializa una partida con 4 cartas a las primeras 4 celdas
     * y 6 cartas a cada una de las 8 cascadas.
     */
    public void iniciarJuego() {
        mazo = new Mazo();
        historial = new ListaSimple<>();

        for (int i = 0; i < 8; i++) {
            cascadas[i] = new Cascada(reyesEnCeldaVacia);
            celdas[i] = new CeldaLibre();
        }
        for (int i = 0; i < 4; i++) {
            fundaciones[i] = new Fundacion();
        }

        for (int i = 0; i < 4; i++) {
            celdas[i].agregarCarta(repartirUna());
        }
        for (int c = 0; c < 8; c++) {
            for (int k = 0; k < 6; k++) {
                cascadas[c].repartirCarta(repartirUna());
            }
        }

        terminado = false;
        victoria = false;
    }

    private CartaInglesa repartirUna() {
        CartaInglesa carta = mazo.obtenerUnaCarta();
        carta.makeFaceUp();
        return carta;
    }

    /**
     * Consulta la carta visible de una zona sin moverla.
     */
    public CartaInglesa obtenerCarta(ZonaTablero zona) {
        if (zona.esCelda()) {
            return celdas[zona.getIndice()].getCarta();
        }
        if (zona.esCascada()) {
            return cascadas[zona.getIndice()].getCartaSuperior();
        }
        return fundaciones[zona.getIndice()].getCartaSuperior();
    }

    /**
     * Consulta la carta de abajo de un grupo sin moverla.
     * @param cantidad de cartas que forman el grupo contando desde la carta visible.
     */
    public CartaInglesa obtenerCartaBase(ZonaTablero zona, int cantidad) {
        if (zona.esCascada()) {
            return cascadas[zona.getIndice()].getCartaBase(cantidad);
        }
        return obtenerCarta(zona);
    }

    /**
     * Verifica que la carta seleccionada cabe en la zona destino.
     */
    private boolean sePuedeColocar(CartaInglesa carta, ZonaTablero destino) {
        if (destino.esCelda()) {
            return celdas[destino.getIndice()].puedeRecibirCarta(carta);
        }
        if (destino.esFundacion()) {
            return fundaciones[destino.getIndice()].puedeAgregarCarta(carta);
        }
        return cascadas[destino.getIndice()].puedeRecibirCarta(carta);
    }

    /**
     * La zona de origen saca las cartas (la cascada solo entrega secuencias válidas).
     */
    private CartaInglesa[] quitarCartas(ZonaTablero zona, int cantidad) {
        if (zona.esCelda()) {
            return new CartaInglesa[]{celdas[zona.getIndice()].removerCarta()};
        }
        if (zona.esFundacion()) {
            return new CartaInglesa[]{fundaciones[zona.getIndice()].removerCarta()};
        }
        return cascadas[zona.getIndice()].removerCartas(cantidad);
    }

    /**
     * La zona de destino recibe las cartas y las vuelve a validar al agregarlas.
     */
    private boolean colocarCartas(CartaInglesa[] grupo, ZonaTablero zona) {
        if (zona.esCelda()) {
            return celdas[zona.getIndice()].agregarCarta(grupo[0]);
        }
        if (zona.esFundacion()) {
            return fundaciones[zona.getIndice()].agregarCarta(grupo[0]);
        }
        return cascadas[zona.getIndice()].agregarBloque(grupo);
    }

    /**
     * Regresa las cartas a su lugar de origen al deshacer.
     */
    private void devolverCartas(CartaInglesa[] grupo, ZonaTablero zona) {
        if (zona.esCelda()) {
            celdas[zona.getIndice()].agregarCarta(grupo[0]);
        } else if (zona.esFundacion()) {
            fundaciones[zona.getIndice()].agregarCarta(grupo[0]);
        } else {
            cascadas[zona.getIndice()].devolverBloque(grupo);
        }
    }
}
