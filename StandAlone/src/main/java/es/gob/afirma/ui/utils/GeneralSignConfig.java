/*
 * Este fichero forma parte del Cliente @firma.
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Ministerio de la Presidencia, Gobierno de Espa�a (opcional: correo de contacto)
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3  seg�n las
 * condiciones que figuran en el fichero 'licence' que se acompa�a.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */
package es.gob.afirma.ui.utils;

/**
 * Clase que guarda una configuracion generica de firma
 */
public class GeneralSignConfig {

	private String signatureProductionPlace = "";
	private String signReason = "";
	private String signContact = "";
	
	private String signAlgorithm = null;
	
	private boolean useAlgorithmInternally = false; 
	
	/**
	 * Establece una configuracion generica de firma. 
	 * @param algorithm Algoritmo de firma.
	 */
	public GeneralSignConfig(String algorithm) {
		this.signAlgorithm = algorithm;
	}

	public String getSignatureProductionPlace() {
		return signatureProductionPlace;
	}

	public void setSignatureProductionPlace(String signatureProductionPlace) {
		this.signatureProductionPlace = signatureProductionPlace;
	}

	public String getSignReason() {
		return signReason;
	}

	public void setSignReason(String signReason) {
		this.signReason = signReason;
	}

	public String getSignContact() {
		return signContact;
	}

	public void setSignContact(String signContact) {
		this.signContact = signContact;
	}

	public String getSignAlgorithm() {
		return signAlgorithm;
	}

	public void setSignAlgorithm(String signAlgorithm) {
		this.signAlgorithm = signAlgorithm;
	}

	public boolean isUseAlgorithmInternally() {
		return useAlgorithmInternally;
	}

	public void setUseAlgorithmInternally(boolean useAlgorithmInternally) {
		this.useAlgorithmInternally = useAlgorithmInternally;
	}
}
