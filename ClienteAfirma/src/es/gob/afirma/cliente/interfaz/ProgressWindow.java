/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Ministerio de la Presidencia, Gobierno de Espa�a (opcional: correo de contacto)
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3  seg�n las
 * condiciones que figuran en el fichero 'licence' que se acompa�a.  Si se   distribuyera este 
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */

package es.gob.afirma.cliente.interfaz;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public final class ProgressWindow extends JFrame {
	
    private static final long serialVersionUID = 1L;

    private final ProgressStatus status;

    private final long max;

    private int progreso = 0;

    public ProgressWindow(String windowTitle, String statusTitle, long max) throws HeadlessException {
        super(windowTitle);

        this.max = max;
        this.status = new ProgressStatus(statusTitle, "   ");

        status.setMaxValue(Integer.MAX_VALUE);

        getContentPane().add(status, BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);
        setVisible(true);
    }

//    public void update(int value) {
//        progreso = value;
//
//        double ratio = (double) progreso / max;
//        int newVal = (int) (ratio * Integer.MAX_VALUE);
//
//        status.updateValue(newVal);
//
//        status.paint(status.getGraphics());
//    }

    public void inc(final int amount) {
        progreso += amount;

        double ratio = (double) progreso / max;
        int newVal = (int) (ratio * Integer.MAX_VALUE);

        status.updateValue(newVal);
        
        status.paint(status.getGraphics());
    }
}
