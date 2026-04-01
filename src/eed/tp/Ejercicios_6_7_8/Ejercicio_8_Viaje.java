/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp.Ejercicios_6_7_8;

import Conjuntista.AVL;
import Grafo.Grafo;
import Lineal.Lista;
import static eed.tp.Servicios.Input.leerInt;
import static eed.tp.Servicios.Input.leerNoVacio;
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

        String codigo1 = leerNoVacio(sc, "Ingrese el nombre de la primera estacion: ");

        String codigo2 = leerNoVacio(sc, "Ingrese el nombre de la segunda estacion: ");

        if (codigo1.equalsIgnoreCase(codigo2)) {
            System.out.println("No se puede buscar dos estaciones con el mismo codigo");
        } else {
            int km = leerInt(sc, "Ingrese el limite de km entre estaciones (entero positivo): ", 1);

            boolean resultado = red.verificarCaminoConUnaCantidadMaximaDeKm(codigo1, codigo2, km);

            System.out.println("Camino existente :" + resultado + " con una distancia maxima de " + km);
        }

    }

    private void obtenerTodosLosCaminosMenosUnaEstacion(Scanner sc) {

        String codigo1 = leerNoVacio(sc, "Ingrese el nombre de la primera estacion: ");
        String codigo2 = leerNoVacio(sc, "Ingrese el nombre de la segunda estacion: ");
        Object codigo3 = leerNoVacio(sc, "Ingrese el nombre de la estacion a excluir: ");

        if (codigo1.equals(codigo2) || codigo1.equals(codigo3) || codigo2.equals(codigo3)) {
            System.out.println("Las estaciones deben ser diferentes entre si.");
        } else {
            Lista ls = red.obtenerTodosLosCaminosIgnorandoUnaEstacion(codigo1, codigo2, codigo3);
            System.out.println("total " + ls.longitud());

            if (ls.longitud() > 0) {
                for (int i = 1; i < ls.longitud(); i++) {
                    System.out.println("camino [ " + i + " ] =>" + ls.recuperar(i).toString());
                }
            } else {
                System.out.println("No se encontro camino");
            }

        }
    }

    private void obtenerCaminosConMenosKm(Scanner sc) {
        String codigo1 = leerNoVacio(sc, "Ingrese el nombre de la primera estacion: ");

        String codigo2 = leerNoVacio(sc, "Ingrese el nombre  de la segunda estacion: ");
        Lista resultado = red.obtenerCaminoConMenosKms(codigo1, codigo2);
        if (resultado.esVacia()) {
            System.out.println("No existe camino.");
        } else {
            String rta = "[ ";
            for (int i = 1; i <= resultado.longitud(); i++) {

                rta += resultado.recuperar(i);

                if (i < resultado.longitud()) {
                    rta += " , ";
                }
            }
            rta += " ]";
            System.out.println("El resultado es: " + rta);
        }

    }

    private void caminoConMenosEstaciones(Scanner sc) {
        String codigo1 = leerNoVacio(sc, "Ingrese el nombre de la primera estación: ");
        String codigo2 = leerNoVacio(sc, "Ingrese el nombre de la segunda estación: ");

        Lista resultado = red.obtenerCaminoConMenosEstaciones(codigo1, codigo2);

        if (resultado.esVacia()) {
            System.out.println("No existe camino.");
        } else {
            String rta = "[ ";
            for (int i = 1; i <= resultado.longitud(); i++) {

                rta += resultado.recuperar(i);

                if (i < resultado.longitud()) {
                    rta += " , ";
                }
            }
            rta += " ]";
            System.out.println("El resultado es: " + rta);
        }
    }

}
