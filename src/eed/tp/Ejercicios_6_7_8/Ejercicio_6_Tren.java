/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp.Ejercicios_6_7_8;

import eed.tp.AVL;
import eed.tp.Estacion.Estacion;
import eed.tp.Tren.Tren;
import static eed.tp.Input.leerInt;
import eed.tp.Linea.Linea;
import eed.tp.Lista;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author juanc
 */
public class Ejercicio_6_Tren {

    private final AVL trenes;
    private final HashMap linea;

    public Ejercicio_6_Tren(AVL trenes, HashMap linea) {
        this.trenes = trenes;
        this.linea = linea;
    }

    public void consultaTrenes(Scanner sc) {
        boolean volver = false;
        while (!volver) {
            System.out.print(
                    "---- Consulta Tren ----\n"
                    + "1) Mostrar informacion de un tren\n"
                    + "2) Mostrar las estaciones de un tren\n"
                    + "3) Listar todos los trenes \n"
                    + "0) Volver\n"
                    + "Opción: "
            );
            int opc = Integer.parseInt(sc.nextLine().trim());

            switch (opc) {
                case 1:
                    buscarTren(sc);
                    break;
                case 2:
                    mostrarEstacionesDeUnTrenDado(sc);
                    break;
                case 3:
                    listarTrenes();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void mostrarEstacionesDeUnTrenDado(Scanner in) {
        int codigo = leerInt(in, "Código a buscar: ", 1);
        Tren tren = (Tren) trenes.buscar(codigo);
        if (tren == null) {
            System.out.println("✗ No existe el tren");
        } else {
            String lineaDelTren = tren.getLinea();
            Linea lineaEncontrada = (Linea) this.linea.get(lineaDelTren);
            Lista estaciones = lineaEncontrada.getEstaciones();
            System.out.println("La linea del tren ingresado es: " + lineaDelTren);
            for (int i = 0; i < estaciones.longitud(); i++) {
                Estacion estacion = (Estacion) estaciones.recuperar(i);
                System.out.println("Las ciudades por donde pasa [ " + estacion.getCiudad() + " ]");

            }
        }
    }

    private void buscarTren(Scanner in) {
        int codigo = leerInt(in, "Código a buscar: ", 1);
        Tren tren = (Tren) trenes.buscar(codigo);
        if (tren == null) {
            System.out.println("✗ No existe el tren");
        } else {
            System.out.println(tren.toString());
        }

    }

    private void listarTrenes() {
        if (trenes.esVacio()) {
            System.out.println("== Trenes == (sin trenes)");

        } else {

            System.out.println("== Trenes ==");
            System.out.println(trenes.listar());
        }

    }

}
