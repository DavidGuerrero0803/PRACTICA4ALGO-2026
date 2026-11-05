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
    private boolean puedeColocarCarta(CartaInglesa carta, ZonaTablero destino) {
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
        return puedeColocarCarta(obtenerCartaBase(origen, cantidad), destino);
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
        actualizarEstado();
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
        Movimiento movimiento = historial.eliminarInicio();
        CartaInglesa[] grupo = quitarCartas(movimiento.getDestino(), movimiento.getCantidad());
        devolverCartas(grupo, movimiento.getOrigen());
        return true;
    }

    /**
     * Entrega una pista, indicando qué cartas mover y hacia dónde.
     * @return el movimiento sugerido, o null si la partida terminó o no hay jugadas.
     */
    public Movimiento darPista() {
        if (terminado) {
            return null;
        }
        Movimiento sugerido = mejorMovimiento(true);
        if (sugerido == null) {
            sugerido = mejorMovimiento(false);
        }
        return sugerido;
    }

    public boolean hayMovimientosPosibles() {
        return mejorMovimiento(false) != null;
    }

    private Movimiento mejorMovimiento(boolean evitarInverso) {
        Movimiento movimiento = buscarHacia("FUNDACION", 4, 0, evitarInverso);
        if (movimiento == null) {
            movimiento = buscarHacia("CASCADA", 8, 1, evitarInverso);
        }
        if (movimiento == null) {
            movimiento = buscarHacia("CASCADA", 8, 2, evitarInverso);
        }
        if (movimiento == null) {
            movimiento = buscarHacia("CELDA", 8, 0, evitarInverso);
        }
        return movimiento;
    }

    private Movimiento buscarHacia(String tipoDestino, int cantidadDestinos, int filtroDestino, boolean evitarInverso) {
        for (int i = 0; i < 16; i++) {
            ZonaTablero origen = (i < 8) ? new ZonaTablero("CASCADA", i) : new ZonaTablero("CELDA", i - 8);
            int maximo;
            if (origen.esCascada()) {
                maximo = cartasMovibles(i);
            } else {
                maximo = celdas[i - 8].estaVacia() ? 0 : 1;
            }

            for (int cuantas = 1; cuantas <= maximo; cuantas++) {
                for (int j = 0; j < cantidadDestinos; j++) {
                    ZonaTablero destino = new ZonaTablero(tipoDestino, j);
                    boolean destinoVacio = obtenerCarta(destino) == null;
                    if (filtroDestino == 1 && destinoVacio) {
                        continue;
                    }
                    if (filtroDestino == 2 && !destinoVacio) {
                        continue;
                    }
                    if (!esMovimientoValido(origen, destino, cuantas)
                            || !descartarMovimiento(origen, destino, cuantas)) {
                        continue;
                    }
                    if (evitarInverso && esInversoDelUltimo(origen, destino, cuantas)) {
                        continue;
                    }
                    return new Movimiento(obtenerCartaBase(origen, cuantas), origen, destino, cuantas);
                }
            }
        }
        return null;
    }

    private boolean descartarMovimiento(ZonaTablero origen, ZonaTablero destino, int cuantas) {
        if (origen.esCelda() && destino.esCelda()) {
            return false;
        }
        if (origen.esCascada() && destino.esCascada()
                && cartasEnCascada(origen.getIndice()) == cuantas
                && obtenerCarta(destino) == null) {
            return false;
        }
        return true;
    }

    public int cartasEnCascada(int indice) {
        return cascadas[indice].contarCartas();
    }

    private boolean esInversoDelUltimo(ZonaTablero origen, ZonaTablero destino, int cuantas) {
        Movimiento ultimo = getUltimoMovimiento();
        return ultimo != null
                && cuantas == ultimo.getCantidad()
                && origen.equals(ultimo.getDestino())
                && destino.equals(ultimo.getOrigen());
    }

    /**
     * El jugador ganó si las 4 fundaciones dicen que están completas.
     */
    public boolean verificarVictoria() {
        for (int i = 0; i < 4; i++) {
            if (!fundaciones[i].estaCompleta()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Se llama después de cada movimiento,
     * verificando si el jugador ya ganó o ha perdido.
     */
    private void actualizarEstado() {
        if (verificarVictoria()) {
            victoria = true;
            terminado = true;
        } else if (!hayMovimientosPosibles()) {
            terminado = true;
        }
    }

    public boolean puedeDeshacer() {
        return !terminado && !historial.estaVacia();
    }

    public boolean puedePedirPista() {
        return !terminado;
    }

    public boolean isTerminado() {
        return terminado;
    }

    public boolean isVictoria() {
        return victoria;
    }

    public Movimiento getUltimoMovimiento() {
        return historial.estaVacia() ? null : historial.getInicio().getInfo();
    }

    public Cascada[] getCascadas() {
        return cascadas;
    }

    public Fundacion[] getFundaciones() {
        return fundaciones;
    }

    public CeldaLibre[] getCeldasLibres() {
        return celdas;
    }
}
