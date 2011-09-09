/*
 * Este fichero forma parte del Cliente @firma.
 * El Cliente @firma es un applet de libre distribucion cuyo codigo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Ministerio de la Presidencia, Gobierno de Espana
 * Este fichero se distribuye bajo licencia GPL version 3 segun las
 * condiciones que figuran en el fichero 'licence' que se acompana.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aqui las condiciones expresadas alli.
 */
package es.gob.afirma.ui.wizardmultifirmacontrafirma;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.gob.afirma.core.AOException;
import es.gob.afirma.core.misc.AOUtil;
import es.gob.afirma.core.signers.AOSignConstants;
import es.gob.afirma.core.signers.AOSigner;
import es.gob.afirma.core.ui.AOUIFactory;
import es.gob.afirma.keystores.common.AOKeyStoreManager;
import es.gob.afirma.keystores.common.KeyStoreConfiguration;
import es.gob.afirma.ui.utils.GeneralConfig;
import es.gob.afirma.ui.utils.HelpUtils;
import es.gob.afirma.ui.utils.JAccessibilityDialogWizard;
import es.gob.afirma.ui.utils.Messages;
import es.gob.afirma.ui.utils.MultisignUtils;
import es.gob.afirma.ui.utils.SelectionDialog;
import es.gob.afirma.ui.utils.SignFileUtils;
import es.gob.afirma.ui.wizardUtils.BotoneraInferior;
import es.gob.afirma.ui.wizardUtils.CabeceraAsistente;
import es.gob.afirma.ui.wizardUtils.JDialogWizard;
import es.gob.afirma.util.signers.AOSignerFactory;

/**
 * Panel de entrada del archivo
 */
public class PanelEntrada extends JAccessibilityDialogWizard {

	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(PanelEntrada.class.getName());
	
	@Override
	public int getMinimumRelation(){
		return 9;
	}
	
	/**
	 * Configuracion del KeyStore
	 */
	private KeyStoreConfiguration kssc = null;
		
	/**
     * Guarda todas las ventanas del asistente para poder controlar la botonera
     * @param ventanas	Listado con todas las paginas del asistente
     */
    public void setVentanas(List<JDialogWizard> ventanas) {
    	Botonera botonera = new Botonera(ventanas, 1);
    	getContentPane().add(botonera, BorderLayout.PAGE_END);
    }
	
    public PanelEntrada(KeyStoreConfiguration kssc) {
    	this.kssc = kssc;
        initComponents();
    }
    
    // Campo donde se guarda el nombre del fichero de firma
    private JTextField campoFirma = new JTextField();

    /**
     * Inicializacion de componentes
     */
    private void initComponents() {
    	// Titulo de la ventana
    	setTitulo(Messages.getString("Wizard.multifirma.simple.contrafirma.titulo"));
    	
    	// Panel con la cabecera
        CabeceraAsistente panelSuperior = new CabeceraAsistente("Wizard.multifirma.simple.contrafirma.ventana1.titulo", 
        		"Wizard.multifirma.simple.contrafirma.ventana1.titulo.descripcion", null, true);
        getContentPane().add(panelSuperior, BorderLayout.NORTH);

        // Panel central
    	JPanel panelCentral = new JPanel();
    	panelCentral.setMinimumSize(new Dimension(603, 289));
    	panelCentral.setLayout(new GridBagLayout());
        
    	GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20, 20, 0, 20);
        c.gridwidth	= 2;
		c.weightx = 1.0;
		c.gridx = 0;
    	
    	// Etiqueta "Fichero de datos:"
    	JLabel etiquetaFirma = new JLabel();
    	etiquetaFirma.setText(Messages.getString("Wizard.multifirma.simple.contrafirma.ventana1.fichero"));
        panelCentral.add(etiquetaFirma, c);
        
        c.insets = new Insets(0, 20, 0, 0);
        c.gridwidth = 1;
        c.gridy	= 1;
        
