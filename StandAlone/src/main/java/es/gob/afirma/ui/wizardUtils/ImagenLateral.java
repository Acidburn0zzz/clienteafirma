/*
 * Este fichero forma parte del Cliente @firma.
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Ministerio de la Presidencia, Gobierno de Espa�a (opcional: correo de contacto)
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3  seg�n las
 * condiciones que figuran en el fichero 'licence' que se acompa�a.  Si se   distribuyera este
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */
package es.gob.afirma.ui.wizardUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Clase para generar la imagen lateral de los asistente
 */
public class ImagenLateral extends JPanel {

	private static final long serialVersionUID = 1L;
	private String rutaImagen = "/resources/images/nubes.png";
	private Dimension dimensiones = new Dimension(145, 388);

	public ImagenLateral() {
		initComponents();
	}
	
	/**
	 * Inicializa la imagen a mostrar en el panel
	 * @param rutaImagen	Ruta del panel
	 */
	public ImagenLateral(String rutaImagen) {
		this.rutaImagen	= rutaImagen;
		initComponents();
	}
	
	/**
	 * Inicializa la imagen a mostrar en el panel
	 * @param rutaImagen	Ruta del panel
	 * @param dimensiones	Dimensiones del panel
	 */
	public ImagenLateral(String rutaImagen, Dimension dimensiones) {
		this.rutaImagen	= rutaImagen;
		this.dimensiones = dimensiones;
		initComponents();
	}
	
	/**
	 * Inicializamos los componentes
	 */
	private void initComponents() {
		// Configuracion panel
		setLayout(new BorderLayout());
		setPreferredSize(dimensiones);
		
		JLabel etiqueta = new JLabel();
		etiqueta.setIcon(new ImageIcon(getClass().getResource(rutaImagen))); // NOI18N
        add(etiqueta, BorderLayout.CENTER);
    }
}
