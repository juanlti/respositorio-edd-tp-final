package eed.tp;

import eed.tp.Servicios.LogHelper;
import eed.tp.Nodos.NodoAVL;

public class AVL {

    private NodoAVL raiz = null;

    public AVL() {
        this.raiz = null;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void eliminar(Object x) {

        if (this.raiz != null) {
            this.raiz = metodoEliminarCasos(this.raiz, x);
        }
    }

    //si eliminar > nodo.getClave() => mayor a 0.
    //si eliminar < nodo.getClave() => menor a 0.
    // si eliminar === nodo.getClave() => 0
    private NodoAVL metodoEliminarCasos(NodoAVL nodo, Object eliminar) {
        // Caso base: si el árbol está vacío o no se encontró el elemento
        if (nodo != null) {

            int comparacion = ((Comparable) eliminar).compareTo(nodo.getClave());

            // 1. Buscar el nodo a eliminar
            if (comparacion < 0) {
                // Buscamos por la izquierda y actualizamos el hijo izquierdo
                nodo.setIzquierdo(metodoEliminarCasos(nodo.getIzquierdo(), eliminar));
            } else if (comparacion > 0) {
                // Buscamos por la derecha y actualizamos el hijo derecho
                nodo.setDerecho(metodoEliminarCasos(nodo.getDerecho(), eliminar));
            } else {
                // 2. ¡Nodo encontrado! Evaluamos los 3 casos de eliminación
                int tipoCaso = catalogarTipoDeElimninacion(nodo);

                switch (tipoCaso) {

                    case 1: // hoja
                        nodo = null;
                        break;

                    case 2: // solo hijo derecho
                        nodo = nodo.getDerecho();
                        break;

                    case 3: // solo hijo izquierdo
                        nodo = nodo.getIzquierdo();
                        break;

                    case 4: // dos hijos
                        NodoAVL sucesor = this.maximoElem(nodo.getIzquierdo());

                        // Reemplazamos los datos del nodo actual con los del sucesor
                        nodo.setElemento(sucesor.getClave(), sucesor.getClave());

                        // Eliminamos el sucesor original que quedó duplicado en el subárbol derecho
                        nodo.setIzquierdo(metodoEliminarCasos(nodo.getIzquierdo(), sucesor.getClave()));

                        break;

                }

            }

        }
        return balancear(nodo);
    }

    private int catalogarTipoDeElimninacion(NodoAVL nodo) {
        int tipoCaso = -1;
        // Caso 1: Es Hoja (No tiene hijos)
        if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
            tipoCaso = 1;
        } // Caso 2: Tiene un solo hijo (Derecho)
        else if (nodo.getDerecho() != null && nodo.getIzquierdo() == null) {
            tipoCaso = 2;
            // Caso 3: Tiene un solo hijo (Izquierdo)
        } else if (nodo.getDerecho() == null && nodo.getIzquierdo() != null) {
            tipoCaso = 3;
        } // Caso 3: Tiene dos hijos
        else if (nodo.getDerecho() != null && nodo.getIzquierdo() != null) {
            // Buscamos el sucesor, cambiamos datos y borramos el duplicado abajo
            NodoAVL sucesor = this.minimoElem(nodo.getDerecho());

            nodo.setElemento(sucesor.getClave(), sucesor.getClave());
            tipoCaso = 4;

        }

        return tipoCaso;

    }

    public boolean insertar(Comparable clave, Object data) {
        this.raiz = insertarAux(this.raiz, clave, data);

        LogHelper.registrar("ABM: Se agrego " + data);
        return true;
    }

    private NodoAVL insertarAux(NodoAVL nodo, Comparable clave, Object data) {
        NodoAVL resultado = nodo;

        // 1) Caso base: El lugar está vacío
        if (nodo == null) {
            resultado = new NodoAVL(clave, data);
        } else {
            Comparable contenidoClave = nodo.getClave();
            int comparacion = clave.compareTo(contenidoClave);

            // 2) Navegación recursiva
            if (comparacion < 0) {
                nodo.setIzquierdo(insertarAux(nodo.getIzquierdo(), clave, data));
                // Después de insertar, balanceamos el nodo actual
                resultado = balancear(nodo);
            } else if (comparacion > 0) {
                nodo.setDerecho(insertarAux(nodo.getDerecho(), clave, data));
                // Después de insertar, balanceamos el nodo actual
                resultado = balancear(nodo);
            }
            // Si comparacion == 0, no hacemos nada (clave duplicada)
            // y el "resultado" sigue siendo "nodo"
        }

        // 3) Único punto de salida
        return resultado;
    }

    private NodoAVL balancear(NodoAVL n) {
        if (n != null) {

            n.recalcularAltura();
            int b = n.calcularBalance(); // izq - der

            if (b > 1) {
                if (n.getIzquierdo().calcularBalance() < 0) {
                    n.setIzquierdo(rotarIzquierda(n.getIzquierdo())); // LR
                }
                n = rotarDerecha(n); // LL
            }
            if (b < -1) {
                if (n.getDerecho().calcularBalance() > 0) {
                    n.setDerecho(rotarDerecha(n.getDerecho())); // RL
                }
                n = rotarIzquierda(n); // RR
            }

        }
        return n;
    }

