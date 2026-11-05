package uabc.david.practica4algo2026.Modelo;

/**
 * Esta clase representa la parte de la fundación del tablero.
 * Verifica que las cartas entren en orden ascendente y del mismo palo
 * (empezando siempre por un As hasta el Rey).
 */
public class Fundacion {
    private ListaSimple<CartaInglesa> cartas;

    public Fundacion() {
        this.cartas = new ListaSimple<>();
    }

    public boolean estaVacia() {
        return cartas.estaVacia();
    }

    /**
     * Cuenta la cantidad de cartas que hay en la fundación.
     * @return número de cartas en la fundación.
     */
    public int contarCartas() {
        return cartas.contarNodos();
    }

    /**
     * Regresa la carta más alta de la fundación (o null si está vacía).
     */
    public CartaInglesa getCartaSuperior() {
        return cartas.getUltimoDato();
    }

    /**
     * Verifica si la fundación ya tiene las 13 cartas (del As al Rey).
     */
    public boolean estaCompleta() {
        return cartas.contarNodos() == 13;
    }

    /**
     * Permite meter un As cuando la fundación está vacía.
     * Con cartas cuando son del mismo palo y valen uno más que la carta más alta.
     */
    public boolean puedeAgregarCarta(CartaInglesa carta) {
        if (carta == null) {
            return false;
        }
        CartaInglesa tope = cartas.getUltimoDato();
        if (tope == null) {
            return carta.getValorBajo() == 1;
        }
        return carta.tieneElMismoPalo(tope.getPalo())
                && carta.getValorBajo() == tope.getValorBajo() + 1;
    }

    /**
     * Solo se puede tomar la carta más alta, y solo si hay alguna.
     */
    public boolean puedeRemoverCarta() {
        return !cartas.estaVacia();
    }

    /**
     * Agrega la carta (un nodo nuevo al final de la lista) si es válida.
     * @return true si se agregó, false si no (y la fundación no cambia).
     */
    public boolean agregarCarta(CartaInglesa cartaNueva) {
        if (!puedeAgregarCarta(cartaNueva)) {
            return false;
        }
        cartas.insertarFin(cartaNueva);
        return true;
    }

    /**
     * Quita la carta más alta.
     * @return la carta (o null si la fundación estaba vacía).
     */
    public CartaInglesa removerCarta() {
        if (!puedeRemoverCarta()) {
            return null;
        }
        return cartas.eliminarFin();
    }
}
