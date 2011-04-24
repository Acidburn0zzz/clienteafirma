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
 * PasswordCallback que siempre devuelve un array de caracteres vac&iacute;o como contrase&ntilde;a.
 */
public final class EmptyPasswordCallback extends PasswordCallback {

	private static final long serialVersionUID = -8633754935170264262L;

	/**
	 * Contruye el la forma b&aacute;sica de la clase. 
	 */
	public EmptyPasswordCallback() {
		super(">", false);
	}
	
	@Override
	public char[] getPassword() {
		return new char[0];
	}
}
