package es.gob.afirma.ui.utils;
import javax.swing.JFrame;

/**
 * Clase para generar un JFrame con la posibilidad de redimension.
 * Extiende JFrame.
 * @author inteco
 *
 */
public abstract class JAccessibilityFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private ResizingAdaptor resizingAdaptor;
	
	public JAccessibilityFrame(){
		super();
		this.resizingAdaptor = new ResizingAdaptor(this, null, null);
		this.addComponentListener(this.resizingAdaptor);
	}
	
	/**
	 * Relaci�n m�nima que se aplica para la redimensi�n de los componentes.
	 * Cuanto menor es este n�mero menor es la redimensi�n aplicada.
	 * @return int Relaci�n m�nima
	 */
	public abstract int getMinimumRelation();
	
	protected final void callResize(){
		this.resizingAdaptor.adjustWindowFonts();
	}
}