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

    public int celdasVacias() {
        int vacias = 0;
        for (int i = 0; i < 8; i++) {
            if (celdas[i].estaVacia()) {
                vacias++;
            }
        }
        return vacias;
    }

    /**
     * Cuenta cuantas cartas se pueden tomar de una cascada
     * (contando desde la carta visible).
     */
    public int cartasMovibles(int indiceCascada) {
        int enSecuencia = cascadas[indiceCascada].contarCartasEnSecuencia();
        return Math.min(enSecuencia, celdasVacias() + 1);
    }

    /**
     * Verifica que se pueda tomar un grupo de cartas.
     */
    public boolean puedeSeleccionar(ZonaTablero zona, int cantidad) {
        if (terminado || cantidad < 1) {
            return false;
        }
        if (zona.esCascada()) {
            return cantidad <= celdasVacias() + 1 && cascadas[zona.getIndice()].sePuedeRemover(cantidad);
        }
        if (zona.esCelda()) {
            return cantidad == 1 && !celdas[zona.getIndice()].estaVacia();
        }
        // De una fundación solo se toma la carta de arriba.
        return cantidad == 1 && fundaciones[zona.getIndice()].puedeRemoverCarta();
    }

    /**
     * Verifica un movimiento sin ejecutarlo.
     */
    public boolean esMovimientoValido(ZonaTablero origen, ZonaTablero destino, int cantidad) {
        if (origen.equals(destino) || !puedeSeleccionar(origen, cantidad)) {
            return false;
        }
        if (cantidad > 1 && !destino.esCascada()) {
            return false;
        }
        if (origen.esFundacion() && destino.esFundacion()) {
            return false;
        }
        return sePuedeColocar(obtenerCartaBase(origen, cantidad), destino);
    }

    /**
     * Ejecuta el movimiento si es válido y lo anota en el historial.
     * @return true si se movieron las cartas, false si el movimiento no era válido.
     */
    public boolean moverCartas(ZonaTablero origen, ZonaTablero destino, int cantidad) {
        if (!esMovimientoValido(origen, destino, cantidad)) {
            return false;
        }
        CartaInglesa base = obtenerCartaBase(origen, cantidad);
        CartaInglesa[] grupo = quitarCartas(origen, cantidad);
        colocarCartas(grupo, destino);
        historial.insertarInicio(new Movimiento(base, origen, destino, cantidad));
        return true;
    }

    /**
     * Revierte el último movimiento.
     * @return false si no hay nada que deshacer (o la partida ya terminó).
     */
    public boolean deshacerMovimiento() {
        if (terminado || historial.estaVacia()) {
            return false;
        }
        Movimiento m = historial.eliminarInicio();
        CartaInglesa[] grupo = quitarCartas(m.getDestino(), m.getCantidad());
        devolverCartas(grupo, m.getOrigen());
        return true;
    }

}
