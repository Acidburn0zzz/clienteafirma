package es.gob.afirma.ui.wizardmultifirmamasiva;

import static org.junit.Assert.*;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Testeo de accesibilidad para la clase PanelFormatos.
 * @author lmerayo
 *
 */
public class PanelFormatosAccessibilityTest {

	/**
	 * Log.
	 */
	static Logger logger = Logger.getLogger(PanelFormatosAccessibilityTest.class.getName());
	
	/**
	 * Comprobaci�n de que el campo labelFor de las etiquetas no est� duplicado. 
	 */
	@Test
	public void testNotDuplicatedLabelForProperty() {
		logger.info("testNotDuplicatedLabelForProperty");

		//Instancia del panel que se va a analizar
		PanelFormatos panelFormatos = new PanelFormatos();
		//Lista de componentes asociados
		List <Component> componentList = new ArrayList<Component>();
		//Conjunto de componentes asociados
		Set <Component> componentSet = null;
		
		//Componentes del wizard
		Component[] components = panelFormatos.getComponents();
		
		//Se recorren
		for (int i = 0; i< components.length; i++) {
			Component componentWizard = components[i];
			//Se trata el panel principal del wizard
			if (componentWizard instanceof JRootPane) {
				//Se obtienen los componentes del panel principal
				Component[] componentsRootPane = ((JRootPane)componentWizard).getComponents();
				//Se recorren
				for (int j = 0; j< componentsRootPane.length; j++) {
					//Se obtienen un elemento
					Component componentRootPane = componentsRootPane[j];
					//Si es un panel se trata
					if (componentRootPane instanceof JPanel) {
						//Se llama al m�todo que obtiene una lista de componentes asociados a la propiedad labelFor del panel
						getLabelForComponentList ((JPanel) componentRootPane, componentList);
						
					} else if (componentRootPane instanceof JLayeredPane) { //Si es un layeredPane se obtienen sus componentes
						Component[] componentsLayeredPane = ((JLayeredPane) componentRootPane).getComponents();
						//Se recorren
						for (int z = 0; z< componentsLayeredPane.length; z++) {
							//Se obtienen un elemento
							Component componentLayeredPane = componentsLayeredPane[z];
							//Si es instancia de JPanel se trata
							if (componentLayeredPane instanceof JPanel) {

								//Se llama al m�todo que obtiene una lista de componentes asociados a la propiedad labelFor del panel
								getLabelForComponentList ((JPanel) componentLayeredPane, componentList);
							}
						}
					}
				}
			}
		}
		//Se crea un conjunto a partir de la lista para eliminar duplicados
		componentSet = new HashSet<Component>(componentList);
		//Si el tama�o de la lista y del conjunto no son iguales, no hay duplicados
		assertTrue(componentSet.size() == componentList.size());

	}

	/**
	 * Comprobaci�n de que el campo Mnemocic de las etiquetas,botones y checkbox no est�n duplicados. 
	 */
	@Test
	public void testNotDuplicatedDisplayedMnemonic() {
		logger.info("testNotDuplicatedDisplayedMnemonic");

		//Instancia del panel que se va a analizar
		PanelFormatos panelFormatos = new PanelFormatos();

		//Lista de mnem�nicos
		List <Integer> keyCodes = new ArrayList<Integer>();
		//Conjunto de mnem�nicos
		Set <Integer> keyCodesSet = null;
		
		//Componentes del wizard
		Component[] components = panelFormatos.getComponents();
		
		//Se recorren
		for (int i = 0; i< components.length; i++) {
			Component componentWizard = components[i];
			//Se trata el panel principal del wizard
			if (componentWizard instanceof JRootPane) {
				//Se obtienen los componentes del panel principal
				Component[] componentsRootPane = ((JRootPane)componentWizard).getComponents();
				//Se recorren
				for (int j = 0; j< componentsRootPane.length; j++) {
					//Se obtienen un elemento
					Component componentRootPane = componentsRootPane[j];
					//Si es un panel se trata
					if (componentRootPane instanceof JPanel) {
						//Se llama al m�todo que obtiene una lista de c�digos de atajos asociados a los componentes del panel
						getKeyCodeList ((JPanel) componentRootPane, keyCodes);
						
					} else if (componentRootPane instanceof JLayeredPane) { //Si es un layeredPane se obtienen sus componentes
						Component[] componentsLayeredPane = ((JLayeredPane) componentRootPane).getComponents();
						//Se recorren
						for (int z = 0; z< componentsLayeredPane.length; z++) {
							//Se obtienen un elemento
							Component componentLayeredPane = componentsLayeredPane[z];
							//Si es instancia de JPanel se trata
							if (componentLayeredPane instanceof JPanel) {
								//Se llama al m�todo que obtiene una lista de c�digos de atajos asociados a los componentes del panel
								getKeyCodeList ((JPanel) componentLayeredPane, keyCodes);
							}
						}
					}
				}
			}
		}

		//Se crea un conjunto a partir de la lista para eliminar duplicados
		keyCodesSet = new HashSet<Integer>(keyCodes);
		//Si el tama�o de la lista y del conjunto no son iguales, no hay duplicados
		assertTrue(keyCodesSet.size() == keyCodes.size());
	}
	
	/**
	 * Comprobaci�n de que el campo nombre accesible para botones, radiobuttons combos y checks
	 * no est� vac�o. 
	 */
	@Test
	public void testNotEmptyAccessibleName() {
		logger.info("testNotEmptyAccessibleName");
		//Instancia del panel que se va a analizar
		PanelFormatos panelFormatos = new PanelFormatos();
		
		//Componentes del wizard
		Component[] components = panelFormatos.getComponents();
		
		//Se recorren
		for (int i = 0; i< components.length; i++) {
			Component componentWizard = components[i];
			//Se trata el panel principal del wizard
			if (componentWizard instanceof JRootPane) {
				//Se obtienen los componentes del panel principal
				Component[] componentsRootPane = ((JRootPane)componentWizard).getComponents();
				//Se recorren
				for (int j = 0; j< componentsRootPane.length; j++) {
					//Se obtienen un elemento
					Component componentRootPane = componentsRootPane[j];
					//Si es un panel se trata
					if (componentRootPane instanceof JPanel) {
						//Se llama al m�todo que comprueba que el nombre no sea vac�o
						assertTrue(checkAccessibleName((JPanel) componentRootPane));
						
					} else if (componentRootPane instanceof JLayeredPane) { //Si es un layeredPane se obtienen sus componentes
						Component[] componentsLayeredPane = ((JLayeredPane) componentRootPane).getComponents();
						//Se recorren
						for (int z = 0; z< componentsLayeredPane.length; z++) {
							//Se obtienen un elemento
							Component componentLayeredPane = componentsLayeredPane[z];
							//Si es instancia de JPanel se trata
							if (componentLayeredPane instanceof JPanel) {
								//Se llama al m�todo que comprueba que el nombre no sea vac�o
								assertTrue(checkAccessibleName((JPanel) componentLayeredPane));
							}
						}
					}
				}
			}
		}
		
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
	 * M�todo que obtiene una lista de c�digos de atajos a los componentes (Etiqueta, Bot�n, Checkbox, Radiobutton) de un panel.
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
				} else if (component instanceof JButton) {
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
				} else if (component instanceof JRadioButton) { //Se comprueba si es un boton de radio
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
