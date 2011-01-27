/*
 * Este fichero forma parte del Cliente @firma. 
 * El Cliente @firma es un applet de libre distribuci�n cuyo c�digo fuente puede ser consultado
 * y descargado desde www.ctt.map.es.
 * Copyright 2009,2010 Gobierno de Espa�a
 * Este fichero se distribuye bajo las licencias EUPL versi�n 1.1  y GPL versi�n 3, o superiores, seg�n las
 * condiciones que figuran en el fichero 'LICENSE.txt' que se acompa�a.  Si se   distribuyera este 
 * fichero individualmente, deben incluirse aqu� las condiciones expresadas all�.
 */


package es.gob.afirma.cliente.interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;


class ProgressStatus extends JComponent
{
    private JProgressBar barra = null;

    private JTextField text = null;

    private String message = "";

    private int maxValue = 0;

    static final long serialVersionUID = 1;

    public ProgressStatus(String info, String message)
    {
        this.message = message;

        setLayout(new BorderLayout());

        this.text = new JTextField();
        add(text, BorderLayout.NORTH);

        this.barra = new JProgressBar();

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(0, 1));
        panelInferior.add(this.barra);
        panelInferior.add(new JLabel(info));

        add(panelInferior, BorderLayout.SOUTH);
    }

    public void setMaxValue(int value)
    {
        this.maxValue = value;

        this.barra.setMaximum(value);
    }

    public void updateValue(int value)
    {
        double percentage = 0.0;

        this.barra.setValue(value);

        if (this.maxValue > 0)
            percentage = ((double) value / this.maxValue) * 100;
        else
            percentage = 0.0;

        // Formateamos el valor obtenido
        String valorPorcentaje = new Double(percentage).toString();
        int dotIndex = valorPorcentaje.indexOf(".");
        if (valorPorcentaje.length() - dotIndex > 2)
            valorPorcentaje = valorPorcentaje.substring(0, dotIndex + 2);

        this.text.setText(this.message + " " + valorPorcentaje + "%");
    }
}
