/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Ministerio de la Presidencia, Gobierno de Espa�a (opcional: correo de contacto)
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3  seg�n las
 * condiciones que figuran en el fichero 'licence' que se acompa�a.  Si se   distribuyera este 
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */

package es.gob.afirma.exceptions;

/**
 * Excepci&oacute;n lanzada cuando se detecta una firma con un formato no reconocido o
 * se indica un formato de firma no soportado.
 */
public class AOUnsupportedSignFormatException extends AOException {

    private static final long serialVersionUID = -1;

    /**
  	 * Crea la excepci&oacute;n con un mensaje determinado.
  	 * @param msg Mensaje descriptivo de la excepci&oacute;n.
  	 */
    public AOUnsupportedSignFormatException(final String msg) {
        super(msg);
    }
    
    /**
  	 * Crea la excepci&oacute;n con un mensaje determinado.
  	 * @param msg Mensaje descriptivo de la excepci&oacute;n.
  	 * @param e Excepci&oacute;n que ha causado el lanzamiento de esta.
  	 */
    public AOUnsupportedSignFormatException(final String msg, final Throwable e) {
        super(msg, e);
    }
}
