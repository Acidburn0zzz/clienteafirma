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

import org.junit.Ignore;
import org.junit.Test;

/**
 * Testeo de accesibilidad para la clase ContextOptionsPane.
 * @author lmerayo
 *
 */
public class ContextOptionsPaneAccessibilityTest {

	/**
	 * Log.
	 */
	static Logger logger = Logger.getLogger(ContextOptionsPaneAccessibilityTest.class.getName());

	/**
	 * Comprobaci�n de que el campo labelFor de las etiquetas no est� duplicado. 
	 */
	@Test
	public void testNotDuplicatedLabelForProperty() {
		logger.info("testNotDuplicatedLabelForProperty");

		//Instancia de la clase que se va a analizar
		ContextOptionsPane contextOptionsPane = new ContextOptionsPane();
		//Lista de componentes asociados
		List <Component> componentList = new ArrayList<Component>();
		//Conjunto de componentes asociados
		Set <Component> componentSet = null;
		
		//Se obtiene el panel principal
		JPanel mainPanel = contextOptionsPane.getConfigurationPanel();

		//Se llama al m�todo que obtiene una lista de componentes asociados a la propiedad labelFor del panel
		getLabelForComponentList ((JPanel) mainPanel, componentList);

		//Se crea un conjunto a partir de la lista para eliminar duplicados
		componentSet = new HashSet<Component>(componentList);
		//Si el tama�o de la lista y del conjunto no son iguales, no hay duplicados
		assertTrue(componentSet.size() == componentList.size());

	}
	
	/**
	 * Comprobaci�n de que el campo Mnemocic de las etiquetas, botones y checkbox no est� duplicado. 
	 */
	@Test
	public void testNotDuplicatedDisplayedMnemonic() {
		logger.info("testNotDuplicatedDisplayedMnemonic");

		//Instancia de la clase que se va a analizar
		ContextOptionsPane contextOptionsPane = new ContextOptionsPane();
		//Lista de mnem�nicos
		List <Integer> keyCodes = new ArrayList<Integer>();
		//Conjunto de mnem�nicos
		Set <Integer> keyCodesSet = null;
		
		//Se obtiene el panel principal
		JPanel mainPanel = contextOptionsPane.getConfigurationPanel();
		
		//Se llama al m�todo que obtiene una lista de c�digos de atajos asociados a los componentes del panel
		getKeyCodeList (mainPanel, keyCodes);
		
		//Se crea un conjunto a partir de la lista para eliminar duplicados
		keyCodesSet = new HashSet<Integer>(keyCodes);
		//Si el tama�o de la lista y del conjunto no son iguales, no hay duplicados
		assertTrue(keyCodesSet.size() == keyCodes.size());
	}

	
	/**
	 * M�todo que obtiene una lista de c�digos de atajos a los botones, etiquetas y checkbox de un panel.
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
				getKeyCodeList((JPanel) component, keyCodeList);
			}
		} //for
	}//getKeyCodeList
	
	/**
	 * M�todo que obtiene la propiedad labelFor de las etiquetas de un panel.
	 */
	@Ignore
	private void getLabelForComponentList(JPanel panel, List <Component> componentList) {
		//Array de componentes del panel
		Component[] components = panel.getComponents();
		Component labelForComponent = null;
		for (int i = 0; i < components.length; i++) {
			//Se obtiene el componente
			Component component = panel.getComponent(i);
			if (!(component instanceof JPanel)) {
				//Se comprueba si es una etiqueta
				if (component instanceof JLabel) {
					JLabel label = (JLabel) component;
					//Se obtiene el componente asociado a la propiedad labelFor
					labelForComponent = label.getLabelFor();
					//Se a�ade a la lista si no es nulo
					if (labelForComponent != null) {
						componentList.add(labelForComponent);
					}
				}
				
			} else {
				//Si es un panel se vuelve a llamar recursivamente al m�todo
				getLabelForComponentList((JPanel) component, componentList);
			}
		} //for
	}//getLabelForComponentList
}
