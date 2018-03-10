package de.uulm.cyv17.tool;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class implements a graphical assembler with
 * which a given architecture can be programmed.
 * 
 * @author Max Brand (max.brand@uni-ulm.de)
 *
 */
public class Assembler implements ActionListener {

	private Map<String, String> replacement;
	private Map<String, Integer> cmdLength;
	private Map<String, Integer> labels;
	private JTextPane asmTextBox;
	private JTextPane hexTextBox;
	private JButton openAsmButton;
	private JButton saveAsmButton;
	private JButton saveHexButton;
	private JButton assembleButton;
	private JFrame window;
	
	/**
	 * The constructor of the assembler program. It
	 * builds and initializes the graphical interface.
	 */
	public void initWindow() {
		asmTextBox = new JTextPane();
		asmTextBox.setFont(Font.getFont("Courier New"));
		hexTextBox = new JTextPane();
		hexTextBox.setFont(Font.getFont("Courier New"));
		JScrollPane asmTextBoxScrollPane = new JScrollPane(asmTextBox);
		JScrollPane hexTextBoxScrollPane = new JScrollPane(hexTextBox);
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel centerPanel = new JPanel(new GridLayout(1, 2, 2, 2));
		JPanel upperPanel = new JPanel(new GridLayout(1, 2, 2, 2));
		JPanel lowerPanel = new JPanel(new GridLayout(1, 4, 2, 2));
		openAsmButton = new JButton("Open .asm");
		openAsmButton.addActionListener(this);
		saveAsmButton = new JButton("Save .asm");
		saveAsmButton.addActionListener(this);
		saveHexButton = new JButton("Save .hex");
		saveHexButton.addActionListener(this);
		assembleButton = new JButton("Assemble");
		assembleButton.addActionListener(this);
		upperPanel.add(new JLabel("Assembler-Code"));
		upperPanel.add(new JLabel("Hex-Code"));
		centerPanel.add(asmTextBoxScrollPane);
		centerPanel.add(hexTextBoxScrollPane);
		lowerPanel.add(openAsmButton);
		lowerPanel.add(saveAsmButton);
		lowerPanel.add(saveHexButton);
		lowerPanel.add(assembleButton);
		mainPanel.add(upperPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		window = new JFrame("Assembler");
		window.setSize(800, 600);
		window.getContentPane().add(mainPanel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	/**
	 * Initializes the replacement function of the assembler.
	 * The method generates the labels for registers (Rn) and
	 * constants (#n). Also it reads the mnemonics out of the
	 * mnemonics file and prepares them.
	 * 
	 * @param mnemonicFile the path of the mnemonics file
	 */
	public void initReplacement(String mnemonicFile) {
		replacement = new HashMap<String, String>();
		cmdLength = new HashMap<String, Integer>();
		File f = new File(mnemonicFile);
		if (Files.exists(f.toPath(), LinkOption.NOFOLLOW_LINKS)) {
			try {
				List<String> lines = Files.readAllLines(f.toPath());
				for (int i = 0; i < lines.size(); i++) {
					String parts[] = lines.get(i).split(";");
					replacement.put(parts[0], parts[1]);
					cmdLength.put(parts[0], Integer.parseInt(parts[2]));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			hexTextBox.setText(mnemonicFile + "not found");
			assembleButton.setEnabled(false);
		}
		for (int i = 0; i < Math.pow(2, 16); i++) {
			replacement.put("R" + i, Integer.toHexString(i));
			replacement.put("#" + i, Integer.toHexString(i));
		}
	}
	
	/**
	 * The button event for the open assembler file dialog.
	 */
	public void OpenAsmButtonEvent() {
		JFileChooser openDialog = new JFileChooser();
		openDialog.setCurrentDirectory(new File(System.getProperty("user.dir")));
		openDialog.setFileFilter(new FileNameExtensionFilter("Assembler File (.asm)", "asm"));
		if (openDialog.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
			File f = openDialog.getSelectedFile();
			try {
				List<String> lines = Files.readAllLines(f.toPath());
				String asm = "";
				for (int i = 0; i < lines.size(); i++) {
					asm += lines.get(i) + System.lineSeparator();
				}
				asmTextBox.setText(asm);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * The button event for the save assembler file dialog.
	 */
	public void SaveAsmButtonEvent() {
		JFileChooser saveDialog = new JFileChooser();
		saveDialog.setCurrentDirectory(new File(System.getProperty("user.dir")));
		saveDialog.setFileFilter(new FileNameExtensionFilter("Assembler File (.asm)", "asm"));
		if (saveDialog.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
			File f = saveDialog.getSelectedFile();
			try {
				String fn = f.getAbsolutePath();
				if (!fn.endsWith(".asm")) {
					fn += ".asm";
				}
				PrintWriter writer = new PrintWriter(fn, "UTF-8");
				writer.println(asmTextBox.getText());
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}		
	}
	
	/**
	 * The button event for the save hex file dialog.
	 */
	public void SaveHexButtonEvent() {
		JFileChooser saveDialog = new JFileChooser();
		FileNameExtensionFilter logisimFileFilter = new FileNameExtensionFilter("Logisim Format (.hex)", "hex");
		FileNameExtensionFilter intelFileFilter = new FileNameExtensionFilter("Intel Format (.hex)", "hex");
		saveDialog.setCurrentDirectory(new File(System.getProperty("user.dir")));
		saveDialog.addChoosableFileFilter(intelFileFilter);
		saveDialog.addChoosableFileFilter(logisimFileFilter);
		saveDialog.setFileFilter(intelFileFilter);
		if (saveDialog.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
			File f = saveDialog.getSelectedFile();
			try {
				String fn = f.getAbsolutePath();
				if (!fn.endsWith(".hex")) {
					fn += ".hex";
				}
				if (saveDialog.getFileFilter().equals(intelFileFilter)) {
					String hexString = hexTextBox.getText().replaceAll("\\s", "");
					String hexCodes[] = new String[hexString.length() / 2];
					for (int i = 0; i < hexString.length() - 1; i += 2) {
						hexCodes[i / 2] = "" + hexString.charAt(i) + hexString.charAt(i + 1);
					}
					HexGenerator.WriteIntelHexFile(fn, hexCodes, 1);
				} else if (saveDialog.getFileFilter().equals(logisimFileFilter)) {
					PrintWriter writer = new PrintWriter(fn, "UTF-8");
					writer.println("v2.0 raw");
					writer.println(hexTextBox.getText());
					writer.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * The button event to start the assembly of the assembler code
	 * to hex code.
	 */
	public void AssembleButtonEvent() {
		String asm[] = asmTextBox.getText().trim().split("[ \t(\r\n)]+");
		Vector<String> commands = new Vector<String>();
		labels = new HashMap<String, Integer>();
		int bytes = 0;
		for (int i = 0; i < asm.length; i++) {
			if (asm[i].endsWith(":")) {
				labels.put(asm[i].substring(0, asm[i].length() - 1), bytes);
			} else if (asm[i].startsWith("@")) {
				int startByte = Integer.parseInt(asm[i].substring(1), 16);
				for (;bytes < startByte;bytes++) {
					commands.add("00");
				}
			} else {
				commands.add(asm[i]);
				bytes++;
			}
		}
		for (int i = 0; i < commands.size(); i++) {
			if (cmdLength.containsKey(commands.get(i))) {
				for (int j = 1; j <= cmdLength.get(commands.get(i)); j++) {
					if ((i + j) >= commands.size() || IsCommand(commands.get(i + j))) {
						hexTextBox.setText("Error at byte " + (i + j) + System.lineSeparator() +
								"Too few bytes after command");
						return;
					}
				}
			}
		}
		for (int i = 0; i < commands.size(); i++) {
			if (replacement.containsKey(commands.get(i))) {
				commands.set(i, replacement.get(commands.get(i)));
			}
		}
		for (int i = 0; i < commands.size(); i++) {
			if (labels.containsKey(commands.get(i))) {
				commands.set(i, Integer.toHexString(labels.get(commands.get(i))));
			}
		}
		String hexCode = "";
		for (int i = 0; i < commands.size(); i++) {
			if (commands.get(i).length() == 1) {
				commands.set(i, "0" + commands.get(i));
			}
			if ((i + 1) % 8 == 0) {
				hexCode += commands.get(i) + System.lineSeparator();
			} else {
				hexCode += commands.get(i) + "  ";				
			}
		}
		hexTextBox.setText(hexCode);
	}

	/**
	 * Action handler for the buttons.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(saveAsmButton)) {
			SaveAsmButtonEvent();
		} else if (e.getSource().equals(openAsmButton)) {
			OpenAsmButtonEvent();
		} else if (e.getSource().equals(saveHexButton)) {
			SaveHexButtonEvent();
		} else if (e.getSource().equals(assembleButton)) {
			AssembleButtonEvent();
		}
	}
	
	/**
	 * Function to test if the given command is a real command or not.
	 * @param cmd
	 * @return true if it is, false otherwise
	 */
	public boolean IsCommand(String cmd) {
		return replacement.containsKey(cmd) && !cmd.startsWith("R") && !cmd.startsWith("#");
	}
}