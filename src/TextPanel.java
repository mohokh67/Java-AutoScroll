import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Text panel class This class is responsible for text area, start & stop button
 * and also do the scrolling with thread
 * 
 * @author moho
 *
 */
public class TextPanel extends JPanel implements ActionListener {
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JButton btnStart;
	private JButton btnStop;
	private StringListener btnListener;

	private boolean stop = false;
	private int currentPosition;

	private int getMaxSize() {
		int maxSize = textArea.getDocument().getLength();
		return maxSize;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	/**
	 * Page layout Constructor
	 */
	public TextPanel() {
		textArea = new JTextArea();
		scrollPane = new JScrollPane();

		btnStart = new JButton("Start");
		btnStart.addActionListener(this);

		btnStop = new JButton("Stop");
		btnStop.addActionListener(this);

		setLayout(new BorderLayout());
		textArea.setEditable(false); // Stop editing text in text area
		textArea.setLineWrap(true); // Wrap long lines

		add(new JScrollPane(textArea), BorderLayout.CENTER);

		add(btnStart, BorderLayout.EAST);
		add(btnStop, BorderLayout.WEST);
	}

	/**
	 * Scroll method Scroll the text area with start and stop button
	 */
	public synchronized void scroll() {
		while (currentPosition < getMaxSize() - 2 && !stop) {
			currentPosition += 1;
			textArea.setCaretPosition(currentPosition);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * This method listens to the start and stop button
	 */
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();

		if (clicked == btnStart) {
			currentPosition = 0;
			stop = false;
			if (btnListener != null) {
				Thread t1 = new Thread(new Runnable() {

					public void run() {
						scroll();
					}
				});

				t1.start();
			}
		} else if (clicked == btnStop) {
			if (btnListener != null) {
				stop = true;
			}
		}
	}

	public void setStringListener(StringListener listener) {
		this.btnListener = listener;
	}

	/**
	 * This function is used to append text to the text area
	 * 
	 * @param text
	 *            String: is an input text for text area
	 */
	public void appendText(String text) {
		textArea.append(text);
		textArea.setCaretPosition(0);
	}

	/**
	 * Clear the TextArea This method is used when user try to load another file
	 */
	public void clear() {
		textArea.setText(null);
	}
}
