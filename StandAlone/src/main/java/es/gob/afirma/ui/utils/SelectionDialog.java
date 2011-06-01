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

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;

/**
 * Clase para seleccionar un tipo de ventana de dialogo.
 */
public class SelectionDialog {

	private static File currentDir = null;
	
	/**
	 * Muestra un di&aacute;logo para la selecci&oacute;n de un fichero en disco.
	 * @param parent Component padre sobre el que se mostrar&aacute; el di&aacute;logo.
	 * @param title T&iacute;tulo del di&aacute;logo de selecci&oacute;n.
	 * @return Fichero seleccionado o {@code null} si no se seleccion&oacute;o ninguno.
	 */
	public File showFileOpenDialog(Component parent, String title) {
		return showOpenDialog(parent, title, JFileChooser.FILES_ONLY);
	}
	
	/**
	 * Muestra un di&aacute;logo para la selecci&oacute;n de un directorio en disco.
	 * @param parent Component padre sobre el que se mostrar&aacute; el di&aacute;logo.
	 * @param title T&iacute;tulo del di&aacute;logo de selecci&oacute;n.
	 * @return Directorio seleccionado o {@code null} si no se seleccion&oacute;o ninguno.
	 */
	public File showDirOpenDialog(Component parent, String title) {
		return showOpenDialog(parent, title, JFileChooser.DIRECTORIES_ONLY);
	}

	/**
	 * Muestra un di&aacute;logo para la selecci&oacute;n de un archivo en discop.
	 * @param parent Component padre sobre el que se mostrar&aacute; el di&aacute;logo.
	 * @param title T&iacute;tulo del di&aacute;logo de selecci&oacute;n.
	 * @param selectionMode Modo de selecci&oacute;n de {@link JFileChooser}.
	 * @return Archivo seleccionado o {@code null} si no se seleccion&oacute;o ninguno.
	 */
	private File showOpenDialog(Component parent, String title, int selectionMode) {
		File filePath = null;

		JFileChooser fc = new JFileChooser(currentDir);
		fc.setDialogTitle(title);
		fc.setFileSelectionMode(selectionMode);
		if(fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			filePath = fc.getSelectedFile();
			currentDir = filePath.getParentFile();
		}
		
		return filePath;
	}
}
