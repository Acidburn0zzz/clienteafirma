/*
 * Este fichero forma parte del Cliente @firma.
 * El Cliente @firma es un applet de libre distribucion cuyo codigo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Ministerio de la Presidencia, Gobierno de Espana
 * Este fichero se distribuye bajo licencia GPL version 3 segun las
 * condiciones que figuran en el fichero 'licence' que se acompana.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aqui las condiciones expresadas alli.
 */
package es.gob.afirma.ui.utils;

import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import es.gob.afirma.ui.principal.Main;

/**
 * Clase para seleccionar un tipo de ventana de dialogo.
 */
public final class SelectionDialog {

	private SelectionDialog() {
		// No permitimos la instanciacion
	}

	/**
	 * Muestra un di&aacute;logo para la selecci&oacute;n de un fichero en disco.
	 * @param parent Component padre sobre el que se mostrar&aacute; el di&aacute;logo.
	 * @param title T&iacute;tulo del di&aacute;logo de selecci&oacute;n.
	 * @return Fichero seleccionado o {@code null} si no se seleccion&oacute;o ninguno.
	 */
	public static File showFileOpenDialog(final Component parent, final String title) {
		return showOpenDialog(parent, title, JFileChooser.FILES_ONLY, null);
	}

	/**
	 * Muestra un di&aacute;logo para la selecci&oacute;n de un fichero en disco
	 * mostrando s&oacute;lo aquellos que pasen el filtro indicado.
	 * @param parent Component padre sobre el que se mostrar&aacute; el di&aacute;logo.
	 * @param title T&iacute;tulo del di&aacute;logo de selecci&oacute;n.
	 * @param filter Filtro de ficheros.
	 * @return Fichero seleccionado o {@code null} si no se seleccion&oacute;o ninguno.
	 */
	public static File showFileOpenDialog(final Component parent, final String title, final ExtFilter filter) {
		return showOpenDialog(parent, title, JFileChooser.FILES_ONLY, filter);
	}

	/**
	 * Muestra un di&aacute;logo para la selecci&oacute;n de un directorio en disco.
	 * @param parent Component padre sobre el que se mostrar&aacute; el di&aacute;logo.
	 * @param title T&iacute;tulo del di&aacute;logo de selecci&oacute;n.
	 * @return Directorio seleccionado o {@code null} si no se seleccion&oacute;o ninguno.
	 */
	public static File showDirOpenDialog(final Component parent, final String title) {
		return showOpenDialog(parent, title, JFileChooser.DIRECTORIES_ONLY, null);
	}

	/**
	 * Muestra un di&aacute;logo para la selecci&oacute;n de un archivo en disco.
	 * @param parent Component padre sobre el que se mostrar&aacute; el di&aacute;logo.
	 * @param title T&iacute;tulo del di&aacute;logo de selecci&oacute;n.
	 * @param selectionMode Modo de selecci&oacute;n de {@link JFileChooser}.
	 * @param filter Filtro de ficheros.
	 * @return Archivo seleccionado o {@code null} si no se seleccion&oacute;o ninguno.
	 */
	private static File showOpenDialog(final Component parent, final String title, final int selectionMode, final ExtFilter filter) {

        String currentDir = Main.getPreferences().get("dialog.load.dir", null); //$NON-NLS-1$
        if (currentDir == null) {
            currentDir = "."; //$NON-NLS-1$
        }

		//Instancia del componente FileChooser accesible
        final JAccessibilityFileChooser fc = new JAccessibilityFileChooser(new File(currentDir));

		fc.setDialogTitle(title);
		fc.setFileSelectionMode(selectionMode);
		if (filter != null) {
			fc.setFileFilter(filter);
		}

		File filePath = null;

		if(fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			filePath = fc.getSelectedFile();
			if (filePath.getParentFile() != null) {
			    Main.getPreferences().put("dialog.load.dir", filePath.getParentFile().getAbsolutePath()); //$NON-NLS-1$
			}
		}

		return filePath;
	}

