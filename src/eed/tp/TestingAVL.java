package eed.tp;

public class TestingAVL {

    static String sOk = "\u001B[32m OK! \u001B[0m";
    static String sErr = " \u001B[31m ERROR \u001B[0m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String RESET = "\u001B[0m";

    public static void main(String args[]) {
        // ... (encabezados y colores)

        AVL a = new AVL();

        // FASE 1: INSERCIÓN (Árbol de 13 nodos)
        int[] valores = {5, 9, 15, 7, 3, 12, 20, 1, 6, 10, 13, 19, 50};
        for (int v : valores) {
            a.insertar(v, v);
        }

        System.out.println(VERDE + "ESTRUCTURA INICIAL (13 NODOS):" + RESET);

        System.out.println("\n toString() esperado (Árbol con 13 nodos): \n"
                + "                    9 \n"
                + "             /             \\ \n"
                + "            5               15 \n"
                + "         /     \\         /      \\ \n"
                + "        3       7       12       20 \n"
                + "       /       /       /  \\     /  \\ \n"
                + "      1       6       10  13   19  50 \n"
                + "\n");
        System.out.println(a.toString());

        System.out.println(AMARILLO + "\n--- INICIANDO PRUEBAS DE ELIMINACIÓN REALES ---" + RESET);

        // CASO 1: Eliminar un nodo HOJA (Sin hijos)
        System.out.println(CYAN + "\n[Caso 1] Eliminar HOJA (el 50):" + RESET);
        a.eliminar(50); // El 50 no tiene hijos.
        System.out.println(a.toString());
        System.out.println("final ");
        a.eliminar(1);
        a.eliminar(3);
      // a.eliminar(6);
        System.out.println("¿Existe el 50?: " + ((a.buscar(50) == null) ? sOk : sErr));
        System.out.println(a.toString());

        // CASO 2: Eliminar un nodo con UN HIJO
        // El nodo 7 es perfecto para esto: su único hijo es el 6.
        System.out.println(CYAN + "\n[Caso 2] Eliminar nodo con UN HIJO (el 7):" + RESET);
        a.eliminar(7);
        System.out.println("¿Existe el 7?: " + ((a.buscar(7) == null) ? sOk : sErr));
        System.out.println("¿El 6 (su hijo) subió correctamente?: " + ((a.buscar(6) != null) ? sOk : sErr));
        System.out.println(a.toString());

        // CASO 3: Eliminar nodo con DOS HIJOS (Subárbol)
        // El 15 tiene dos hijos: el bloque del 12 y el bloque del 20.
        System.out.println(CYAN + "\n[Caso 3] Eliminar nodo con DOS HIJOS (el 15):" + RESET);
          a.eliminar(15);
        System.out.println("¿Existe el 15?: " + ((a.buscar(15) == null) ? sOk : sErr));
        System.out.println("Verificando que sus descendientes (12, 20, 19) sigan vivos...");
        System.out.println("12: " + (a.buscar(12) != null ? "SÍ" : "NO")
                + " | 20: " + (a.buscar(20) != null ? "SÍ" : "NO")
                + " | 19: " + (a.buscar(19) != null ? "SÍ" : "NO"));
        System.out.println(a.toString());

        // CASO EXTRA: La Raíz
        System.out.println(CYAN + "\n[Caso Especial] Eliminar la RAIZ (el 9):" + RESET);
        a.eliminar(9);
        System.out.println("¿Existe el 9?: " + ((a.buscar(9) == null) ? sOk : sErr));

        System.out.println("\n" + VERDE + "ESTRUCTURA FINAL TRAS ELIMINACIONES:" + RESET);
        System.out.println(a.toString());

        System.out.println("\n" + AMARILLO + "Listado Inorden Final: " + RESET + a.listar().toString());
        System.out.println("altura " + a);
    }
}
