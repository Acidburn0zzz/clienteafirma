/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Ministerio de la Presidencia, Gobierno de Espa�a (opcional: correo de contacto)
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3  seg�n las
 * condiciones que figuran en el fichero 'licence' que se acompa�a.  Si se   distribuyera este 
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */

package es.gob.afirma.cliente.utilidades.browser;


import java.awt.Frame;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.swing.UIManager;

public final class Browser {

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (final Throwable evt) {
            Logger.getLogger("es.gob.afirma").warning("No se pudo cargar el Look & Feel de Windows" + evt);
        }
    }
    
    public FirmadorWeb.FirmaWeb browse(final String html, final String hashAlg) throws IOException, NoSuchAlgorithmException {
        final BrowserDialog bd;
        if(Frame.getFrames()!=null&&Frame.getFrames().length>0){
        	Logger.getLogger("es.gob.afirma").info("Se ha encontrado al menos un frame");
            bd = new BrowserDialog(html,Frame.getFrames()[0]);
        }
        else{
        	Logger.getLogger("es.gob.afirma").info("No se han encontrado frames. Creamos uno invisible");
            bd = new BrowserDialog(html,new Frame());
        }
        
        bd.setVisible(true);
        boolean firmar= bd.isFirmar();
        
        if(firmar) return new FirmadorWeb().firmar(
    		html, 
    		AFirmaWebSignHTMLDocument.files.toArray(new Attachment[AFirmaWebSignHTMLDocument.files.size()]), 
    		hashAlg
		);
        return null;

    }
}
