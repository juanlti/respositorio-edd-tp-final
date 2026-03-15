/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp.Linea;

import eed.tp.AVL;
import eed.tp.Estacion.Estacion;
import eed.tp.Linea.Linea;
import eed.tp.Lista;
import static eed.tp.Servicios.Input.leerInt;
import static eed.tp.Servicios.Input.leerNoVacio;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author juanc
 */
public class AbmLinea {

    private final java.util.HashMap<String, Linea> lineas;
    private AVL estaciones;

    public AbmLinea(HashMap lineas, AVL estaciones) {
        this.lineas = lineas;
        this.estaciones = estaciones;
    }

    public void abmLineas(Scanner sc) {
        boolean volver = false;
        while (!volver) {
            System.out.print(
                    "---- ABM LÍNEAS ----\n"
                    + "1) Alta\n"
                    + "2) Baja\n"
                    + "3) Modificación\n"
                    + "0) Volver\n"
                    + "Opción: "
            );
            String op = sc.nextLine().trim();
            switch (op) {
                case "1":
                    altaLinea(sc);
                    break;
                case "2":
                    bajaLinea(sc);
                    break;
                case "3":
                    modificarLinea(sc);
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void altaLinea(Scanner sc) {
        String nombreLinea = leerNoVacio(sc, "Nombre de la línea: ");
        if (lineas.containsKey(nombreLinea)) {
            System.out.println("✗ Ya existe la línea " + nombreLinea);
        } else {
            int n = leerInt(sc, "Cantidad de estaciones en el recorrido: ", 2);
            Lista recorrido = new Lista();
            for (int i = 1; i <= n; i++) {
                int codEst = leerInt(sc, "Código estación #" + i + ": ", 1);
                Estacion estacion = (Estacion) estaciones.buscar(codEst);
                if (estacion == null) {
                    System.out.println("✗ No existe estación con código " + codEst);
                    return;
                } else {
                    recorrido.insertar(estacion, recorrido.longitud() + 1);
                }
            }
            Linea linea = new Linea(nombreLinea, recorrido);
            lineas.put(nombreLinea, linea);

            System.out.println("✓ Línea creada: " + nombreLinea);
        }

    }

    private void bajaLinea(Scanner sc) {
        String nombreLinea = leerNoVacio(sc, "Nombre de la línea a eliminar: ");
        Linea elim = lineas.remove(nombreLinea);
        if (elim == null) {
            System.out.println("✗ No existe la línea " + nombreLinea);
        } else {
            System.out.println("✓ Línea eliminada: " + nombreLinea);
        }
    }

    private void modificarLinea(Scanner sc) {
        String nombreLinea = leerNoVacio(sc, "Nombre de la línea a modificar: ");
        if (!lineas.containsKey(nombreLinea)) {
            System.out.println("✗ No existe la línea " + nombreLinea);

        } else {
            // opción simple: re-cargar recorrido completo
            lineas.remove(nombreLinea);
            System.out.println("(Se re-carga el recorrido completo)");
            altaLinea(sc);
        }

    }

    private void listarLineas() {
        if (lineas.isEmpty()) {
            System.out.println("No hay líneas cargadas.");
        } else {
            System.out.println("=== LÍNEAS ===");
            lineas.forEach((key, linea) -> System.out.println(key + " = " + linea));
        }

    }

}
