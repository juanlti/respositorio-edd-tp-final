/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import Estructura.AVL;
import Modelos.Tren;
import static eed.tp.Servicios.Input.leerInt;
import static eed.tp.Servicios.Input.leerIntOpcional;
import static eed.tp.Servicios.Input.leerNoVacio;
import static eed.tp.Servicios.Input.leerOpcional;
import eed.tp.Servicios.LogHelper;
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

    public void abmTrenes(Scanner sc) {
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
            int opc = sc.nextInt();
            sc.nextLine();
            switch (opc) {
                case 1:
                    altaTren(sc);
                    break;
                case 2:
                    bajaTren(sc);
                    break;
                case 3:
                    modificarTren(sc);
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void altaTren(Scanner sc) {
        int codigo = leerInt(sc, "Código (entero positivo): ", 1);
        Tren tren = (Tren) trenes.buscar(codigo);

        if (tren != null) {
            System.out.println("✗ Ya existe un tren con código " + codigo);

        } else {
            String propulsion = leerNoVacio(sc, "Propulsión (electrico/diesel/otro): ");
            int vagPas = leerInt(sc, "Vagones de pasajeros (>=0): ", 0);
            int vagCar = leerInt(sc, "Vagones de carga (>=0): ", 0);
            String linea = leerOpcional(sc, "Línea (Enter para 'no-asignado'): ");
            if (linea.isEmpty()) {
                linea = "no-asignado";
            }
            trenes.insertar(codigo, new Tren(propulsion, vagPas, vagCar, linea));
            LogHelper.registrar("ABM: Tren  creado con " + codigo);

        }

    }

    private void bajaTren(Scanner sc) {
        int codigo = leerInt(sc, "Código del tren a eliminar: ", 1);
        trenes.eliminar(codigo);
        if (trenes.buscar(codigo) == null) {
            System.out.println("✓ Baja OK");
            LogHelper.registrar("ABM: Tren  baja exitosa con " + codigo);

        } else {
            System.out.println("✗ No existe el tren " + codigo);
            LogHelper.registrar("ABM: Tren  baja fallida con " + codigo);
        }
    }

    private void modificarTren(Scanner sc) {
        int codigo = leerInt(sc, "Código del tren a modificar: ", 1);
        Tren t = (Tren) trenes.buscar(codigo);
        if (t == null) {
            System.out.println("✗ No existe el tren " + codigo);
            LogHelper.registrar("ABM: Tren  modificacion fallida con " + codigo);
        } else {
            System.out.println("Actual: " + t);
            String propulsion = leerOpcional(sc, "Nueva propulsión (Enter mantiene): ");
            Integer vagPas = leerIntOpcional(sc, "Nuevos vagones pasajeros (Enter mantiene): ", 0);
            Integer vagCar = leerIntOpcional(sc, "Nuevos vagones carga (Enter mantiene): ", 0);
            String linea = leerOpcional(sc, "Nueva línea (Enter mantiene): ");
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
            LogHelper.registrar("ABM: Tren  modificacion exitosa con " + codigo);
        }

    }

}
