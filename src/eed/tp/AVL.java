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

    public boolean eliminar(Object x) {
        boolean eliminado = false;

        if (this.raiz != null) {
            metodoEliminarCasos(this.raiz, x);
        }
        if (this.raiz.equals(x) && this.raiz.getDerecho() == null && this.raiz.getIzquierdo() == null) {
            this.raiz = null;
        } else {

        }

        return eliminado;

    }

    private void eliminarHoja() {

    }

    private int metodoEliminarCasos(NodoAVL nodo, Object eliminar) {
        int tipoCaso = -1;
        if (nodo != null) {
            //    return null; // Caso base: no encontramos nada

            int comparacion = ((Comparable) eliminar).compareTo(nodo.getClave());
            //si eliminar > nodo.getClave() => mayor a 0.
            //si eliminar < nodo.getClave() => menor a 0.
            // si eliminar === nodo.getClave() => 0
            System.out.println("valor de comparacion " + comparacion + " respecto a " + nodo.getClave());

            if (comparacion < 0) {
                // Seguimos buscando a la izquierda
                metodoEliminarCasos(nodo.getIzquierdo(), eliminar);

            } else if (comparacion > 0) {
                metodoEliminarCasos(nodo.getDerecho(), eliminar);

            } else {
                // ¡AQUÍ ES DONDE LO ENCONTRAMOS! (comparacion == 0)

                // Caso 1: Es Hoja (No tiene hijos)
                if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                    tipoCaso = 1;
                } // Caso 2: Tiene un solo hijo (Derecho)
                else if (nodo.getDerecho() != null && nodo.getIzquierdo() == null) {
                    tipoCaso = 2;
                    // Caso 2: Tiene un solo hijo (Izquierdo)
                } else if (nodo.getDerecho() == null && nodo.getIzquierdo() != null) {
                    tipoCaso = 3;
                } // Caso 3: Tiene dos hijos
                else {
                    // Buscamos el sucesor, cambiamos datos y borramos el duplicado abajo
                    tipoCaso = 4;

                }

                if (tipoCaso != -1) {
                    boolean eliminado = false;
                    switch (tipoCaso) {

                        case 1:
                            nodo.setIzquierdo(null);
                            break;
                        case 2:
                            eliminado = this.eliminarConUnHijo(nodo, eliminar, false);
                            break;
                        case 3:
                            eliminado = this.eliminarConUnHijo(nodo, eliminar, true);
                            break;
                        case 4:
                            NodoAVL sucesor = minimoElem(nodo.getDerecho());
                            nodo.setElemento(sucesor.getClave(), sucesor.getElemento());
                            nodo.setDerecho(metodoEliminarCasos(nodo.getDerecho(), sucesor.getClave()));
                            break;

                    }

                    if (!eliminado) {
                        System.out.println("ERROR AL ELIMINAR REVISAR EL CASO: " + tipoCaso);
                    }
                    tipoCaso = -1;

                }

                // Al salir de cualquier bloque, balanceamos antes de retornar al padre
            }
            this.balancear(nodo);
        }

    }

    private boolean eliminarConDosHijos(NodoAVL padre, Object eliminar, boolean isIzquierdo) {
        NodoAVL temp = null;
        if (padre.getDerecho().getClave().compareTo(eliminar) == 0) {
            NodoAVL sucesor = minimoElem(padre.getDerecho());

        } else {
            NodoAVL sucesor = minimoElem(padre.getDerecho());

        }
        padre.setDerecho(null);
        padre.setDerecho(temp);
        return true;

    }

    private boolean eliminarConUnHijo(NodoAVL padre, Object eliminar, boolean isIzquierdo) {
        NodoAVL temp = null;
        if (padre.getDerecho().getClave().compareTo(eliminar) == 0) {
            if (isIzquierdo) {
                temp = padre.getDerecho().getIzquierdo();

            } else {
                temp = padre.getDerecho().getDerecho();
            }

        } else {
            if (isIzquierdo) {
                temp = padre.getIzquierdo().getIzquierdo();

            } else {
                temp = padre.getIzquierdo().getDerecho();
            }

        }
        padre.setDerecho(null);
        padre.setDerecho(temp);
        return true;

    }

    public boolean insertar(Comparable clave, Object data) {
        this.raiz = insertarAux(this.raiz, clave, data);

        LogHelper.registrar("ABM: Se agrego " + data);
        return true;
    }

    private NodoAVL insertarAux(NodoAVL nodo, Comparable clave, Object data) {
        // 1) Caso base: Si el lugar está vacío, creamos el nodo
        if (nodo == null) {
            return new NodoAVL(clave, data);
        }

        Comparable contenidoClave = nodo.getClave();

        // 2) Navegación recursiva
        if (clave.compareTo(contenidoClave) < 0) {
            nodo.setIzquierdo(insertarAux(nodo.getIzquierdo(), clave, data));
        } else if (clave.compareTo(contenidoClave) > 0) {
            nodo.setDerecho(insertarAux(nodo.getDerecho(), clave, data));
        } else {
            // Clave duplicada: no hacemos nada
            return nodo;
        }

        // 3) IMPORTANTE: Recalcular altura y balancear SIEMPRE
        // (Quitamos los if/else anidados para que esto se ejecute al volver de la recursión)
        return balancear(nodo);
    }

    private NodoAVL balancear(NodoAVL n) {
        if (n == null) {
            return null;
        }
        n.recalcularAltura();
        int b = n.calcularBalance(); // izq - der

        if (b > 1) {
            if (n.getIzquierdo().calcularBalance() < 0) {
                n.setIzquierdo(rotarIzquierda(n.getIzquierdo())); // LR
            }
            return rotarDerecha(n); // LL
        }
        if (b < -1) {
            if (n.getDerecho().calcularBalance() > 0) {
                n.setDerecho(rotarDerecha(n.getDerecho())); // RL
            }
            return rotarIzquierda(n); // RR
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
            lista.insertar(nodo.getElemento(), lista.longitud() + 1);
            listarAux(nodo.getIzquierdo(), lista);
        }
    }

    public NodoAVL minimoElem(NodoAVL nodo) {

        // bajada por la izquierda
        while (nodo != null) {
            nodo = nodo.getDerecho();
        }
        return nodo;
    }

    public Object maximoElem() {
        NodoAVL nodo = this.raiz;
        // bajada por la derecha
        while (nodo != null) {
            nodo = nodo.getDerecho();
        }
        return nodo.getElemento();
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

    // ---- metodos de test, 
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
