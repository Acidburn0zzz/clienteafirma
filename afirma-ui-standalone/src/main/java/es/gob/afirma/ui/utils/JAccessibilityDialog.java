package es.gob.afirma.ui.utils;


import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JDialog;

/**
 * Clase para generar un JDialog con la posibilidad de redimension.
 * Extiende JDialog
 * @author inteco
 *
 */
public abstract class JAccessibilityDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	protected static int actualPositionX = -1;
	
	protected static int actualPositionY = -1;
	
	protected static int actualWidth = -1;
	
	protected static int actualHeight = -1;
	
	public JAccessibilityDialog(){
		super();
		ResizingAdaptor adaptador = new ResizingAdaptor(null,this,null,null,null);
		this.addComponentListener(adaptador);
		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent e)
		    {
		    	resized(e);
		    }
		    public void componentMoved(ComponentEvent e)
		    {
		    	resized(e);
		    }
		});

	}
	
	/**
	 * Relación mínima que se aplica para la redimensión de los componentes.
	 * Cuanto menor es este número menor es la redimensión aplicada.
	 * @return int Relación mínima
	 */
	public abstract int getMinimumRelation();
	
	/**
	 * Obtiene un componente de un contenedor a traves de su nombre
	 * @param name Nombre del componente a buscar
	 * @param container Contenedor donde se encuentra el componente a buscar
	 * @return
	 */
	private Component getComponentByName(String name, Container container){
		if(name.equals(container.getName())){
			return container;
		}
		else {
			Component[] componentes = container.getComponents();
			for(int i = 0; i < componentes.length; i++){
				if(componentes[i] instanceof Container){
					Component res = getComponentByName(name, (Container) componentes[i]);
					if(res != null){
						return res;
					}
				}
				else{
					if(componentes[i].getName().equals(name)){
						return componentes[i];
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Evento de redimensionado. Comprueba el tamaÃ±o de la ventana para habilitar o deshabilitar el boton
	 *  de Maximizar ventana. Tambien almacena el tamaño y posicion de la ventana para su restauracion.
	 */
	public void resized(ComponentEvent e) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    Dimension fullScreen = new Dimension((int)screenSize.getWidth(), (int)screenSize.getHeight()-35);
	    Dimension actualSize = getJAccessibilityDialog(this).getSize();
	    Component boton = getComponentByName("maximizar", getJAccessibilityDialog(this));
	    Component botonRestaurar = getComponentByName("restaurar", getJAccessibilityDialog(this));
	    if(boton != null){
	    	if (actualSize.equals(fullScreen)){
				boton.setEnabled(false);
				if (botonRestaurar != null) {
	    			//Si la ventana está maximizada, el botón de restaurar debe estar visible
	    			botonRestaurar.setEnabled(true);
	    		}
		    } else {
		    	boton.setEnabled(true);
		    	if (botonRestaurar != null) {
			    	//Se comprueba si la ventana está restaurada
			    	if ((this.getX() == actualPositionX) && (this.getY() == actualPositionY) 
			    			&& (this.getWidth() == actualWidth) && (this.getHeight() == actualHeight)) {
			    		botonRestaurar.setEnabled(false); //Se deshabilita
			    	} else {
			    		botonRestaurar.setEnabled(true); //Se habilita
			    	}
		    	}
		    }
	    }
	    // Se almacenan los valores de la posicion y el tamaño para la restauracion
	    //si no se han guardado ya previamente
		if (this.getWidth()!=(int)screenSize.getWidth() && this.getHeight()!=(int)screenSize.getHeight()-35 && (actualWidth == -1)){
			actualPositionX = this.getX();
			actualPositionY = this.getY();
			actualWidth = this.getWidth();
			actualHeight = this.getHeight();
		}
	}
	
	/**
	 * Busca el JAccessibilityDialog padre de un componente.
	 * @param component El componente.
	 * @return El JAccessibilityDialog buscado.
	 */
	public static JAccessibilityDialog getJAccessibilityDialog(Component component)
	{
		JAccessibilityDialog  resultingJAccessibilityDialog = null;
		while (component != null && resultingJAccessibilityDialog == null)
		{
	        if (component instanceof JAccessibilityDialog){
	        	resultingJAccessibilityDialog = (JAccessibilityDialog)component;
	        }
	        else{
	        	component = component.getParent();
	        }
		 }
		 return resultingJAccessibilityDialog;
	 }
}
