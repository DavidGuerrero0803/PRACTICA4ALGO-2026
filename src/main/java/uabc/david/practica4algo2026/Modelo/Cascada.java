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
}
