/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author juanc
 */
public class Tren {

    private String propulsion;
    private int vagPasajeros;
    private int vagCarga;
    private String linea;

    public Tren(String propulsion, int vagPasajeros, int vagCarga, String linea) {

        this.propulsion = propulsion;
        this.vagPasajeros = vagPasajeros;
        this.vagCarga = vagCarga;
        this.linea = linea;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public void setPropulsion(String propulsion) {
        this.propulsion = propulsion;

    }

    public void setCantidadVagonesPasajeros(int cantVagonesPasajeros) {
        this.vagPasajeros = cantVagonesPasajeros;

    }

    public void setCantidadVagonesCarga(int cantVagonesCarga) {
        this.vagCarga = cantVagonesCarga;

    }

}
