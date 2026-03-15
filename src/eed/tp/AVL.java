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

    public boolean eliminar(Comparable x) {
        boolean eliminado = false;
        Object elemento = this.buscar(x);
        if (elemento != null) {

            this.raiz = eliminarAux(this.raiz, x);

            eliminado = true;
        }
        return eliminado;

    }

    public boolean insertar(Comparable clave, Object data) {
        this.raiz = insertarAux(this.raiz, clave, data);
        LogHelper.registrar("ABM: Se agrego " + data);
        return true;
    }

    private NodoAVL insertarAux(NodoAVL nodo, Comparable clave, Object data) {
        // 1) Caso base
        if (nodo == null) {
            return new NodoAVL(clave, data);
        }

        Comparable contenidoClave = nodo.getClave();

        if (clave.compareTo(contenidoClave) < 0) {
            if (nodo.getIzquierdo() != null) {
                nodo.setIzquierdo(insertarAux(nodo.getIzquierdo(), clave, data));
            } else {
                nodo.setIzquierdo(new NodoAVL(clave, data));
            }
        } else if (clave.compareTo(contenidoClave) > 0) {
            if (nodo.getDerecho() != null) {
                nodo.setDerecho(insertarAux(nodo.getDerecho(), clave, data));
            } else {
                nodo.setDerecho(new NodoAVL(clave, data));
            }
        } else {

            return nodo;
        }

        nodo.recalcularAltura();

        return nodo;
    }

    private NodoAVL eliminarAux(NodoAVL nodo, Comparable x) {
        if (nodo == null) {
            return null;
        }

        int cmp = x.compareTo(nodo.getClave());
        if (cmp < 0) {
            nodo.setIzquierdo(eliminarAux(nodo.getIzquierdo(), x));
            return balancear(nodo);
        } else if (cmp > 0) {
            nodo.setDerecho(eliminarAux(nodo.getDerecho(), x));
            return balancear(nodo);
        } else {

            if (nodo.getIzquierdo() == null) {
                return nodo.getDerecho();
            }
            if (nodo.getDerecho() == null) {
                return nodo.getIzquierdo();
            }
            NodoAVL suc = minimo(nodo.getDerecho());
            nodo.setElemento(suc.getClave(), suc.getElemento());
            nodo.setDerecho(eliminarAux(nodo.getDerecho(), suc.getClave()));
            return balancear(nodo);
        }
    }

    private NodoAVL minimo(NodoAVL n) {
        while (n.getIzquierdo() != null) {
            n = n.getIzquierdo();
        }
        return n;
    }

    private boolean eliminarNodo(NodoAVL nodo, NodoAVL padre) {
        NodoAVL izquierdo = nodo.getIzquierdo();
        NodoAVL derecho = nodo.getDerecho();

        if (izquierdo == null && derecho == null) {

            eliminarHoja(nodo, padre);
        } else if (izquierdo != null && derecho != null) {

            eliminarConDosHijos(nodo);
        } else {

            eliminarConUnHijo(nodo, padre);
        }
        return true;
    }

    // caso 1
    private boolean eliminarHoja(NodoAVL hijo, NodoAVL padre) {
        boolean eliminado = false;
        if (padre == null) {

            this.raiz = null;
        } else if (padre.getIzquierdo() == hijo) {
            padre.setIzquierdo(null);
            eliminado = true;
        } else {
            padre.setDerecho(null);
            eliminado = true;
        }
        return eliminado;
    }

    // caso 2
    private boolean eliminarConUnHijo(NodoAVL hijo, NodoAVL padre) {
        NodoAVL izquierdo = hijo.getIzquierdo();
        NodoAVL derecho = hijo.getDerecho();
        boolean eliminado = false;
        if (padre == null) {
            this.raiz = (izquierdo != null) ? izquierdo : derecho;
        } else if (izquierdo != null) {
            padre.setIzquierdo(izquierdo);
            eliminado = true;
        } else {
            padre.setDerecho(derecho);
            eliminado = true;
        }
        return eliminado;
    }

    // caso 3
    private boolean eliminarConDosHijos(NodoAVL nodo) {
        NodoAVL candidato = nodo.getDerecho();
        NodoAVL padreCandidato = nodo;
        boolean eliminado = false;
        while (candidato.getIzquierdo() != null) {
            padreCandidato = candidato;
            candidato = candidato.getIzquierdo();
        }
        nodo.setElemento(candidato.getClave(), candidato.getElemento());
        NodoAVL hijoCandidato = candidato.getDerecho();
        if (nodo.getDerecho() == candidato) {
            nodo.setDerecho(hijoCandidato);
            eliminado = true;
        } else {
            padreCandidato.setIzquierdo(hijoCandidato);
            eliminado = true;
        }
        return eliminado;
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

    public Object minimoElem() {
        NodoAVL nodo = this.raiz;
        // bajada por la izquierda
        while (nodo != null) {
            nodo = nodo.getIzquierdo();
        }
        return nodo.getElemento();
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

}
