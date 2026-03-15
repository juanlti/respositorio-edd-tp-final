/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp.Riel;

import eed.tp.AVL;
import eed.tp.Grafo;
import eed.tp.Lista;
import static eed.tp.Input.leerInt;
import java.util.Scanner;

/**
 *
 * @author juanc
 */
public class AbmRiel {

    private Grafo red;
    private AVL estaciones;

    public AbmRiel(AVL estaciones,Grafo red) {
        this.red = red;
        this.estaciones = estaciones;
    }

    private void imprimirMenuRieles() {
        System.out.println("=== ABM RIELES (RED) ===");
        System.out.println("1) Alta de riel");
        System.out.println("2) Baja de riel");
        System.out.println("3) Modificación de riel");
        System.out.println("4) Listar rieles");
        System.out.println("0) Volver");
        System.out.print("Opción: ");
    }

    public void abmRieles(Scanner sc) {
        boolean volver = false;
        while (!volver) {
            imprimirMenuRieles();
            String op = sc.nextLine().trim();
            switch (op) {
                case "1":
                    altaRiel(sc);
                    break;
                case "2":
                    bajaRiel(sc);
                    break;
                case "3":
                    modificarRiel(sc);
                    break;
                case "4":
                    listarRieles();
                    break;
                case "5":
                    // listarRielesDeEstacion(sc);
                    break;
                case "0":
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
            Riel riel = new Riel(codOri, codDes, distancia);
            red.insertarArco(codOri, codDes, riel);
        }

    }

    private void bajaRiel(Scanner sc) {

        int codOri = leerInt(sc, "Código estación origen: ", 1);
        int codDes = leerInt(sc, "Código estación destino: ", 1);

        if (codOri == codDes) {
            System.out.println("No se puede eliminar  un riel de una estación consigo misma.");
        } else {
            System.out.println("✓ Riel eliminado: " + red.eliminarArco(codOri, codDes));
        }

    }

    private void modificarRiel(Scanner sc) {
        int codOri = leerInt(sc, "Código estación origen: ", 1);
        int codDes = leerInt(sc, "Código estación destino: ", 1);
        if (codOri == codDes) {
            System.out.println("No se puede modificar un riel de una estación consigo misma.");
        } else {
            int nuevaDist = leerInt(sc, "Nueva distancia (km): ", 1);
            System.out.println("El resultado de la actualizacion es : " + red.modificarDistanciaRiel(codOri, codDes, nuevaDist));
        }
    }

    private void listarRieles() {
        Lista etiquetas = red.listarEtiquetas();
        if (etiquetas.esVacia()) {
            System.out.println("No hay rieles cargados.");
            return;
        }

        System.out.println("=== LISTA DE RIELES ===");

        java.util.HashSet<String> vistos = new java.util.HashSet<>();
        for (int i = 1; i <= etiquetas.longitud(); i++) {
            Riel r = (Riel) etiquetas.recuperar(i);
            String key = (Math.min(r.getCodEstacionOrigen(), r.getCodEstacionDestino()))
                    + "-"
                    + (Math.max(r.getCodEstacionOrigen(), r.getCodEstacionDestino()));
            if (vistos.add(key)) {
                System.out.println(r);
            }
        }
    }
    /*
    private void listarRielesDeEstacion(Scanner sc) {
        int cod = leerInt(sc, "Código de estación: ", 1);
        Estacion est = buscarEstacionPorCodigo(cod);
        if (est == null) {
            System.out.println("No existe estación con código " + cod);
            return;
        }

        Lista rieles = red.listarEtiquetasDeVertice(est);
        if (rieles.esVacia()) {
            System.out.println("No hay rieles conectados a esa estación.");
            return;
        }

        System.out.println("=== RIELES DE LA ESTACIÓN " + cod + " ===");
        for (int i = 1; i <= rieles.longitud(); i++) {
            Riel r = (Riel) rieles.recuperar(i);
            System.out.println(r);
        }
    }
     */
}
