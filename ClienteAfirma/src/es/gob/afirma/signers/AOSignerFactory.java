/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Gobierno de Espa�a
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3, o superiores, seg�n las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompa�a.  Si se   distribuyera este 
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */


package es.gob.afirma.signers;

import java.security.Security;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.util.logging.Logger;

import es.gob.afirma.misc.AOConstants;
import es.gob.afirma.misc.SHA2AltNamesProvider;

/**
 * Factor&iacute;a que gestiona todos los formatos de firma disponibles en cada momento en el cliente.
 */
public final class AOSignerFactory {

	private static AOSignerFactory signerFactory = null;
	
	/** Listado completo de formatos de firma soportados y el manejador de firma asociado. */
	private static final String[][] ID_SIGNERS = {
		{AOConstants.SIGN_FORMAT_CADES, "es.gob.afirma.signers.AOCADESSigner"},
		{AOConstants.SIGN_FORMAT_CMS, "es.gob.afirma.signers.AOCMSSigner"},
		{AOConstants.SIGN_FORMAT_XADES_DETACHED, "es.gob.afirma.signers.AOXAdESSigner"},
		{AOConstants.SIGN_FORMAT_XADES_EXTERNALLY_DETACHED, "es.gob.afirma.signers.AOXAdESSigner"},
		{AOConstants.SIGN_FORMAT_XADES_ENVELOPED, "es.gob.afirma.signers.AOXAdESSigner"},
		{AOConstants.SIGN_FORMAT_XADES_ENVELOPING, "es.gob.afirma.signers.AOXAdESSigner"},
		{AOConstants.SIGN_FORMAT_XMLDSIG_DETACHED, "es.gob.afirma.signers.AOXMLDSigSigner"},
		{AOConstants.SIGN_FORMAT_XMLDSIG_EXTERNALLY_DETACHED, "es.gob.afirma.signers.AOXMLDSigSigner"},
		{AOConstants.SIGN_FORMAT_XMLDSIG_ENVELOPED, "es.gob.afirma.signers.AOXMLDSigSigner"},
		{AOConstants.SIGN_FORMAT_XMLDSIG_ENVELOPING, "es.gob.afirma.signers.AOXMLDSigSigner"},
		{AOConstants.SIGN_FORMAT_PDF, "es.gob.afirma.signers.AOPDFSigner"},
		{AOConstants.SIGN_FORMAT_ODF, "es.gob.afirma.signers.AOODFSigner"},
		{AOConstants.SIGN_FORMAT_OOXML, "es.gob.afirma.signers.AOOOXMLSigner"},
		{AOConstants.SIGN_FORMAT_PKCS1, "es.gob.afirma.signers.AOPKCS1Signer"}
	};
	
	private static Vector<String> signersID;
	private static HashMap<String, AOSigner> signers;
		
	private AOSignerFactory() {}
	
	/**
	 * Obtiene una instancia de la factor&iacute;a.
	 * @return Instancia de la factor&iacute;a
	 */
	public static final AOSignerFactory getInstance() {
		if(signerFactory != null) {
			return signerFactory;
		}
				
		// Cargamos 
		signerFactory = new AOSignerFactory();
		signersID = new Vector<String>();
		signers = new HashMap<String, AOSigner>();
		
		for (int i = 0; i < ID_SIGNERS.length; i++) {
			try {
				AOSigner signer = (AOSigner)Class.forName(ID_SIGNERS[i][1]).newInstance();
				signersID.add(ID_SIGNERS[i][0]);
				if(!signers.containsKey(ID_SIGNERS[i][1])) {
					signers.put(ID_SIGNERS[i][1], signer);
				}
				//Logger.getLogger("es.gob.afirma").info("Se ha configurado el formato de firma: "+ID_SIGNERS[i][0]);
			} catch (Throwable e) {
				// No se encuentra el plugin para el formato de firma
			    Logger.getLogger("es.gob.afirma").warning("No se encuentra el plugin para el formato: "+ID_SIGNERS[i][0]);
			}
		}
		
		// Cargamos un proveedor de seguridad con alias alternativos para los algoritmos
		// de hash SHA-2, necesario para determinados signers.
 		if (Security.getProvider("AOSHA2AltNamesProvider") == null)
 			Security.addProvider(new SHA2AltNamesProvider());
		
		return signerFactory;
	}
	