        // Caja de texto donde se guarda el nombre del archivo de firma
        campoFirma.setToolTipText(Messages.getString("Wizard.multifirma.simple.ventana1.fichero.datos.description")); // NOI18N
        campoFirma.getAccessibleContext().setAccessibleName(Messages.getString("Wizard.multifirma.simple.contrafirma.ventana1.fichero.description")); // NOI18N
        campoFirma.getAccessibleContext().setAccessibleDescription(Messages.getString("Wizard.multifirma.simple.contrafirma.ventana1.fichero.description")); // NOI18N
        panelCentral.add(campoFirma, c);
        
        //Relaci�n entre etiqueta y campo de texto
        etiquetaFirma.setLabelFor(campoFirma);
  		//Asignaci�n de mnem�nico
        etiquetaFirma.setDisplayedMnemonic(KeyEvent.VK_F);
        
        c.insets = new Insets(0, 10, 0, 20);
        c.gridwidth = 1;
        c.weightx = 0.0;
        c.gridy	= 1;
        c.gridx = 1;
        
        // Boton examinar (fichero datos)
        JButton	examinarFirma = new JButton();
        examinarFirma.setMnemonic(KeyEvent.VK_E);
        examinarFirma.setText(Messages.getString("PrincipalGUI.Examinar"));
        examinarFirma.setToolTipText(Messages.getString("PrincipalGUI.Examinar.description")); 
        examinarFirma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	examinarFirmaActionPerformed();
            }
        });
        panelCentral.add(examinarFirma, c);
        
        c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(20, 20, 0, 20);
		c.gridwidth = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 2;
		
		// Panel introducido para poder mantener la linea superior correcta
		Panel panelVacio = new Panel();
		panelCentral.add(panelVacio, c);
        
        getContentPane().add(panelCentral, BorderLayout.CENTER);
    	
        // Accesos rapidos al menu de ayuda
        HelpUtils.enableHelpKey(campoFirma, "multifirma.wizard.ficherocontrafirma");

    }

    /**
     * Comprueba si el archivo introducido es correcto y guarda su nombre en el campo de texto
     */
    private void examinarFirmaActionPerformed() {
        File selectedFile = new SelectionDialog().showFileOpenDialog(this, Messages.getString("PrincipalGUI.chooser.title"));
      	if (selectedFile != null) {
      		campoFirma.setText(selectedFile.getAbsolutePath());
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
			
			String ficheroFirma = campoFirma.getText();
			
			if (checkFicheroEntrada(ficheroFirma)) {
				byte[] dataFile = readFile(ficheroFirma);
				
				String formato = getFormatPdfOdfOoxml(dataFile);
							
				// Si es PDF, ODF o OOXML lo firmamos y saltamos al final
				if (formato != null) {	
					JOptionPane.showMessageDialog((PanelEntrada) getVentanas().get(1), Messages.getString("Wizard.multifirma.simple.message"), Messages.getString("informacion"), JOptionPane.INFORMATION_MESSAGE);	
					if (firmarFichero(dataFile, formato, ficheroFirma)) {					
						// Nos saltamos la pagina 2
						getVentanas().get(3).setVisibleAndHide(true, getVentanas().get(1));
					}
				}
				else {
					// Insertamos la ruta del archivo en la siguiente ventana
					JDialogWizard ventanaSiguiente = getVentanas().get(2);
					if (((PanelMultifirma) ventanaSiguiente).cargarDatos(ficheroFirma, dataFile))
						super.siguienteActionPerformed(anterior, siguiente, finalizar);
				}
			}
		}
	}

	/**
	 * Lee un fichero de firmas de entrada y comprueba si es correcto
	 * @param ficheroFirma	Ruta del fichero a firmar
	 * @return	True o false si se ha podido leer bien el fichero
	 */
	public boolean checkFicheroEntrada(String ficheroFirma) {
		// Comprobaci�n de la ruta de fichero de entrada.
		if (ficheroFirma == null || ficheroFirma.equals("") || !new File(ficheroFirma).exists() && !new File(ficheroFirma).isFile()){
			JOptionPane.showMessageDialog(this, Messages.getString("Wizard.multifirma.simple.error.datos"), Messages.getString("error"), JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Firma el fichero dado
	 * @param data Datos que se desean firmar.
	 * @param formato	Formato del archivo a firmar
	 * @param dataFilepath	Ruta del fichero a firmar
	 */
	private boolean firmarFichero(byte[] data, String formato, String dataFilepath) {
		AOSigner aoSigner =  AOSignerFactory.getSigner(formato);

		Properties prop = GeneralConfig.getSignConfig();
		prop.setProperty("format", formato);
		
		byte[] signedData = null;
		try {
			MultisignUtils msUtils = new MultisignUtils();
			AOKeyStoreManager keyStoreManager = msUtils.getAOKeyStoreManager(kssc, this);
			
			// Recuperamos la clave del certificado
			PrivateKeyEntry keyEntry = msUtils.getPrivateKeyEntry(kssc, keyStoreManager, this);
			signedData = aoSigner.sign(
					data,
					GeneralConfig.getSignAlgorithm(),
					keyEntry,
					prop
			);			
		} catch (AOException e) {
			logger.severe("Ocurrio un error al generar la firma electronica: " + e);
			JOptionPane.showMessageDialog(this, Messages.getString("Wizard.multifirma.simple.error.generar.firma"), 
					Messages.getString("error"), JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, Messages.getString("Wizard.multifirma.simple.error.generar.firma"), 
					Messages.getString("error"), JOptionPane.ERROR_MESSAGE);
			return false;
		} 
		
		if (signedData != null) {
			// Salvamos el fichero de datos
			final File savedFile = AOUIFactory.getSaveDataToFile(signedData,
		            new File(aoSigner.getSignedName(dataFilepath, ".signed")), //$NON-NLS-1$
		            SignFileUtils.getOutFileFilter(formato), this);
			// Si el usuario cancela el guardado de los datos, no nos desplazamos a la ultima pantalla
			if (savedFile == null) {
				return false;
			}
		}
		
		return true;
	}

	private byte[] readFile(String filepath) {
		byte[] data = null;
		InputStream fileIn = null;
		try {
			fileIn = AOUtil.loadFile(AOUtil.createURI(filepath));
			data = AOUtil.getDataFromInputStream(fileIn);
		} catch (FileNotFoundException e) {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			JOptionPane.showMessageDialog(this, Messages.getString("Wizard.multifirma.simple.error.fichero.encontrar"), 
					Messages.getString("error"), JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (IOException e) {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			JOptionPane.showMessageDialog(this, Messages.getString("Wizard.multifirma.simple.error.fichero.leer"), 
					Messages.getString("error"), JOptionPane.ERROR_MESSAGE);
			return null;
		}
		catch (AOException e) {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} finally {
			if (fileIn != null) {
				try { fileIn.close(); } catch (Exception e) {}
			}
		}
		
		return data;
	}
	
	/**
	 * Comprueba si los datos correspondientes a la firma son firmas odf, xml o pdf.
	 * Si no es ninguno de ellos, devuelve {@code null}. 
	 * @param sign 	Firma electr&oacute;nica
	 * @return Formato del archivo
	 */
	private static String getFormatPdfOdfOoxml(byte[] sign){
	    
	    final String[][] signersClass = {
	            {"es.gob.afirma.signers.pades.AOPDFSigner", AOSignConstants.SIGN_FORMAT_PDF},//$NON-NLS-1$
	            {"es.gob.afirma.signers.odf.AOODFSigner", AOSignConstants.SIGN_FORMAT_ODF}, //$NON-NLS-1$
	            {"es.gob.afirma.signers.ooxml.AOOOXMLSigner", AOSignConstants.SIGN_FORMAT_OOXML}, //$NON-NLS-1$
	    };
	    
	    for (String[] signer : signersClass) {
	        try {
	            Class<?> signerClass = Class.forName(signer[0]);
	            AOSigner signerObject = (AOSigner) signerClass.newInstance();
	            if (signerObject.isValidDataFile(sign)) {
	                return signer[1];
	            }
	        } catch (Exception e) {
	            /* Si falla un signer continuamos con el resto */
	            e.printStackTrace();
	        }
	    }
	    return null;
	}
}
