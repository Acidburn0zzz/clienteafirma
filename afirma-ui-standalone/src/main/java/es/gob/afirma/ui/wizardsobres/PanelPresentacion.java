/*
 * Este fichero forma parte del Cliente @firma.
 * El Cliente @firma es un applet de libre distribucion cuyo codigo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Ministerio de la Presidencia, Gobierno de Espana
 * Este fichero se distribuye bajo licencia GPL version 3 segun las
 * condiciones que figuran en el fichero 'licence' que se acompana.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aqui las condiciones expresadas alli.
 */
package es.gob.afirma.ui.wizardsobres;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JPanel;

import es.gob.afirma.ui.principal.Main;
import es.gob.afirma.ui.utils.InfoLabel;
import es.gob.afirma.ui.utils.JAccessibilityDialogWizard;
import es.gob.afirma.ui.utils.Messages;
import es.gob.afirma.ui.utils.RequestFocusListener;
import es.gob.afirma.ui.utils.Utils;
import es.gob.afirma.ui.wizardUtils.BotoneraInferior;
import es.gob.afirma.ui.wizardUtils.BotoneraSuperior;
import es.gob.afirma.ui.wizardUtils.ImagenLateral;
import es.gob.afirma.ui.wizardUtils.JDialogWizard;

/**
 *
 * Panel explicativo de presentacion
 */
public class PanelPresentacion extends JAccessibilityDialogWizard {

	private static final long serialVersionUID = 1L;
	
	@Override
	public int getMinimumRelation(){
		return 9;
	}
	
	/**
     * Guarda todas las ventanas del asistente para poder controlar la botonera
     * @param ventanas	Listado con todas las paginas del asistente
     */
    public void setVentanas(List<JDialogWizard> ventanas) {
    	this.setBotoneraSuperior(new BotoneraSuperior(ventanas));
    	this.setBotonera(new BotoneraInferior(ventanas, 0));
    	getContentPane().add(getBotoneraSuperior(), BorderLayout.PAGE_START);
    	getContentPane().add(getBotonera(), BorderLayout.PAGE_END);
    	//Se asigna el botón por defecto
    	this.getRootPane().setDefaultButton(getBotonera().getSiguiente());
    }
	
    public PanelPresentacion() {
        initComponents();
    }
    
    /**
     * Inicializacion de componentes
     */
    private void initComponents() {
    	// Titulo de la ventana
    	setTitulo(Messages.getString("Wizard.sobres.titulo"));
    	
    	// Panel con la imagen lateral
        ImagenLateral panelIzdo = new ImagenLateral();
        if (Main.isOSHighContrast){
        	panelIzdo.setOpaque(false);
        }
        Utils.setContrastColor(panelIzdo);
        getContentPane().add(panelIzdo, BorderLayout.WEST);
        
        // Panel con el contenido
        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(Color.WHITE);
        // si el color de fondo ya no es blanco
        if (Main.isOSHighContrast){
        	panelCentral.setOpaque(false);
        }
        Utils.setContrastColor(panelCentral);
        panelCentral.setLayout(new GridBagLayout());

        // Configuramos el layout
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20, 20, 20, 20);
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		
		//Etiqueta con el texto "Bienvenido al asistente..."
        String textLabel = Messages.getString("Wizard.sobres.presentacion1") +
        		"<br>"+"<br>"+Messages.getString("Wizard.sobres.presentacion2")+
        		"<br>"+"<br>"+Messages.getString("Wizard.sobres.presentacion3")+
        		"<br>"+"<br>"+Messages.getString("Wizard.sobres.presentacion4")+
        		"<br>"+"<br>"+Messages.getString("Wizard.sobres.presentacion5");
        InfoLabel presentationLabel = new InfoLabel(textLabel, false);
        //Foco al contenido
        presentationLabel.addAncestorListener(new RequestFocusListener(false));
        panelCentral.add(presentationLabel, c);

        getContentPane().add(panelCentral, BorderLayout.CENTER);
    }
}
