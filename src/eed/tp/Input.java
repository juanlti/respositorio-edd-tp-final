/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eed.tp;

import java.util.Scanner;

/**
 *
 * @author juanc
 */
public class Input {

    public static String leerNoVacio(Scanner in, String prompt) {
        String s;
        do {
            System.out.print(prompt);
            s = in.nextLine().trim();
        } while (s.isEmpty());
        return s;
    }

    public static String leerOpcional(Scanner in, String prompt) {
        System.out.print(prompt);
        String s = in.nextLine();
        return (s == null) ? "" : s.trim();
    }

    public static int leerInt(Scanner in, String prompt, int minInclusive) {
        while (true) {
            System.out.print(prompt);
            String s = in.nextLine().trim();
            try {
                int val = Integer.parseInt(s);
                if (val < minInclusive) {
                    System.out.println("Debe ser >= " + minInclusive);
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un entero válido.");
            }
        }
    }

    public static Integer leerIntOpcional(Scanner in, String prompt, int minInclusive) {
        System.out.print(prompt);
        String s = in.nextLine().trim();
        if (s.isEmpty()) {
            return null;
        }

        try {
            int val = Integer.parseInt(s);
            if (val < minInclusive) {
                System.out.println("Valor inválido; se ignora el cambio.");
                return null;
            }
            return val;
        } catch (NumberFormatException e) {
        }
        return null;
    }
}
