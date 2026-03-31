/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abm;

import Estructura.Grafo;
import static eed.tp.Servicios.Input.leerInt;
import eed.tp.Servicios.LogHelper;
import java.util.Scanner;

/**
 *
 * @author juanc
 */
public class AbmRiel {

    private final Grafo red;

    public AbmRiel(Grafo red) {
        this.red = red;
    }

    public void abmRieles(Scanner sc) {
        boolean volver = false;
        while (!volver) {
            System.out.print(
                    "---- ABM RIELES ----\n"
                    + "1) Alta\n"
                    + "2) Baja\n"
                    + "3) Modificación\n"
                    + "0) Volver\n"
                    + "Opción: ");
            int opc = sc.nextInt();
            sc.nextLine();
            switch (opc) {
                case 1:
                    altaRiel(sc);
                    break;
                case 2:
                    bajaRiel(sc);
                    break;
                case 3:
                    modificarRiel(sc);
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void altaRiel(Scanner sc) {
        int codOri = leerInt(sc, "Código estación origen: ", 1);
        int codDes = leerInt(sc, "Código estación destino: ", 1);

        if (codOri == codDes) {
            System.out.println("No se puede crear un riel de una estación consigo misma.");

        } else {
            int distancia = leerInt(sc, "Distancia (km): ", 1);

            red.insertarArco(codOri, codDes, distancia);

            LogHelper.registrar("ABM: Riel creado entre " + codOri + " y " + codDes);
        }

    }

    private void bajaRiel(Scanner sc) {

        int codOri = leerInt(sc, "Código estación origen: ", 1);
        int codDes = leerInt(sc, "Código estación destino: ", 1);

        if (codOri == codDes) {
            System.out.println("No se puede eliminar  un riel de una estación consigo misma.");

        } else {
            System.out.println("✓ Riel eliminado: " + red.eliminarArco(codOri, codDes));
            LogHelper.registrar("Se eliminó el riel entre " + codOri + " y " + codDes);
        }

    }

    private void modificarRiel(Scanner sc) {
        int codOri = leerInt(sc, "Código estación origen: ", 1);
        int codDes = leerInt(sc, "Código estación destino: ", 1);
        if (codOri == codDes) {
            System.out.println("No se puede modificar un riel de una estación consigo misma.");
        } else {
            int nuevaDist = leerInt(sc, "Nueva distancia (km): ", 1);
            System.out.println("El resultado de la actualizacion es : " + red.modificarEtiqueta(codOri, codDes, nuevaDist));
            LogHelper.registrar("Se modifico el riel entre " + codOri + " y " + codDes);
        }
    }

}