    private NodoAVL rotarIzquierda(NodoAVL nodo) {
        // pivot
        NodoAVL h = nodo.getDerecho();
        // temporal
        NodoAVL temp = h.getIzquierdo();
        h.setIzquierdo(nodo);
        nodo.setDerecho(temp);

        nodo.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private NodoAVL rotarDerecha(NodoAVL nodo) {

        NodoAVL h = nodo.getIzquierdo();

        NodoAVL temp = h.getDerecho();
        h.setDerecho(nodo);
        nodo.setIzquierdo(temp);

        nodo.recalcularAltura();
        h.recalcularAltura();
        return h;
    }

    private NodoAVL rotarIzquierdaDerecha(NodoAVL nodo) {
        nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
        return rotarDerecha(nodo);
    }

    private NodoAVL rotarDerechaIzquierda(NodoAVL nodo) {
        nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
        return rotarIzquierda(nodo);
    }

    public Lista listar() {
        Lista lista = new Lista();
        listarAux(this.raiz, lista);
        return lista;
    }

    public void listarAux(NodoAVL nodo, Lista lista) {
        if (nodo != null) {
            listarAux(nodo.getDerecho(), lista);
            String elemento = "[clave: " + nodo.getClave() + " informacion :" + nodo.getElemento() + " ]";
            lista.insertar(elemento, lista.longitud() + 1);
            listarAux(nodo.getIzquierdo(), lista);
        }
    }

    public NodoAVL minimoElem(NodoAVL nodo) {

        while (nodo.getIzquierdo() != null) {

            nodo = nodo.getIzquierdo();
        }

        return nodo;
    }

    public NodoAVL maximoElem(NodoAVL nodo) {

        while (nodo.getDerecho() != null) {

            nodo = nodo.getDerecho();
        }

        return nodo;
    }

    public Lista listarRango(int minimo, int maximo) {
        Lista lista = new Lista();
        listarRangoAux(this.raiz, lista, minimo, maximo);
        return lista;
    }

    private void listarRangoAux(NodoAVL nodo, Lista lista, int minimo, int maximo) {
        if (nodo != null) {
            if (nodo.getClave().compareTo(maximo) < 0) {
                listarRangoAux(nodo.getDerecho(), lista, minimo, maximo);
            }
            if (nodo.getClave().compareTo(minimo) >= 0 && nodo.getClave().compareTo(maximo) <= 0) {
                lista.insertar(nodo.getElemento(), 1);
            }
            if (nodo.getClave().compareTo(minimo) > 0) {
                listarRangoAux(nodo.getIzquierdo(), lista, minimo, maximo);
            }
        }
    }

    public String toString() {
        String res = " ";
        if (this.raiz != null) {
            res = toStringAux(this.raiz, res);
        }
        return res;
    }

    private String toStringAux(NodoAVL nodo, String s) {
        if (nodo != null) {
            s += "\n" + nodo.getElemento() + "\t";
            if (nodo.getIzquierdo() != null) {
                System.out.println("actual " + nodo.getIzquierdo().getElemento());
            }
            NodoAVL izquierdo = nodo.getIzquierdo();
            NodoAVL derecho = nodo.getDerecho();
            s += "HI: " + ((izquierdo != null) ? izquierdo.getElemento() : "-") + "\t"
                    + "HD: " + ((derecho != null) ? derecho.getElemento() : "-");

            s = toStringAux(nodo.getIzquierdo(), s);
            s = toStringAux(nodo.getDerecho(), s);
        }
        return s;
    }

    public Object buscar(Comparable codigo) {
        NodoAVL nodo = this.raiz;
        boolean pertenece = false;
        Object elementoEncontrado = null;

        while (nodo != null && !pertenece) {
            if (nodo.getClave().compareTo(codigo) > 0) {
                nodo = nodo.getIzquierdo();
            } else if (nodo.getClave().compareTo(codigo) < 0) {
                nodo = nodo.getDerecho();
            } else {
                pertenece = true;
                elementoEncontrado = nodo.getElemento();
            }
        }
        return elementoEncontrado;

    }

    public void mostrarEstructura() {
        if (this.raiz == null) {
            System.out.println("El árbol AVL está vacío.");
        } else {
            mostrarEstructuraAux(this.raiz, "");
        }
    }

    private void mostrarEstructuraAux(NodoAVL nodo, String prefijo) {
        if (nodo != null) {
            String valorIzq = (nodo.getIzquierdo() != null) ? nodo.getIzquierdo().getElemento().toString() : "-";
            String valorDer = (nodo.getDerecho() != null) ? nodo.getDerecho().getElemento().toString() : "-";

            System.out.println(prefijo + "Nodo: [" + nodo.getElemento().toString() + "] "
                    + "| Altura: " + nodo.getAltura()
                    + " | Hijo Izq: " + valorIzq
                    + " | Hijo Der: " + valorDer);

            mostrarEstructuraAux(nodo.getIzquierdo(), prefijo + "    ");
            mostrarEstructuraAux(nodo.getDerecho(), prefijo + "    ");
        }
    }

    public Lista listarPreorden() {
        Lista ls = new Lista();
        preordenAux(this.raiz, ls);
        return ls;
    }

    private void preordenAux(NodoAVL nodo, Lista ls) {

        if (nodo != null) {
            System.out.println(nodo.getElemento());
            ls.insertar(nodo.getElemento(), ls.longitud() + 1);
            preordenAux(nodo.getIzquierdo(), ls);
            preordenAux(nodo.getDerecho(), ls);

        }

    }

    public void vaciar() {
        this.raiz = null;
    }
}
