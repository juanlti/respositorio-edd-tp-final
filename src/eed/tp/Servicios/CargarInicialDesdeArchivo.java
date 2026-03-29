/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp.Servicios;

import eed.tp.AVL;
import eed.tp.Estacion.Estacion;
import eed.tp.Grafo;
import eed.tp.Linea.Linea;
import eed.tp.Lista;

import eed.tp.Tren.Tren;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author juanc
 */
public class CargarInicialDesdeArchivo {
// 1. Declaramos las estructuras que la clase necesita manipular

    private AVL trenes;
    private AVL estaciones;
    private Grafo red;
    private HashMap<String, Linea> lineas; // O la estructura que uses para líneas
    private String fileName;

    // 2. El Constructor recibe las instancias reales desde el Main/Controlador
    public CargarInicialDesdeArchivo(AVL trenes, AVL estaciones, Grafo red, HashMap<String, Linea> lineas, String url) {
        this.trenes = trenes;
        this.estaciones = estaciones;
        this.red = red;
        this.lineas = lineas;
        this.fileName = url;
    }

    public void cargarInicialDesdeArchivo() throws IOException {
        java.io.File archivo = new java.io.File(this.fileName);
        if (!archivo.exists()) {
            System.out.println("Archivo no encontrado. Archivos disponibles:");
            for (String s : new java.io.File(".").list()) {
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
                String[] p = line.split("\\s*;\\s*");

                switch (Character.toUpperCase(line.charAt(0))) {
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

        lineasPend.forEach(this::cargarLineaDesdeArchivo);
        rielesPend.forEach(this::cargarRielDesdeArchivo);
        trenesPend.forEach(this::cargarTrenDesdeArchivo);

        System.out.println("Carga inicial OK.");
    }

    private void cargarEstacionDesdeArchivo(String[] p) {
        if (p.length < 9) {
            System.out.println("Advertencia: Línea de estación incompleta omitida -> " + Arrays.toString(p));
            return;
        }

        int codEstacion = Integer.parseInt(p[1]);
        Estacion nuevaEstacion = new Estacion(
                p[2], p[4], p[5], p[3], p[6], Integer.parseInt(p[7]), Integer.parseInt(p[8])
        );

        estaciones.insertar(codEstacion, nuevaEstacion);
        red.insertarVertice(codEstacion);
    }

    private void cargarLineaDesdeArchivo(String[] p) {
        if (p.length < 2) {
            return;
        }
        String nombreLinea = p[1];

        if (lineas.containsKey(nombreLinea)) {
            return;
        }

        Lista recorrido = new Lista();
        for (int i = 2; i < p.length; i++) {
            int codEst = Integer.parseInt(p[i]);
            Estacion estacion = buscarEstacionPorCodigo(codEst);

            if (estacion != null) {
                recorrido.insertar(codEst, recorrido.longitud() + 1);
            } else {
                System.out.println("Advertencia: Estación " + codEst + " no encontrada para la línea " + nombreLinea);
            }
        }

        lineas.put(nombreLinea, new Linea(nombreLinea, recorrido));
    }

    private void cargarRielDesdeArchivo(String[] p) {
        if (p.length < 4) {
            return;
        }

        if (p[1] == null || p[2] == null) {
            System.out.println("Advertencia: Riel omite estación inexistente: " + p[1] + " / " + p[2]);
            return;
        }
        int codOri = Integer.parseInt(p[1]);
        int codDes = Integer.parseInt(p[2]);
        int distancia = Integer.parseInt(p[3]);

        red.insertarArco(codOri, codDes, distancia);

    }

    private void cargarTrenDesdeArchivo(String[] p) {
        if (p.length < 6) {
            return;
        }

        int idTren = Integer.parseInt(p[1]);
        String propulsion = p[2];

        int vagPasajeros = Integer.parseInt(p[3]);
        int vagCarga = 0;

        int codEstacionActual = Integer.parseInt(p[4]);
        String nombreLinea = p[5];

        // 2. Creamos el objeto usando TU constructor exacto
        Tren nuevoTren = new Tren(propulsion, vagPasajeros, vagCarga, nombreLinea);

        // 3. Verificamos que la estación actual exista
        Estacion estacionActual = buscarEstacionPorCodigo(codEstacionActual);
        if (estacionActual != null) {
            // Opcional: Si tu clase Estacion guarda qué trenes están allí, iría aquí
        } else {
            System.out.println("Advertencia: Estación " + codEstacionActual + " no encontrada para el tren " + idTren);
        }

        // 4. Verificamos que la línea exista (solo como control, ya que el tren guarda el String)
        if (!nombreLinea.equalsIgnoreCase("no-asignado")) {
            if (!lineas.containsKey(nombreLinea)) {
                System.out.println("Advertencia: Línea '" + nombreLinea + "' no encontrada para el tren " + idTren);
            }
        }

        // 5. Insertamos el tren en tu árbol AVL de trenes (Clave = ID, Valor = Objeto)
        trenes.insertar(idTren, nuevoTren);
    }

    private Estacion buscarEstacionPorCodigo(int codigo) {
        return (Estacion) estaciones.buscar(codigo);
    }

}
