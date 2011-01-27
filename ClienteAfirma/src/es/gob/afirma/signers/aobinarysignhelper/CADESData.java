/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Gobierno de Espa�a
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3, o superiores, seg�n las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompa�a.  Si se   distribuyera este 
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */


package es.gob.afirma.signers.aobinarysignhelper;


import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;


/**
 * Clase que implementa firma digital Data de CADES, que se basa en PKCS#7/CMS Data.
 * La Estructura del mensaje es la siguiente:<br>
 * <pre><code>

 * id-data OBJECT IDENTIFIER ::= { iso(1) member-body(2)
 *        us(840) rsadsi(113549) pkcs(1) pkcs7(7) 1 }
 *
 * Data ::= OCTET STRING
 *
 *</code></pre>
 * La implementaci&oacute;n del c&oacute;digo ha seguido los pasos necesarios para crear un
 * mensaje Data de BouncyCastle: <a href="http://www.bouncycastle.org/">www.bouncycastle.org</a>
 */

public final class CADESData {

    /**
     * M&eacute;odo que genera una firma digital usando el sitema conocido como
     * Data y que consiste en el contenido del fichero codificado como un conjunto
     * de bytes.
     *
     * @param parameters Par&aacute;metros necesarios para obtener los datos
     *                      de SignedData.
     * @return           El contenido del fichero en formato Data.
     */
    public byte[] genData(P7ContentSignerParameters parameters){


       // construimos el Data y lo devolvemos
        return new ContentInfo(
        	PKCSObjectIdentifiers.data,
        	new DEROctetString(parameters.getContent())
        ).getDEREncoded();
    }

}
