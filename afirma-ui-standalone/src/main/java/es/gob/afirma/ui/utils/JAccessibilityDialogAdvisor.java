package es.gob.afirma.ui.utils;

import java.awt.Component;

import javax.swing.JDialog;

/**
 * Clase para generar un JFrame con la posibilidad de redimension.
 * Extiende JFrame.
 * @author inteco
 *
 */
public abstract class JAccessibilityDialogAdvisor extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private ResizingAdaptor resizingAdaptor;
	
	public JAccessibilityDialogAdvisor(){
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
	public static JAccessibilityDialogAdvisor getJAccessibilityDialogAdvisor(Component component)
	{
		JAccessibilityDialogAdvisor  resultingJAccessibilityDialogAdvisor = null;
		while (component != null && resultingJAccessibilityDialogAdvisor == null)
		{
	        if (component instanceof JAccessibilityDialogAdvisor){
	        	resultingJAccessibilityDialogAdvisor = (JAccessibilityDialogAdvisor)component;
	        }
	        else{
	        	component = component.getParent();
	        }
		 }
		 return resultingJAccessibilityDialogAdvisor;
	 }
}