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
		this.resizingAdaptor = new ResizingAdaptor(null, null, null,this,null);
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
	 * Busca el JAccessibilityFrameAdvisor padre de un componente.
	 * @param component El componente.
	 * @return El JAccessibilityFrameAdvisor buscado.
	 */
	public static JAccessibilityFrameAdvisor getJAccessibilityFrameAdvisor(Component component)
	{
		JAccessibilityFrameAdvisor  resultingJAccessibilityFrameAdvisor = null;
		while (component != null && resultingJAccessibilityFrameAdvisor == null)
		{
	        if (component instanceof JAccessibilityFrameAdvisor){
	        	resultingJAccessibilityFrameAdvisor = (JAccessibilityFrameAdvisor)component;
	        }
	        else{
	        	component = component.getParent();
	        }
		 }
		 return resultingJAccessibilityFrameAdvisor;
	 }
}