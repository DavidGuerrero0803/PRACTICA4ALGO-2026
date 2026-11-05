package uabc.david.practica4algo2026.Modelo;

/**
 * Esta clase representa una celda libre del tablero.
 * Guarda una sola carta (o ninguna) y sabe si puede recibir otra.
 */
public class CeldaLibre {
    private CartaInglesa carta;

    public CeldaLibre() {
        this.carta = null;
    }

    public boolean estaVacia() {
        return carta == null;
    }

    /**
     * Regresa la carta que guarda la celda, o null si está vacía.
     * @return una carta guardada en la celda.
     */
    public CartaInglesa getCarta() {
        return carta;
    }

    /**
     * Puede recibir una carta si la celda está vacía (y la carta existe).
     * @return true si puede agregarse una carta, false en caso de que no.
     */
    public boolean puedeRecibirCarta(CartaInglesa cartaNueva) {
        return cartaNueva != null && carta == null;
    }

    /**
     * Guarda la carta si la celda puede recibirla.
     * @return true si se guardó, false si la celda estaba ocupada.
     */
    public boolean agregarCarta(CartaInglesa cartaNueva) {
        if (!puedeRecibirCarta(cartaNueva)) {
            return false;
        }
        carta = cartaNueva;
        return true;
    }

    /**
     * Saca la carta y deja la celda vacía.
     * @return la carta, o null si ya estaba vacía.
     */
    public CartaInglesa removerCarta() {
        CartaInglesa cartaRemovida = carta;
        carta = null;
        return cartaRemovida;
    }
}
