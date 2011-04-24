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
 * Excepci&oacute;n que indica una operaci&oacute;n cancelada voluntariamente por el usuario.
 */
public final class AOCancelledOperationException extends RuntimeException {

	private static final long serialVersionUID = 4447842480432712246L;

	/**
	 * Crea una excepci&oacute;n sin informaci&oacute;n adicional.
	 */
	public AOCancelledOperationException() {
		super();
	}
	
	/**
	 * Crea la excepci&oacute;n con un mensaje determinado.
	 * @param msg Mensaje descriptivo de la excepci&oacute;n.
	 */
	public AOCancelledOperationException(String msg) {
		super(msg);
	}

}
