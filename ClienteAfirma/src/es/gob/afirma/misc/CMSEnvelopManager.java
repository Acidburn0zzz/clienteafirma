/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Gobierno de Espa�a
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3, o superiores, seg�n las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompa�a.  Si se   distribuyera este 
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */


package es.gob.afirma.misc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;

import es.gob.afirma.exceptions.AOException;
import es.gob.afirma.signers.AOCMSSigner;
import es.gob.afirma.signers.aobinarysignhelper.CMSAuthenticatedData;
import es.gob.afirma.signers.aobinarysignhelper.CMSAuthenticatedEnvelopedData;
import es.gob.afirma.signers.aobinarysignhelper.CMSEnvelopedData;
import es.gob.afirma.signers.aobinarysignhelper.CMSSignedAndEnvelopedData;
import es.gob.afirma.signers.aobinarysignhelper.ValidateCMS;


/**
 * Manejador para la generaci&oacute;n de estructuras CMS.
 */
public class CMSEnvelopManager {
	
	/**
	 * Agrega un nuevo remitente a un envoltorio CMS compatible.  
	 * @param is Envoltorio original.
	 * @param originatorCertChain Cadena de certificaci&oacute;n del nuevo remitente.
	 * @return Envoltorio con el nuevo remitente.
	 * @throws IOException Cuando se produce un error al leer los datos.
	 * @throws AOException Cuando se produce un error al agregar el nuevo remitente.
	 */
	public byte[] doCoEnvelop(InputStream is, X509Certificate[] originatorCertChain) throws IOException, AOException {
		
		String contentInfo;
		ValidateCMS validator = new ValidateCMS();
		byte[] envelop = AOUtil.getDataFromInputStream(is);
		if (validator.isCMSEnvelopedData(new ByteArrayInputStream(envelop))) {
			contentInfo = AOConstants.BINARY_ENVELOP_ENVELOPEDDATA;
		} else if (validator.isCMSAuthenticatedData(new ByteArrayInputStream(envelop))) {
			contentInfo = AOConstants.BINARY_ENVELOP_AUTHENTICATEDDATA;
		} else if (validator.isCMSAuthenticatedEnvelopedData(new ByteArrayInputStream(envelop))) {
			contentInfo = AOConstants.BINARY_ENVELOP_AUTHENTICATEDENVELOPEDDATA;
		} else {
			throw new IllegalArgumentException("Los datos proporcionado no son un envoltorio que soporte multiples remitentes");
		}
		
		return doCoEnvelopOperation(new ByteArrayInputStream(envelop), contentInfo, originatorCertChain); 
	}
	
	/**
	 * Agrega los datos de un remitente adicional a un envoltorio compatible. Los envoltorios
	 * que admiten m&aacute;s de un remitente son:
	 * <ul>
	 * <li>Enveloped Data</li>
	 * <li>Authenticated Data</li>
	 * <li>Authenticated And Enveloped Data</li>
	 * </ul>
	 * @param streamToEnvelop Estructura a la que se le desea agregar un remitente.
	 * @param contentInfo Tipo de contenido que se desea envolver.
	 * @param originatorCertChain Cadena de certificaci&oacute;n del nuevo destinatario.
	 * @throws AOException Cuando ocurrio un error al agregar el remitente a la estructura.
	 * @throws IllegalArgumentException Cuando se indica un contentInfo no compatible con m&uacute;tiples remitentes.
	 */
	private byte[] doCoEnvelopOperation(final InputStream streamToEnvelop, final String contentInfo, final X509Certificate[] originatorCertChain) throws AOException {
		
				byte[] envelop;
				if (contentInfo.equals(AOConstants.BINARY_ENVELOP_AUTHENTICATEDENVELOPEDDATA)) {
					CMSAuthenticatedEnvelopedData enveloper = new CMSAuthenticatedEnvelopedData();
					envelop = enveloper.addOriginatorInfo(streamToEnvelop, originatorCertChain);
				}
				else if (contentInfo.equals(AOConstants.BINARY_ENVELOP_AUTHENTICATEDDATA)) {
					CMSAuthenticatedData enveloper = new CMSAuthenticatedData();
					envelop = enveloper.addOriginatorInfo(streamToEnvelop, originatorCertChain);
				}
				else if (contentInfo.equals(AOConstants.BINARY_ENVELOP_ENVELOPEDDATA)) {
					CMSEnvelopedData enveloper = new CMSEnvelopedData();
					envelop = enveloper.addOriginatorInfo(streamToEnvelop, originatorCertChain);
				}
				else {
					throw new IllegalArgumentException(
							"La estructura para el ContentInfo indicado no esta soportada o " +
							"no admite multiples remitentes");
				}

				if (envelop == null) {
					throw new AOException("Ocurrio un error al agregar el nuevo remitente al envoltorio");
				}
				
				return envelop;
	}
}
