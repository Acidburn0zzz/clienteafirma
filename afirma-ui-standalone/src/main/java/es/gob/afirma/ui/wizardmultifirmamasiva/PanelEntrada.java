/*
 * Este fichero forma parte del Cliente @firma.
 * El Cliente @firma es un applet de libre distribucion cuyo codigo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Ministerio de la Presidencia, Gobierno de Espana
 * Este fichero se distribuye bajo licencia GPL version 3 segun las
 * condiciones que figuran en el fichero 'licence' que se acompana.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aqui las condiciones expresadas alli.
 */
package es.gob.afirma.ui.wizardmultifirmamasiva;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Caret;

import es.gob.afirma.ui.utils.ConfigureCaret;
import es.gob.afirma.ui.utils.GeneralConfig;
import es.gob.afirma.ui.utils.HelpUtils;
import es.gob.afirma.ui.utils.JAccessibilityDialogWizard;
import es.gob.afirma.ui.utils.Messages;
import es.gob.afirma.ui.utils.SelectionDialog;
import es.gob.afirma.ui.utils.Utils;
import es.gob.afirma.ui.wizardUtils.BotoneraInferior;
import es.gob.afirma.ui.wizardUtils.CabeceraAsistente;
import es.gob.afirma.ui.wizardUtils.JDialogWizard;

class PanelEntrada extends JAccessibilityDialogWizard {

	private static final long serialVersionUID = 1L;

	// Indica si el asistente se ha saltado la pagina anterior
	private Boolean salto = false;
	
	@Override
	public int getMinimumRelation(){
		return 9;
	}
	
	/**
	 * Registra un salto dentro del asistente de la pagina 2 a la 3.
	 * @param salto	Indica si se ha realizado un salto
	 */
	void setSalto(Boolean salto) {
		this.salto = salto;
	}
	
	/**
	 * Guarda todas las ventanas del asistente para poder controlar la botonera
	 * @param ventanas	Listado con todas las paginas del asistente
	 */
	void setVentanas(List<JDialogWizard> ventanas) {
		Botonera botonera = new Botonera(ventanas, 3);
		getContentPane().add(botonera, BorderLayout.PAGE_END);
	}
	
    PanelEntrada() {
        initComponents();
    }
    
    // Caja de texto donde se guarda el directorio
    private JTextField campoDirectorio = new JTextField();
    // Caja de texto para escribir las extensiones
    private JTextField campoExtensiones = new JTextField();
    // Checkbox con el texto "Incluir subdirectorios..."
    private JCheckBox checkIncluir = new JCheckBox();
    
    /**
     * Inicializacion de componentes
     */
    private void initComponents() {
    	// Titulo de la ventana
    	setTitulo(Messages.getString("Wizard.multifirma.titulo.ventana"));
    	
    	// Panel con la cabecera
        CabeceraAsistente panelSuperior = new CabeceraAsistente("Wizard.multifirma.ventana3.titulo", "Wizard.multifirma.ventana3.titulo.descripcion", null, true);
        Utils.setContrastColor(panelSuperior);
        Utils.setFontBold(panelSuperior);
        getContentPane().add(panelSuperior, BorderLayout.NORTH);
		
        // Panel central
    	JPanel panelCentral = new JPanel();
    	panelCentral.setMinimumSize(new Dimension(603, 289));
    	panelCentral.setLayout(new GridBagLayout());
        
    	// Configuramos el layout
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20, 20, 0, 20);
        c.gridwidth = 2;
		c.weightx = 1.0;
    	
    	// Etiqueta con el texto "Directorio con los..."
    	JLabel etiquetaFirma = new JLabel();
    	etiquetaFirma.setText(Messages.getString("Wizard.multifirma.ventana3.directorio"));
    	Utils.setContrastColor(etiquetaFirma);
    	Utils.setFontBold(etiquetaFirma);
        panelCentral.add(etiquetaFirma, c);
    	
