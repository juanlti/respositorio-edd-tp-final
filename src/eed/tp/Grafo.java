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
                // 2. ¿Es este el arco que conecta con el destino?
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

    public boolean modificarDistanciaRiel(Object origen, Object destino, int cantKm) {

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
        //System.out.println("vertice " + this.ubicarVertice(origen).getElemento());
        obtenerCaminoConMenosEstacionesAux(this.ubicarVertice(origen), destino, new Lista(), caminoEncontrado, new Lista());
        return caminoEncontrado;
    }

    private void obtenerCaminoConMenosEstacionesAux(NodoVert origen, Object destino, Lista visitados, Lista mejorCamino, Lista caminoActual) {

        if (origen != null) {
            //System.out.println("Estoy en: " + origen.getElemento());
            visitados.insertar(origen.getElemento(), visitados.longitud() + 1);
            caminoActual.insertar(origen.getElemento(), caminoActual.longitud() + 1);

            if (origen.getElemento().equals(destino)) {
              //  System.out.println("LLEGUE AL DESTINO");
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

    private Object buscarPadreEnTabla(Object hijo, Lista tabla) {
        Object respuesta = null;
        for (int i = 1; i <= tabla.longitud(); i++) {
            Parentesco p = (Parentesco) tabla.recuperar(i);
            if (p.getHijo().equals(hijo)) {
                respuesta = p.getPadre();
            }
        }
        return respuesta;
    }

    public String obtenerCaminosConMenosKm(Object origen, Object destino) {
        String resultado = "";
        NodoVert nodoOrigen = ubicarVertice(origen);
        resultado = resultado + origen;

        while (nodoOrigen != null) {
            Object estacion = nodoOrigen.getElemento();
            if (estacion.equals(destino)) {
                resultado = resultado + destino;
            } else {
                NodoAdy primerAdy = nodoOrigen.getPrimerAdyc();

                nodoOrigen = primerAdy.getVertice();

            }

            resultado = resultado + estacion;

        }
        return resultado;

    }

    public Lista obtenerTodosLosCaminosIgnorandoUnaEstacion(Object origen, Object destino, Object ignorarEstacion) {
        Lista todosLosCaminos = new Lista();
        Lista visitados = new Lista();
        Lista caminoActual = new Lista();

        // Usamos tu ubicarVertice que ya es inteligente
        NodoVert nodoOrigen = ubicarVertice(origen);

        if (nodoOrigen != null) {
            obtenerTodosLosCaminosIgnorandoUnaEstacionAux(nodoOrigen, destino, visitados, caminoActual, ignorarEstacion, todosLosCaminos);
        }

        return todosLosCaminos;
    }

    private void obtenerTodosLosCaminosIgnorandoUnaEstacionAux(NodoVert actual, Object destino, Lista visitados, Lista caminoActual, Object ignorarEstacion, Lista todosLosCaminos) {
        if (actual != null) {
            Object estacionActual = actual.getElemento();

            // 1. Verificamos que no sea la que ignoramos Y que no esté visitada
            if (!estacionActual.equals(ignorarEstacion) && visitados.localizar(estacionActual) < 0) {

                // VISITAR
                visitados.insertar(estacionActual, visitados.longitud() + 1);
                caminoActual.insertar(estacionActual, caminoActual.longitud() + 1);

                // 2. ¿Llegamos al destino?
                if (estacionActual.equals(destino)) {
                    // Clonamos el camino actual para guardarlo en la lista de resultados
                    caminoActual.insertar(" | ", caminoActual.longitud() + 1);

                    todosLosCaminos.insertar(caminoActual.clone(), todosLosCaminos.longitud() + 1);
                } else {
                    // 3. Si no es el destino, seguimos explorando vecinos
                    NodoAdy ady = actual.getPrimerAdyc();
                    while (ady != null) {
                        obtenerTodosLosCaminosIgnorandoUnaEstacionAux(ady.getVertice(), destino, visitados, caminoActual, ignorarEstacion, todosLosCaminos);
                        ady = ady.getSigAdyacente();
                    }
                }

                // Quitamos el último elemento de ambos para que la función padre pueda probar otras rutas
                caminoActual.eliminar(caminoActual.longitud());
                visitados.eliminar(visitados.longitud());
            }
        }
    }
    /*
    public boolean verificarCaminoConUnaCantidadMaximaDeKm(Object origen, Object destino, int km) {

        Lista visitados = new Lista();
        Lista caminoActual = new Lista();

        NodoVert nodoOrigen = ubicarVertice(origen);

        boolean resultado = verificarCaminoConUnaCantidadMaximaDeKmAuxV(nodoOrigen, destino, visitados, caminoActual, km, 0);

        return resultado;

    }

    private boolean verificarCaminoConUnaCantidadMaximaDeKmAuxV(NodoVert actual, Object destino, Lista visitados, Lista caminoActual, int distanciaMaximaEnKm, int distanciaAcumulada) {
        if (actual == null) {
            return false;
        }

        Object estacion = actual.getElemento();

        if (estacion.equals(destino)) {
            if (distanciaAcumulada <= distanciaMaximaEnKm) {
                caminoActual.insertar(estacion, caminoActual.longitud() + 1);

                return true;
            }
            return false;
        }

        if (distanciaAcumulada > distanciaMaximaEnKm || visitados.localizar(estacion) > 0) {
            return false;
        }

        visitados.insertar(estacion, visitados.longitud() + 1);
        caminoActual.insertar(estacion, caminoActual.longitud() + 1);

        boolean encontrado = false;
        NodoAdy ady = actual.getPrimerAdyc();

        while (ady != null && !encontrado) {
            Riel riel = (Riel) ady.getEtiqueta();

            encontrado = verificarCaminoConUnaCantidadMaximaDeKmAuxV(ady.getVertice(), destino, visitados, caminoActual, distanciaMaximaEnKm, distanciaAcumulada + riel.getDistanciaKm());

            ady = ady.getSigRiel();
        }

        if (!encontrado) {
            visitados.eliminar(visitados.localizar(estacion));
            caminoActual.eliminar(caminoActual.longitud());
        }

        return encontrado;
    }

    public void mostrarEstructura() {
        NodoVert aux = this.inicio;
        if (aux == null) {
            System.out.println("El grafo está vacío.");
        } else {
            while (aux != null) {
                System.out.print("Estación [" + aux.getElemento() + "] -> ");
                NodoAdy ady = aux.getPrimerAdyc();
                while (ady != null) {
                    Riel r = (Riel) ady.getEtiqueta();
                    System.out.print("[" + ady.getVertice().getElemento() + " (" + r.getDistanciaKm() + "km)] ");
                    ady = ady.getSigRiel();
                }
                System.out.println();
                aux = aux.getSiguienteNodoVertice();
            }
        }
    }
     */
}
