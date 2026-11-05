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
     * Verifica si la fundación ya tiene las 13 cartas (del As al Rey)
     */
    public boolean estaCompleta() {
        return cartas.contarNodos() == 13;
    }
}
