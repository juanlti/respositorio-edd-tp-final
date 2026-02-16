package eed.tp;

public class NodoVert {

    private Object estacion;
    private NodoVert sigEstacion;
    private NodoAdy primerRiel;
    private Lista refAdy;

    public NodoVert(Object elem, NodoVert sigVertice, NodoAdy primerAdy) {
        this.estacion = elem;
        this.sigEstacion = sigVertice;
        this.primerRiel = primerAdy;
        this.refAdy = null;
    }

    public String toString() {
        return estacion.toString();
    }

    public Lista getRefAdy() {
        return refAdy;
    }

    public void setRefAdy(Lista refAdy) {
        this.refAdy = refAdy;
    }

    public Object getEstacion() {
        return estacion;
    }

    public NodoVert getSigEstacion() {
        return sigEstacion;
    }

    public NodoAdy getPrimerRiel() {
        return primerRiel;
    }

    public void setEstacion(Object elem) {
        this.estacion = elem;
    }

    public void setSigEstacion(NodoVert sigEstacion) {
        this.sigEstacion = sigEstacion;
    }

    public void setPrimerRiel(NodoAdy primerRiel) {
        this.primerRiel = primerRiel;
    }

}
