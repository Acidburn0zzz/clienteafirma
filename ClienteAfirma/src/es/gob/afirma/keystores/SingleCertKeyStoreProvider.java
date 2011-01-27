/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Gobierno de Espa�a
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3, o superiores, seg�n las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompa�a.  Si se   distribuyera este 
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */


package es.gob.afirma.keystores;

import java.security.AccessController;
import java.security.Provider;

/**
 * Proveedor de seguridad espec&iacute;fico para servicios de <i>KeyStore</i> restringidos a almacenes
 * PKCS#7 y certificados X.509 en Base64.
 */
public final class SingleCertKeyStoreProvider extends Provider {

	private static final long serialVersionUID = 3525417804439532445L;

	protected SingleCertKeyStoreProvider() {
	   super("PKCS7", 0.1d, "KeyStore for a PKCS7 or X509 certificate");
		
       AccessController.doPrivileged(new java.security.PrivilegedAction<Object>() {
            public Object run() {
				put("KeyStore.PKCS7", "es.gob.afirma.keystores.SingleCertKeyStore");
				return null;
			}
       });
	}
}
