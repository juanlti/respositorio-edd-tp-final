package eed.tp.Nodos;

import eed.tp.Lista;

public class NodoVert {

    private Object elemento;
    private NodoVert SigNodoVertice;
    private NodoAdy primerAdyc; 
    private Lista refAdy;

    public NodoVert(Object elem, NodoVert sigVertice, NodoAdy primerAdy) {
        this.elemento = elem;
        this.SigNodoVertice = sigVertice;
        this.primerAdyc = primerAdy;
        this.refAdy = null;
    }

    public String toString() {
        return elemento.toString();
    }

    public Lista getRefAdy() {
        return refAdy;
    }

    public void setRefAdy(Lista refAdy) {
        this.refAdy = refAdy;
    }

    public Object getElemento() {
        return this.elemento;
    }

    public void setElemento(Object elem) {
        this.elemento = elem;
    }

    public NodoVert getSiguienteNodoVertice() {
        return this.SigNodoVertice;
    }

    public void setNodoVertice(NodoVert sigEstacion) {
        this.SigNodoVertice = sigEstacion;
    }

    public NodoAdy getPrimerAdyc() {
        return primerAdyc;
    }

    public void setPrimerAdyc(NodoAdy primerRiel) {
        this.primerAdyc = primerRiel;
    }

}
