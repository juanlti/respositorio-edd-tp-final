/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author juanc
 */
public class EEDTP {

    public static void main(String[] args) throws IOException {
        TrenesSA app = new TrenesSA();
        Scanner sc = new Scanner(System.in);

        app.cargarInicialDesdeArchivo("file.txt");

        LogHelper.registrar("\n=== ESTADO DEL SISTEMA POST-CARGA INICIAL ===");
        LogHelper.registrar(app.toString());
        LogHelper.registrar("==============================================\n");

        app.comenzar();

    }

}
