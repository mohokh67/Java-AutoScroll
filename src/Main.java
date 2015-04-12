import javax.swing.SwingUtilities;

/**
 * Main class to run. This class only load the main frame
 * @author moho
 *
 */
public class Main {

	public static void main(String[] args) {
		//stop error in java 4
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new MainFrame();
			}
		});
		
		
		}
}
