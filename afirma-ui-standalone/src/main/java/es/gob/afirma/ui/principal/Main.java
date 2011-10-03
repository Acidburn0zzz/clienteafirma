/*
 * Este fichero forma parte del Cliente @firma.
 * El Cliente @firma es un applet de libre distribucion cuyo codigo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Ministerio de la Presidencia, Gobierno de Espana
 * Este fichero se distribuye bajo licencia GPL version 3 segun las
 * condiciones que figuran en el fichero 'licence' que se acompana.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aqui las condiciones expresadas alli.
 */
package es.gob.afirma.ui.principal;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import es.gob.afirma.core.misc.Platform;
import es.gob.afirma.ui.utils.Constants;
import es.gob.afirma.ui.utils.JAccessibilityOptionPane;
import es.gob.afirma.ui.utils.Messages;

/**
 *	Entrada de la aplicacion
 */
public class Main {

	/** Versi&oacute;n de la interfaz gr&aacute;fica de escritorio. */
	public static final String VERSION = "1.3"; //$NON-NLS-1$
	
	static Logger logger = Logger.getLogger(Main.class.getName());
	
	/** Preferencias generales establecidas para el aplicativo. */
    public static Preferences preferences = Preferences.userRoot().node(Constants.ourNodeName);
	
    /**
     * Arranca la interfaz de escritorio del Cliente @firma.
     * @param args Par&aacute;metros de entrada.
     */
    public static void main(String[] args) {
		if (System.getProperty("java.version").compareTo("1.6.0_18") < 0) {
				JAccessibilityOptionPane.showMessageDialog(
					null,
					Messages.getString("main.requerido")+
					System.getProperty("java.version")+".<br>"+
					Messages.getString("main.porfavor"),
					Messages.getString("main.cliente"),
				JOptionPane.ERROR_MESSAGE);
			System.exit(-5);
		}
    	try {
        	if (Platform.getOS().equals(Platform.OS.LINUX))
        		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        	else
        		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        	logger.log(Level.SEVERE, null, ex);
        }
        
        if (preferences.get("users", "0").equals("0")){
        	UserProfile.currentUser=Constants.defaultUser;
        	new PrincipalGUI().main();
        } else {
        	new UserProfile().main();
        }
    }

}
