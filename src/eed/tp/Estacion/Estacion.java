/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp.Estacion;

import eed.tp.Servicios.LogHelper;

/**
 *
 * @author juanc
 */
public class Estacion {

    private String nombre;
    private String calle;
    private String numero;
    private String ciudad;
    private String cp;
    private int vias;
    private int plataformas;

    public Estacion(String nombre, String calle, String numero, String ciudad, String cp, int vias, int plataformas) {

        this.nombre = nombre;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.cp = cp;
        this.vias = vias;
        this.plataformas = plataformas;

    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String data) {
        this.ciudad = data;
    }

    public void setCodigoPostal(String data) {
        this.cp = data;
    }

    public void setCalle(String data) {
        this.calle = data;
    }

    public void setNumero(String data) {
        this.numero = data;
    }

}
