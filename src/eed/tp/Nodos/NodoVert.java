package eed.tp.Nodos;

import eed.tp.Lista;


public class NodoVert {

    private Object objct;
    private NodoVert sigEstacion;
    private NodoAdy primerRiel; //primerAdyc
    private Lista refAdy;

    public NodoVert(Object elem, NodoVert sigVertice, NodoAdy primerAdy) {
        this.objct = elem;
        this.sigEstacion = sigVertice;
        this.primerRiel = primerAdy;
        this.refAdy = null;
    }

    public String toString() {
        return objct.toString();
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
