/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Gobierno de Espa�a
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3, o superiores, seg�n las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompa�a.  Si se   distribuyera este 
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */


package es.gob.afirma.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import es.atosorigin.AOCertVerifier;
import es.gob.afirma.Messages;
import es.gob.afirma.exceptions.AOCancelledOperationException;
import es.gob.afirma.exceptions.AOCertificatesNotFoundException;
import es.gob.afirma.exceptions.AOException;
import es.gob.afirma.keystores.KeyStoreUtilities;
import es.gob.afirma.misc.AOConstants;

/** 
 * Gestor de componentes de interfas gr&aacute;fico (tanto para Applet como para aplicaci&oacute;n de escritorio)
 * de la aplicaci&oacute;n.
 * @version 0.4
 */
public final class AOUIManager {
	
	private static Image icon;
	
	static {
		
		String lookandfeel = UIManager.getSystemLookAndFeelClassName(); 
		
		try {
	        UIManager.setLookAndFeel(lookandfeel);
	    }
		catch(Throwable e) {
			Logger.getLogger("es.gob.afirma").warning( //$NON-NLS-1$
					"No se ha podido establecer el Look&Feel '" + lookandfeel + "', las " + //$NON-NLS-1$ //$NON-NLS-2$
					"ventanas careceran de decoracion: " + e //$NON-NLS-1$
			);
		}
		
		// Nos aseguramos de que los dialogos salgan decorados
		JDialog.setDefaultLookAndFeelDecorated(true);
		javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
	}

	/**
	 * Muestra un di&aacute;logo para que el usuario seleccione entre los certificados mostrados.
	 * @param alias Alias de los certificados entre los que el usuario debe seleccionar uno
	 * @param kss Listado de KeyStores de donde se han sacadon los alias (debe ser <code>null</code>
	 *           si se quiere usar el m&eacute;todo para seleccionar otra cosa que no sean certificados
	 *           X.509 (como claves de cifrado)
	 * @param keyUsageFilter Filtro que determina que certificados se van a mostrar seg&uacute;n su <code>KeyUsage</code>
	 * @param parentComponent Componente padre (para ls amodalidad)
	 * @param checkPrivateKeys Indica si se debe comprobar que el certificado tiene clave privada o no, para no mostrar
	 *                         aquellos que carezcan de ella
	 * @param checkValidity Indica si se debe comprobar la validez temporal de un certificado al ser seleccionado
	 * @param showExpiredCertificates Indica si se deben o no mostrar los certificados caducados o aun no v&aacute;lidos 
	 * @return Alias seleccionado por el usuario
	 * @throws AOCancelledOperationException Si el usuario cancela manualmente la operaci&oacute;n
	 * @throws AOCertificatesNotFoundException Si no hay certificados que mostrar al usuario
	 */
	public final static String showCertSelectionDialog(final String[] alias, 
			                                     final Vector<KeyStore> kss, 
			                                     final Boolean[] keyUsageFilter, 
			                                     final Component parentComponent, 
			                                     final boolean checkPrivateKeys, 
			                                     final boolean checkValidity, 
			                                     final boolean showExpiredCertificates) throws AOCancelledOperationException, 
			                                                                             AOCertificatesNotFoundException {
		return showCertSelectionDialog(alias, kss, keyUsageFilter, parentComponent, checkPrivateKeys, checkValidity, showExpiredCertificates, null, null, false);
	}
		
