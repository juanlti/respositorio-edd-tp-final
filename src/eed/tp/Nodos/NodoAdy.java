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
    private double etiqueta;   // objecto Riel

    public NodoAdy(NodoVert vertice, NodoAdy sigAdyancete, double etiqueta) {
        this.nodoVertice = vertice;
        this.sigRiel = sigAdyancete;
        this.etiqueta = etiqueta;
    }

    public double getEtiqueta() {
        return this.etiqueta;
    }

    public void setEtiqueta(double etiqueta) {
        this.etiqueta = etiqueta;
    }

    public NodoVert getVertice() {
        return nodoVertice;
    }

    public NodoAdy getSigRiel() {
        return sigRiel;
    }

    public void setVertice(NodoVert vertice) {
        this.nodoVertice = vertice;
    }

    public void setSigAdyancete(NodoAdy sigAdyancete) {
        this.sigRiel = sigAdyancete;
    }

}
