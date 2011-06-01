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

import java.util.Properties;

import es.gob.afirma.misc.AOConstants;
import es.gob.afirma.ui.principal.MainOptionsPane;

/**
 * Configuraci&oacute;n global de la aplicaci&oacute;n.
 */
public class GeneralConfig {
	
	public static final String OPTION_ADVANCED_VIEW = "advancedView";
	public static final String OPTION_ALGORITHM = "algoritmo";
	public static final String OPTION_USE_ALGORITHM_XML = "algoritmoXML";
	
	/** Opciones de configuraci&oacute;n general. */
	private static Properties configOptions = new Properties();
	
	/** Opciones de configuraci&oacute;n de firma. */
	private static Properties configSignatureOptions = new Properties();
	
	private GeneralConfig() {}
	
	/** Estancia unica de la clase de configuraci&oacute;n. */
	private static GeneralConfig instance = null;
	
	/**
	 * Recupera la configuraci&oacute;n de firma.
	 * @return Devuelve la configurac&oacute;n general de firma configurada.
	 */
	public static GeneralConfig getInstance() {
		if (instance == null) {
			instance = new GeneralConfig();
		}
		
		return instance;
	}
	
	
	/**
	 * Recupera el algoritmo de firma configurado.
	 * @return Algoritmo de firma.
	 */
	public static String getSignAlgorithm() {
		return configOptions.getProperty(MainOptionsPane.MAIN_DEFAULT_ALGORITHM, "SHA1withRSA");
	}

	public static boolean isAvanzados() {
		return Boolean.parseBoolean(configOptions.getProperty(MainOptionsPane.MAIN_ADVANCED_VIEW, "false"));
	}

	
	public static void setOption(String optionKey, String optionValue) {
		configOptions.setProperty(optionKey, optionValue);
	}
	
	/**
	 * Recupera el valor de una de las opciones de configuraci&oacute;n.
	 * @param keyOption Clave de la opci&oacute;n deseada.
	 * @return Valor de la opci&oacute;n.
	 */
	public static String getOption(String keyOption) {
		return configOptions.getProperty(keyOption); 
	}
	
	/**
	 * Recupera la configuraci&oacute;n a establecer a las firmas.
	 * @return Configuraci&oacute;n para las firmas.
	 */
	public static Properties getSignConfig() {
		Properties config = new Properties();
		config.putAll(configSignatureOptions);
		return config;
	}
	
	/**
	 * Recupera la configuraci&oacute;n de la ventana de opciones.
	 * @return Configuraci&oacute;n de la ventana de opciones.
	 */
	public static Properties getConfig() {
		Properties config = new Properties();
		config.putAll(configOptions);
		return config;
	}
	
	/**
	 * Carga la nueva configuraci&oacute;n de la ventana de opciones.
	 * @param config Configuraci&oacute;n.
	 */
	public static void loadConfig(Properties config) {
		configOptions = new Properties();
		configOptions.putAll(config);
	}
	
	/**
	 * Carga la nueva configuraci&oacute;n de firma.
	 * @param config Configuraci&oacute;n que se desea cargar.
	 */
	public static void loadSignatureConfig(Properties config) {
		configSignatureOptions = new Properties();
		configSignatureOptions.putAll(getDefaultSignatureConfig());
		configSignatureOptions.putAll(config);
	}
	
	private static Properties getDefaultSignatureConfig() {
		Properties defaultConfig = new Properties();
		defaultConfig.setProperty("mode", AOConstants.SIGN_MODE_IMPLICIT);
		return defaultConfig;
	}
}
