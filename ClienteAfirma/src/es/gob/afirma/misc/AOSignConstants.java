/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Gobierno de Espa�a
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3, o superiores, seg�n las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompa�a.  Si se   distribuyera este 
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */


package es.gob.afirma.misc;

/**
 * Constantes relativas a las firmas digitales.
 */
public final class AOSignConstants {
	
	//************************************************************
	//************* OPCIONES DE MULTIFIRMA ***********************
	//************************************************************
    
    /**
     * Permite definir los objetivos para la contrafirma:
     * <ul>
     * <li>Signers: Contrafirma de firmantes concretos.</li>
     * <li>Nodes: Contrafirma de nodos de firma concretos.</li>
     * <li>Tree: Contrafirma de todo el &aacute;rbol de firma.</li>
     * <li>Leafs: Contrafirma de todos los nodos de firma.</li>
     * </ul>
     */
    public static enum CounterSignTarget {
    	/** Contrafirma de firmantes concretos. */
    	Signers,
    	/** Contrafirma de nodos de firma concretos. */
    	Nodes,
    	/** Contrafirma de todo el &aacute;rbol de firma. */
    	Tree,
    	/** Contrafirma de todas las hojas del &aacute;rbol de firma. */
    	Leafs
    };

    private AOSignConstants() {}
}
