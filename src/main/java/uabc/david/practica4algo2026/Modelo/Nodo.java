package uabc.david.practica4algo2026.Modelo;

/**
 * Representa un nodo (un pequeño contenedor)
 * que divide su información en dos partes esenciales:
 * el dato y una referencia.
 */
public class Nodo<T> {
    private T info;
    private Nodo<T> sig;

    public Nodo(T info, Nodo<T> sig) {
        this.info = info;
        this.sig = sig;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public Nodo<T> getSig() {
        return sig;
    }

    public void setSig(Nodo<T> sig) {
        this.sig = sig;
    }

}