	/**
	 * Obtiene un manejador para un formato de firma dado. En caso de no encontrar ninguno, se devuelve
	 * <code>null</code>.
	 * @param signerID Formato de firma para el cual solicitamos el manejador.
	 * @return Manejador capaz de firmar en el formato indicado.
	 */
	public final AOSigner getSigner(String signerID) {
		if (signersID.contains(signerID)) {
			for (int i = 0; i < ID_SIGNERS.length; i++) {
				if (ID_SIGNERS[i][0].equals(signerID)) {
					return signers.get(ID_SIGNERS[i][1]);
				}
			}
		}
		
		Logger.getLogger("es.gob.afirma").warning("El formato de firma '" + signerID + "' no esta soportado, se devolvera null");
		
		return null;
	}

	/**
	 * Obtiene el manejador del formato de firma por defecto establecido. 
	 * @return Manejador de firma.
	 * @see es.gob.afirma.misc.AOConstants#DEFAULT_SIGN_FORMAT
	 */
	public final AOSigner getDefaultSigner() {
		for (int i = 0; i < ID_SIGNERS.length; i++) {
			if (ID_SIGNERS[i][0].equals(AOConstants.DEFAULT_SIGN_FORMAT)) {
				AOSigner signer = signers.get(ID_SIGNERS[i][1]);
				if (signer == null) {
					Logger.getLogger("es.gob.afirma").severe(
								"No se ha encontrado el manejador del formato de firma por defecto ('" + 
								AOConstants.DEFAULT_SIGN_FORMAT + "')"
					);
				}
				return signer;
			}
		}
		Logger.getLogger("es.gob.afirma").severe(
					"El formato de firma por defecto ('" + AOConstants.DEFAULT_SIGN_FORMAT + "') no esta soportado"
		);
		return null;
	}
	
	/**
	 * Recupera el listado de los manejadores de firma disponibles en el sistema seg&uacute;n el
	 * orden en el que se insertasen en el sistema. Aunque un manejador soporte m&aacute:s de un
	 * formato de firma, s&oacute;lo aparecer&aacute; &uacute;nica vez.
	 * @return Listado de manejadores.
	 */
	public final AOSigner[] getSigners() {
		Vector<AOSigner> result = new Vector<AOSigner>(signers.size());
		HashSet<String> classes = new HashSet<String>(signers.size());
		
		// Recorremos los formatos soportados
		for(int i=0; i<signersID.size(); i++) {
			// Buscamos el manejador del formato
			for(int j=0; j<ID_SIGNERS.length; j++) {
				if(signersID.get(i).equals(ID_SIGNERS[j][0])) {
					// Si el manejador no se ha agregado ya a la lista de resultados, se agrega
					if(!classes.contains(ID_SIGNERS[j][1])) {
						classes.add(ID_SIGNERS[j][1]);
						result.add(signers.get(ID_SIGNERS[j][1]));
					}
					break;
				}
			}
		}
		return result.toArray(new AOSigner[result.size()]); 
	}
	
	/**
	 * Recupera el listado de identificadores de formatos de firma actualmente disponibles en un orden
	 * predefinido.  
	 * @return Identificadors de formato de firma.
	 */
	public String[] getSignersID() {
		return signersID.toArray(new String[signersID.size()]); 
	}

	
	/**
	 * Devuelve el listado de formatos de firma soportados.
	 * @return Indentificadores de los formatos de firma soportados. 
	 */
	public String toString() {
		StringBuilder exstr = new StringBuilder(); //$NON-NLS-1$
		for (String format : signersID) {
			exstr.append(format).append("\n"); //$NON-NLS-1$
		}
		return exstr.toString();
	}
	
//	public static void main(String[] args) {
//		
//		AOSignerFactory factory = AOSignerFactory.getInstance();
//		for(String id : factory.getSignersID()) {
//			System.out.println(id);
//		}
//		System.out.println("==============");
//		for(AOSigner sig : factory.getSigners()) {
//			System.out.println(sig.getClass().getName());
//		}
//		System.out.println("==============");
//		
//		System.out.println("Prueba:");
//		System.out.println(AOConstants.SIGN_FORMAT_XADES_ENVELOPED + ": "+factory.getSigner(AOConstants.SIGN_FORMAT_XADES_ENVELOPED).getClass().getName());
//	}
}
