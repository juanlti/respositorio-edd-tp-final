/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp.Ejercicios_6_7_8;

import eed.tp.AVL;
import eed.tp.Estacion.Estacion;
import eed.tp.Grafo;
import static eed.tp.Servicios.Input.leerInt;
import java.util.Scanner;

/**
 *
 * @author juanc
 */
public class Ejercicio_8_Viaje {

    public AVL estaciones;
    public Grafo red;

    public Ejercicio_8_Viaje(AVL estaciones, Grafo red) {
        this.estaciones = estaciones;
        this.red = red;
    }

    public void consultasViajes(Scanner sc) {
        boolean volver = false;
        while (!volver) {
            System.out.print(
                    "---- Consulta Viaje ----\n"
                    + "1) Obtener el camino mas corto entre dos estaciones que pasen por menos estaciones\n"
                    + "2) Obtener el camino con menos kilometros entre dos estaciones\n"
                    + "3) Obtener todos los caminos posibles entre dos estaciones sin pasar por una estacion dada \n"
                    + "4) Verificar la existencia de una camino entre dos estaciones con un limite maximo de km\n"
                    + "0) Volver\n"
                    + "Opción: "
            );
            int opc = sc.nextInt();
            sc.nextLine();
            switch (opc) {
                case 1:
                    caminoConMenosEstaciones(sc);
                    break;
                case 2:
                    obtenerCaminosConMenosKm(sc);
                    break;
                case 3:
                    obtenerTodosLosCaminosMenosUnaEstacion(sc);
                    break;
                case 4:
                    verificarLaExistenciaDeUnCaminoEntreDosEstacionesConUnLimiteMaximoEnKm(sc);
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void verificarLaExistenciaDeUnCaminoEntreDosEstacionesConUnLimiteMaximoEnKm(Scanner sc) {

        Object codigo1 = leerInt(sc, "Ingrese el codigo (entero positivo) de la primera estacion: ", 1);

        Object codigo2 = leerInt(sc, "Ingrese el codigo (entero positivo) de la segunda estacion: ", 1);

        if (codigo1 == codigo2) {
            System.out.println("No se puede buscar dos estaciones con el mismo codigo");
        } else {
            int km = leerInt(sc, "Ingrese el limite de km entre estaciones (entero positivo): ", 1);

            boolean resultado = red.verificarCaminoConUnaCantidadMaximaDeKm(codigo1, codigo2, km);
            System.out.println("Camino existente :" + resultado + " con una distancia maxima de " + km);
        }

    }

    private void obtenerTodosLosCaminosMenosUnaEstacion(Scanner sc) {

        Object codigo1 = leerInt(sc, "Ingrese el codigo de la primera estacion: ", 1);
        Object codigo2 = leerInt(sc, "Ingrese el codigo de la segunda estacion: ", 1);
        Object codigo3 = leerInt(sc, "Ingrese el codigo de la estacion a excluir: ", 1);

        // 1. Corregimos la comparación: Usar .equals() para Objects
        if (codigo1.equals(codigo2) || codigo1.equals(codigo3) || codigo2.equals(codigo3)) {
            System.out.println("Las estaciones deben ser diferentes entre si.");
        } else {
            System.out.println("Caminos encontrados: " + red.obtenerTodosLosCaminosIgnorandoUnaEstacion(codigo1, codigo2, codigo3).toString());

        }
    }

    private void obtenerCaminosConMenosKm(Scanner sc) {
        Object codigo1 = leerInt(sc, "Ingrese el codigo (entero positivo) de la primera estacion: ", 1);

        Object codigo2 = leerInt(sc, "Ingrese el codigo (entero positivo) de la segunda estacion: ", 1);

        System.out.println("Lista resultante " + red.obtenerCaminoMasCorto(codigo1, codigo2));
    }

    private void caminoConMenosEstaciones(Scanner sc) {
        Object codigo1 = leerInt(sc, "Ingrese el codigo (entero positivo) de la primera estacion: ", 1);

        Object codigo2 = leerInt(sc, "Ingrese el codigo (entero positivo) de la segunda estacion: ", 1);

        System.out.println(red.caminoConMenosEstaciones(codigo1, codigo2));
    }

}
