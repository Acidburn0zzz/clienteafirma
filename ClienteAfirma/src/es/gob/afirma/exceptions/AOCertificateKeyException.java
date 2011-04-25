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
 * Excepci&oacute;n para notificar que no se ha podido recuperar la clave de un certificado.
 */
public final class AOCertificateKeyException extends AOException {

	private static final long serialVersionUID = -6996346324337434742L;

	/**
	 * Crea la excepci&oacute;n con un mensaje determinado.
	 * @param msg Mensaje descriptivo de la excepci&oacute;n
	 */
	public AOCertificateKeyException(String msg) {
		super(msg);
	}

	/**
	 * Crea la excepci&oacute;n con un mensaje determinado a&ntilde;adiendo a la pila otra excepci&oacute;n.
	 * @param msg Mensaje descriptivo de la excepci&oacute;n
	 * @param e Excepci&oacute;n a a&ntilde;afir a la pila
	 */
	public AOCertificateKeyException(String msg, Throwable e) {
		super(msg, e);
	}
}
