/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp;

/**
 *
 * @author juanc
 */
public class Parentesco {

    private String hijo;
    private String padre;

    public Parentesco(String hijo, String padre) {
        this.hijo = hijo;
        this.padre = padre;
    }

    public String getHijo() {
        return hijo;
    }

    public String getPadre() {
        return padre;
    }
}
