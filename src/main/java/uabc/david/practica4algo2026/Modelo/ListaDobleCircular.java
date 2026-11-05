package uabc.david.practica4algo2026.Modelo;

/**
 * Representa una variante de una lista en donde el último nodo
 * se conecta directamente con el primero, y el primero con el último.
 */
public class ListaDobleCircular<T> {
    private NodoDoble<T> inicio;
    private NodoDoble<T> fin;

    public ListaDobleCircular() {
        this.inicio = null;
        this.fin = null;
    }

    public void insertarInicio(T dato) {
        NodoDoble<T> n = new NodoDoble<>(dato);
        if (inicio == null) {
            inicio = n;
            fin = n;
            n.setSig(inicio);
            n.setAnt(inicio);
        } else {
            n.setSig(inicio);
            inicio.setAnt(n);
            inicio = n;
            fin.setSig(inicio);
            n.setAnt(fin);
        }
    }

    public void insertarFin(T dato) {
        NodoDoble<T> n = new NodoDoble<>(dato);
        if (inicio == null) {
            inicio = n;
            fin = n;
            n.setSig(inicio);
            n.setAnt(inicio);
        } else {
            n.setSig(inicio);
            inicio.setAnt(n);
            fin.setSig(n);
            n.setAnt(fin);
            fin = n;
        }
    }

    public T eliminarInicio() {
        if (inicio == null) {
            return null;
        }

        T dato = inicio.getInfo();
        if (inicio == fin) {
            inicio = null;
            fin = null;
        } else {
            fin.setSig(inicio.getSig());
            inicio = inicio.getSig();
            inicio.setAnt(fin);
        }

        return dato;
    }

    public T eliminarFin() {
        if (inicio == null) {
            return null;
        }

        T dato = fin.getInfo();
        if (inicio == fin) {
            inicio = null;
            fin = null;
        } else {
            NodoDoble<T> r = fin.getAnt();
            r.setSig(inicio);
            inicio.setAnt(r);
            fin = r;
        }

        return dato;
    }
}
