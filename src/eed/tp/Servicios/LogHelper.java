/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp.Servicios;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author juanc
 */
public class LogHelper {

    private static final String RUTA_LOG = "ejecucion_sistema.log";

    public static void registrar(String mensaje) {

        try (PrintWriter out = new PrintWriter(new FileWriter(RUTA_LOG, true))) {
            out.println(mensaje);
        } catch (IOException e) {
            System.err.println("Error al escribir en el log: " + e.getMessage());
        }
    }

}
