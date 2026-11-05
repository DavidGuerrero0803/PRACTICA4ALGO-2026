package uabc.david.practica4algo2026.Modelo;

/**
 * Esta clase representa un nodo que añade una segunda referencia
 * que apunta al nodo anterior, lo que permite recorrer la lista
 * en ambas direcciones (hacia adelante y hacia atrás).
 */
public class NodoDoble<T> {
    private T info;
    private NodoDoble<T> sig;
    private NodoDoble<T> ant;

    public NodoDoble(T info) {
        this.info = info;
        this.sig = null;
        this.ant = null;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public NodoDoble<T> getSig() {
        return sig;
    }

    public void setSig(NodoDoble<T> sig) {
        this.sig = sig;
    }

    public NodoDoble<T> getAnt() {
        return ant;
    }

    public void setAnt(NodoDoble<T> ant) {
        this.ant = ant;
    }
}
