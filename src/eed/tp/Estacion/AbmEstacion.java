/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp.Estacion;

import eed.tp.AVL;
import eed.tp.Grafo;
import static eed.tp.Servicios.Input.leerInt;
import static eed.tp.Servicios.Input.leerNoVacio;
import static eed.tp.Servicios.Input.leerOpcional;
import eed.tp.Servicios.LogHelper;
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

    public void abmEstaciones(Scanner sc) {
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
            int op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    altaEstacion(sc);
                    break;
                case 2:
                    bajaEstacion(sc);
                    break;
                case 3:
                    modificarEstacion(sc);
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void altaEstacion(Scanner sc) {

        String codigo = leerNoVacio(sc, "Código de estación (entero positivo): ");

        if (estaciones.buscar(codigo) != null) {

            System.out.println("✗ Ya existe una estacion con código " + codigo);

        } else {
            String nombre = leerNoVacio(sc, "Nombre de la estación: ");
            String numero = leerOpcional(sc, "Número calle : ");
            String ciudad = leerNoVacio(sc, "Ciudad: ");
            String calle = leerNoVacio(sc, "Calle de la estación: ");
            String cp = leerNoVacio(sc, "Código Postal: ");
            int cantVias = leerInt(sc, "Cantidad de vías: ", 0);
            int cantPlataformas = leerInt(sc, "Cantidad de plataformas: ", 0);
            estaciones.insertar(codigo, new Estacion(nombre, calle, numero, ciudad, cp, cantVias, cantPlataformas));
            red.insertarVertice(codigo);
            System.out.println("✓ Alta de estación OK");
            LogHelper.registrar("Alta: Estación " + codigo);
        }

    }

    private void bajaEstacion(Scanner sc) {
        String codigo = leerNoVacio(sc, "Código de la estación a eliminar: ");

        if (this.estaciones.buscar(codigo) != null) {
            this.red.eliminarVertice(codigo);
            this.estaciones.eliminar(codigo);
            System.out.println("✓ Baja OK");
            LogHelper.registrar("Baja: Estación " + codigo + " y sus rieles eliminados.");
        } else {
            System.out.println("✗ No existe la estación " + codigo);
        }
    }

    private void modificarEstacion(Scanner sc) {
        String codigo = leerNoVacio(sc, "Código de la estación a modificar: ");
        Estacion estacion = (Estacion) estaciones.buscar(codigo);

        if (estacion == null) {
            System.out.println("✗ No existe la estación " + codigo);

        } else {
            System.out.println("Actual: " + estacion);
            String numero = leerOpcional(sc, "Nuevo número calle (Enter mantiene): ");
            String calle = leerOpcional(sc, "Nueva calle (Enter mantiene): ");
            String ciudad = leerOpcional(sc, "Nueva ciudad (Enter mantiene): ");
            String codigoPostal = leerOpcional(sc, "Nuevo código postal (Enter mantiene): ");
            if (numero != null && !calle.isEmpty() && !ciudad.isEmpty() && !codigoPostal.isEmpty()) {
                estacion.setNumero(numero);
                estacion.setCalle(calle);
                estacion.setCiudad(ciudad);
                estacion.setCodigoPostal(codigoPostal);
            }
            System.out.println("✓ Modificación OK");
            LogHelper.registrar("Modificacion: Estación " + codigo + " y sus rieles eliminados.");
        }
    }

}
