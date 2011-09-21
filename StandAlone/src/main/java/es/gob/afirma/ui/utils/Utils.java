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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

import es.gob.afirma.ui.principal.PrincipalGUI;

/**
 * Clase con utilidades varias
 */
public class Utils {

	/**
	 * Abre un fichero en la aplicaci\u00F3n predefinida por el sistema operativo actual.
	 * @param filepath Ruta completa al fichero.
	 */
	public static void openFile(String filepath){
		String os = System.getProperty("os.name").toLowerCase();
		Runtime rt = Runtime.getRuntime();
		try {
			if (os.indexOf( "win" ) >= 0) 
				rt.exec("cmd.exe /C \""+filepath+"\"");
			else if (os.indexOf( "mac" ) >= 0)
				rt.exec( "open " + filepath);
			else {
				//prioritized 'guess' of users' preference
				List<String> browsers = new ArrayList<String>(Arrays.asList("epiphany", "firefox", "mozilla", "konqueror",
						"netscape","opera","links","lynx"));

				StringBuffer cmd = new StringBuffer();
				for (String browser : browsers)
					cmd.append( (browsers.get(0).equals(browser)  ? "" : " || " ) + browser +" \"" + filepath + "\" ");

				rt.exec(new String[] { "sh", "-c", cmd.toString() });
			}
		} catch (IOException e) {
			e.printStackTrace();
			PrincipalGUI.setNuevoEstado(Messages.getString("Validacion.error.valide"));
		}
	}

	/**
     * Abre un fichero en la aplicaci\u00F3n predefinida por el sistema operativo actual.
     * @param filepath Ruta completa al fichero.
     */
    public static void openFile(File file){
        try {
            openFile(file.getCanonicalPath());
        } catch (Exception e) {
            openFile(file.getAbsolutePath());
        }
    }
	
	/**
	 * M�todo que devuelve un mnem�nico v�lido para el lenguaje que recibe como par�metro.
	 * @param listMnemonic lista de mnem�nicos que ya han sido utilizados para otros lenguajes.
	 * @param actualLanguage lenguaje para el que se est� buscando un mnem�nico
	 * @return mnem�nico seleccionado o 0 en el caso de que no se haya encontrado ninguno disponible
	 */
	public static char getLanguageMnemonic(List<Character> mnemonicList, String actualLanguage){
		//Se recorren las letras del lenguaje actual
		for (int i=0; i< actualLanguage.length(); i++) {
			//Se lee el caracter correspondiente al �ndice i
			char caracter = actualLanguage.charAt(i);
			//Se comprueba si se ha utilizado
			if (!mnemonicList.contains(caracter)) {
				//se a�ade a la lista de caracteres utilizados
				mnemonicList.add(caracter);
				//Se devuelve
				return caracter;
			}
		}
		//TODO: mejorar para que en el caso de que no encuentre mnem�nico pueda cambiar alguno de los anteriores
		return 0;
	}
	
