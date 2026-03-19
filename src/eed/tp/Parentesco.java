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

    private Object hijo;
    private Object padre;

    public Parentesco(Object hijo, Object padre) {
        this.hijo = hijo;
        this.padre = padre;
    }

    public Object getHijo() {
        return hijo;
    }

    public Object getPadre() {
        return padre;
    }
}
