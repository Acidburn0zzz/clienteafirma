/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Gobierno de Espa�a
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3, o superiores, seg�n las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompa�a.  Si se   distribuyera este 
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */


package es.gob.afirma.exceptions;

/**
 * Excepci&oacute;n para notificar que se ha encontrado un objeto con un formato
 * inesperado.
 */
public final class AOInvalidFormatException extends AOException {

    private static final long serialVersionUID = 825249824660706387L;

    /**
  	 * Crea la excepci&oacute;n con un mensaje determinado.
  	 * @param msg Mensaje descriptivo de la excepci&oacute;n.
  	 */
    public AOInvalidFormatException(String msg) {
        super(msg);
    }

    /**
  	 * Crea la excepci&oacute;n con un mensaje determinado.
  	 * @param msg Mensaje descriptivo de la excepci&oacute;n.
  	 * @param e Excepci&oacute;n que ha causado el lanzamiento de esta.
  	 */
    public AOInvalidFormatException(String msg, Throwable e) {
        super(msg, e);
    }
}
