package uabc.david.practica4algo2026.Modelo;

/**
 * Esta clase registra un movimiento del jugador
 * (la carta/cartas que movió, de dónde salió y a dónde llegó).
 */
public class Movimiento {
    private CartaInglesa cartaMovida;
    private ZonaTablero origen;
    private ZonaTablero destino;
    private int cantidad;

    public Movimiento(CartaInglesa cartaMovida, ZonaTablero origen,
                      ZonaTablero destino, int cantidad) {
        this.cartaMovida = cartaMovida;
        this.origen = origen;
        this.destino = destino;
        this.cantidad = cantidad;
    }

    public CartaInglesa getCartaMovida() {
        return cartaMovida;
    }

    public ZonaTablero getOrigen() {
        return origen;
    }

    public ZonaTablero getDestino() {
        return destino;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return cantidad + " carta(s) desde " + cartaMovida + ": " + origen + " a " + destino;
    }
}
