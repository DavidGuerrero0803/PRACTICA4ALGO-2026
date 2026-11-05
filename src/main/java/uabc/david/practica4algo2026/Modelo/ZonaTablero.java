package uabc.david.practica4algo2026.Modelo;

/**
 * Indica una zona del tablero: una cascada, una fundación o una celda libre.
 */
public class ZonaTablero {
    private String tipo;
    private int indice;

    public ZonaTablero(String tipo, int indice) {
        this.tipo = tipo;
        this.indice = indice;
    }

    public boolean esCascada() {
        return tipo.equals("CASCADA");
    }

    public boolean esFundacion() {
        return tipo.equals("FUNDACION");
    }

    public boolean esCelda() {
        return tipo.equals("CELDA");
    }

    public String getTipo() {
        return tipo;
    }

    public int getIndice() {
        return indice;
    }

    @Override
    public int hashCode() {
        return tipo.hashCode() * 31 + indice;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof ZonaTablero)) {
            return false;
        }
        ZonaTablero zona = (ZonaTablero) otro;
        return tipo.equals(zona.tipo) && indice == zona.indice;
    }

    @Override
    public String toString() {
        return switch (tipo) {
            case "CASCADA" -> "Cascada " + (indice + 1);
            case "FUNDACION" -> "Fundación " + (indice + 1);
            default -> "Celda " + (indice + 1);
        };
    }
}
