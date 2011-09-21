package es.gob.afirma.ui.principal;

import static org.junit.Assert.*;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.junit.Ignore;
import org.junit.Test;


public class MultifirmaMasivaAccessibilityTest {

	/**
	 * Log.
	 */
	static Logger logger = Logger.getLogger(MultifirmaMasivaAccessibilityTest.class.getName());
	
	/**
	 * Comprobaci�n de que el campo labelFor de las etiquetas no est� duplicado. 
	 */
	@Test
	public void testNotDuplicatedLabelForProperty() {
		logger.info("testNotDuplicatedLabelForProperty");

		//Instancia del panel que se va a analizar
		MultifirmaMasiva multifirmaMasivaPanel = new MultifirmaMasiva();
		//Lista de componentes asociados
		List <Component> componentList = new ArrayList<Component>();
		//Conjunto de componentes asociados
		Set <Component> componentSet = null;
		
		//Array de componentes
		Component[] components = multifirmaMasivaPanel.getComponents();
		//Se recorren los componentes del panel
		for (int i = 0; i< components.length; i++) {
			//Se comprueba si es una etiqueta
			if (components[i] instanceof JLabel) {
				JLabel label = (JLabel) components[i];
				Component component = label.getLabelFor();
				//Para este panel hasta el momento todas las etiquetas tienen asociado el campo labelFor
				assertNotNull(component);
				//Se a�ade a la lista el componente
				componentList.add(component);
			}
		}
		//Se crea un conjunto a partir de la lista para eliminar duplicados
		componentSet = new HashSet<Component>(componentList);
		//Si el tama�o de la lista y del conjunto no son iguales, no hay duplicados
		assertTrue(componentSet.size() == componentList.size());

	}

	/**
	 * Comprobaci�n de que el campo Mnemocic de las etiquetas, botones y botones de radio
	 *  no est� duplicado
	 */
	@Test
	public void testNotDuplicatedDisplayedMnemonic() {
		logger.info("testNotDuplicatedDisplayedMnemonic");

		//Instancia del panel que se va a analizar
		MultifirmaMasiva multifirmaMasivaPanel = new MultifirmaMasiva();
		
		//Lista de mnem�nicos
		List <Integer> keyCodes = new ArrayList<Integer>();
		//Conjunto de mnem�nicos
		Set <Integer> keyCodesSet = null;
		
		//Se llama al m�todo que obtiene una lista de c�digos de atajos asociados a los componentes del panel
		getKeyCodeList (multifirmaMasivaPanel, keyCodes);

		//Se crea un conjunto a partir de la lista para eliminar duplicados
		keyCodesSet = new HashSet<Integer>(keyCodes);
		//Si el tama�o de la lista y del conjunto no son iguales, no hay duplicados
		assertTrue(keyCodesSet.size() == keyCodes.size());
	}
	

	/**
	 * M�todo que obtiene una lista de c�digos de atajos a los componentes (Etiqueta, Bot�n, radio button) de un panel.
	 */
	@Ignore
	private void getKeyCodeList(JPanel panel, List <Integer> keyCodeList) {
		//Array de componentes del panel
		Component[] components = panel.getComponents();
		int keyCode = 0;
		for (int i = 0; i < components.length; i++) {
			//Se obtiene el componente
			Component component = panel.getComponent(i);
			if (!(component instanceof JPanel)) {
				//Se comprueba si es una etiqueta
				if (component instanceof JLabel) {
					JLabel label = (JLabel) component;
					//Se obtiene el c�digo del atajo asociado
					keyCode = label.getDisplayedMnemonic();
					//Se a�ade a la lista si existe este c�digo, es decir, si es distinto de 0
					if (keyCode != 0) {
						keyCodeList.add(new Integer(keyCode));
					}
				} else if (component instanceof JButton) { //Se comprueba si es un bot�n
					JButton button = (JButton) component;
					//Se obtiene el c�digo del atajo asociado
					keyCode = button.getMnemonic();
					//Se a�ade a la lista si existe este c�digo, es decir, si es distinto de 0
					if (keyCode != 0) {
						keyCodeList.add(new Integer(keyCode));
					}
				} else if (component instanceof JRadioButton) { //Se comprueba si es un bot�n de radio
					JRadioButton radioButton = (JRadioButton) component;
					//Se obtiene el c�digo del atajo asociado
					keyCode = radioButton.getMnemonic();
					//Se a�ade a la lista si existe este c�digo, es decir, si es distinto de 0
					if (keyCode != 0) {
						keyCodeList.add(new Integer(keyCode));
					}
				} else if (component instanceof JCheckBox) { //Se comprueba si es un checkbox
					JCheckBox checkBox = (JCheckBox) component;
					//Se obtiene el c�digo del atajo asociado
					keyCode = checkBox.getMnemonic();
					//Se a�ade a la lista si existe este c�digo, es decir, si es distinto de 0
					if (keyCode != 0) {
						keyCodeList.add(new Integer(keyCode));
					}
				}
				
			} else {
				//Si es un panel se vuelve a llamar recursivamente al m�todo
				getKeyCodeList((JPanel)component, keyCodeList);
			}
		} //for
	}//getKeyCodeList


}
