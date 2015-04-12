import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Main frame class
 * It's only load other classes and panels. 
 * @author moho
 *
 */
public class MainFrame extends JFrame {

	private TopToolbar topToolbar;
	private TextPanel textPanel;
	
	public MainFrame() {
		super("808956");
		
		setLayout(new BorderLayout());
		
		topToolbar = new TopToolbar();
		textPanel = new TextPanel();
		
		textPanel.setStringListener(new StringListener() {
			public void textEmitter(String text) {
				textPanel.appendText(text);				
			}
			public void clear(){
				textPanel.clear();
			}
		});
		
		
		topToolbar.setStringListener(new StringListener() {
			public void textEmitter(String text) {
				textPanel.appendText(text);				
			}
			public void clear(){
				textPanel.clear();
			}
		});
		
		
		add(topToolbar, BorderLayout.NORTH);
		add(textPanel, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminate the app
		setMinimumSize(new Dimension(800, 600));
		setSize(800, 600);
		setVisible(true);
	}
	
	
}