        c.insets = new Insets(0, 20, 0, 0);
        c.gridwidth = 1;
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 1;
        
        // Caja de texto donde se guarda el directorio de entrada
        campoDirectorio.setToolTipText(Messages.getString("Wizard.multifirma.ventana3.directorio.description"));
        campoDirectorio.getAccessibleContext().setAccessibleName(Messages.getString("Wizard.multifirma.ventana3.directorio"));
        campoDirectorio.getAccessibleContext().setAccessibleDescription(Messages.getString("Wizard.multifirma.ventana3.directorio.description"));
        if (GeneralConfig.isBigCaret()) {
			Caret caret = new ConfigureCaret();
			campoDirectorio.setCaret(caret);
		}
        if (GeneralConfig.isRemarked()){
        	Utils.remarcar(campoDirectorio);
        }
        Utils.setContrastColor(campoDirectorio);
        Utils.setFontBold(campoDirectorio);
        panelCentral.add(campoDirectorio, c);
        
        //Relaci�n entre etiqueta y campo de texto
        etiquetaFirma.setLabelFor(campoDirectorio);
  		//Asignaci�n de mnem�nico
        etiquetaFirma.setDisplayedMnemonic(KeyEvent.VK_D);
        
        c.insets = new Insets(0, 10, 0, 20);
        c.gridwidth = 1;
		c.weightx = 0.0;
		c.gridx = 1;
        
