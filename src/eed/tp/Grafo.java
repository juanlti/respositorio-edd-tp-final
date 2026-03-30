package eed.tp;

import eed.tp.Nodos.NodoAdy;
import eed.tp.Nodos.NodoVert;

/**
 * ↑: Alt+24 para flecha arriba. ↓: Alt+25 para flecha abajo. →: Alt+26 para
 * flecha derecha. ←: Alt+27 para flecha izquierda
 *
 * @author juanc
 */
public class Grafo {

    private NodoVert inicio;

    public Grafo() {
        this.inicio = null;

    }

    public boolean insertarVertice(Object x) {

        boolean exito = false;
        NodoVert aux = this.ubicarVertice(x);
        if (aux == null) {
            this.inicio = new NodoVert(x, this.inicio, null);
            exito = true;

        }

        return exito;
    }

    private NodoVert ubicarVertice(Object x) {

        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElemento().equals(x)) {

            aux = aux.getSiguienteNodoVertice();
        }

        return aux;
    }

    private NodoVert encontrarNodoVerticeAnterior(Object x) {

        NodoVert aux = this.inicio;
        NodoVert nodoVertAnterior = null;

        if (aux.getElemento().equals(x)) {
            nodoVertAnterior = null;

        } else {

            while (nodoVertAnterior == null && aux != null) {

                if (aux.getSiguienteNodoVertice().getElemento().equals(x)) {
                    nodoVertAnterior = aux;
                }
                aux = aux.getSiguienteNodoVertice();

            }

        }

        return nodoVertAnterior;
    }

    public boolean insertarArco(Object origen, Object destino, double etiqueta) {

        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);
        nodoOrigen.setPrimerAdyc(new NodoAdy(nodoDestino, nodoOrigen.getPrimerAdyc(), etiqueta));
        nodoDestino.setPrimerAdyc(new NodoAdy(nodoOrigen, nodoDestino.getPrimerAdyc(), etiqueta));

        return true;
    }

    private Lista obtenerTodosLosVerticesDeAdyacencia(NodoVert pivote) {
        Lista listaDeNodo = new Lista();
        NodoAdy pivoteEnlace = pivote.getPrimerAdyc();
        while (pivoteEnlace != null) {
            listaDeNodo.insertar(pivoteEnlace.getVertice(), listaDeNodo.longitud() + 1);

            pivoteEnlace = pivoteEnlace.getSigAdyacente();

        }
        return listaDeNodo;

    }

    public boolean eliminarVertice(Object elemento) {
        boolean exito = false;
        Lista recorrerLosNodosAdyc;

        if (this.inicio != null && this.inicio.getElemento().equals(elemento)) {
            recorrerLosNodosAdyc = obtenerTodosLosVerticesDeAdyacencia(this.inicio);
            this.eliminarVerticeAux(recorrerLosNodosAdyc, elemento);

            NodoVert eliminar = this.inicio;
            this.inicio.setPrimerAdyc(null);
            this.inicio = eliminar.getSiguienteNodoVertice();

            exito = true;

        } else {

            NodoVert nodoAEliminarAnterior = this.encontrarNodoVerticeAnterior(elemento);

            if (nodoAEliminarAnterior != null) {
                NodoVert nodoEliminar = nodoAEliminarAnterior.getSiguienteNodoVertice();

                recorrerLosNodosAdyc = obtenerTodosLosVerticesDeAdyacencia(nodoEliminar);

                this.eliminarVerticeAux(recorrerLosNodosAdyc, elemento);
                nodoEliminar.setPrimerAdyc(null);
                nodoAEliminarAnterior.setNodoVertice(nodoEliminar.getSiguienteNodoVertice());

                exito = true;
            }

        }

        return exito;
    }

    private void eliminarVerticeAux(Lista recorrerLosNodosAdyc, Object elemento) {

        int longitud, posicion;
        longitud = recorrerLosNodosAdyc.longitud();
        posicion = 1;

        while (posicion <= longitud) {

            this.eliminarArcoDireccional((NodoVert) recorrerLosNodosAdyc.recuperar(posicion), elemento);
            posicion++;

        }

    }

    public boolean eliminarArco(Object origen, Object destino) {
        boolean eliminado = false;
        // 1. Solo buscamos el vértice de origen una vez
        NodoVert vOrigen = ubicarVertice(origen);
        NodoVert nodoVertDestino = this.eliminarArcoDireccional(vOrigen, destino);
        if (nodoVertDestino != null) {
            if (this.eliminarArcoDireccional(nodoVertDestino, origen) != null) {
                eliminado = true;
            }

        }

        return eliminado;
    }

    private NodoVert eliminarArcoDireccional(NodoVert pivote, Object encontrar) {

        NodoVert verticeDestino = null;

        if (pivote != null) {

            boolean eliminado = false;
            NodoAdy vecinoAdyc = pivote.getPrimerAdyc();
            NodoAdy vecinoAnterior = null;

            while (vecinoAdyc != null && !eliminado) {
                if (vecinoAdyc.getVertice().getElemento().equals(encontrar)) {

                    if (vecinoAnterior != null) {
                        vecinoAnterior.setSigAdyancete(vecinoAdyc.getSigAdyacente());
                    } else {

                        pivote.setPrimerAdyc(vecinoAdyc.getSigAdyacente());

                    }
                    eliminado = true;
                    verticeDestino = vecinoAdyc.getVertice();

                } else {
                    vecinoAnterior = vecinoAdyc; //b
                    vecinoAdyc = vecinoAdyc.getSigAdyacente();
                }
            }

        }

        return verticeDestino;

    }

    public boolean modificarEtiqueta(Object origen, Object destino, int cantKm) {

        boolean exito = false;
        NodoVert verticeOrigen = ubicarVertice(origen);
        NodoAdy primerElementoAdycante = verticeOrigen.getPrimerAdyc();

        while (!exito && primerElementoAdycante != null) {
            NodoVert nodoVerticePrimereElemento = primerElementoAdycante.getVertice();
            if (nodoVerticePrimereElemento.getElemento().equals(destino)) {
                primerElementoAdycante.setEtiqueta(cantKm);
                exito = true;
            }

            primerElementoAdycante = primerElementoAdycante.getSigAdyacente();

        }

        return exito;

    }

    public Lista obtenerCaminoConMenosEstaciones(Object origen, Object destino) {
        Lista caminoEncontrado = new Lista();
        obtenerCaminoConMenosEstacionesAux(this.ubicarVertice(origen), destino, new Lista(), caminoEncontrado, new Lista());
        return caminoEncontrado;
    }

    private void obtenerCaminoConMenosEstacionesAux(NodoVert origen, Object destino, Lista visitados, Lista mejorCamino, Lista caminoActual) {

        if (origen != null) {
            visitados.insertar(origen.getElemento(), visitados.longitud() + 1);
            caminoActual.insertar(origen.getElemento(), caminoActual.longitud() + 1);

            if (origen.getElemento().equals(destino)) {
                if (mejorCamino.esVacia() || mejorCamino.longitud() > caminoActual.longitud()) {
                    mejorCamino.vaciar();

                    for (int i = 1; i <= caminoActual.longitud(); i++) {
                        mejorCamino.insertar(caminoActual.recuperar(i), i);

                    }

                }

            } else {

                NodoAdy vecino = origen.getPrimerAdyc();

                while (vecino != null) {

                    if (visitados.localizar(vecino.getVertice().getElemento()) < 0) {
                        this.obtenerCaminoConMenosEstacionesAux(vecino.getVertice(), destino, visitados, mejorCamino, caminoActual);

                    }
                    vecino = vecino.getSigAdyacente();

                }

            }
            visitados.eliminar(visitados.longitud());
            caminoActual.eliminar(caminoActual.longitud());

        }

    }

    public Lista obtenerCaminoConMenosKms(Object origen, Object destino) {
        Lista caminoEncontrado = new Lista();
        obtenerCaminoConMenosKmsAux(this.ubicarVertice(origen), destino, new Lista(), caminoEncontrado, 0, 1000000);
        return caminoEncontrado;
    }

    private double obtenerCaminoConMenosKmsAux(NodoVert origen, Object destino, Lista visitados, Lista mejorCamino, double cantidadKmAcumulados, double kmCaminObtenido) {
        double km = 0;
        if (origen != null) {

            visitados.insertar(origen.getElemento(), visitados.longitud() + 1);

            if (origen.getElemento().equals(destino)) {

                if (mejorCamino.esVacia() || cantidadKmAcumulados < kmCaminObtenido) {
                    kmCaminObtenido = cantidadKmAcumulados;

                    mejorCamino.vaciar();

                    for (int i = 1; i <= visitados.longitud(); i++) {
                        mejorCamino.insertar(visitados.recuperar(i), i);

                    }

                }

            } else {
                NodoAdy vecino = origen.getPrimerAdyc();

                while (vecino != null) {

                    if (visitados.localizar(vecino.getVertice().getElemento()) < 0) {
                        if (cantidadKmAcumulados + vecino.getEtiqueta() < kmCaminObtenido) {
                            kmCaminObtenido = this.obtenerCaminoConMenosKmsAux(vecino.getVertice(), destino, visitados, mejorCamino, cantidadKmAcumulados + vecino.getEtiqueta(), kmCaminObtenido);

                        }

                    }
                    vecino = vecino.getSigAdyacente();

                }

            }
            visitados.eliminar(visitados.longitud());
        }
        return kmCaminObtenido;

    }

    public Lista obtenerTodosLosCaminosIgnorandoUnaEstacion(Object origen, Object destino, Object ignorarEstacion) {
        Lista todosLosCaminos = new Lista();
        Lista visitados = new Lista();

        NodoVert nodoOrigen = ubicarVertice(origen);

        if (nodoOrigen != null) {
            obtenerTodosLosCaminosIgnorandoUnaEstacionAux(nodoOrigen, destino, visitados, ignorarEstacion, todosLosCaminos);
        }

        return todosLosCaminos;
    }

    private void obtenerTodosLosCaminosIgnorandoUnaEstacionAux(NodoVert actual, Object destino, Lista visitados, Object ignorarEstacion, Lista todosLosCaminos) {
        if (actual != null) {
            Object estacionActual = actual.getElemento();

            if (!estacionActual.equals(ignorarEstacion)) {

                visitados.insertar(estacionActual, visitados.longitud() + 1);

                if (estacionActual.equals(destino)) {

                    todosLosCaminos.insertar(visitados.clone(), todosLosCaminos.longitud() + 1);

                } else {

                    NodoAdy ady = actual.getPrimerAdyc();
                    while (ady != null) {
                        if (visitados.localizar(ady.getVertice().getElemento()) < 0 && !ady.getVertice().getElemento().equals(ignorarEstacion)) {
                            obtenerTodosLosCaminosIgnorandoUnaEstacionAux(ady.getVertice(), destino, visitados, ignorarEstacion, todosLosCaminos);

                        }
                        ady = ady.getSigAdyacente();

                    }
                }

                visitados.eliminar(visitados.longitud());
            }
        }
    }

    public boolean verificarCaminoConUnaCantidadMaximaDeKm(Object origen, Object destino, int km) {

        Lista visitados = new Lista();
        Lista caminoActual = new Lista();

        NodoVert nodoOrigen = ubicarVertice(origen);

        return verificarCaminoConUnaCantidadMaximaDeKmAuxV(nodoOrigen, destino, visitados, caminoActual, km, 0);

    }

    private boolean verificarCaminoConUnaCantidadMaximaDeKmAuxV(NodoVert actual, Object destino, Lista visitados, Lista caminoActual, int distanciaMaximaEnKm, double distanciaAcumulada) {
        boolean existeCamino = false;

        if (actual != null) {

            Object estacion = actual.getElemento();

            visitados.insertar(estacion, visitados.longitud() + 1);

            if (estacion.equals(destino) && distanciaAcumulada <= distanciaMaximaEnKm) {

                existeCamino = true;

            }

            NodoAdy vecino = actual.getPrimerAdyc();

            while (vecino != null && !existeCamino) {
                if (visitados.localizar(vecino.getVertice().getElemento()) < 0 && (distanciaAcumulada + vecino.getEtiqueta() <= distanciaMaximaEnKm)) {
                    existeCamino = verificarCaminoConUnaCantidadMaximaDeKmAuxV(vecino.getVertice(), destino, visitados, caminoActual, distanciaMaximaEnKm, distanciaAcumulada + vecino.getEtiqueta());

                }

                vecino = vecino.getSigAdyacente();
            }

            if (!existeCamino) {
                visitados.eliminar(visitados.localizar(estacion));
            }
        }

        return existeCamino;
    }

}
