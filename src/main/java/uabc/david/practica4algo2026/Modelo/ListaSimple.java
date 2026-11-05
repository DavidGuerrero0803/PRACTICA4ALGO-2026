package uabc.david.practica4algo2026.Modelo;

/**
 * Representa una lista en la que la referencia de inicio apunta
 * al primer nodo de dicha lista, y cuando no hay nada apunta a null.
 */
public class ListaSimple<T> {
    private Nodo<T> inicio;

    public void insertarInicio(T dato) {
        Nodo<T> n = new Nodo<>(dato, null);
        n.setSig(inicio);
        inicio = n;
    }

    public void insertarFin(T dato) {
        Nodo<T> n = new Nodo<>(dato, null);
        if (inicio == null) {
            n.setSig(inicio);
            inicio = n;
        } else {
            Nodo<T> r = inicio;
            while (r.getSig() != null) {
                r = r.getSig();
            }
            r.setSig(n);
            n.setSig(null);
        }
    }

    public T eliminarInicio() {
        if (inicio == null) {
            return null;
        }
        T dato = inicio.getInfo();
        inicio = inicio.getSig();

        return dato;
    }

    public T eliminarFin() {
        if (inicio == null) {
            return null;
        }

        if (inicio.getSig() == null) {
            T dato = inicio.getInfo();
            inicio = null;
            return dato;
        } else {
            Nodo<T> r = inicio;
            Nodo<T> a = null;
            while (r.getSig() != null) {
                a = r;
                r = r.getSig();
            }
            T dato = r.getInfo();
            a.setSig(null);

            return dato;
        }
    }

    public boolean estaVacia() {
        return inicio == null;
    }

    /**
     * Cuenta la cantidad de nodos que tiene la lista.
     * @return número que representa la cantidad de nodos.
     */
    public int contarNodos() {
        int contador = 0;
        Nodo<T> r = inicio;
        while (r != null) {
            contador++;
            r = r.getSig();
        }

        return contador;
    }

    /**
     * Busca el dato de una posición sin quitarlo de la lista.
     * @return el dato, o null si la posición no existe.
     */
    public T buscarDato(int posicion) {
        if (posicion < 0) {
            return null;
        }
        Nodo<T> r = inicio;
        int indiceActual = 0;
        while (r != null && indiceActual < posicion) {
            r = r.getSig();
            indiceActual++;
        }

        return (r == null) ? null : r.getInfo();
    }

    /**
     * Busca el último dato sin quitarlo de la lista.
     * @return el dato del último nodo, o null si la lista está vacía.
     */
    public T getUltimoDato() {
        if (inicio == null) {
            return null;
        }
        Nodo<T> r = inicio;
        while (r.getSig() != null) {
            r = r.getSig();
        }

        return r.getInfo();
    }

    public Nodo<T> getInicio() {
        return inicio;
    }
}