        // Boton examinar
        JButton examinar = new JButton();
        examinar.setMnemonic(KeyEvent.VK_E);
        examinar.setText(Messages.getString("PrincipalGUI.Examinar"));
        examinar.setToolTipText(Messages.getString("PrincipalGUI.Examinar.description"));
        examinar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                examinarActionPerformed();
            }
        });
        if (GeneralConfig.isRemarked()){
        	Utils.remarcar(examinar);
        }
        Utils.setContrastColor(examinar);
        Utils.setFontBold(examinar);
        panelCentral.add(examinar, c);
        
        c.insets = new Insets(5, 20, 0, 20);
        c.gridwidth = 2;
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 2;
        
        // Checkbox con el texto "Incluir subdirectorios..."
        checkIncluir.setText(Messages.getString("Wizard.multifirma.ventana3.check.incluir"));
        checkIncluir.getAccessibleContext().setAccessibleName(Messages.getString("Wizard.multifirma.ventana3.check.incluir"));
        checkIncluir.getAccessibleContext().setAccessibleDescription(Messages.getString("Wizard.multifirma.ventana3.check.incluir.description"));
        checkIncluir.setMnemonic(KeyEvent.VK_I); //Se asigna un atajo al checkbox
        if (GeneralConfig.isRemarked()){
        	Utils.remarcar(checkIncluir);
        }
        Utils.setContrastColor(checkIncluir);
        Utils.setFontBold(checkIncluir);
        panelCentral.add(checkIncluir, c);
        
        c.insets = new Insets(20, 20, 0, 20);
		c.gridy = 3;
        
        // Etiqueta con el texto "Aplicar solo a los..."
        JLabel etiquetaAplicar = new JLabel();
        etiquetaAplicar.setText(Messages.getString("Wizard.multifirma.ventana3.aplicar"));
        Utils.setContrastColor(etiquetaAplicar);
        Utils.setFontBold(etiquetaAplicar);
        panelCentral.add(etiquetaAplicar, c);
        
        c.insets = new Insets(0, 20, 0, 20);
		c.gridy = 4;
        
        // Caja de texto para escribir las extensiones
        campoExtensiones.setToolTipText(Messages.getString("Wizard.multifirma.ventana3.aplicar.description"));
        campoExtensiones.getAccessibleContext().setAccessibleName(Messages.getString("Wizard.multifirma.ventana3.aplicar"));
        campoExtensiones.getAccessibleContext().setAccessibleDescription(Messages.getString("Wizard.multifirma.ventana3.aplicar.description"));
        if (GeneralConfig.isBigCaret()) {
			Caret caret = new ConfigureCaret();
			campoExtensiones.setCaret(caret);
		}
        if (GeneralConfig.isRemarked()){
        	Utils.remarcar(campoExtensiones);
        }
        Utils.setContrastColor(campoExtensiones);
        Utils.setFontBold(campoExtensiones);
        panelCentral.add(campoExtensiones, c);
        
        //Relaci�n entre etiqueta y campo de texto
        etiquetaAplicar.setLabelFor(campoExtensiones);
  		//Asignaci�n de mnem�nico
        etiquetaAplicar.setDisplayedMnemonic(KeyEvent.VK_P);
    
        c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(20, 20, 0, 20);
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridy = 5;
		
		// Panel introducido para poder mantener la linea superior correcta
		Panel panelVacio = new Panel();
		panelCentral.add(panelVacio, c);
        
        getContentPane().add(panelCentral, BorderLayout.CENTER);
        
        // Accesos rapidos al menu de ayuda
        HelpUtils.enableHelpKey(campoDirectorio,"multifirma.masiva.wizard.firma.directorios");
        HelpUtils.enableHelpKey(checkIncluir,"multifirma.masiva.wizard.firma.incluir");
        HelpUtils.enableHelpKey(campoExtensiones,"multifirma.masiva.wizard.firma.extension");
    }

    /**
     * Comprueba que el fichero seleccionado es valido y guarda su nombre en el campo de texto
     */
    private void examinarActionPerformed() {
    	File selectedFile = new SelectionDialog().showDirOpenDialog(this, Messages.getString("PrincipalGUI.chooser.dir.title"));    	
    	if (selectedFile != null) {
    		campoDirectorio.setText(selectedFile.getAbsolutePath());
    	}
    }
    
    /**
	 * Botonera con funciones para la pagina panel de multifirma - cofirma
	 */
	private class Botonera extends BotoneraInferior {

		private static final long serialVersionUID = 1L;

		public Botonera(List<JDialogWizard> ventanas, Integer posicion) {
			super(ventanas, posicion);
		}

		@Override
		protected void siguienteActionPerformed(JButton anterior,
				JButton siguiente, JButton finalizar) {
			
			Boolean continuar = true;
			continuar = verificarFicheros();
			
			if (continuar == true) {
				// Carga las extensiones
				((PanelMultifirmaMasiva) getVentanas().get(4)).setExtensiones(campoExtensiones.getText());
				
				// Carga el directorio de entrada
				((PanelMultifirmaMasiva) getVentanas().get(4)).setDirectorioEntrada(campoDirectorio.getText());
				
				// Indica si se deben incluir los subdirectorios
				((PanelMultifirmaMasiva) getVentanas().get(4)).setIncluir(checkIncluir.isSelected());
				
				super.siguienteActionPerformed(anterior, siguiente, finalizar);
			}
		}
		
		@Override
		protected void anteriorActionPerformed(JButton anterior,
				JButton siguiente, JButton finalizar) {
			
			if (salto) {
				Integer indice = 1;
				
				//ventanas.get(posicion).dispose();
				getVentanas().get(indice).setVisibleAndHide(true, getVentanas().get(3));
			}
			else
				super.anteriorActionPerformed(anterior, siguiente, finalizar);
		}
	}

	/**
	 * Verifica que los archivos del directorio seleccionado son correctos
	 * @return	True o false segun la verificacion
	 */
	Boolean verificarFicheros() {
		//comprobaci�n de la ruta de fichero de entrada.
		String directorio = campoDirectorio.getText();
		if (directorio == null || directorio.equals("")) {
			JOptionPane.showMessageDialog(this, Messages.getString("Wizard.multifirma.error.directorio.origen"), Messages.getString("error"), JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (!new File(directorio).exists() || !new File(directorio).isDirectory()) {
			JOptionPane.showMessageDialog(this, Messages.getString("Wizard.multifirma.error.directorio.origen2"), Messages.getString("error"), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
}
