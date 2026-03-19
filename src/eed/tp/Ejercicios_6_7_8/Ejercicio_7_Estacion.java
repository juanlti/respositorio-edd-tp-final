/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp.Ejercicios_6_7_8;

import eed.tp.AVL;
import eed.tp.Estacion.Estacion;
import static eed.tp.Servicios.Input.leerInt;
import static eed.tp.Servicios.Input.leerNoVacio;
import eed.tp.Lista;
import java.util.Scanner;

/**
 *
 * @author juanc
 */
public class Ejercicio_7_Estacion {

    public AVL estaciones;

    public Ejercicio_7_Estacion(AVL estaciones) {
        this.estaciones = estaciones;
    }

    public void consultasEstaciones(Scanner sc) {

        boolean volver = false;
        while (!volver) {
            System.out.print(
                    "---- Consulta Estacion ----\n"
                    + "1) Mostrar informacion de una estación\n"
                    + "2) Obtener todas las estaciones que comiencen con un prefijo.\n"
                    + "3) Listar estaciones \n"
                    + "0) Volver\n"
                    + "Opción: "
            );
            int opc = sc.nextInt();
            sc.nextLine();
            switch (opc) {
                case 1:
                    mostrarEstacionDada(sc);
                    break;
                case 2:
                    mostrarTodasLasEstacionesConUnPreFijo(sc);
                    break;
                case 3:
                    listarEstaciones();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void mostrarTodasLasEstacionesConUnPreFijo(Scanner in) {
        String prefijo = leerNoVacio(in, "Nombre (prefijo) a buscar: ").trim().toLowerCase();

        Lista ls = estaciones.listar(); // Lista de Estacion
        StringBuilder out = new StringBuilder();

        for (int i = 1; i <= ls.longitud(); i++) { 
            Estacion est = (Estacion) ls.recuperar(i);
            if (est == null) {
                continue;
            }

            String nombre = est.getNombre();
            if (nombre != null && nombre.trim().toLowerCase().startsWith(prefijo)) {
                out.append("• ").append(nombre).append('\n');
            }
        }

        if (out.length() == 0) {
            System.out.println("No hay estaciones que empiecen con '" + prefijo + "'.");
        } else {
            System.out.println("Coincidencias:\n" + out);
        }
    }

    private void mostrarEstacionDada(Scanner in) {
        int codigo = leerInt(in, "Código a buscar: ", 1);
        Estacion estacion = (Estacion) estaciones.buscar(codigo);
        if (estacion == null) {
            System.out.println("✗ No existe la estación " + codigo);
        } else {
            System.out.println(estacion);
        }
    }

    private void listarEstaciones() {
        if (estaciones.esVacio()) {
            System.out.println("No hay estaciones cargadas");
        } else {
            System.out.println("== Estaciones ==");
            System.out.println(estaciones.listar());
        }

    }

}
