package uabc.david.practica4algo2026.Modelo;

/**
 * Carta de la baraja inglesa.
 *
 * @author (Cecilia Curlango Rosas)
 * @version (2025-2)
 */
public class CartaInglesa extends Carta {

    public CartaInglesa(int valor, Palo figura, String color) {
        super(valor, figura, color);
    }

    /**
     * El compareTo decide el rango, y si el rango es igual, desempata con el peso del palo.
     * Solo regresa 0 si son exactamente la misma carta (mismo valor y mismo palo).
     */
    @Override
    public int compareTo(Carta o) {
        if (getValor() != o.getValor()) {
            return getValor() - o.getValor();
        }
        return palo.getPeso() - o.palo.getPeso();
    }
}
