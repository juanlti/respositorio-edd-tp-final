package eed.tp;


import eed.tp.Servicios.LogHelper;

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
        if (ubicarVertice(x) == null) {
            this.inicio = new NodoVert(x, this.inicio, null);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object x) {
        NodoVert encontrado = null;
        if (x != null) {

            NodoVert aux = this.inicio;
            while (aux != null) {
                // Aquí está lo que tú dices: aux.getEstacion() ES la clave
                if (aux.getEstacion().equals(x)) {
                    encontrado = aux;
                }
                aux = aux.getSigEstacion();
            }
        }
        return encontrado;
    }

    public boolean insertarArco(Object origen, Object destino, Riel etiqueta) {
        boolean exito = false;
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);
        nodoOrigen.getEstacion().equals(nodoDestino.getEstacion()); //comparacion correcta

        if (nodoOrigen != null && nodoDestino != null) {
            if (!existeAdyacente(nodoOrigen, destino)) {
                NodoAdy nuevoOrigen = new NodoAdy(nodoDestino, nodoOrigen.getPrimerRiel(), etiqueta);
                nodoOrigen.setPrimerRiel(nuevoOrigen);

                NodoAdy nuevoDestino = new NodoAdy(nodoOrigen, nodoDestino.getPrimerRiel(), etiqueta);
                nodoDestino.setPrimerRiel(nuevoDestino);

                LogHelper.registrar("ABM: Riel creado entre " + origen + " y " + destino);
                exito = true;
            }
        }
        return exito;
    }

    private boolean existeAdyacente(NodoVert nodo, Object destino) {
        boolean encontrado = false;
        NodoAdy aux = nodo.getPrimerRiel();
        while (aux != null && !encontrado) {
            if (aux.getVertice().getEstacion().equals(destino)) {
                encontrado = true;
            }
            aux = aux.getSigRiel();
        }
        return encontrado;
    }

    public boolean eliminarVertice(Object elemento) {
        boolean exito = false;
        // 1. Ubicamos el nodo para saber si existe y acceder a sus adyacentes
        NodoVert nodoAEliminar = ubicarVertice(elemento);

        if (nodoAEliminar != null) {
            // 2. Antes de borrar el vértice, recorremos sus rieles
            // para avisarle a los vecinos que ya no están conectados a él.
            NodoAdy ady = nodoAEliminar.getPrimerRiel();
            while (ady != null) {
                // Borramos la referencia inversa (del vecino hacia 'elemento')
                eliminarReferenciaSimple(ady.getVertice(), elemento);
                ady = ady.getSigRiel();
            }

            // 3. Eliminamos el nodo de la lista maestra de vértices del Grafo
            exito = eliminarDeListaMaestra(elemento);

            // 4. Si se eliminó del grafo, lo sacamos también del AVL para mantener la sincronía
            if (exito) {

                LogHelper.registrar("Baja: Estación " + elemento + " y sus rieles eliminados.");
            }
        }

        return exito;
    }

    public boolean eliminarArco(Object origen, Object destino) {
        boolean eliminado = false;
        // 1. Solo buscamos el vértice de origen una vez
        NodoVert vOrigen = ubicarVertice(origen);

        if (vOrigen != null) {
            NodoAdy actual = vOrigen.getPrimerRiel();
            NodoAdy anterior = null;

            while (actual != null && !eliminado) {
                // 2. ¿Es este el arco que conecta con el destino?
                if (actual.getVertice().getEstacion().equals(destino)) {

                    // --- PARTE A: Eliminar de Origen -> Destino ---
                    if (anterior == null) {
                        vOrigen.setPrimerRiel(actual.getSigRiel());
                    } else {
                        anterior.setSigAdyancete(actual.getSigRiel());
                    }

                    // --- PARTE B: Eliminar de Destino -> Origen ---
                    // "actual.getVertice()" es directamente el NodoVert del destino
                    // No necesitamos llamar a ubicarVertice de nuevo.
                    eliminarReferenciaSimple(actual.getVertice(), origen);

                    LogHelper.registrar("Se eliminó el riel entre " + origen + " y " + destino);
                    eliminado = true;
                }

                if (!eliminado) {
                    anterior = actual;
                    actual = actual.getSigRiel();
                }
            }
        }
        return eliminado;
    }

    /**
     * Método privado que elimina la adyacencia hacia 'destinoId' dentro de un
     * NodoVert ya localizado.
     */
    private void eliminarReferenciaSimple(NodoVert nodoADesconectar, Object destinoId) {
        NodoAdy act = nodoADesconectar.getPrimerRiel();
        NodoAdy ant = null;
        boolean borrado = false;

        while (act != null && !borrado) {
            if (act.getVertice().getEstacion().equals(destinoId)) {
                if (ant == null) {
                    nodoADesconectar.setPrimerRiel(act.getSigRiel());
                } else {
                    ant.setSigAdyancete(act.getSigRiel());
                }
                borrado = true;
            }
            ant = act;
            act = act.getSigRiel();
        }
    }

    private boolean eliminarDeListaMaestra(Object elemento) {
        boolean exito = false;
        if (this.inicio != null) {
            if (this.inicio.getEstacion().equals(elemento)) {
                this.inicio = this.inicio.getSigEstacion();
                exito = true;
            } else {
                NodoVert aux = this.inicio;
                while (aux.getSigEstacion() != null && !exito) {
                    if (aux.getSigEstacion().getEstacion().equals(elemento)) {
                        aux.setSigEstacion(aux.getSigEstacion().getSigEstacion());
                        exito = true;
                    }
                    aux = aux.getSigEstacion();
                }
            }
        }
        return exito;
    }

    public Object modificarDistanciaRiel(Object origen, Object destino, int cantKm) {

        boolean exito = false;
        Riel riel = null;
        //busco el nivel de la estacion Origen para realizar una busqueda adyacente hasta encontrar a la estacion destino
        NodoVert verticeOrigen = ubicarVertice(origen);
        NodoAdy primerVecino = verticeOrigen.getPrimerRiel();
        if (verticeOrigen != null && primerVecino != null && !exito) {
            NodoVert vecino = primerVecino.getVertice();
            if (vecino.getEstacion().equals(destino)) {
                riel = (Riel) primerVecino.getEtiqueta();
                riel.setDistanciaKm(cantKm);
                exito = true;
            }

            primerVecino = primerVecino.getSigRiel();

        }

        return riel;

    }

    public Lista obtenerCaminoMasCortoEnNodos(Object origen, Object destino) {

        Lista resultado = new Lista();

        NodoVert inicio = this.ubicarVertice(origen);
        NodoVert fin = this.ubicarVertice(destino);

        if (inicio != null && fin != null) {

            Cola colaNodos = new Cola();
            Cola colaCaminos = new Cola();
            Lista visitados = new Lista();

            Lista caminoInicial = new Lista();
            caminoInicial.insertar(origen, 1);

            colaNodos.poner(inicio);
            colaCaminos.poner(caminoInicial);

            visitados.insertar(origen, 1);

            while (!colaNodos.esVacia()) {

                NodoVert nodoActual = (NodoVert) colaNodos.obtenerFrente();
                colaNodos.sacar();

                Lista caminoActual = (Lista) colaCaminos.obtenerFrente();
                colaCaminos.sacar();

                Estacion estActual = (Estacion) nodoActual.getEstacion();

                if (nodoActual.getEstacion().equals(destino)) {
                    resultado = caminoActual;
                    break;
                }

                NodoAdy ady = nodoActual.getPrimerRiel();

                while (ady != null) {

                    NodoVert vecino = ady.getVertice();
                    Estacion estVecino = (Estacion) vecino.getEstacion();

                    if (visitados.localizar(estVecino.getNombre()) < 0) {

                        visitados.insertar(estVecino.getNombre(), visitados.longitud() + 1);

                        Lista nuevoCamino = caminoActual.clone();
                        nuevoCamino.insertar(estVecino.getNombre(), nuevoCamino.longitud() + 1);

                        colaNodos.poner(vecino);
                        colaCaminos.poner(nuevoCamino);
                    }

                    ady = ady.getSigRiel();
                }
            }
        }

        return resultado;
    }

    public String caminoConMenosEstaciones(Object origen, Object destino) {
        Lista caminoFinal = new Lista();
        Cola cola = new Cola();
        Lista visitados = new Lista();
        Lista tablaPadres = new Lista();

        // 1. Normalizar las claves de entrada
        NodoVert nodoOrigen = ubicarVertice(origen);
        if (nodoOrigen == null) {
            return "[]";
        }

        cola.poner(nodoOrigen);
        // Guardamos el ID, no el objeto origen completo
        visitados.insertar(origen, 1);
        boolean encontrado = false;

        while (!cola.esVacia() && !encontrado) {
            NodoVert vActual = (NodoVert) cola.obtenerFrente();
            cola.sacar();
            Object idActual = vActual.getEstacion(); // Esta es la clave del nodo

            if (idActual.equals(destino)) {
                encontrado = true;
            } else {
                NodoAdy ady = vActual.getPrimerRiel();
                while (ady != null) {
                    NodoVert vVecino = ady.getVertice();
                    Object idVecino = vVecino.getEstacion(); // OBTENEMOS LA CLAVE DEL VECINO

                    // Buscamos la clave en visitados
                    if (visitados.localizar(idVecino) < 0) {
                        visitados.insertar(idVecino, visitados.longitud() + 1);
                        // Guardamos el parentesco entre CLAVES
                        tablaPadres.insertar(new Parentesco(idVecino, idActual), 1);
                        cola.poner(vVecino);
                    }
                    ady = ady.getSigRiel();
                }
            }
        }

        // 2. Reconstrucción del camino usando las claves
        if (encontrado) {
            Object idRecorrido = destino;
            while (idRecorrido != null) {
                caminoFinal.insertar(idRecorrido, 1);
                idRecorrido = buscarPadreEnTabla(idRecorrido, tablaPadres);
            }
        }

        return caminoFinal.toString();
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
            Object estacion = nodoOrigen.getEstacion();
            if (estacion.equals(destino)) {
                resultado = resultado + destino;
            } else {
                NodoAdy primerAdy = nodoOrigen.getPrimerRiel();

                nodoOrigen = primerAdy.getVertice();

            }

            resultado = resultado + estacion;

        }
        return resultado;

    }

    public Lista obtenerCaminoMasCorto(Object origen, Object destino) {
        NodoVert inicio = ubicarVertice(origen);

        Lista caminoMasCorto = new Lista();

        if (inicio != null) {
            Lista visitados = new Lista();

            caminoMasCorto = buscarCaminoMinimoAuxv(inicio, destino, 0, visitados, 100000000, caminoMasCorto);
        }

        return caminoMasCorto;
    }

    private Lista buscarCaminoMinimoAuxv(NodoVert partida, Object destino, double kmAcumulados, Lista visitados, double kmCaminoMasCorto, Lista caminoMasCorto) {

        Object actual = partida.getEstacion();
        visitados.insertar(actual, visitados.longitud() + 1);

        if (actual.equals(destino)) {

            if (kmAcumulados < kmCaminoMasCorto) {
                kmCaminoMasCorto = kmAcumulados;
                caminoMasCorto = visitados.clone();
            }

        } else {

            NodoAdy ady = partida.getPrimerRiel();

            while (ady != null) {

                Riel riel = (Riel) ady.getEtiqueta();
                NodoVert vecino = ady.getVertice();
                Object estVecino = vecino.getEstacion();

                if (visitados.localizar(estVecino) < 0) {

                    Lista candidato = buscarCaminoMinimoAuxv(vecino, destino, kmAcumulados + riel.getDistanciaKm(), visitados, kmCaminoMasCorto, caminoMasCorto);

                    if (candidato.longitud() > 0) {
                        caminoMasCorto = candidato;
                    }
                }
                ady = ady.getSigRiel();
            }
        }

        visitados.eliminar(visitados.longitud());

        return caminoMasCorto;
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
            Object estacionActual = actual.getEstacion();

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
                    NodoAdy ady = actual.getPrimerRiel();
                    while (ady != null) {
                        obtenerTodosLosCaminosIgnorandoUnaEstacionAux(ady.getVertice(), destino, visitados, caminoActual, ignorarEstacion, todosLosCaminos);
                        ady = ady.getSigRiel();
                    }
                }

                // Quitamos el último elemento de ambos para que la función padre pueda probar otras rutas
                caminoActual.eliminar(caminoActual.longitud());
                visitados.eliminar(visitados.longitud());
            }
        }
    }

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

        Object estacion = actual.getEstacion();

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
        NodoAdy ady = actual.getPrimerRiel();

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
                System.out.print("Estación [" + aux.getEstacion() + "] -> ");
                NodoAdy ady = aux.getPrimerRiel();
                while (ady != null) {
                    Riel r = (Riel) ady.getEtiqueta();
                    System.out.print("[" + ady.getVertice().getEstacion() + " (" + r.getDistanciaKm() + "km)] ");
                    ady = ady.getSigRiel();
                }
                System.out.println();
                aux = aux.getSigEstacion();
            }
        }
    }
}
