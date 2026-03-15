/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp.Tren;

import eed.tp.AVL;
import eed.tp.Tren.Tren;
import static eed.tp.Input.leerInt;
import static eed.tp.Input.leerIntOpcional;
import static eed.tp.Input.leerNoVacio;
import static eed.tp.Input.leerOpcional;
import java.util.Scanner;

/**
 *
 * @author juanc
 */
public class AbmTren {

    private AVL trenes;

    public AbmTren(AVL trenes) {
        this.trenes = trenes;
    }

    public void abmTrenes(Scanner in) {
        boolean volver = false;
        while (!volver) {
            System.out.print(
                    "---- ABM Trenes ----\n"
                    + "1) Alta\n"
                    + "2) Baja\n"
                    + "3) Modificación\n"
                    + "0) Volver\n"
                    + "Opción: "
            );
            String op = in.nextLine().trim();
            switch (op) {
                case "1":
                    altaTren(in);
                    break;
                case "2":
                    bajaTren(in);
                    break;
                case "3":
                    modificarTren(in);
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void altaTren(Scanner in) {
        int codigo = leerInt(in, "Código (entero positivo): ", 1);
        Tren tren = (Tren) trenes.buscar(codigo);

        if (tren != null) {
            System.out.println("✗ Ya existe un tren con código " + codigo);

        } else {
            String propulsion = leerNoVacio(in, "Propulsión (electrico/diesel/otro): ");
            int vagPas = leerInt(in, "Vagones de pasajeros (>=0): ", 0);
            int vagCar = leerInt(in, "Vagones de carga (>=0): ", 0);
            String linea = leerOpcional(in, "Línea (Enter para 'no-asignado'): ");
            if (linea.isEmpty()) {
                linea = "no-asignado";
            }

            trenes.insertar(codigo, new Tren(codigo, propulsion, vagPas, vagCar, linea));

        }

    }

    private void bajaTren(Scanner in) {
        int codigo = leerInt(in, "Código del tren a eliminar: ", 1);
        if (trenes.eliminar(codigo)) {
            System.out.println("✓ Baja OK");
        } else {
            System.out.println("✗ No existe el tren " + codigo);
        }
    }

    private void modificarTren(Scanner in) {
        int codigo = leerInt(in, "Código del tren a modificar: ", 1);
        Tren t = (Tren) trenes.buscar(codigo);

        if (t == null) {
            System.out.println("✗ No existe el tren " + codigo);
            return;
        }

        System.out.println("Actual: " + t);
        String propulsion = leerOpcional(in, "Nueva propulsión (Enter mantiene): ");
        Integer vagPas = leerIntOpcional(in, "Nuevos vagones pasajeros (Enter mantiene): ", 0);
        Integer vagCar = leerIntOpcional(in, "Nuevos vagones carga (Enter mantiene): ", 0);
        String linea = leerOpcional(in, "Nueva línea (Enter mantiene): ");
        if (!propulsion.isEmpty()) {
            t.setPropulsion(propulsion);
        }
        if (vagPas != null) {
            t.setCantidadVagonesPasajeros(vagPas);
        }
        if (vagCar != null) {
            t.setCantidadVagonesCarga(vagCar);
        }
        if (!linea.isEmpty()) {
            t.setLinea(linea);
        }

        System.out.println("✓ Modificación OK");
    }

}
