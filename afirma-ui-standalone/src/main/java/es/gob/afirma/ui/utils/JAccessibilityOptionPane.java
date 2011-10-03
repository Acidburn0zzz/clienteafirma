package es.gob.afirma.ui.utils;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * Clase que extiende JOptionPane para hacerla accesible.
 * @author lmerayo
 *
 */
public class JAccessibilityOptionPane extends JOptionPane {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Muestra un di�logo con un mensaje.
	 * @param componentParent componente padre
	 * @param message mensaje a mostrar
	 * @param title t�tulo del di�logo
	 * @param messageType tipo de mensaje
	 */
	public static void showMessageDialog(Component componentParent, String message, String title, int messageType){
		
		//Etiqueta con el texto que se desea mostrar en el di�logo
		InfoLabel infoLabel = new InfoLabel(message, false);
		//Foco a la etiqueta
		infoLabel.addAncestorListener(new RequestFocusListener());
		//Se muestra el di�logo
		JOptionPane.showMessageDialog(componentParent, infoLabel, title, messageType);
	}
	

	/**
	 * Muestra un di�logo de confirmaci�n.
	 * @param componentParent componente padre
	 * @param message mensaje a mostrar
	 * @param title t�tulo del di�logo
	 * @param messageType tipo de mensaje
	 */
	public static int showConfirmDialog(Component componentParent, String message, String title, int messageType){
		
		//Etiqueta con el texto que se desea mostrar en el di�logo
		InfoLabel infoLabel = new InfoLabel(message, false);
		//Foco a la etiqueta
		infoLabel.addAncestorListener(new RequestFocusListener());
		//Se muestra el di�logo
		return JOptionPane.showConfirmDialog(componentParent, infoLabel, title, messageType);
	}
}