	/**
	 * Muestra un di&aacute;logo para que el usuario seleccione entre los certificados mostrados.
	 * Es posible indicar que s&ocuate;lo puede haber un certificado tras recuperarlos del repositorio
	 * y aplicar los filtros, en cuyo caso se seleccionar&iacute; autom&aacute;ticamente. Si se
	 * pidiese que se seleccione autom&aacute;ticamemte un certificado y hubiese m&aacute;s de uno,
	 * se devolver&iacute;a una excepci&oacute;n.
	 * @param alias Alias de los certificados entre los que el usuario debe seleccionar uno
	 * @param kss Listado de KeyStores de donde se han sacadon los alias (debe ser <code>null</code>
	 *           si se quiere usar el m&eacute;todo para seleccionar otra cosa que no sean certificados
	 *           X.509 (como claves de cifrado)
	 * @param keyUsageFilter Filtro que determina que certificados se van a mostrar seg&uacute;n su <code>KeyUsage</code>
	 * @param parentComponent Componente padre (para ls amodalidad)
	 * @param checkPrivateKeys Indica si se debe comprobar que el certificado tiene clave privada o no, para no mostrar
	 *                         aquellos que carezcan de ella
	 * @param checkValidity Indica si se debe comprobar la validez temporal de un certificado al ser seleccionado
	 * @param showExpiredCertificates Indica si se deben o no mostrar los certificados caducados o aun no v&aacute;lidos 
	 * @param issuerFilter Filtro seg&uacute; la RFC2254 para el emisor del certificado 
	 * @param subjectFilter Filtro seg&uacute; la RFC2254 para el titular del certificado
	 * @param mandatoryCertificate Indica si los certificados disponibles (tras aplicar el filtro) debe ser solo uno.
	 * @return Alias seleccionado por el usuario
	 * @throws AOCancelledOperationException Si el usuario cancela manualmente la operaci&oacute;n
	 * @throws AOCertificatesNotFoundException Si no hay certificados que mostrar al usuario
	 */
	public final static String showCertSelectionDialog(final String[] alias, 
			                                     final Vector<KeyStore> kss, 
			                                     final Boolean[] keyUsageFilter, 
			                                     final Component parentComponent, 
			                                     final boolean checkPrivateKeys, 
			                                     final boolean checkValidity, 
			                                     final boolean showExpiredCertificates,
			                                     final String issuerFilter,
			                                     final String subjectFilter,
			                                     final boolean mandatoryCertificate) throws AOCancelledOperationException, 
			                                                                             AOCertificatesNotFoundException {
		if (alias == null || alias.length == 0) {
			//Logger.getLogger("es.gob.afirma").warning("No se han encontrado certificados en el repositorio indicado");
			JOptionPane.showMessageDialog(
					parentComponent,
					Messages.getString("AOUIManager.4"), //$NON-NLS-1$
					Messages.getString("AOUIManager.5"), //$NON-NLS-1$
					JOptionPane.WARNING_MESSAGE
			);
			throw new AOCertificatesNotFoundException("El almac\u00E9n no conten\u00EDa entradas"); //$NON-NLS-1$
		}
		
		
		Hashtable<String, String> aliassesByFriendlyName = KeyStoreUtilities.getAlisasesByFriendlyName(
			alias, 
			kss, 
			keyUsageFilter, 
			checkPrivateKeys, 
			checkValidity, 
			showExpiredCertificates, 
			issuerFilter, 
			subjectFilter
		);
		
		// Miramos si despues de filtrar las entradas queda alguna o se ha quedado la lista vacia
		if (aliassesByFriendlyName.size() == 0) {
			JOptionPane.showMessageDialog(
					parentComponent,
					Messages.getString("AOUIManager.10"), //$NON-NLS-1$
					Messages.getString("AOUIManager.5"), //$NON-NLS-1$
					JOptionPane.WARNING_MESSAGE
			);
			throw new AOCertificatesNotFoundException("El almacen no contenia entradas validas"); //$NON-NLS-1$
		}
		
		// Si se ha pedido que se seleccione automaticamente un certificado, se seleccionara
		// si hay mas de un ceretificado que se ajuste al filtro, se dera a elegir
		if(mandatoryCertificate && aliassesByFriendlyName.size() == 1) {
				return aliassesByFriendlyName.keys().nextElement();
		}
		
		// Ordenamos el array de alias justo antes de mostrarlo, ignorando entre
		// may�sculas y min�sculas
		Object[] finalOrderedAliases = aliassesByFriendlyName.values().toArray();
		Arrays.sort(finalOrderedAliases, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				if (o1==null && o2==null) return 0;
				else if (o1==null && o2!=null) return 1;
				else if (o1!=null && o2==null) return -1;
				else return o1.toString().compareToIgnoreCase(o2.toString()); 
			}
		});
		
		Object o = JOptionPane.showInputDialog(
            parentComponent,
            Messages.getString("AOUIManager.13"), //$NON-NLS-1$
            Messages.getString("AOUIManager.14"), //$NON-NLS-1$
            JOptionPane.PLAIN_MESSAGE,
            null,
            finalOrderedAliases,
            null
		);
		
		final String certName;
		if(o != null) certName = o.toString();
		else {
			Logger.getLogger("es.gob.afirma").warning("Operacion de seleccion de certificado cancelada"); //$NON-NLS-1$ //$NON-NLS-2$
			throw new AOCancelledOperationException("Operacion de seleccion de certificado cancelada"); //$NON-NLS-1$
		}
		
		for(String al : aliassesByFriendlyName.keySet().toArray(new String[aliassesByFriendlyName.size()])) {
			if(aliassesByFriendlyName.get(al).equals(certName)) {
				if (checkValidity && kss != null) {
					boolean rejected = false;
					AOCertVerifier cv = new AOCertVerifier();
					for (KeyStore ks : kss) {
						try {
							if (ks.containsAlias(al)) {
								try {
									cv.checkCertificate(new java.security.cert.Certificate[] { ks.getCertificate(al) }, false);
								}
								catch(Throwable t) {
									if (JOptionPane.showConfirmDialog(
										parentComponent, 
										t.getMessage() + 
										Messages.getString("AOUIManager.8"),  //$NON-NLS-1$
										Messages.getString("AOUIManager.5"),  //$NON-NLS-1$
										JOptionPane.YES_NO_OPTION, 
										JOptionPane.WARNING_MESSAGE
									) == JOptionPane.YES_OPTION) return al;
									rejected = true;
								}
							}
						}
						catch(Throwable e) {}
						if (rejected) throw new AOCancelledOperationException("Se ha reusado un certificado probablemente no valido"); //$NON-NLS-1$
					}
				}
				return al;
			}
		}
		return null;
	}
	
	/**
	 * Pregunta al usuario por un nombre de fichero para su carga.
	 * @param extensions Extensiones predeterminadas para el fichero
	 * @param description Descripci&oacute;n del tipo de fichero correspondiente con las extensiones
	 * @param parentComponent Componente padre (para la modalidad)
	 * @return Nombre de fichero (con ruta) seleccionado por el usuario
	 */
	public static final String getLoadFileName(final String[] extensions, 
			                                   final String description, 
			                                   final Component parentComponent) {
		return getLoadFileName(null, extensions, description, parentComponent);
	}
	
	/**
	 * Pregunta al usuario por un nombre de fichero para su carga.
	 * @param dialogTitle T&iacute;tulo de la ventana de di&aacute;logo.
	 * @param extensions Extensiones predeterminadas para el fichero
	 * @param description Descripci&oacute;n del tipo de fichero correspondiente con las extensiones
	 * @param parentComponent Componente padre (para la modalidad)
	 * @return Nombre de fichero (con ruta) seleccionado por el usuario
	 */
	public final static String getLoadFileName(final String dialogTitle, 
			                                   final String[] extensions, 
			                                   final String description, 
			                                   final Component parentComponent) {
		final JFileChooser jfc = new JFileChooser();
		if(dialogTitle != null && dialogTitle.length() > 0) jfc.setDialogTitle(dialogTitle);
		if (extensions != null && extensions.length > 0) jfc.setFileFilter(new ExtFilter(extensions, description));
		int ret = jfc.showOpenDialog(parentComponent);
		if (ret == JFileChooser.APPROVE_OPTION) return jfc.getSelectedFile().getAbsolutePath();
		return null;
	}
	
	/**
     * Permite al usuario seleccionar un directorio y devuelve su ruta absoluta. Si no se
     * selecciona ninguno se devuelve <code>null</code>.
     * @param parentComponent Componente padre (para la modalidad)
	 * @param title T&iacute;tulo del di&aacute;logo de selecci&oacute;n
     * @return Ruta absoluta del directorio seleccionado.
     */
	public final static String selectDirectory(final Component parentComponent, final String title) {
	    final JFileChooser jfc = new JFileChooser();
	    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY );
        jfc.setDialogTitle(title);
        int ret = jfc.showOpenDialog(parentComponent);
        if (ret == JFileChooser.APPROVE_OPTION) return jfc.getSelectedFile().getAbsolutePath();
        return null;
	}
	
	/**
	 * Pregunta al usuario por un nombre de fichero para salvar en disco.
	 * @param extensions Extensiones predeterminadas para el fichero
	 * @param description Descripci&oacute;n del tipo de fichero correspondiente con las extensiones
	 * @param parentComponent Componente padre (para la modalidad)
	 * @return Nombre de fichero (con ruta) seleccionado por el usuario
	 */
	public final static String getSaveFileName(final String[] extensions, 
			                                   final String description, 
			                                   final Component parentComponent) {

		final JFileChooser jfc = new JFileChooser();
		if (extensions != null && extensions.length > 0) {
			jfc.setFileFilter(new ExtFilter(extensions, description));
			jfc.setSelectedFile(new File("*."+extensions[0])); //$NON-NLS-1$
		}
		
		boolean selectedFilename = false;
		String finalFilename = null;
		do {
			int ret = jfc.showSaveDialog(parentComponent);
			if(ret == JFileChooser.APPROVE_OPTION) {
				File tempFile = jfc.getSelectedFile();
				if(tempFile.exists()) {
					if(tempFile.isDirectory() && !tempFile.canWrite()) {
						JOptionPane.showMessageDialog(
							parentComponent, 
							Messages.getString("AOUIManager.74") + jfc.getSelectedFile().getAbsolutePath() + ".", //$NON-NLS-1$ //$NON-NLS-2$
							Messages.getString("AOUIManager.81"),  //$NON-NLS-1$
							JOptionPane.WARNING_MESSAGE
						);
						continue;
					}
					int resp = JOptionPane.showConfirmDialog(
							parentComponent,
							Messages.getString("AOUIManager.77") + "\r\n" + jfc.getSelectedFile().getAbsolutePath(), //$NON-NLS-1$ //$NON-NLS-2$
							Messages.getString("AOUIManager.81"), //$NON-NLS-1$
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE
					);
					if(resp == JOptionPane.YES_OPTION) {	// Sobreescribir fichero
						finalFilename = jfc.getSelectedFile().getAbsolutePath();
						selectedFilename = true;
					} else if(resp == JOptionPane.NO_OPTION) {	// Seleccionar fichero
						continue;
					} else { 		// Cancelar operacion de guardado
						finalFilename = null;
						selectedFilename = true;
					}
				}
				else {
					finalFilename = jfc.getSelectedFile().getAbsolutePath();
					selectedFilename = true;
				}
			}
			else { 		// Cancelar operacion de seleccion de nombre
				finalFilename = null;
				selectedFilename = true;
			}
		} while (!selectedFilename);
		
		return finalFilename;
	}
	
	/**
	 * Filtra los ficheros por extensi&oacute;n para los di&aacute;logos de carga y guardado.
	 * Se declara como p&uacute;blico para que pueda ser usado tambi&eacute;n por el interfaz de
	 * aplicaci&oacute;n de escritorio. No usamos <code>FileNameExtensionFilter</code> directamente
	 * para compatibilizar con Java 1.4
	 * @version 0.3
	 */
	public final static class ExtFilter extends FileFilter implements java.io.FileFilter {
		
		String[] extensions;
		String description;
		
		/**
		 * Construye un filtro para la selecci&oacute;n de ficheros en un <code>JFileChooser</code>.
		 * @param exts Extensiones de fichero permitidas 
		 * @param desc Descripci&oacute;n del tipo de fichero correspondiente a las extensiones
		 */
		public ExtFilter(final String[] exts, String desc) {
			if (exts==null || exts.length < 1) throw new NullPointerException(
				"No se puede crear un filtro vacio" //$NON-NLS-1$
			);
			if (desc==null || desc.length() < 1) desc = Messages.getString("AOUIManager.0"); //$NON-NLS-1$
			extensions = exts.clone();
			description = desc;
		}
		
	    @Override
		public boolean accept(File f) {
	        if (f.isDirectory()) return true;
	        // getExtension() pasa la extension a minusculas, no hace falta
	        // el "ignoreCase"
	        String extension = getExtension(f);
	        for (int i=0; i<extensions.length; i++) if (extensions[i].equalsIgnoreCase(extension)) return true;
	        return false;
	    }

//	    public String[] getExtensions() {
//	        return extensions;
//	    }
	    
	    @Override
		public String getDescription() {
	        return description;
	    }
	    
	    /**
	     * Devuelve la extensi&oacute;n de un fichero.
	     * @param f Fichero del cual queremos conocer la extensi&oacute;n
	     * @return Extensi&oacute;n del fichero o cadena vac&iacute;a si este no tiene
	     *         extensi&oacute;n
	     */
	    private final static String getExtension(final File f) {
	        final String s = f.getName();
	        int i = s.lastIndexOf('.');
	        if (i > 0 &&  i < s.length() - 1) return s.substring(i+1).toLowerCase();
	        return ""; //$NON-NLS-1$
	    }

	}

	/**
	 * Devuelve el icono de la aplicaci&oacute;n.
	 * @return Icono gr&aacute;fico de la aplicaci&oacute;n
	 */
	public final static Image getIcon() {
		if(icon == null) {
			try {
				icon = Toolkit.getDefaultToolkit().getImage(AOUIManager.class.getResource("/resources/afirma_ico.png")); //$NON-NLS-1$
			}
			catch(Throwable e) {
				Logger.getLogger("es.gob.afirma").warning("No se ha podido cargar el icono de la aplicacion: " + e); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return icon;
	}
	
	/**
	 * Muestra un di&aacute;logo para pedir una contrase&ntilde;a al usuario.
	 * @param text Texto con el que se solicitar&aacute; la entrada de texto al usuario (<i>prompt</i>)
	 * @param charSet Juego de caracteres aceptados para la contrase&ntilde;a
	 * @param beep <code>true</code> si se desea un sonido de advertencia al introducir un caracter
	 *             no v&aacute;lido, <code>false</code> en caso contrario 
	 * @param c Componente padre (para la modalidad)
	 * @return Array de caracteres del texto introducido como contrase&ntilde;a
	 * @throws AOCancelledOperationException Cuando el usuario cancela o cierra el di&aacute;logo
	 */
	public final static char[] getPassword(String text, final String charSet, final boolean beep, final Component c) throws AOCancelledOperationException {
		if (text==null) text = Messages.getString("AOUIManager.24"); //$NON-NLS-1$
		JPasswordField pwd = new JPasswordField(10);
		if (charSet != null) pwd.setDocument(new JTextFieldFilter(charSet, beep));
		JLabel lbText = new JLabel(text);
		lbText.setMinimumSize(new Dimension(
		        lbText.getFontMetrics(lbText.getFont()).stringWidth(text),
		        lbText.getSize().height));
		lbText.setLabelFor(pwd);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(lbText);
		panel.add(pwd);
		int action = JOptionPane.showConfirmDialog(
			c,
			panel,
			Messages.getString("AOUIManager.25"), //$NON-NLS-1$
			JOptionPane.OK_CANCEL_OPTION
		);
		if(!(action == JOptionPane.OK_OPTION)) throw new AOCancelledOperationException(
			"La insercion de contrasena ha sido cancelada por el usuario" //$NON-NLS-1$
		);
		return pwd.getPassword();
	}
	
	/**
	 * Pregunta al usuario por una contrase&ntilde;a.
	 * @param text Texto que se muestra en el di&aacute;logo para pedir la contrase&ntilde;a
	 * @param c Componente padre (para la modalidad)
	 * @return Contrase&ntilde;a introducida por el usuario
	 * @throws AOCancelledOperationException Cuando el usuario cancela el proceso de solicitud de contrase&ntilde;a 
	 */
	public final static char[] getPassword(final String text, final Component c) throws AOCancelledOperationException {
		return getPassword(text, null, false, c);
	}
	
	/**
	 * Obtiene un filtro de fichero acorde a un formato de firma.
	 * @param signFormat Formato de firma.
	 * @return Filtro con las extensiones de fichero v&aacute;lidas para el formato de firma especificado. 
	 */
	public static final FileFilter getFileFilter(final String signFormat) {
		
		if(signFormat.equals(AOConstants.SIGN_FORMAT_XADES_ENVELOPED)
	    || signFormat.equals(AOConstants.SIGN_FORMAT_XMLDSIG_ENVELOPED)
	    || signFormat.equals(AOConstants.SIGN_FORMAT_SOAP)) {
			return new ExtFilter(new String[]{"xml"}, Messages.getString("AOUIManager.28")); //$NON-NLS-1$ //$NON-NLS-2$
		} 
		else if(signFormat.equals(AOConstants.SIGN_FORMAT_PDF)) {
			return new ExtFilter(new String[]{"pdf"}, Messages.getString("AOUIManager.30")); //$NON-NLS-1$ //$NON-NLS-2$
		} 
		else if(signFormat.equals(AOConstants.SIGN_FORMAT_OOXML)) {
			return new ExtFilter(new String[]{"docx", "pptx", "ppsx"}, Messages.getString("AOUIManager.50")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		else if(signFormat.equals(AOConstants.SIGN_FORMAT_ODF)) {
			return new ExtFilter(new String[]{"odt", "ods", "odp"}, Messages.getString("AOUIManager.16")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
		}

		return null;
	}
	
	/**
	 * Obtiene un filtro de fichero correspondiente para el almacenamiento de un fichero de firma.
	 * @param signFormat Formato de firma.
	 * @return Filtro con las extensiones de fichero v&aacute;lidas para el formato de firma especificado. 
	 */
	public static final FileFilter getOutFileFilter(final String signFormat) {
		if(signFormat.equals(AOConstants.SIGN_FORMAT_CMS)) {
			return new ExtFilter(new String[]{"csig"}, Messages.getString("AOUIManager.43")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else if(signFormat.equals(AOConstants.SIGN_FORMAT_CADES)) {
			return new ExtFilter(new String[]{"csig"}, Messages.getString("AOUIManager.1")); //$NON-NLS-1$ //$NON-NLS-2$
		} 
		else if(signFormat.equals(AOConstants.SIGN_FORMAT_PDF)) {
			return new ExtFilter(new String[]{"pdf"}, Messages.getString("AOUIManager.30")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else if(signFormat.equals(AOConstants.SIGN_FORMAT_ODF)) {
			return new ExtFilter(new String[]{"odt", "ods", "odp"}, Messages.getString("AOUIManager.16")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		else if(signFormat.equals(AOConstants.SIGN_FORMAT_OOXML)) {
			return new ExtFilter(new String[]{"docx", "xlsx", "pptx", "ppsx"}, Messages.getString("AOUIManager.50")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}
		else if(signFormat.equals(AOConstants.SIGN_FORMAT_XMLDSIG_DETACHED) || signFormat.equals(AOConstants.SIGN_FORMAT_XMLDSIG_ENVELOPING) || signFormat.equals(AOConstants.SIGN_FORMAT_XMLDSIG_ENVELOPED) || signFormat.equals(AOConstants.SIGN_FORMAT_XMLDSIG_EXTERNALLY_DETACHED) ||
		        signFormat.equals(AOConstants.SIGN_FORMAT_XADES_DETACHED) || signFormat.equals(AOConstants.SIGN_FORMAT_XADES_ENVELOPING) || signFormat.equals(AOConstants.SIGN_FORMAT_XADES_ENVELOPED) || signFormat.equals(AOConstants.SIGN_FORMAT_XADES_EXTERNALLY_DETACHED)) {
	            return new ExtFilter(new String[]{"xsig"}, Messages.getString("AOUIManager.17")); //$NON-NLS-1$ //$NON-NLS-2$
	    }
		return new ExtFilter(new String[]{"sig"}, Messages.getString("AOUIManager.52")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/**
	 * Obtiene el nombre que le corresponde a un fichero tras su firma seg&uacute;n su
	 * nombre original y el formato de la firma. En caso de no reconocerse el formato
	 * de firma o existir alg&uacute;n problema con el nombre de fichero, se agregar&aacute;
	 * al nombre la extension ".sig".
	 * @param inName Nombre original del fichero. 
	 * @param signFormat Formato de firma recogido en {@link AOConstants AOConstants}. 
	 * @return Nombre de salida del fichero
	 */
	public static final String getOutFileName(final String inName, final String signFormat) {
		
		if(inName == null || inName.equals("")) throw new NullPointerException( //$NON-NLS-1$
			"El nombre de fichero no puede estar vacio" //$NON-NLS-1$
		);
		
		if(signFormat == null) throw new NullPointerException("El formato de firma no puede ser nulo"); //$NON-NLS-1$
		
		if(signFormat.equals(AOConstants.SIGN_FORMAT_CMS) ||
				signFormat.equals(AOConstants.SIGN_FORMAT_CADES)) return inName + ".csig"; //$NON-NLS-1$
		if(signFormat.equals(AOConstants.SIGN_FORMAT_XMLDSIG_DETACHED) ||
				signFormat.equals(AOConstants.SIGN_FORMAT_XMLDSIG_ENVELOPED) ||
				signFormat.equals(AOConstants.SIGN_FORMAT_XMLDSIG_ENVELOPING) ||
				signFormat.equals(AOConstants.SIGN_FORMAT_XMLDSIG_EXTERNALLY_DETACHED) ||
				signFormat.equals(AOConstants.SIGN_FORMAT_XADES_DETACHED) ||
				signFormat.equals(AOConstants.SIGN_FORMAT_XADES_ENVELOPED) ||
				signFormat.equals(AOConstants.SIGN_FORMAT_XADES_ENVELOPING) ||
                signFormat.equals(AOConstants.SIGN_FORMAT_XADES_EXTERNALLY_DETACHED)) {
	        return inName + ".xsig"; //$NON-NLS-1$
		}
		if(signFormat.equals(AOConstants.SIGN_FORMAT_PDF) ||
				signFormat.equals(AOConstants.SIGN_FORMAT_ODF) ||
				signFormat.equals(AOConstants.SIGN_FORMAT_OOXML)) {
	        int i = inName.lastIndexOf('.');
	        if (i > 0 &&  i < inName.length() - 1) return inName.substring(0, i) + ".signed" + inName.substring(i).toLowerCase(); //$NON-NLS-1$
		}
		
		return inName + ".sig"; //$NON-NLS-1$
	}
	
	/**
	 * Muestra el di&aacute;logo de selecci&oacute;n de nodos de firma. 
	 * @param tree &Aacute;rbol de firmantes asociado a un fichero de firma.
	 * @param parentComponent Componente padre sobre el que se mostrar&aacute;n los di&aacute;logos.
	 * @return Listado con los &iacute;ndices de los nodos seleccionados.
	 * @throws AOCancelledOperationException Cuando el usuario cancel&oacute; la operaci&oacute;n.
	 */
	public static final int[] showNodeSignSelectionPane(final TreeModel tree, final Component parentComponent) throws AOCancelledOperationException {

		final DefaultTreeCellRenderer treeRenderer = new DefaultTreeCellRenderer();
		treeRenderer.setLeafIcon(null);
		treeRenderer.setClosedIcon(null);
		treeRenderer.setOpenIcon(null);

		final JTree arbolNodos = new JTree(tree);
		arbolNodos.setRootVisible(false);
		arbolNodos.setCellRenderer(treeRenderer);
		arbolNodos.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		for(int i=0; i<arbolNodos.getRowCount(); i++) arbolNodos.expandRow(i);
		arbolNodos.addTreeExpansionListener(new TreeExpansionListener() {
			public void treeCollapsed(TreeExpansionEvent event) {
				((JTree)event.getSource()).expandPath(event.getPath());
			}
			public void treeExpanded(TreeExpansionEvent event) {}
		});

		// Seleccionamos el primer elemento para que siempre haya 
		arbolNodos.setSelectionRow(1);
		
		final JScrollPane spArbolNodos = new JScrollPane(arbolNodos);
		spArbolNodos.setPreferredSize(new Dimension(280, 200));
		
		final JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		panel.add(new JLabel("Seleccione los nodos de firma que desee contrafirmar:"), c); //$NON-NLS-1$
		c.weighty = 1.0;
		c.gridy = 1;
		panel.add(spArbolNodos, c);
		
		do {
		    final int action = JOptionPane.showConfirmDialog(
		            parentComponent,
		            panel,
		            Messages.getString("AOUIManager.61"), //$NON-NLS-1$
		            JOptionPane.OK_CANCEL_OPTION,
		            JOptionPane.PLAIN_MESSAGE
		    );
		    if (action != JOptionPane.OK_OPTION) {
		        throw new AOCancelledOperationException("La operacion de firma ha sido cancelada por el usuario"); //$NON-NLS-1$
		    }
		    
		    if(arbolNodos.getSelectionRows() == null || arbolNodos.getSelectionRows().length < 1) {
		        JOptionPane.showMessageDialog(parentComponent, Messages.getString("AOUIManager.18"), Messages.getString("AOUIManager.19"), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
		    }
		    
		} while(arbolNodos.getSelectionRows() == null || arbolNodos.getSelectionRows().length < 1);
		
		return arbolNodos.getSelectionRows();
	}
	
	/**
	 * Muestra el di&aacute;logo de selecci&oacute;n de firmantes. 
	 * @param tree &Aacute;rbol de firmantes asociado a un fichero de firma.
	 * @param parentComponent Componente padre sobre el que se mostrar&aacute;n los di&aacute;logos.
	 * @return Listado de firmantes seleccionados.
	 * @throws AOException Cuando el &aacute;rbol de firmantes contiene errores.
	 * @throws AOCancelledOperationException Cuando el usuario cancel&oacute; la operaci&oacute;n.
	 */
	public static final String[] showSignersSelectionPane(final TreeModel tree, final Component parentComponent) throws AOException, AOCancelledOperationException {

		final HashSet<String> signersSet = new HashSet<String>();
		
		if(tree == null || tree.getRoot() == null || !(tree.getRoot() instanceof DefaultMutableTreeNode)) {
			throw new AOException("El arbol introducido no es valido"); //$NON-NLS-1$
		}
				
		// Recorremos todos los nodos menos el root que no contiene informacion de firmante
		final DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getRoot();
		try {
			for(int i=0; i<root.getChildCount(); i++) getSigners((DefaultMutableTreeNode)root.getChildAt(i), signersSet);
		} 
		catch(Throwable e) {
			throw new AOException("El arbol introducido contiene elementos no validos: " + e); //$NON-NLS-1$
		}
		
		// Recogemos los firmantes de los nodos
		String[] signers = new String[signersSet.size()];
		signersSet.toArray(signers);

		// Mostramos la lista de firmantes de la firma introducida
		
		final JList jList = new JList(signers);
		final JScrollPane spArbolNodos = new JScrollPane(jList);
		spArbolNodos.setPreferredSize(new Dimension(280, 200));
		final JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		panel.add(new JLabel(Messages.getString("AOUIManager.65")), c); //$NON-NLS-1$
		c.weighty = 1.0;
		c.gridy = 1;
		panel.add(spArbolNodos, c);
		
		do {
		    final int action = JOptionPane.showConfirmDialog(
		            parentComponent,
		            panel,
		            Messages.getString("AOUIManager.66"), //$NON-NLS-1$
		            JOptionPane.OK_CANCEL_OPTION
		    );
		    if (action != JOptionPane.OK_OPTION) {
		        throw new AOCancelledOperationException("La operacion de firma ha sido cancelada por el usuario"); //$NON-NLS-1$
		    }

		    if(jList.getSelectedIndices() == null || jList.getSelectedIndices().length < 1) {
		        JOptionPane.showMessageDialog(parentComponent, Messages.getString("AOUIManager.20"), Messages.getString("AOUIManager.21"), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
		    }

		} while(jList.getSelectedIndices() == null || jList.getSelectedIndices().length < 1);
		
		// Devolvemos los firmantes seleccionados
		final Object[] selectedValues = jList.getSelectedValues();
		signers = new String[selectedValues.length];
		for(int i=0; i<selectedValues.length; i++) signers[i] = (String)selectedValues[i];
		return signers;
	}
	
	/**
	 * Introduce los alias de todos los certificados de firma del nodo indicado y todos sus subnodos.
	 * @param node Nodo de firma.
	 * @param signersSet Conjunto con los alias de los certificados de firma.
	 */
	private static final void getSigners(final DefaultMutableTreeNode node, final HashSet<String> signersSet) {
		signersSet.add((String)node.getUserObject());
		for(int i=0; i<node.getChildCount(); i++) {
			getSigners((DefaultMutableTreeNode)node.getChildAt(i), signersSet);
		}
	}
	
	/**
	 * Original code: <a href="http://tactika.com/realhome/realhome.html">http://tactika.com/realhome/realhome.html</a>
	 * @author Real Gagnon
	 */
	public static final class  JTextFieldFilter extends PlainDocument {

		private static final long serialVersionUID = -5746396042117084830L;
		
		/**
		 * Crea un nuevo filtro para campo de entrada de texto.
		 * @param beepOnError <code>true</code> si desea que se reproduzca un sonido cuando el usuario
		 *                    introduce un caracter no v&aacute;lido, false en caso contrario
		 */
		public JTextFieldFilter(final boolean beepOnError) {
		      this(AOConstants.ACCEPTED_CHARS, beepOnError);
		}
		
		private String acceptedChars = null;

		/**
		 * Crea un nuevo filtro para campo de entrada de texto.
		 * @param acceptedchars Cadena que debe contener todos los caracteres aceptados. Cualquier caracter no
		 *                      incluido en esta cadena ser&aacute; considerado inv&aacute;lido
		 * @param beepOnError <code>true</code> si desea que se reproduzca un sonido cuando el usuario
		 *                    introduce un caracter no v&aacute;lido, false en caso contrario
		 */
		public JTextFieldFilter(final String acceptedchars, final boolean beepOnError) {
			beep = beepOnError;
		    acceptedChars = acceptedchars;
		}
		
		private boolean beep = false;
		   
		@Override
		public void insertString
	      (int offset, String  str, AttributeSet attr) throws BadLocationException {
	      if (str == null) return;
	      for (int i=0; i < str.length(); i++) if (acceptedChars.indexOf(String.valueOf(str.charAt(i))) == -1) {
	    	  if (beep) Toolkit.getDefaultToolkit().beep();
	    	  return;
	      }
	      super.insertString(offset, str, attr);
	    }

	}
	
	/**
	 * Muestra un di&aacute;logo de guardado para almacenar los datos indicados. Los datos
	 * ser&aacute;n almacenados en el directorio y con el nombre que indique el usuario. Si
	 * el fichero ya existe se le preguntar&aacute; al usuario si desea sobreescribirlo.
	 * En caso de cancelar la operaci&oacute;n se devolvera null, si la operaci&oacute;n
	 * finaliza correctamente se devolver&aacute; el path completo del fichero. 
	 * @param parentComponent Componente padre sobre el que se mostrar&aacute; el di&aacute;logo de guardado.
	 * @param data Datos que se desean almacenar.
	 * @param selectedFile Nombre de fichero por defecto.
	 * @param fileFilter Filtro de fichero para el di&aacute;logo de guardado.
	 * @return Nombre del fichero  
	 * @throws NullPointerException No se introdujeron los datos que se desean almacenar.
	 */
	public final static String saveDataToFile(final Component parentComponent, 
			                                  final byte[] data, 
			                                  final File selectedFile, 
			                                  final FileFilter fileFilter) {

		if(data == null) {
			Logger.getLogger("es.gob.afirma").warning("No se han introducido los datos que se desean guardar. Se cancelara la operacion"); //$NON-NLS-1$ //$NON-NLS-2$
			throw new NullPointerException("No se introdujeron datos que almacenar"); //$NON-NLS-1$
		}
		
		String filename = null;
		boolean tryAgain = true;
		File file = null;
		while(tryAgain) {
			
			tryAgain = false;
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.getAccessibleContext().setAccessibleName(Messages.getString("AOUIManager.81")); //$NON-NLS-1$
			fileChooser.getAccessibleContext().setAccessibleDescription(Messages.getString("AOUIManager.82")); //$NON-NLS-1$
			fileChooser.setToolTipText(Messages.getString("AOUIManager.81")); //$NON-NLS-1$
			fileChooser.setSelectedFile(file);
			
			// Si se nos ha indicado un nombre de fichero por defecto, lo establecemos
			if(selectedFile != null)
				fileChooser.setSelectedFile(selectedFile);
			
			// Solo aplicamos el filtro cuando este definido para evitar que el desplegable de
			// la ventana de guardado nos aparecezca vacio
			if(fileFilter != null)
				fileChooser.setFileFilter(fileFilter);
			
			int selectedOption = JOptionPane.YES_OPTION;
			if(JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(parentComponent)) {
				file = fileChooser.getSelectedFile();
				if(file.exists()) {
					selectedOption = JOptionPane.showConfirmDialog(parentComponent, Messages.getString("AOUIManager.77") + "\r\n" + file.getAbsolutePath(), Messages.getString("AOUIManager.85"), JOptionPane.YES_NO_CANCEL_OPTION); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					if(selectedOption == JOptionPane.CANCEL_OPTION) {
						Logger.getLogger("es.gob.afirma").info("Se ha cancelado la operacion de guardado."); //$NON-NLS-1$ //$NON-NLS-2$ 
						return null;
					}
					// Si se ha seleccionado la opcion YES (se desea sobreescribir) continuamos
					// normalmente con el guardado del fichero 
				}
				
				if(selectedOption == JOptionPane.NO_OPTION) {
					tryAgain = true;
				}
				else {	// Hemos seleccionado la opcion de sobreescribir
					FileOutputStream fos = null;
					try {
						fos = new FileOutputStream(file);
						fos.write(data);
					} 
					catch (Throwable ex) {
						Logger.getLogger("es.gob.afirma").warning("No se pudo guardar la informacion en el fichero indicado: " + ex); //$NON-NLS-1$ //$NON-NLS-2$ 
						ex.printStackTrace();
						JOptionPane.showMessageDialog(parentComponent, Messages.getString("AOUIManager.88"), Messages.getString("AOUIManager.89"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
						fos = null;
						// Volvemos a intentar guardar
						tryAgain = true;
					}
					if(fos != null) {
						try {
							fos.close();
						}
						catch (IOException ex) {
							Logger.getLogger("es.gob.afirma").warning("No se pudo cerrar el fichero generado con los datos cifrados: " + ex); //$NON-NLS-1$ //$NON-NLS-2$
						}
					}
					filename = file.getAbsolutePath();
				}
			}
		}
		
		// Devolvemos el path del fichero en el que se han guardado los datos
		return filename;
	}
	
}
