/* Copyright (C) 2011 [Gobierno de Espana]
 * This file is part of "Cliente @Firma".
 * "Cliente @Firma" is free software; you can redistribute it and/or modify it under the terms of:
 *   - the GNU General Public License as published by the Free Software Foundation; 
 *     either version 2 of the License, or (at your option) any later version.
 *   - or The European Software License; either versi�n 1.1 or (at your option) any later version.
 * Date: 11/01/11
 * You may contact the copyright holder at: soporte.afirma5@mpt.es
 */

package es.gob.afirma.standalone.dnie;

/** Excepci&oacute;n en la gesti&oacute;n de almac&eacute;n DNIe v&iacute;a PKCS#11 y JSR-268.
 * @author Tom&aacute;s Garc&iacute;a-Mer&aacute;s */
public final class DNIeManagerException extends Exception {

    private static final long serialVersionUID = 9198656551956236883L;

    DNIeManagerException(final String msg, final Throwable t) {
        super(msg, t);
    }

    DNIeManagerException(final String msg) {
        super(msg);
    }

}
