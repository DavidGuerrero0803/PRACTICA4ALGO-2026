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

    public boolean estaVacia() {
        return inicio == null;
    }

    public void ordenarLista() {
        if (inicio == null || inicio == fin) {
            return;
        }

        NodoDoble<T> i = inicio;
        do {
            NodoDoble<T> menor = i;
            NodoDoble<T> j = i.getSig();

            while (j != inicio) {
                Comparable<T> comp = (Comparable<T>) j.getInfo();
                if (comp.compareTo(menor.getInfo()) < 0) {
                    menor = j;
                }
                j = j.getSig();
            }

            if (menor != i) {
                T temp = i.getInfo();
                i.setInfo(menor.getInfo());
                menor.setInfo(temp);
            }

            i = i.getSig();
        } while (i != fin);
    }

    public String mostrarLista() {
        String cadena = "";
        if (inicio != null) {
            NodoDoble<T> r = inicio;
            do {
                cadena += r.getInfo() + " ";
                r = r.getSig();
            } while (r != inicio);
        } else {
            System.out.println("Lista vacia");
        }
        return cadena;
    }
}
