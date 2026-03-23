package eed.tp.Nodos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author juanc
 */
public class NodoAdy {

    private NodoVert nodoVertice; // el vertice al que apunta esta conexion
    private NodoAdy sigRiel; //siguiente riel en la lista de adyacentes (vecino inmedianto de este)
    private int etiqueta;   // objecto Riel

    public NodoAdy(NodoVert vertice, NodoAdy sigAdyancete) {
        this.estacionDestino = vertice;
        this.sigRiel = sigAdyancete;

    }

    public NodoAdy(NodoVert vertice, NodoAdy sigAdyancete, Object etiqueta) {
        this.estacionDestino = vertice;
        this.sigRiel = sigAdyancete;
        this.riel = etiqueta;
    }

    public Object getEtiqueta() {
        return riel;
    }

    public void setEtiqueta(Object etiqueta) {
        this.riel = etiqueta;
    }

    public NodoVert getVertice() {
        return estacionDestino;
    }

    public NodoAdy getSigRiel() {
        return sigRiel;
    }

    public void setVertice(NodoVert vertice) {
        this.estacionDestino = vertice;
    }

    public void setSigAdyancete(NodoAdy sigAdyancete) {
        this.sigRiel = sigAdyancete;
    }

}
