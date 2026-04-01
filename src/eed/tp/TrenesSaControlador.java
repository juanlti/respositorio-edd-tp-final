package eed.tp;

import Grafo.Grafo;
import Conjuntista.AVL;
import abm.AbmEstacion;
import abm.AbmLinea;
import Modelos.Linea;
import abm.AbmRiel;
import abm.AbmTren;
import eed.tp.Ejercicios_6_7_8.Ejercicio_6_Tren;
import eed.tp.Ejercicios_6_7_8.Ejercicio_7_Estacion;
import eed.tp.Ejercicios_6_7_8.Ejercicio_8_Viaje;
import eed.tp.Servicios.CargarInicialDesdeArchivo;
import java.io.IOException;
import java.util.Scanner;

public class TrenesSaControlador {

    public final AVL trenes = new AVL();
    public final AVL estaciones = new AVL();
    public final java.util.HashMap<String, Linea> lineas = new java.util.HashMap<>();
    public final Grafo red = new Grafo();

    AbmTren abmTren = new AbmTren(this.trenes);
    AbmEstacion abmEstaciones = new AbmEstacion(this.estaciones, this.red);
    AbmRiel abmRiel = new AbmRiel(this.red);
    AbmLinea abmLinea = new AbmLinea(this.lineas, this.estaciones);
    Ejercicio_6_Tren ejercio_6_Tren = new Ejercicio_6_Tren(this.trenes, this.lineas);
    Ejercicio_7_Estacion ejercio_7_Estacion = new Ejercicio_7_Estacion(this.estaciones);
    Ejercicio_8_Viaje ejercio_8_Viaje = new Ejercicio_8_Viaje(this.estaciones, this.red);

    public void comenzar() {
        Scanner sc = new Scanner(System.in);
        int opc = -1; // Le ponemos -1 para obligarlo a entrar al while la primera vez

        while (opc != 0) {
            System.out.print(
                    "\n================== TrenesSA ==================\n"
                    + "1) ABM Trenes\n"
                    + "2) ABM Estaciones\n"
                    + "3) ABM Líneas\n"
                    + "4) ABM Rieles\n"
                    + "5) Consultas de Trenes\n"
                    + "6) Consultas de Estaciones\n"
                    + "7) Consultas de Viajes\n"
                    + "8) Mostrar sistema\n"
                    + "0) Salir\n"
                    + "------------------------------------------------\n"
                    + "Opción: "
            );

            opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1:
                    abmTren.abmTrenes(sc);
                    break;
                case 2:
                    abmEstaciones.abmEstaciones(sc);
                    break;
                case 3:
                    abmLinea.abmLineas(sc);
                    break;
                case 4:
                    abmRiel.abmRieles(sc);
                    break;
                case 5:
                    ejercio_6_Tren.consultaTrenes(sc);
                    break;
                case 6:
                    ejercio_7_Estacion.consultasEstaciones(sc);
                    break;
                case 7:
                    ejercio_8_Viaje.consultasViajes(sc);
                    break;
                case 8:
                    mostrarSistema();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
    }

    private void mostrarSistema() {
        System.out.println("\n--- 1. ÁRBOL AVL DE TRENES ---");
        this.trenes.mostrarEstructura();

        System.out.println("\n--- 2. ÁRBOL AVL DE ESTACIONES ---");
        this.estaciones.mostrarEstructura();

        System.out.println("\n--- 3. MAPEO DE LÍNEAS (HashMap) ---");
        if (this.lineas.isEmpty()) {
            System.out.println("El mapeo de líneas está vacío.");
        } else {
            System.out.println("Capacidad actual (elementos): " + this.lineas.size());
            for (java.util.Map.Entry<String, Linea> entrada : this.lineas.entrySet()) {
                System.out.println("Clave (Key): [" + entrada.getKey() + "] -> Valor (Value): " + entrada.getValue().toString());
            }
        }

        System.out.println("\n--- 4. ESTRUCTURA DE LA RED (GRAFO) ---");

        System.out.println("\n=================================================");
    }

    public void cargarInicialDesdeArchivo(String url) throws IOException {
        CargarInicialDesdeArchivo carga = new CargarInicialDesdeArchivo(this.trenes, this.estaciones, this.red, this.lineas, url);
        carga.cargarInicialDesdeArchivo();
    }

}
