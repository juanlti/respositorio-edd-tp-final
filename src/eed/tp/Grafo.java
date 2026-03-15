package eed.tp;

import eed.tp.Estacion.Estacion;
import eed.tp.Riel.Riel;
import eed.tp.Cola;
import eed.tp.Lista;
import eed.tp.Nodos.NodoAdy;
import eed.tp.Nodos.NodoVert;
import java.util.ArrayList;

/**
 * ↑: Alt+24 para flecha arriba. ↓: Alt+25 para flecha abajo. →: Alt+26 para
 * flecha derecha. ←: Alt+27 para flecha izquierda
 *
 * @author juanc
 */
public class Grafo {

    private NodoVert inicio;
    private int cantidadVertices;
    private double minimoKmsGlobal;
    private Lista caminoMasCorto;
    private final ArrayList<Lista> todosLosCaminos;

    public Grafo() {
        this.inicio = null;
        this.cantidadVertices = 0;
        this.todosLosCaminos = new ArrayList<>();

    }

    public boolean insertarVertice(Comparable x) {

        boolean exito = false;
        NodoVert aux = ubicarVertice(x);
        if (aux == null) {

            this.inicio = new NodoVert(x, this.inicio, null);
            exito = true;
            this.cantidadVertices++;
            //LogHelper.registrar("Se agrego la estacion " + x.getNombre());
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object x) {

        boolean exito = false;

        NodoVert aux = this.inicio;

        while (aux != null && !exito) {

            if (aux.getEstacion().equals(x)) {
                exito = true;
            } else {
                aux = aux.getSigEstacion();
            }
        }
        return aux;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (NodoVert v = this.inicio; v != null; v = v.getSigEstacion()) {
            sb.append(v.getEstacion()).append(" → ");

            NodoAdy a = v.getPrimerRiel();
            if (a == null) {
                sb.append("∅");
            } else {
                while (a != null) {
                    sb.append('[')
                            .append(a.getVertice().getEstacion())
                            .append(':')
                            .append(a.getEtiqueta())
                            .append(']');
                    a = a.getSigRiel();
                    if (a != null) {
                        sb.append(" → ");
                    }
                }
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    public boolean insertarArco(Comparable origen, Comparable destino, Riel etiqueta) {

        boolean exito = false;
        if (this.inicio != null) {

            NodoVert auxOrigen = ubicarVertice(origen);

            if (auxOrigen != null) {
                NodoVert auxDestino = ubicarVertice(destino);

                if (auxDestino != null) {

                    NodoAdy destinoNodoAdy = new NodoAdy(auxDestino, null, etiqueta);

                    recorrerAdyacentes(auxOrigen, destinoNodoAdy);

                    exito = true;
                    LogHelper.registrar("ABM: Se creó el tramo de riel entre " + origen.toString() + " y " + destino.toString());

                    if (true) {
                        NodoAdy origenNodoAdy = new NodoAdy(auxOrigen, null, etiqueta);
                        recorrerAdyacentes(auxDestino, origenNodoAdy);

                    }

                }

            }
        }
        return exito;
    }

    private void recorrerAdyacentes(NodoVert ubicacion, NodoAdy destinoInsertar) {

        NodoAdy aux = null;

        if (ubicacion.getPrimerRiel() != null) {
            aux = ubicacion.getPrimerRiel();
            destinoInsertar.setSigAdyancete(aux);
            ubicacion.setPrimerRiel(destinoInsertar);

            if (ubicacion.getPrimerRiel().getSigRiel() != null) {
                if (ubicacion.getPrimerRiel().getSigRiel().getSigRiel() != null) {
                }

            }
        } else {
            ubicacion.setPrimerRiel(destinoInsertar);
        }

    }

    private boolean recorrerAdyacentesOtraOpc(NodoVert ubicacion, NodoAdy destinoInsertar) {

        boolean existe = false;

        if (ubicacion.getPrimerRiel() == null) {
            ubicacion.setPrimerRiel(destinoInsertar);
            existe = true;

        } else {
            NodoAdy moverAdy = ubicacion.getPrimerRiel();
            while (moverAdy.getSigRiel() != null && !existe) {

                if (moverAdy.getVertice().getEstacion().equals(destinoInsertar.toString())) {
                    existe = true;

                }

                moverAdy = moverAdy.getSigRiel();

            }
            if (!existe) {
                moverAdy.setSigAdyancete(destinoInsertar);

            }
        }

        return existe;
    }

    public boolean eliminarVertice(Object elemento) {
        boolean fueEliminado = false;
        NodoVert otrosVertices = this.inicio;
        //buscar vertice = elemento 
        boolean encontrado = false;
        while (otrosVertices != null && encontrado) {
            if (!otrosVertices.getEstacion().equals(elemento)) {

                NodoAdy auxMover = otrosVertices.getPrimerRiel();
                if (auxMover != null && auxMover.getVertice().getEstacion().equals(elemento)) {
                    otrosVertices.setPrimerRiel(auxMover.getSigRiel());
                } else {

                    while (auxMover != null && !fueEliminado) {
                        if (auxMover.getSigRiel() != null && auxMover.getSigRiel().getVertice().getEstacion().equals(elemento)) {
                            auxMover.setSigAdyancete(auxMover.getSigRiel().getSigRiel());
                            fueEliminado = true;
                        }
                        auxMover = auxMover.getSigRiel();
                    }
                    fueEliminado = false;
                }

            }
            otrosVertices = otrosVertices.getSigEstacion();
        }

        fueEliminado = auxEliminarYauxExisteUnVerice(elemento, true);
        if (fueEliminado) {
            LogHelper.registrar("Se quito la estación " + elemento);
        }
        return fueEliminado;

    }

    private boolean auxEliminarYauxExisteUnVerice(Object elemento, boolean seElimina) {
        boolean fueEliminado = false;
        if (this.inicio != null) {

            if (this.inicio.getEstacion().equals(elemento)) {
                if (seElimina) {

                    this.inicio = this.inicio.getSigEstacion();

                }
                fueEliminado = true;

            } else {

                NodoVert auxVert = this.inicio;

                while (auxVert.getSigEstacion() != null && !fueEliminado) {
                    if (auxVert.getSigEstacion().getEstacion().equals(elemento)) {
                        if (seElimina) {
                            auxVert.setSigEstacion(auxVert.getSigEstacion().getSigEstacion());
                        }

                        fueEliminado = true;
                    }
                    auxVert = auxVert.getSigEstacion();
                }

            }

        }

        return fueEliminado;
    }

    public boolean existeVertice(Object elemento) {

        return auxEliminarYauxExisteUnVerice(elemento, false);

    }

    public boolean existeArco(Estacion origen, Estacion destino) {
        NodoVert vo = ubicarVertice(origen);
        if (vo == null) {
            return false;
        }

        NodoAdy a = vo.getPrimerRiel();
        while (a != null) {
            if (a.getVertice().getEstacion().equals(destino)) {
                return true;
            }
            a = a.getSigRiel();
        }
        return false;
    }

    public boolean eliminarArco(Object origen, Object destino) {
        NodoVert vo = ubicarVertice(origen);
        if (vo == null) {
            return false;
        }

        NodoAdy a = vo.getPrimerRiel();
        NodoAdy prev = null;

        while (a != null) {
            if (a.getVertice().getEstacion().equals(destino)) {
                if (prev == null) {
                    vo.setPrimerRiel(a.getSigRiel());
                } else {
                    prev.setSigAdyancete(a.getSigRiel());
                }

                return true;
            }
            prev = a;
            a = a.getSigRiel();
        }
        return false;
    }

    /*
    public Object obtenerEtiquetaArco(Object origen, Object destino) {
        
        NodoVert vo = ubicarVertice(origen);
        if (vo == null) {
            return null;
        }

        NodoAdy ady = vo.getPrimerRiel();
        while (ady != null) {
            if (ady.getVertice().getEstacion().equals(destino)) {
                return ady.getEtiqueta();
            }
            ady = ady.getSigRiel();
        }
        return null;
    }
     */
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

    public Lista listarEtiquetas() {
        Lista l = new Lista();
        for (NodoVert v = this.inicio; v != null; v = v.getSigEstacion()) {
            NodoAdy a = v.getPrimerRiel();
            while (a != null) {
                l.insertar(a.getEtiqueta(), l.longitud() + 1);
                a = a.getSigRiel();
            }
        }
        return l;
    }

    public Lista listarEtiquetasDeVertice(Estacion v) {
        Lista l = new Lista();
        NodoVert nv = ubicarVertice(v);
        if (nv == null) {
            return l;
        }

        NodoAdy a = nv.getPrimerRiel();
        while (a != null) {
            l.insertar(a.getEtiqueta(), l.longitud() + 1);
            a = a.getSigRiel();
        }
        return l;
    }

    public String getEstacionMasCercana(int codigo) {
        NodoAdy aux = this.inicio.getSigEstacion().getSigEstacion().getSigEstacion().getPrimerRiel();

        String estaciones = "";
        Riel r = (Riel) aux.getSigRiel().getEtiqueta();

        while (aux != null) {
            estaciones = estaciones + (Riel) aux.getSigRiel().getEtiqueta();
            aux = aux.getSigRiel();
        }
        return "adyacentes de " + this.inicio.getSigEstacion().getSigEstacion().getSigEstacion().toString() + " estaciones : " + estaciones;
    }

    public String obtenerCaminoMasCortoEnNodos(String v) {
        NodoAdy aux = this.inicio.getSigEstacion().getSigEstacion().getSigEstacion().getPrimerRiel();

        Riel distancia = (Riel) aux.getSigRiel().getEtiqueta();
        String distanciasEstaciones = "primera distancia: [" + distancia + "] ";
        aux = aux.getSigRiel();
        int distanciaMasCorta = 1000000;
        int codigoProximaEstacion = -1;
        AVL myAVL = new AVL();

        Riel rielDeLaEstacionOrigen = (Riel) aux.getEtiqueta();

        Object[] response = (Object[]) myAVL.buscar(rielDeLaEstacionOrigen.getCodEstacionOrigen());

        if (response != null && (Boolean) response[0]) {
            Estacion unaEstacion = (Estacion) response[1];
            System.out.println("Estación encontrada: " + unaEstacion.getNombre());
        }

        String estacionMasCercana = "";
        while (aux.getSigRiel() != null) {

            aux = aux.getSigRiel();
            distancia = (Riel) aux.getEtiqueta();
            if (distanciaMasCorta > distancia.getDistanciaKm()) {
                distanciaMasCorta = distancia.getDistanciaKm();
                codigoProximaEstacion = distancia.getCodEstacionDestino();
                if (response[0] instanceof Boolean) {
                    if (!(Boolean) response[0]) {
                        System.out.println("✗ No existe la estación " + codigoProximaEstacion);
                        return "";
                    }
                    Estacion estacion = (Estacion) response[1];
                    estacionMasCercana = estacion.getNombre();
                }

            }

            distanciasEstaciones = distanciasEstaciones + " [" + distancia.getDistanciaKm() + "]";

        }

        return "la distancia entre dos estaciones es: " + estacionMasCercana + " " + distanciaMasCorta + " distancias desde la estacion : " + this.inicio.getSigEstacion().getSigEstacion().getSigEstacion().toString() + "  respecto a las estaciones adyacentes " + distanciasEstaciones;

    }

    private void caminoMasCortoEnNodosAux(Lista ls, String v, NodoAdy nodo) {

        if (nodo == null) {
            System.out.println("Lista ls " + ls.toString());
            System.out.println("muestro estaciones de la linea ");
        } else {

            if (nodo.equals(v)) {

            } else {
                if (ls.localizar(v) != -1) {

                } else {
                    ls.insertar(v, ls.longitud());
                    System.out.println("Lista ls antes " + ls.toString());
                    caminoMasCortoEnNodosAux(ls, v, nodo.getSigRiel());
                    System.out.println("Lista ls despues " + ls.toString());

                }

            }

        }

    }

    public String caminoConMenosEstaciones(Estacion origen, Estacion destino) {
        Lista caminoFinal = new Lista();
        Cola cola = new Cola();
        Lista visitados = new Lista();
        Lista tablaPadres = new Lista();
        NodoVert nodoOrigen = ubicarVertice(origen);
        if (nodoOrigen == null) {
            return caminoFinal.toString();
        }

        cola.poner(nodoOrigen);
        visitados.insertar(origen.getNombre(), visitados.longitud() + 1);
        boolean encontrado = false;

        while (!cola.esVacia() && !encontrado) {
            NodoVert actual = (NodoVert) cola.obtenerFrente();
            cola.sacar();
            Estacion datosActual = (Estacion) actual.getEstacion();

            if (datosActual.getNombre().equalsIgnoreCase(destino.getNombre())) {
                encontrado = true;
            } else {
                NodoAdy ady = actual.getPrimerRiel();
                while (ady != null) {
                    NodoVert vecino = ady.getVertice();
                    Estacion vecinoEstacion = (Estacion) vecino.getEstacion();
                    String nombreVecino = vecinoEstacion.getNombre();

                    if (visitados.localizar(nombreVecino) < 0) {
                        visitados.insertar(vecinoEstacion.getNombre(), visitados.longitud() + 1);
                        tablaPadres.insertar(new Parentesco(nombreVecino, datosActual.getNombre()), 1);
                        cola.poner(vecino);
                    }

                    ady = ady.getSigRiel();
                }
            }
        }

        cola.sacar();

        if (encontrado) {
            String actual = destino.getNombre();
            while (actual != null) {
                caminoFinal.insertar(actual, 1);
                actual = buscarPadreEnTabla(actual, tablaPadres);
            }
        }

        return caminoFinal.toString();
    }

    private String buscarPadreEnTabla(String hijo, Lista tabla) {
        for (int i = 1; i <= tabla.longitud(); i++) {
            Parentesco p = (Parentesco) tabla.recuperar(i);
            if (p.getHijo().equalsIgnoreCase(hijo)) {
                return p.getPadre();
            }
        }
        return null;
    }

    public String obtenerCaminosConMenosKm(Estacion origen, Estacion destino) {
        String resultado = "";
        Lista caminoFinal = new Lista();
        Cola cola = new Cola();
        Lista visitados = new Lista();
        Lista tablaPadres = new Lista();
        NodoVert nodoOrigen = ubicarVertice(origen);
        resultado = resultado + origen.getNombre();

        while (nodoOrigen != null) {
            Estacion auxEstacion = (Estacion) nodoOrigen.getEstacion();
            if (auxEstacion.getNombre().equalsIgnoreCase(destino.getNombre())) {
                resultado = resultado + destino.getNombre();
            } else {
                NodoAdy primerAdy = nodoOrigen.getPrimerRiel();

                nodoOrigen = primerAdy.getVertice();

            }

            resultado = resultado + auxEstacion;

        }
        return resultado;

    }

    public Lista obtenerCaminoMasCorto(Estacion origen, Estacion destino) {
        NodoVert verificamosEstacionPartida = ubicarVertice(origen);

        if (verificamosEstacionPartida != null) {
            this.minimoKmsGlobal = Double.MAX_VALUE;
            this.caminoMasCorto = new Lista();
            Lista visitadosAux = new Lista();

            buscarCaminoMinimoAuxv(verificamosEstacionPartida, destino, 0, visitadosAux);
        }

        return this.caminoMasCorto;
    }

    private void buscarCaminoMinimoAuxv(NodoVert partida, Estacion destino, double kmAcumulados, Lista visitados) {

        Estacion estacionEntrante = (Estacion) partida.getEstacion();

        visitados.insertar(estacionEntrante.getNombre(), visitados.longitud() + 1);

        if (estacionEntrante.getNombre().equalsIgnoreCase(destino.getNombre())) {

            if (kmAcumulados < this.minimoKmsGlobal) {
                this.caminoMasCorto = visitados.clone();
                this.minimoKmsGlobal = kmAcumulados;
            }

            visitados.eliminar(visitados.longitud());
            return;
        }

        NodoAdy rielVecino = partida.getPrimerRiel();

        while (rielVecino != null) {
            Riel riel = (Riel) rielVecino.getEtiqueta();
            NodoVert vecino = rielVecino.getVertice();
            Estacion vecinoEstacion = (Estacion) vecino.getEstacion();

            if (kmAcumulados + riel.getDistanciaKm() < this.minimoKmsGlobal) {

                if (visitados.localizar(vecinoEstacion.getNombre()) < 0) {
                    buscarCaminoMinimoAuxv(vecino, destino, kmAcumulados + riel.getDistanciaKm(), visitados);
                }
            }

            rielVecino = rielVecino.getSigRiel();
        }

        visitados.eliminar(visitados.longitud());
    }

    public void obtenerTodosLosCaminosIgnorandoUnaEstacion(Estacion origen, Estacion destino, Estacion ignorarEstacion) {

        Lista visitados = new Lista();
        Lista caminoActual = new Lista();

        NodoVert nodoOrigen = ubicarVertice(origen);
        boolean resultado = false;

        obtenerTodosLosCaminosIgnorandoUnaEstacionAux(nodoOrigen, destino, visitados, caminoActual, ignorarEstacion);

        System.out.println("el camino encontrado es " + this.todosLosCaminos.toString());

    }

    private void obtenerTodosLosCaminosIgnorandoUnaEstacionAux(NodoVert actual, Estacion destino, Lista visitados, Lista caminoActual, Estacion ignorarEstacion) {
        if (actual == null) {
            return;
        }

        Estacion estacion = (Estacion) actual.getEstacion();

        if (estacion.getNombre().equalsIgnoreCase(ignorarEstacion.getNombre())) {
            return;
        }

        if (visitados.localizar(estacion.getNombre()) > 0) {
            return;
        }

        visitados.insertar(estacion.getNombre(), visitados.longitud() + 1);
        caminoActual.insertar(estacion.getNombre(), caminoActual.longitud() + 1);

        if (estacion.getNombre().equalsIgnoreCase(destino.getNombre())) {

            this.todosLosCaminos.add(caminoActual.clone());
            Lista ls = new Lista();
            ls.insertar("|", ls.longitud() + 1);
            this.todosLosCaminos.add(ls);
        } else {

            NodoAdy vecino = actual.getPrimerRiel();
            while (vecino != null) {
                obtenerTodosLosCaminosIgnorandoUnaEstacionAux(vecino.getVertice(), destino, visitados, caminoActual, ignorarEstacion);
                vecino = vecino.getSigRiel();
            }
        }

        caminoActual.eliminar(caminoActual.longitud());
        visitados.eliminar(visitados.localizar(estacion.getNombre()));
    }

    public boolean verificarCaminoConUnaCantidadMaximaDeKm(Estacion origen, Estacion destino, int km) {

        Lista visitados = new Lista();
        Lista caminoActual = new Lista();

        NodoVert nodoOrigen = ubicarVertice(origen);

        boolean resultado = verificarCaminoConUnaCantidadMaximaDeKmAuxV(nodoOrigen, destino, visitados, caminoActual, km, 0);

        return resultado;

    }

    private boolean verificarCaminoConUnaCantidadMaximaDeKmAuxV(NodoVert actual, Estacion destino, Lista visitados, Lista caminoActual, int distanciaMaximaEnKm, int distanciaAcumulada) {
        if (actual == null) {
            return false;
        }

        Estacion estacion = (Estacion) actual.getEstacion();

        if (estacion.getNombre().equalsIgnoreCase(destino.getNombre())) {
            if (distanciaAcumulada <= distanciaMaximaEnKm) {
                caminoActual.insertar(estacion.getNombre(), caminoActual.longitud() + 1);

                return true;
            }
            return false;
        }

        if (distanciaAcumulada > distanciaMaximaEnKm || visitados.localizar(estacion.getNombre()) > 0) {
            return false;
        }

        visitados.insertar(estacion.getNombre(), visitados.longitud() + 1);
        caminoActual.insertar(estacion.getNombre(), caminoActual.longitud() + 1);

        boolean encontrado = false;
        NodoAdy ady = actual.getPrimerRiel();

        while (ady != null && !encontrado) {
            Riel riel = (Riel) ady.getEtiqueta();

            encontrado = verificarCaminoConUnaCantidadMaximaDeKmAuxV(ady.getVertice(), destino, visitados, caminoActual, distanciaMaximaEnKm, distanciaAcumulada + riel.getDistanciaKm());

            ady = ady.getSigRiel();
        }

        if (!encontrado) {
            visitados.eliminar(visitados.localizar(estacion.getNombre()));
            caminoActual.eliminar(caminoActual.longitud());
        }

        return encontrado;
    }

    public void mostrarEstructura() {
        NodoVert aux = this.inicio;

        if (aux == null) {
            System.out.println("El grafo está vacío.");
            return;
        }

        while (aux != null) {
            System.out.print("Vértice [" + aux.getEstacion().toString() + "] conecta con: ");

            NodoAdy ady = aux.getPrimerRiel();

            if (ady == null) {
                System.out.print("Ninguna estación (Sin salidas).");
            } else {

                while (ady != null) {

                    String destino = ady.getVertice().getEstacion().toString();
                    Riel riel = (Riel) ady.getEtiqueta();

                    System.out.print(" -> (" + riel.getDistanciaKm() + "km) [" + destino + "] ");

                    ady = ady.getSigRiel();
                }
            }
            System.out.println();
            aux = aux.getSigEstacion();
        }
    }
}
