package Lineal;

import eed.tp.Nodos.Nodo;

public class Lista {

    private Nodo cabecera;
    private int longitud;

    public Lista() {
        this.cabecera = null;
        this.longitud = 0;

    }
    
    

    public boolean insertar(Object nuevoElem, int pos) {
        boolean fueInsertado = false;
        Nodo nuevoNodo;

        if (pos == 1) {

            if (this.longitud == 0) {
                this.cabecera = new Nodo(nuevoElem, null);

                fueInsertado = true;

            } else {
                nuevoNodo = new Nodo(nuevoElem, null);
                nuevoNodo.setEnlace(cabecera);
                this.cabecera = nuevoNodo;
                fueInsertado = true;

            }

            this.longitud++;
        } else {

            if (pos > 0 && pos <= this.longitud + 1) {
                Nodo tempNodoAnterior = this.cabecera;

                int posAux = 1;
                while (posAux < pos - 1) {

                    tempNodoAnterior = tempNodoAnterior.getEnlace();
                    posAux++;

                }
                Nodo siguiente = tempNodoAnterior.getEnlace();
                nuevoNodo = new Nodo(nuevoElem, null);
                nuevoNodo.setEnlace(siguiente);
                tempNodoAnterior.setEnlace(nuevoNodo);

                fueInsertado = true;
                this.longitud++;

            }

        }
        return fueInsertado;
    }

    public Lista clone() {
        Lista nuevaLista = new Lista();

        if (this.cabecera != null) {
            // 1. Creamos la nueva cabecera con el dato de la original
            nuevaLista.cabecera = new Nodo(this.cabecera.getElem(), null);
            nuevaLista.longitud = this.longitud;

            // 2. Punteros para recorrer: uno en la original y otro en la copia
            Nodo auxOriginal = this.cabecera.getEnlace();
            Nodo auxClone = nuevaLista.cabecera;

            // 3. Recorremos hasta que no haya más nodos en la original
            while (auxOriginal != null) {
                // Creamos un NODO NUEVO (esto rompe la referencia con la lista original)
                Nodo nuevoNodo = new Nodo(auxOriginal.getElem(), null);

                // Lo enganchamos a nuestra nueva lista
                auxClone.setEnlace(nuevoNodo);

                // Avanzamos ambos "dedos" para la siguiente vuelta
                auxClone = auxClone.getEnlace();
                auxOriginal = auxOriginal.getEnlace();
            }
        }
        return nuevaLista;
    }

    public boolean eliminar(int pos) {

        boolean fueEliminado = false;
        if (pos > 0 && pos <= this.longitud) {

            if (pos == 1) {
                this.cabecera = this.cabecera.getEnlace();
                fueEliminado = true;
                this.longitud--;

            } else {
                int i = 1;

                Nodo aux = this.cabecera;

                while (!fueEliminado && i <= this.longitud) {

                    if ((pos - 1) == i) {

                        aux.setEnlace(aux.getEnlace().getEnlace());

                        fueEliminado = true;
                        this.longitud--;

                    } else {
                        aux = aux.getEnlace();

                        i++;
                    }

                }
            }
        }

        return fueEliminado;
    }

    public boolean esVacia() {
        return this.cabecera == null;
    }

    public void vaciar() {

        this.cabecera = null;
        this.longitud = 0;
    }

    public int longitud() {
        return this.longitud;
    }

    //Simulacro parcial
    public Lista obtenerMultiplos(int num) {

        Lista lm = new Lista();
        Nodo nodoIteradorAux = lm.cabecera;

        Nodo mover = this.cabecera;

        int i = 1;

        int limite = this.longitud;

        while (i <= limite) {

            if ((i % num) == 0) {

                lm.longitud++;
                if (lm.cabecera == null) {

                    nodoIteradorAux = new Nodo(mover.getElem(), null);
                    lm.cabecera = nodoIteradorAux;

                } else {

                    Nodo nodoN = new Nodo(mover.getElem(), null);

                    nodoIteradorAux.setEnlace(nodoN);
                    nodoIteradorAux = nodoIteradorAux.getEnlace();

                }

            }

            mover = mover.getEnlace();
            i++;

        }
        return lm;
    }

    public void eliminarApareciones(Object x) {
        int i = 1;
        Nodo n = this.cabecera;
        while (n != null) {

            if (i == 1 && n.getElem().equals(x)) {
                this.cabecera.setEnlace(n);
                this.longitud--;

            } else {
                if (n.getEnlace() != null && n.getEnlace().getElem().equals(x)) {
                    n.setEnlace(n.getEnlace().getEnlace());
                    this.longitud--;
                }

            }
            i++;

            n = n.getEnlace();

        }

    }

    public Object recuperar(int pos) {
        Object elemento = null;
        int index = 1;
        boolean encontrado = false;
        Nodo mover = this.cabecera;
        while (index <= this.longitud && !encontrado) {

            if (pos == index) {
                encontrado = true;
                elemento = mover.getElem();
            } else {
                mover = mover.getEnlace();
                index++;
            }

        }
        return elemento;

    }

    public int localizar(Object elemento) {
        int res = -1;
        if (this.cabecera != null && elemento != null) {
            res = localizarAux(this.cabecera, elemento);
        }
        return res;
    }

    private int localizarAux(Nodo n, Object elemento) {
        int i, res;
        i = 1;
        res = -1;
        boolean corte = false;

        while (!n.getElem().equals(elemento) && !corte) {

            if (n.getEnlace() != null) {
                n = n.getEnlace();
                i = i + 1;

            } else {
                corte = true;
            }

        }
        if (!corte) {
            res = i;
        }

        return res;

    }
    
    public void copiar(Lista otraLista){
        this.cabecera=otraLista.cabecera;
        this.longitud=otraLista.longitud;
    }

    public String toString() {
        String cadena;

        Nodo aux;
        cadena = "";
        if (!this.esVacia()) {
            aux = this.cabecera;

            while (aux != null) {
                cadena += aux.getElem() + ",";
                aux = aux.getEnlace();
            }
        } else {
            cadena = "Lista vacia";
        }

        return cadena;
    }

}
