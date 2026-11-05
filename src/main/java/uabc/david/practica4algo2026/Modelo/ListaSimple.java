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
}
