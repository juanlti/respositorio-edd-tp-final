package eed.tp;

import eed.tp.Estacion.AbmEstacion;
import eed.tp.Linea.AbmLinea;
import eed.tp.Linea.Linea;
import eed.tp.Riel.AbmRiel;
import eed.tp.Tren.AbmTren;
import eed.tp.Ejercicios_6_7_8.Ejercicio_6_Tren;
import eed.tp.Ejercicios_6_7_8.Ejercicio_7_Estacion;
import eed.tp.Ejercicios_6_7_8.Ejercicio_8_Viaje;
import eed.tp.Estacion.Estacion;
import eed.tp.Tren.Tren;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TrenesSA {

    // ===== INICIO GRUPO: ESTRUCTURAS =====
    public final AVL trenes = new AVL();
    public final AVL estaciones = new AVL();
    public final java.util.HashMap<String, Linea> lineas = new java.util.HashMap<>();
    public final Grafo red = new Grafo();

    AbmTren abmTren = new AbmTren(this.trenes);
    AbmEstacion abmEstaciones = new AbmEstacion(this.estaciones, this.red);
    AbmRiel abmRiel = new AbmRiel(this.estaciones, this.red);
    AbmLinea abmLinea = new AbmLinea(this.lineas, this.estaciones);
    Ejercicio_6_Tren ejercio_6_Tren = new Ejercicio_6_Tren(this.trenes, this.lineas);
    Ejercicio_7_Estacion ejercio_7_Estacion = new Ejercicio_7_Estacion(this.estaciones);
    Ejercicio_8_Viaje ejercio_8_Viaje = new Ejercicio_8_Viaje(this.estaciones, this.red);

    // ===== FIN GRUPO: ESTRUCTURAS =====
    // ===== INICIO GRUPO: SISTEMA Y MENÚ PRINCIPAL =====
    public void comenzar() {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            imprimirMenu();
            String linea = sc.nextLine().trim();
            int opc;
            try {
                opc = Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida.");
                continue;
            }

            salir = procesarOpcion(opc, sc);
        }

        sc.close();
    }

    public boolean procesarOpcion(int opc, Scanner sc) {
        switch (opc) {
            case 1:
                cargarInicial(sc);
                break;
            case 2:
                abmTren.abmTrenes(sc);
                break;
            case 3:
                abmEstaciones.abmEstaciones(sc);
                break;
            case 4:
                abmLinea.abmLineas(sc);
                //
                break;
            case 5:
                abmRiel.abmRieles(sc);
                break;
            case 6:
                ejercio_6_Tren.consultaTrenes(sc);
                break;
            case 7:
                ejercio_7_Estacion.consultasEstaciones(sc);
                break;
            case 8:
                ejercio_8_Viaje.consultasViajes(sc);
                break;
            case 9:
                mostrarSistema();
                break;
            case 0:
                System.out.println("Saliendo...");
                return true;
            default:
                System.out.println("Opción inválida.");
                break;

        }
        return false;
    }

    private void imprimirMenu() {
        System.out.print(
                "\n================== TrenesSA ==================\n"
                + "1) Cargar datos iniciales (archivo)\n"
                + "2) ABM Trenes\n"
                + "3) ABM Estaciones\n"
                + "4) ABM Líneas\n"
                + "5) ABM Rieles\n"
                + "6) Consultas de Trenes\n"
                + "7) Consultas de Estaciones\n"
                + "8) Consultas de Viajes\n"
                + "9) Mostrar sistema\n"
                + "0) Salir\n"
                + "------------------------------------------------\n"
                + "Opción: "
        );
    }
