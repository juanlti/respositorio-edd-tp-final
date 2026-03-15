/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp.Ejercicios_6_7_8;

import eed.tp.AVL;
import eed.tp.Estacion.Estacion;
import static eed.tp.Input.leerInt;
import static eed.tp.Input.leerNoVacio;
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
        System.out.println("[TODO] Consultas de Estaciones...");
        boolean volver = false;
        while (!volver) {
            System.out.println("=== CONSULTAS ===");
            System.out.println("1. Mostrar informacion de una estación.");
            System.out.println("2. Obtener todaS las estaciones que comiencen con un prefijo.");
            System.out.println("3. Listar estaciones");
            System.out.println("0. Volver");
            System.out.print("Opción: ");

            int opc = Integer.parseInt(sc.nextLine().trim());
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

        for (int i = 1; i <= ls.longitud(); i++) {   // 1..N
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
