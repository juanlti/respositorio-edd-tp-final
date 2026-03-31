/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp.Servicios;

import Estructura.AVL;
import Modelos.Estacion;
import Estructura.Grafo;
import Modelos.Linea;
import Estructura.Lista;

import Modelos.Tren;
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

    private AVL trenes;
    private AVL estaciones;
    private Grafo red;
    private HashMap<String, Linea> lineas;
    private String fileName;

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

        if (p.length == 8) {

            String nombre = p[1];
            String ciudad = p[2];
            String calle = p[3];
            String numero = p[4];
            String cp = p[5];
            int vias = Integer.parseInt(p[6]);
            int plataformas = Integer.parseInt(p[7]);

            Estacion nuevaEstacion = new Estacion(
                    nombre, calle, numero, ciudad, cp, vias, plataformas
            );

            estaciones.insertar(nombre, nuevaEstacion);
            red.insertarVertice(nombre);

        } else {
            System.out.println("⚠️ Línea inválida: " + String.join(";", p));
        }
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

            String nombreEstacion = p[i];
            Object estacion = estaciones.buscar(nombreEstacion); // o método equivalente

            if (estacion != null) {
                recorrido.insertar(nombreEstacion, recorrido.longitud() + 1);
            } else {
                System.out.println("⚠️ Estación no encontrada: " + nombreEstacion);
            }
        }

        lineas.put(nombreLinea, new Linea(nombreLinea, recorrido));
    }

    private void cargarRielDesdeArchivo(String[] p) {
        if (p.length < 4) {
            return;
        }

        String origen = p[1];
        String destino = p[2];
        int distancia = Integer.parseInt(p[3]);

        if (estaciones.buscar(origen) == null || estaciones.buscar(destino) == null) {
            System.out.println("⚠️ Riel con estaciones inexistentes: " + origen + " / " + destino);
            return;
        }

        red.insertarArco(origen, destino, distancia);
    }

    private void cargarTrenDesdeArchivo(String[] p) {
        if (p.length < 6) {
            return;
        }

        int idTren = Integer.parseInt(p[1]);
        String propulsion = p[2];

        int vagPasajeros = Integer.parseInt(p[3]);
        int vagCarga = 0;

        String nombreEstacionActual = p[4];
        String nombreLinea = p[5];

        Tren nuevoTren = new Tren(propulsion, vagPasajeros, vagCarga, nombreLinea);

        Estacion estacionActual = (Estacion) estaciones.buscar(nombreEstacionActual);

        if (estacionActual == null) {
            System.out.println("Advertencia: Estación " + nombreEstacionActual + " no encontrada para el tren " + idTren);
        }

        if (!nombreLinea.equalsIgnoreCase("no-asignado")) {
            if (!lineas.containsKey(nombreLinea)) {
                System.out.println("Advertencia: Línea '" + nombreLinea + "' no encontrada para el tren " + idTren);
            }
        }

        trenes.insertar(idTren, nuevoTren);
    }

}