//9

    public void mostrarSistema() {
        System.out.println("=================================================");
        System.out.println("          ESTADO ACTUAL DEL SISTEMA              ");
        System.out.println("=================================================");

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
        this.red.mostrarEstructura();

        System.out.println("\n=================================================");
    }

    private void cargarInicial(Scanner sc) {
        System.out.println("[TODO] Carga inicial (StringTokenizer)...");
    }

    private boolean trenInsertar(int codigo, Tren tren) {
        return trenes.insertar((Comparable) codigo, tren);
    }

    public void cargarInicialDesdeArchivo(String fileName) throws IOException {

        java.io.File archivo = new java.io.File(fileName);
        if (!archivo.exists()) {

            String[] lista = new java.io.File(".").list();
            for (String s : lista) {
                System.out.println("   -> " + s);
            }
            return;
        }

        System.out.println("✓ ¡ARCHIVO DETECTADO! Iniciando lectura...");

        List<String[]> lineasPend = new ArrayList<>();
        List<String[]> rielesPend = new ArrayList<>();
        List<String[]> trenesPend = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String raw;
            int nro = 0;

            while ((raw = br.readLine()) != null) {
                nro++;
                String line = raw.replace("\uFEFF", "").trim();

                if (line.isEmpty() || line.startsWith("#") || line.startsWith("//")) {
                    continue;
                }

                char tipo = Character.toUpperCase(line.charAt(0));
                String[] p = line.split(";");
                for (int i = 0; i < p.length; i++) {
                    p[i] = p[i].trim();
                }

                switch (tipo) {
                    case 'E':
                        cargarEstacionDesdeArchivo(p);
                        break;
                    case 'L':
                        lineasPend.add(p);
                        break;
                    case 'R':
                        rielesPend.add(p);
                        break;
                    case 'T':
                        trenesPend.add(p);
                        break;
                    default:
                        System.out.println("Tipo desconocido en línea " + nro + ": " + line);
                        break;
                }
            }
        }

        /*
        for (String[] p : lineasPend) {
            cargarLineaDesdeArchivo(p);
        }
        for (String[] p : rielesPend) {
            cargarRielDesdeArchivo(p);
        }
        for (String[] p : trenesPend) {
            cargarTrenDesdeArchivo(p);
        }
         */
        System.out.println("Carga inicial OK.");
    }

    private void cargarEstacionDesdeArchivo(String[] p) {

        int codigo = Integer.parseInt(p[1]);
        String nombre = p[2];
        String ciudad = p[3];
        String calle = p[4];
        String numero = p[5];
        String cp = p[6];
        int vias = Integer.parseInt(p[7]);
        int plataformas = Integer.parseInt(p[8]);

        estaciones.insertar(codigo, new Estacion(nombre, calle, numero, ciudad, cp, vias, plataformas));
    }

    private void cargarLineaDesdeArchivo(String[] p) {

        String nombreLinea = p[1];
        if (lineas.containsKey(nombreLinea)) {
            return;
        }

        Lista recorrido = new Lista();
        for (int i = 2; i < p.length; i++) {
            int codEst = Integer.parseInt(p[i]);
            Estacion estacion = (Estacion) estaciones.buscar(codEst);
            if (estacion != null) {
                recorrido.insertar(codEst, recorrido.longitud() + 1);
                red.insertarVertice(codEst);
                Linea l = new Linea(nombreLinea, recorrido);
                lineas.put(nombreLinea, l);
            }

        }

    }
    /*
    private void cargarRielDesdeArchivo(String[] p) {

        int codOri = Integer.parseInt(p[1]);
        int codDes = Integer.parseInt(p[2]);
        int dist = Integer.parseInt(p[3]);

        Estacion ori = buscarEstacionPorCodigo(codOri);
        Estacion des = buscarEstacionPorCodigo(codDes);
        if (ori == null || des == null) {
            throw new IllegalStateException("Riel referencia estación inexistente: " + codOri + " / " + codDes);
        }

        red.insertarVertice(ori);
        red.insertarVertice(des);
        if (red.existeArco(ori, des) || red.existeArco(des, ori)) {
            return;
        }

        Riel r = new Riel(codOri, codDes, dist);
        red.insertarArco(ori, des, true, r);
        // true => no dirigido (ida y vuelta)
    }

    private void cargarTrenDesdeArchivo(String[] p) {
        int codigo = Integer.parseInt(p[1]);
        String prop = p[2];
        int vagPas = Integer.parseInt(p[3]);
        int vagCar = Integer.parseInt(p[4]);
        String linea = p[5];
        if (!linea.equalsIgnoreCase("no-asignado") && !lineas.containsKey(linea)) {
            throw new IllegalStateException("Tren " + codigo + " referencia línea inexistente: " + linea);
        }
        if (trenes.buscar(codigo) == null) {
            return;
        }

        trenInsertar(codigo, new Tren(codigo, prop, vagPas, vagCar, linea));
    }

     */

}
