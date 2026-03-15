/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp.Estacion;

import eed.tp.AVL;
import eed.tp.Grafo;
import static eed.tp.Input.leerInt;
import static eed.tp.Input.leerNoVacio;
import static eed.tp.Input.leerOpcional;
import java.util.Scanner;

/**
 *
 * @author juanc
 */
public class AbmEstacion {

    public AVL estaciones;
    public Grafo red;

    public AbmEstacion(AVL estaciones, Grafo red) {
        this.estaciones = estaciones;
        this.red = red;
    }

    public void abmEstaciones(Scanner in) {
        boolean volver = false;
        while (!volver) {
            System.out.print(
                    "---- ABM Estaciones ----\n"
                    + "1) Alta\n"
                    + "2) Baja\n"
                    + "3) Modificación\n"
                    + "0) Volver\n"
                    + "Opción: "
            );
            String op = in.nextLine().trim();
            switch (op) {
                case "1":
                    altaEstacion(in);
                    break;
                case "2":
                    bajaEstacion(in);
                    break;
                case "3":
                    modificarEstacion(in);
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void altaEstacion(Scanner in) {

        int codigo = leerInt(in, "Código de estación (entero positivo): ", 1);

        if (estaciones.buscar(codigo) != null) {

            System.out.println("✗ Ya existe una estacion con código " + codigo);

        } else {
            String nombre = leerNoVacio(in, "Nombre de la estación: ");
            String ciudad = leerNoVacio(in, "Ciudad: ");
            String calle = leerNoVacio(in, "Calle de la estación: ");
            String numero = leerNoVacio(in, "Número: ");
            String cp = leerNoVacio(in, "Código Postal: ");
            int cantVias = leerInt(in, "Cantidad de vías: ", 0);
            int cantPlataformas = leerInt(in, "Cantidad de plataformas: ", 0);
            estaciones.insertar(codigo, new Estacion(nombre, calle, numero, ciudad, cp, cantVias, cantPlataformas));
            red.insertarVertice(codigo);

            //agregar en grafo directamente
            System.out.println("✓ Alta de estación OK");
        }

    }

    private void bajaEstacion(Scanner in) {
        int codigo = leerInt(in, "Código de la estación a eliminar: ", 1);
        if (estaciones.eliminar(codigo)) {
            System.out.println("✓ Baja OK");
        } else {
            System.out.println("✗ No existe la estación " + codigo);
        }
    }

    private void modificarEstacion(Scanner in) {
        int codigo = leerInt(in, "Código de la estación a modificar: ", 1);
        Estacion estacion = (Estacion) estaciones.buscar(codigo);

        if (estacion == null) {

            System.out.println("✗ No existe la estación " + codigo);

        } else {
            System.out.println("Actual: " + estacion);
            String numero = leerOpcional(in, "Nuevo número (Enter mantiene): ");
            String calle = leerOpcional(in, "Nueva calle (Enter mantiene): ");
            String ciudad = leerOpcional(in, "Nueva ciudad (Enter mantiene): ");
            String codigoPostal = leerOpcional(in, "Nuevo código postal (Enter mantiene): ");
            if (numero != null && !calle.isEmpty() && !ciudad.isEmpty() && !codigoPostal.isEmpty()) {

                estacion.setNumero(numero);
                estacion.setCalle(calle);
                estacion.setCiudad(ciudad);
                estacion.setCodigoPostal(codigoPostal);
            }

            System.out.println("✓ Modificación OK");
        }
    }

}
