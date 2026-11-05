package uabc.david.practica4algo2026.Modelo;

/**
 * Representa una cascada del tablero.
 * Las cartas se apilan en orden descendente y del mismo palo.
 */
public class Cascada {
    private ListaSimple<CartaInglesa> cartas;
    private boolean reyEnCeldaVacia;

    public Cascada(boolean reyEnCeldaVacia) {
        this.cartas = new ListaSimple<>();
        this.reyEnCeldaVacia = reyEnCeldaVacia;
    }

    public boolean estaVacia() {
        return cartas.estaVacia();
    }

    /**
     * Cuenta la cantidad de cartas que hay en la cascada.
     * @return número de cartas en la cascada.
     */
    public int contarCartas() {
        return cartas.contarNodos();
    }

    /**
     * Reparte una carta al empezar la partida.
     */
    public void repartirCarta(CartaInglesa carta) {
        cartas.insertarFin(carta);
    }

    /**
     * Agrega una carta encima de la visible (si es válido).
     * @return true si se agregó, false si no encaja.
     */
    public boolean agregarCarta(CartaInglesa carta) {
        if (!puedeRecibirCarta(carta)) {
            return false;
        }
        cartas.insertarFin(carta);
        return true;
    }

    /**
     * Regresa la carta de una posición.
     * @return carta en una posición dada.
     */
    public CartaInglesa getCarta(int posicion) {
        return cartas.buscarDato(posicion);
    }

    /**
     * Regresa la carta de abajo de un grupo,
     * contando desde la visible (o null si no existe).
     */
    public CartaInglesa getCartaBase(int cantidad) {
        return cartas.buscarDato(contarCartas() - cantidad);
    }

    /**
     * Regresa la carta de la lista.
     * @return la carta visible (la última de la lista), o null si la cascada está vacía.
     */
    public CartaInglesa getCartaSuperior() {
        return cartas.getUltimoDato();
    }

    /**
     * Verifica si la carta superior puede ir sobre la carta inferior.
     * (si es del mismo palo y de un valor menos).
     */
    private boolean encaja(CartaInglesa cartaEncima, CartaInglesa cartaDebajo) {
        return cartaEncima.tieneElMismoPalo(cartaDebajo.getPalo())
                && cartaDebajo.getValorBajo() == cartaEncima.getValorBajo() + 1;
    }

    /**
     * Se verifica si carta se puede poner sobre la carta visible (o en la cascada vacía).
     */
    public boolean puedeRecibirCarta(CartaInglesa carta) {
        if (carta == null) {
            return false;
        }
        CartaInglesa tope = cartas.getUltimoDato();
        if (tope == null) {
            return !reyEnCeldaVacia || carta.getValorBajo() == 13;
        }
        return encaja(carta, tope);
    }

    /**
     * Verifica que la primera carta del bloque (la de abajo) encaje con la carta visible.
     */
    public boolean sePuedeAgregarBloque(CartaInglesa[] bloque) {
        if (bloque == null || bloque.length == 0 || !puedeRecibirCarta(bloque[0])) {
            return false;
        }
        for (int i = 1; i < bloque.length; i++) {
            if (bloque[i] == null || !encaja(bloque[i], bloque[i - 1])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Agrega un bloque de cartas si es válido.
     * @return true si se agregó, false si no era válido.
     */
    public boolean agregarBloque(CartaInglesa[] bloque) {
        if (!sePuedeAgregarBloque(bloque)) {
            return false;
        }
        for (int i = 0; i < bloque.length; i++) {
            cartas.insertarFin(bloque[i]);
        }
        return true;
    }

    /**
     * Cuenta cuantas cartas forman una secuencia válida
     * (mismo palo en orden descendente).
     */
    public int contarCartasEnSecuencia() {
        int total = contarCartas();
        if (total == 0) {
            return 0;
        }
        int cuantas = 1;
        while (cuantas < total) {
            CartaInglesa debajo = cartas.buscarDato(total - 1 - cuantas);
            CartaInglesa encima = cartas.buscarDato(total - cuantas);
            if (!encaja(encima, debajo)) {
                break;
            }
            cuantas++;
        }
        return cuantas;
    }

    /**
     * Verifica si se pueden tomar la última cantidad de cartas.
     */
    public boolean sePuedeRemover(int cantidad) {
        return cantidad >= 1 && cantidad <= contarCartasEnSecuencia();
    }

    /**
     * Se remueven la cantidad cartas y las regresa en su orden original.
     * @return el bloque de cartas, o null si esas cartas no forman una secuencia válida.
     */
    public CartaInglesa[] removerCartas(int cantidad) {
        if (!sePuedeRemover(cantidad)) {
            return null;
        }
        CartaInglesa[] bloque = new CartaInglesa[cantidad];
        for (int i = cantidad - 1; i >= 0; i--) {
            bloque[i] = cartas.eliminarFin();
        }
        return bloque;
    }

    /**
     * Regresa un bloque de la cascada al deshacer un movimiento.
     */
    public void devolverBloque(CartaInglesa[] bloque) {
        for (int i = 0; i < bloque.length; i++) {
            cartas.insertarFin(bloque[i]);
        }
    }
}