	/**
	 * Configura el formato del remarcado del componente al ser seleccionado.
	 * @param component El componente seleccionado.
	 */
	public static void remarcar(JComponent component){
		if (component instanceof JButton){
			final JButton button = (JButton) component;
			button.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					button.setFont(new Font(button.getFont().getName(), button.getFont().getStyle(), button.getFont().getSize()-5));
				}		
				public void focusGained(FocusEvent e) {
					button.setFont(new Font(button.getFont().getName(), button.getFont().getStyle(), button.getFont().getSize()+5));
				}
			});
			if (button.getIcon() != null) {			
				button.addFocusListener(new FocusListener() {
					public void focusLost(FocusEvent e) {
						button.setBorder(BorderFactory.createEmptyBorder());
					}		
					public void focusGained(FocusEvent e) {
						button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
					}
				});
			}
		}
		if (component instanceof JToggleButton){
			final JToggleButton button = (JToggleButton) component;
			button.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					button.setFont(new Font(button.getFont().getName(), button.getFont().getStyle(), button.getFont().getSize()-5));
				}		
				public void focusGained(FocusEvent e) {
					button.setFont(new Font(button.getFont().getName(), button.getFont().getStyle(), button.getFont().getSize()+5));
				}
			});
		}		
		if (component instanceof JTextField){
			final JTextField textField = (JTextField) component;
			textField.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					textField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
				}
				public void focusGained(FocusEvent e) {
					textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				}
			});
		}
		if (component instanceof JComboBox){
			final JComboBox comboBox = (JComboBox) component;
			comboBox.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					comboBox.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
				}
				
				public void focusGained(FocusEvent e) {
					comboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));//(new DashBorder(Color.BLACK, new Insets(-1,-1,-1,-1)));
				}
			});
		}
		if (component instanceof JRadioButton){
			final JRadioButton radioButton = (JRadioButton) component;
			radioButton.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					radioButton.setFont(new Font(radioButton.getFont().getName(), radioButton.getFont().getStyle(), radioButton.getFont().getSize()-10));
				}
				public void focusGained(FocusEvent e) {
					radioButton.setFont(new Font(radioButton.getFont().getName(), radioButton.getFont().getStyle(), radioButton.getFont().getSize()+10));
				}
			});
		}
		if (component instanceof JLabel){
			final JLabel label = (JLabel) component;
			label.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					label.setBorder(BorderFactory.createEmptyBorder());
				}
				public void focusGained(FocusEvent e) {
					label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				}
			});
		}
		if (component instanceof JCheckBox){
			final JCheckBox checkBox = (JCheckBox) component;
			checkBox.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					checkBox.setFont(new Font(checkBox.getFont().getName(), checkBox.getFont().getStyle(), checkBox.getFont().getSize()-10));
				}
				public void focusGained(FocusEvent e) {
					checkBox.setFont(new Font(checkBox.getFont().getName(), checkBox.getFont().getStyle(), checkBox.getFont().getSize()+10));
				}
			});
		}
		if (component instanceof JTextPane){
			final JTextPane textPane = (JTextPane) component;
			textPane.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					textPane.setBorder(BorderFactory.createEmptyBorder());
				}
				public void focusGained(FocusEvent e) {
					textPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				}
			});
		}
		if (component instanceof JTree){
			final JTree tree = (JTree) component;
			tree.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					tree.setBorder(BorderFactory.createEmptyBorder());
				}
				public void focusGained(FocusEvent e) {
					tree.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				}
			});
		}
		if (component instanceof JList){
			final JList list = (JList) component;
			list.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					list.setBorder(BorderFactory.createEmptyBorder());
				}
				public void focusGained(FocusEvent e) {
					list.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				}
			});
		}
	}
	
	/**
	 * Configura el comportamiento de ciertos componentes en Alto Contraste
	 * @param component Componente al que aplicar el alto contraste
	 */
	public static void setContrastColor (JComponent component){
		if (GeneralConfig.isHighContrast()){
			if (component instanceof JComboBox || component instanceof JPasswordField || component instanceof JTextField){
				component.setBackground(Color.WHITE);
			} else if(component instanceof JCheckBox) {
				component.setForeground(Color.WHITE);
			} else if(component instanceof JTree){
				component.setForeground(Color.WHITE);
			} else if(component instanceof JList){
				component.setForeground(Color.BLACK);
			} else if(component instanceof JPanel){
				if (component.getBorder()!=null){
					if (component.getBorder().getClass().getName().equals("javax.swing.border.TitledBorder")){
						if (((TitledBorder)component.getBorder())!=null){
							((TitledBorder)component.getBorder()).setTitleColor(Color.WHITE);
						}
					}
				}
				component.setForeground(Color.WHITE);
				component.setBackground(Color.BLACK);
			} else if (component instanceof JStatusBar){
				((JLabel)component.getComponent(0)).setForeground(Color.WHITE);
			}			
			else{
				component.setForeground(Color.WHITE);
				component.setBackground(Color.BLACK);
			}
		} else {
			if (component instanceof JStatusBar){
				((JLabel)component.getComponent(0)).setForeground(Color.BLACK);
			}
		}
	}
	
	/**
	 * Aplica el tama�o de fuente grande
	 * @param component Componente al que aplicar el tama�o de fuente.
	 */
	public static void setBigFontSize(JComponent component){
		if (GeneralConfig.isBigFontSize()){
			if (component instanceof JLabel){
				JLabel label = (JLabel) component;
				System.out.println(label.getFont().getSize());
				label.setFont(label.getFont().deriveFont((float)35));
				System.out.println(label.getFont().getSize());
			}
			if (component instanceof JButton){
				JButton button = (JButton) component;
				System.out.println(button.getFont().getSize());
				button.setFont(button.getFont().deriveFont((float)35));
				System.out.println(button.getFont().getSize());
			}
		}
	}
	
	/**
	 * Aplica el estilo de fuente negrita
	 * @param component Componente al que aplicar el estilo de fuente negrita.
	 */
	public static void setFontBold(JComponent component){
		if (GeneralConfig.isFontBold()){
			if (component instanceof JLabel){
				JLabel label = (JLabel) component;
				label.setFont(new Font(label.getFont().getName(),Font.BOLD , label.getFont().getSize()));
			}
			if (component instanceof JButton){
				JButton button = (JButton) component;
				button.setFont(new Font(button.getFont().getName(),Font.BOLD , button.getFont().getSize()));
			}
		}
	}
		
}
