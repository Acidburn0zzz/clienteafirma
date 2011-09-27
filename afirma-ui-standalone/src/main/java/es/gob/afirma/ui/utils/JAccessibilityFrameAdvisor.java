package es.gob.afirma.ui.utils;

import java.awt.Component;

import javax.swing.JFrame;

/**
 * Clase para generar un JFrame con la posibilidad de redimension.
 * Extiende JFrame.
 * @author inteco
 *
 */
public abstract class JAccessibilityFrameAdvisor extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private ResizingAdaptor resizingAdaptor;
	
	public JAccessibilityFrameAdvisor(){
		super();
		this.resizingAdaptor = new ResizingAdaptor(null, null, null,this);
		this.addComponentListener(this.resizingAdaptor);
	}
	
	/**
	 * Relaci�n m�nima que se aplica para la redimensi�n de los componentes.
	 * Cuanto menor es este n�mero menor es la redimensi�n aplicada.
	 * @return int Relaci�n m�nima
	 */
	public abstract int getMinimumRelation();
	
	public final void callResize(){
		this.resizingAdaptor.adjustWindowFonts();
	}
	
	/**
	 * Busca el JAccessibilityFrame padre de un componente.
	 * @param component El componente.
	 * @return El JAccessibilityFrame buscado.
	 */
	public static JAccessibilityFrame getJAccessibilityFrame(Component component)
	{
		JAccessibilityFrame  resultingJAccessibilityFrame = null;
		while (component != null && resultingJAccessibilityFrame == null)
		{
	        if (component instanceof JAccessibilityFrame){
	        	resultingJAccessibilityFrame = (JAccessibilityFrame)component;
	        }
	        else{
	        	component = component.getParent();
	        }
		 }
		 return resultingJAccessibilityFrame;
	 }
}