package es.gob.afirma.ui.principal;

import static org.junit.Assert.*;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.junit.Ignore;
import org.junit.Test;

import es.gob.afirma.ui.utils.Constants;
import es.gob.afirma.ui.utils.GeneralConfig;

/**
 * Testeo de accesibilidad para la clase MultifirmaSimple.
 * @author lmerayo
 *
 */
public class MultifirmaSimpleAccessibilityTest {

	/**
	 * Log.
	 */
	static Logger logger = Logger.getLogger(MultifirmaSimpleAccessibilityTest.class.getName());
	
	/**
	 * Comprobaci�n de que el campo labelFor de las etiquetas no est� duplicado. 
	 */
	@Test
	public void testNotDuplicatedLabelForProperty() {
		logger.info("testNotDuplicatedLabelForProperty");

		//Instancia del panel que se va a analizar
		MultifirmaSimple multifirmaPanel = new MultifirmaSimple();
		//Lista de componentes asociados
		List <Component> componentList = new ArrayList<Component>();
		//Conjunto de componentes asociados
		Set <Component> componentSet = null;
		
		//Array de componentes
		Component[] components = multifirmaPanel.getComponents();
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
	 *  no est� duplicado. Modo Simple.
	 */
	@Test
	public void testNotDuplicatedDisplayedMnemonic_SimpleMode() {
		logger.info("testNotDuplicatedDisplayedMnemonic_SimpleMode");

		//Instancia del panel que se va a analizar
		MultifirmaSimple multifirmaPanel = new MultifirmaSimple();
		
		//Lista de mnem�nicos
		List <Integer> keyCodes = new ArrayList<Integer>();
		//Conjunto de mnem�nicos
		Set <Integer> keyCodesSet = null;
		
		//Se llama al m�todo que obtiene una lista de c�digos de atajos asociados a los componentes del panel
		getKeyCodeList (multifirmaPanel, keyCodes);

		//Se crea un conjunto a partir de la lista para eliminar duplicados
		keyCodesSet = new HashSet<Integer>(keyCodes);
		//Si el tama�o de la lista y del conjunto no son iguales, no hay duplicados
		assertTrue(keyCodesSet.size() == keyCodes.size());
	}

	/**
	 * Comprobaci�n de que el campo Mnemocic de las etiquetas, botones y botones de radio
	 *  no est� duplicado. Modo Avanzado.
	 */
	@Test
	public void testNotDuplicatedDisplayedMnemonic_AdvancedMode() {
		logger.info("testNotDuplicatedDisplayedMnemonic_AdvancedMode");

		//Se obtiene la cofiguraci�n general
		//Se a�ade el perfil por defecto
		UserProfile.currentUser=Constants.defaultUser;
		GeneralConfig.loadConfig(GeneralConfig.getConfig());
		Properties config = GeneralConfig.getConfig();
		
		//Se cambia al modo avanzado
		config.setProperty(MainOptionsPane.MAIN_ADVANCED_VIEW, "true");

		//Se asigna
		GeneralConfig.loadConfig(config);
				
		//Instancia del panel que se va a analizar
		MultifirmaSimple multifirmaPanel = new MultifirmaSimple();
		
		//Lista de mnem�nicos
		List <Integer> keyCodes = new ArrayList<Integer>();
		//Conjunto de mnem�nicos
		Set <Integer> keyCodesSet = null;
		
		//Se llama al m�todo que obtiene una lista de c�digos de atajos asociados a los componentes del panel
		getKeyCodeList (multifirmaPanel, keyCodes);

		//Se crea un conjunto a partir de la lista para eliminar duplicados
		keyCodesSet = new HashSet<Integer>(keyCodes);
		//Si el tama�o de la lista y del conjunto no son iguales, no hay duplicados
		assertTrue(keyCodesSet.size() == keyCodes.size());
	}
	
	/**
	 * Comprobaci�n de que el campo nombre accesible para botones, combos y checks
	 * no est� vac�o. 
	 */
	@Test
	public void testNotEmptyAccessibleName_SimpleMode() {
		logger.info("testNotEmptyAccessibleName_SimpleMode()");
		//Instancia del panel que se va a analizar
		MultifirmaSimple multifirmaSimplePanel = new MultifirmaSimple();
		//Se llama al m�todo que comprueba que el nombre no sea vac�o
		assertTrue(checkAccessibleName(multifirmaSimplePanel));
	}
	
	/**
	 * Comprobaci�n de que el campo nombre accesible para botones, radioButtons, combos y checks
	 * no est� vac�o. 
	 */
	@Test
	public void testNotEmptyAccessibleName_AdvancedMode() {
		logger.info("testNotEmptyAccessibleName_AdvancedMode()");
		//Se obtiene la cofiguraci�n general
		//Se a�ade el perfil por defecto
		UserProfile.currentUser=Constants.defaultUser;
		GeneralConfig.loadConfig(GeneralConfig.getConfig());
		Properties config = GeneralConfig.getConfig();
		
		//Se cambia al modo avanzado
		config.setProperty(MainOptionsPane.MAIN_ADVANCED_VIEW, "true");
				
		//Instancia del panel que se va a analizar
		MultifirmaSimple multifirmaSimplePanel = new MultifirmaSimple();
		//Se llama al m�todo que comprueba que el nombre no sea vac�o
		assertTrue(checkAccessibleName(multifirmaSimplePanel));
	}
	
	/**
	 * Recorre el panel comprobando que todos sus componentes (botones, radioButtons, checks y combos)
	 * tienen un nombre accesible asignado.
	 * @param panel panel
	 * @return verdadero -> si los componentes tienen un nombre accesible asignado
	 * 		   falso -> si alg�n componente no tiene un nombre accesible asignado
	 */
	@Ignore
	private boolean checkAccessibleName(JPanel panel) {
		boolean result = true;
		//Array de componentes del panel
		Component[] components = panel.getComponents();
		for (int i = 0; i < components.length; i++) {
			//Se obtiene el componente
			Component component = panel.getComponent(i);
			if (!(component instanceof JPanel)) {
				if (component instanceof JButton) { //Se comprueba si es un bot�n
					JButton button = (JButton) component;
					if (button.getAccessibleContext().getAccessibleName().equalsIgnoreCase("")) {
						return false; //Si no tiene asignado un nombre accesible se sale del m�todo
					}
				} else if (component instanceof JCheckBox) { //Se comprueba si es un checkBox
					JCheckBox checkBox = (JCheckBox) component;
					if (checkBox.getAccessibleContext().getAccessibleName().equalsIgnoreCase("")) {
						return false; //Si no tiene asignado un nombre accesible se sale del m�todo
					}
				} else if (component instanceof JComboBox) { //Se comprueba si es un combo
					JComboBox comboBox = (JComboBox) component;
					if (comboBox.getAccessibleContext().getAccessibleName().equalsIgnoreCase("")) {
						return false; //Si no tiene asignado un nombre accesible se sale del m�todo
					}
				} else if (component instanceof JRadioButton) { //Se comprueba si es un radioButton
					JRadioButton radioButton = (JRadioButton) component;
					if (radioButton.getAccessibleContext().getAccessibleName().equalsIgnoreCase("")) {
						return false; //Si no tiene asignado un nombre accesible se sale del m�todo
					}
				} else if (component instanceof JTextField) { //Se comprueba si es un campo de texto
					JTextField textField = (JTextField) component;
					if (textField.getAccessibleContext().getAccessibleName().equalsIgnoreCase("")) {
						return false; //Si no tiene asignado un nombre accesible se sale del m�todo
					}
				}
				
			} else {
				//Si es un panel se vuelve a llamar recursivamente al m�todo
				result = checkAccessibleName((JPanel)component);
			}
		} //for
		return result;
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
				}
				
			} else {
				//Si es un panel se vuelve a llamar recursivamente al m�todo
				getKeyCodeList((JPanel)component, keyCodeList);
			}
		} //for
	}//getKeyCodeList


}
