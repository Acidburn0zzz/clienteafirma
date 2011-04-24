/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Ministerio de la Presidencia, Gobierno de Espa�a (opcional: correo de contacto)
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3  seg�n las
 * condiciones que figuran en el fichero 'licence' que se acompa�a.  Si se   distribuyera este 
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */

package es.gob.afirma.callbacks;

import javax.security.auth.callback.PasswordCallback;

/**
 * PasswordCallbak que cachea y devuelve la contrase&ntilde;a con la que se construy&oacute;
 * o la que se le establece posteriormente.
 */
public final class CachePasswordCallback extends PasswordCallback {

	private static final long serialVersionUID = 816457144215238935L;

	/**
	 * Contruye una Callback con una contrase&ntilda; preestablecida. 
	 * @param password Contrase&ntilde;a por defecto.
	 */
	public CachePasswordCallback(char[] password) {
		super(">", false);
		this.setPassword(password);
	}
}
