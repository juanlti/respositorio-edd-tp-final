/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp.Ejercicios_6_7_8;

import eed.tp.AVL;
import eed.tp.Estacion.Estacion;
import eed.tp.Grafo;
import static eed.tp.Input.leerInt;
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

    public void consultasViajes(Scanner in) {
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
            String op = in.nextLine().trim();
            switch (op) {
                case "1":
                    caminoConMenosEstaciones(in);
                    break;
                case "2":
                    obtenerCaminosConMenosKm(in);
                    break;
                case "3":
                    obtenerTodosLosCaminosMenosUnaEstacion(in);
                    break;
                case "4":
                    verificarLaExistenciaDeUnCaminoEntreDosEstacionesConUnLimiteMaximoEnKm(in);
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void verificarLaExistenciaDeUnCaminoEntreDosEstacionesConUnLimiteMaximoEnKm(Scanner in) {

        int codigo1 = leerInt(in, "Ingrese el codigo (entero positivo) de la primera estacion: ", 1);
        Estacion estacionInicio = (Estacion) estaciones.buscar(codigo1);

        int codigo2 = leerInt(in, "Ingrese el codigo (entero positivo) de la segunda estacion: ", 1);
        Estacion estacionFinal = (Estacion) estaciones.buscar(codigo2);

        if (codigo1 == codigo2) {
            System.out.println("No se puede buscar dos estaciones con el mismo codigo");
        } else {
            int km = leerInt(in, "Ingrese el limite de km entre estaciones (entero positivo): ", 1);

            boolean resultado = red.verificarCaminoConUnaCantidadMaximaDeKm(estacionInicio, estacionFinal, km);
            System.out.println("Camino existente :" + resultado + " con una distancia maxima de " + km);
        }

    }

    private void obtenerTodosLosCaminosMenosUnaEstacion(Scanner in) {

        int codigo1 = leerInt(in, "Ingrese el codigo (entero positivo) de la primera estacion: ", 1);
        Estacion estacionInicio = (Estacion) estaciones.buscar(codigo1);

        int codigo2 = leerInt(in, "Ingrese el codigo (entero positivo) de la segunda estacion: ", 1);
        Estacion estacionFinal = (Estacion) estaciones.buscar(codigo2);

        int codigo3 = leerInt(in, "Ingrese el codigo (entero positivo) de la estacion a excluir: ", 1);
        Estacion estacionExcluir = (Estacion) estaciones.buscar(codigo2);

        if (codigo1 == codigo2 || codigo1 == codigo3) {
            System.out.println("Las estaciones deben ser diferentes entre si");
        } else {
            red.obtenerTodosLosCaminosIgnorandoUnaEstacion(estacionInicio, estacionFinal, estacionExcluir);
        }
    }

    private void obtenerCaminosConMenosKm(Scanner in) {
        int codigo1 = leerInt(in, "Ingrese el codigo (entero positivo) de la primera estacion: ", 1);
        Estacion estacionInicio = (Estacion) estaciones.buscar(codigo1);

        int codigo2 = leerInt(in, "Ingrese el codigo (entero positivo) de la segunda estacion: ", 1);
        Estacion estacionFinal = (Estacion) estaciones.buscar(codigo2);

        System.out.println("Lista resultante " + red.obtenerCaminoMasCorto(estacionInicio, estacionFinal));
    }

    private void caminoConMenosEstaciones(Scanner in) {
        int codigo1 = leerInt(in, "Ingrese el codigo (entero positivo) de la primera estacion: ", 1);
        Estacion estacionInicio = (Estacion) estaciones.buscar(codigo1);

        int codigo2 = leerInt(in, "Ingrese el codigo (entero positivo) de la segunda estacion: ", 1);
        Estacion estacionFinal = (Estacion) estaciones.buscar(codigo2);

        System.out.println(red.caminoConMenosEstaciones(estacionInicio, estacionFinal));
    }

}
