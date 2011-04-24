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
 * PasswordCallback que siempre devuelve <code>null</code> como contrase&ntilde;a.
 */
public final class NullPasswordCallback extends PasswordCallback {

	private static final long serialVersionUID = -5926953046433722802L;

	/**
	 * Contruye el la forma b&aacute;sica de la clase. 
	 */
	public NullPasswordCallback() {
		super(">", false);
	}
	
	@Override
	public char[] getPassword() {
		return null;
	}
}
