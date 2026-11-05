package uabc.david.practica4algo2026.Modelo;

/**
 * Mazo de 52 cartas guardado en una lista doble circular.
 *
 * @author (Cecilia Curlango Rosas)
 * @version (2025-2)
 */

public class Mazo {
    private ListaDobleCircular<CartaInglesa> cartas = new ListaDobleCircular<>();

    public Mazo() {
        llenar(); // crea todas las cartas, excluyendo Jokers
        mezclar();
    }

    /**
     * Obtiene todas las cartas del mazo.
     * @return la lista doble circular con las cartas que quedan.
     */
    public ListaDobleCircular<CartaInglesa> getCartas() {
        return cartas;
    }

    /**
     * Saca la carta de arriba del mazo (el nodo inicio de la lista).
     * @return la carta, o null si el mazo ya no tiene cartas.
     */
    public CartaInglesa obtenerUnaCarta() {
        if (cartas.estaVacia()) {
            return null;
        }
        return cartas.eliminarInicio();
    }

    private void mezclar() {

    }

    private void llenar() {
        for (int i = 2; i <= 14; i++) {
            for (Palo palo : Palo.values()) {
                CartaInglesa c = new CartaInglesa(i, palo, palo.getColor());
                cartas.insertarFin(c);
            }
        }
    }

    public void ordenar() {
        cartas.ordenarLista();
    }

    @Override
    public String toString() {
        return cartas.mostrarLista();
    }
}
