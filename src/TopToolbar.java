import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author moho Top tool bar for select and load text file
 *
 */
public class TopToolbar extends JPanel implements ActionListener {
	private JLabel fileLabel;
	private JTextField fileTextBox;
	private JButton btnLoad;
	private JButton btnOpen;
	private JFileChooser fileChooser;

	final static Charset ENCODING = StandardCharsets.UTF_8;

	private MainFrame mainFrame;
	private StringListener btnListener;

	/**
	 * Constructor for top tool bar. It contains two button: Load: it will load
	 * the content of file from input text box Open: Open a file chooser and
	 * then show the content of text file
	 */
	public TopToolbar() {
		fileLabel = new JLabel("File name: ");
		fileTextBox = new JTextField(37);
		// fileTextBox.setText("1.txt");
		btnLoad = new JButton("Load");
		btnOpen = new JButton("Open");
		fileChooser = new JFileChooser();

		btnLoad.addActionListener(this);
		btnOpen.addActionListener(this);

		btnOpen.setMnemonic(KeyEvent.VK_O);

		/* GridBagLayout */
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		/* First row */
		gbc.weightx = 0.5;
		gbc.weighty = 0.1;

		gbc.gridx = 0;
		gbc.gridy = 0;

		gbc.fill = GridBagConstraints.NONE; // needs to be write only once
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(5, 0, 5, 5); // Margin TOP - LEFT - BUTTOM -
												// RIGHT
		add(fileLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;

		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(5, 5, 5, 0); // Margin TOP - LEFT - BUTTOM -
												// RIGHT
		add(fileTextBox, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;

		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(5, 5, 5, 0); // Margin TOP - LEFT - BUTTOM -
												// RIGHT
		add(btnLoad, gbc);

		gbc.gridx = 3;
		gbc.gridy = 0;

		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(5, 0, 5, 0); // Margin TOP - LEFT - BUTTOM -
												// RIGHT
		add(btnOpen, gbc);

	}

	/**
	 * listen to the button
	 * 
	 * @param listener
	 *            StringListener
	 */
	public void setStringListener(StringListener listener) {
		this.btnListener = listener;
	}

	/**
	 * Read content of the file with Java core and put it in the text area
	 * 
	 * @param fileName
	 *            File
	 * @param btnListener
	 *            StringListener
	 */

	public void readFile(File fileName, StringListener btnListener) {
		Path path = Paths.get(fileName.toString());
		StringBuilder content = new StringBuilder("");

		try (Scanner scanner = new Scanner(path, ENCODING.name())) {
			while (scanner.hasNextLine()) {
				content.append(String.valueOf(scanner.nextLine()));
				content.append("\n");
			}
		} catch (IOException e) {
			btnListener.textEmitter(e.getMessage());
		}

		btnListener.textEmitter(content.toString());

	}

	// FileNotFoundException
	/**
	 * Action listener for load and open button in the top tool bar This
	 * function is only used by setStringListener
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();

		if (clicked == btnLoad) {
			if (btnListener != null) {
				btnListener.clear();

				String fileName = this.fileTextBox.getText();
				if (fileName.length() < 3) {
					btnListener.textEmitter("File name is not valid");
				} else {
					File inputFile = new File(fileName);
					// readFile(inputFile, btnListener);

					// check if file exist
					if (inputFile.exists() && inputFile.isFile()
							&& !inputFile.isDirectory()) {
						readFile(inputFile, btnListener);
					} else {
						btnListener.textEmitter("File does not exist!!!");
					}
				}
			}

		} else if (btnOpen != null) {
			btnListener.clear();
			if (fileChooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
				fileTextBox.setText((fileChooser.getSelectedFile().toString()));
				try {
					// File fileName = fileChooser.getSelectedFile();

					Thread tOpen = new Thread(new Runnable() {

						@Override
						public void run() {
							File fileName = fileChooser.getSelectedFile();
							readFile(fileName, btnListener);
						}
					});

					tOpen.start();
					/*
					 * try { t1.join(); } catch (InterruptedException e2) {
					 * btnListener.textEmitter(e2.getMessage()); }
					 */

					// readFile(fileName, btnListener);
				} catch (Exception e1) {
					btnListener.textEmitter(e1.getMessage());
				}
			}

			else {
				btnListener.textEmitter("You did not choose any file...");
			}
		} // end of else if

	} // end of method

}