	/** Muestra un di&aacute;logo de guardado para almacenar los datos indicados.
     * Los datos ser&aacute;n almacenados en el directorio y con el nombre que
     * indique el usuario. Si el fichero ya existe se le preguntar&aacute; al
     * usuario si desea sobrescribirlo. En caso de cancelar la operaci&oacute;n
     * se devolvera null, si la operaci&oacute;n finaliza correctamente se
     * devolver&aacute; el path completo del fichero.
     *
     * @param dialogTitle
     *        T&iacute;tulo de la ventana de guardado.
     * @param data
     *        Datos que se desean almacenar.
     * @param defaultName
     *        Nombre de fichero por defecto.
     * @param fileFilter
     *        Filtro de fichero para el di&aacute;logo de guardado.
     * @param parent
     *        Componente padre sobre el que se mostrar&aacute; el
     *        di&aacute;logo de guardado.
     * @return Fichero guardado.
     * @throws NullPointerException
     *         No se introdujeron los datos que se desean almacenar. */
    public static File saveDataToFile(final String dialogTitle, final byte[] data, final String defaultName, final FileFilter fileFilter, final Object parent) {

        if (data == null) {
            Logger.getLogger("es.gob.afirma").warning("No se han introducido los datos que se desean guardar. Se cancelara la operacion"); //$NON-NLS-1$ //$NON-NLS-2$
            throw new IllegalArgumentException("No se introdujeron datos que almacenar"); //$NON-NLS-1$
        }

        Component parentComponent = null;
        if (parent instanceof Component) {
            parentComponent = (Component) parent;
        }
        File resultFile = null;
        boolean tryAgain = true;
        File file = null;
        while (tryAgain) {
        	tryAgain = false;
	        //Instancia del dialogo de guardado accesible
	        final JAccessibilityFileChooserToSave fileChooser = new JAccessibilityFileChooserToSave();
	        final String currentDir = Main.getPreferences().get("dialog.save.dir", null); //$NON-NLS-1$
	        if (currentDir != null) {
	            fileChooser.setCurrentDirectory(new File(currentDir));
	        }

	        fileChooser.setDialogTitle(dialogTitle); //Se le asigna un titulo al dialogo
            fileChooser.getAccessibleContext().setAccessibleName(Messages.getString("SelectionDialog.saveDialog.accesible.name")); //$NON-NLS-1$
            fileChooser.getAccessibleContext().setAccessibleDescription(Messages.getString("SelectionDialog.saveDialog.accesible.desc")); //$NON-NLS-1$
            fileChooser.setToolTipText(Messages.getString("SelectionDialog.saveDialog.accesible.name")); //$NON-NLS-1$
            if (file != null) {
                fileChooser.setSelectedFile(file);
            } else if (defaultName != null) {
                fileChooser.setSelectedFile(new File(defaultName));
            }

            // Solo aplicamos el filtro cuando este definido para evitar que el
            // desplegable de la ventana de guardado nos aparecezca vacio
            if (fileFilter != null) {
                fileChooser.setFileFilter(fileFilter);
            }

            int selectedOption = JOptionPane.YES_OPTION;
            if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(parentComponent)) {
                file = fileChooser.getSelectedFile();
                if (file.exists()) {
                	//Path al fichero
                	String filePath = file.getAbsolutePath();
                	//Comprobacion del numero de caracteres para acortar o no el path que se muestra en la alerta de sobreescritura de fichero
                	if (filePath.length() >20) {
                		//Se obtiene el indice del final del primer directorio
                		final int indexFirstDirectory = filePath.indexOf(File.separator,1);
                		//Se almacena el primer directorio o unidad en windows
                		String filePathTemp= filePath.substring(0, indexFirstDirectory + 1);
                		//Se almacena el path sin incluir el nombre del fichero
                		final String subSequence = filePath.substring(0,filePath.lastIndexOf(File.separator));
                		//Se almacena el indice del comienzo del ultimo directorio
                		final int indexLastDirectory = subSequence.lastIndexOf(File.separator);
                		//Si el primer directorio y el ultimo no son el mismo
                		if (indexFirstDirectory<indexLastDirectory){
                			//Se anaden unos puntos suspensivos al primer directorio
                			filePathTemp = filePathTemp +"...";
                			//Se anade el ultimo directorio y el nombre del fichero
                			filePathTemp = filePathTemp + filePath.substring(indexLastDirectory, filePath.length());
                			//Se sustituye el path completo por el path acortado
                			filePath = filePathTemp;
                		}
                	}
                    selectedOption =
                    		CustomDialog.showConfirmDialog(parentComponent, true,
                                Messages.getString("SelectionDialog.saveDialog.overwrite.adv", filePath), Messages.getString("SelectionDialog.saveDialog.title"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
                    if (selectedOption == JOptionPane.CANCEL_OPTION) {
                        Logger.getLogger("es.gob.afirma").info("Se ha cancelado la operacion de guardado."); //$NON-NLS-1$ //$NON-NLS-2$
                        return null;
                    }
                }

                if (selectedOption == JOptionPane.NO_OPTION) {
                    tryAgain = true;
                }
                else { // Hemos seleccionado la opcion de sobrescribir
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(file);
                        fos.write(data);
                    }
                    catch (final Exception ex) {
                        Logger.getLogger("es.gob.afirma").warning("No se pudo guardar la informacion en el fichero indicado: " + ex); //$NON-NLS-1$ //$NON-NLS-2$
                       CustomDialog.showMessageDialog(parentComponent, true,
                                Messages.getString("SelectionDialog.saveDialog.error.msg"), Messages.getString("SelectionDialog.saveDialog.error.title"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
                        fos = null;
                        // Volvemos a intentar guardar
                        tryAgain = true;
                    }
                    if (fos != null) {
                        try {
                            fos.flush();
                        }
                        catch (final Exception e) { /** No hacemos nada. */ }
                        try {
                            fos.close();
                        }
                        catch (final Exception e) { /** No hacemos nada. */ }
                    }
                    resultFile = file;
                }
            }
        }

        try {
            if (resultFile != null && resultFile.getParentFile() != null) {
                Main.getPreferences().put("dialog.save.dir", resultFile.getParentFile().getAbsolutePath()); //$NON-NLS-1$
            }
        } catch (final Exception e) {
            /* No hacemos nada */
        }

        // Devolvemos el path del fichero en el que se han guardado los datos
        return resultFile;
    }
}
