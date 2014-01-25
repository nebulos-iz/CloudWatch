package edu.brown.cs.ajlin.hackatbrown2014;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

public class Cloudwatcher extends JFrame {
	
	private Image inputImage_;
	private Image outputImage_;
	
	private JPanel panel_;
	
	private JPanel upperPanel_;
	private JPanel lowerPanel_;
	
	private ImageIcon inputIcon_;
	private ImageIcon outputIcon_;
	private JLabel inputLabel_;
	private JLabel outputLabel_;
	private JLabel inputTextLabel_;
	private JLabel outputTextLabel_;
	private JLabel middleLabel_;
	
	private JButton convertButton_;
	
	private JMenuBar menuBar_;
	private JMenu menuFile_;
	private JMenuItem menuFileOpenImage_;
	private JMenuItem menuFileExit_;
	
	private JFileChooser openImageChooser_;
	
	public Cloudwatcher() { }
	
	public void begin() {
		try {
			inputImage_ = outputImage_ = ImageIO.read(new File("assets/testimage.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Set JFrame properties
		this.setTitle("Cloudwatcher 2014");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Set up panels
		panel_ = new JPanel(new BorderLayout());
		GridBagLayout gbl = new GridBagLayout();
		upperPanel_ = new JPanel(gbl);
		lowerPanel_ = new JPanel();
		
		// Set up upper panel
		inputIcon_ = new ImageIcon();
		outputIcon_ = new ImageIcon();
		inputLabel_ = new JLabel(inputIcon_);
		outputLabel_ = new JLabel(outputIcon_);
		inputTextLabel_ = new JLabel("Input image", JLabel.CENTER);
		outputTextLabel_ = new JLabel("Output image", JLabel.CENTER);
		middleLabel_ = new JLabel("=>", JLabel.CENTER);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(4, 4, 4, 4);
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.gridy = 1;
		gbc.weightx = 0.5;
		gbl.setConstraints(inputLabel_, gbc);
		upperPanel_.add(inputLabel_);
		gbc.gridx = 2;
		gbl.setConstraints(outputLabel_, gbc);
		upperPanel_.add(outputLabel_);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.0;
		gbl.setConstraints(inputTextLabel_, gbc);
		upperPanel_.add(inputTextLabel_);
		gbc.gridx = 2;
		gbl.setConstraints(outputTextLabel_, gbc);
		upperPanel_.add(outputTextLabel_);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbl.setConstraints(middleLabel_, gbc);
		upperPanel_.add(middleLabel_);
		
		// Set up lower panel
		convertButton_ = new JButton("Imagine");
		convertButton_.setMnemonic(KeyEvent.VK_I);
		convertButton_.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			Image i = convert();
				outputIcon_.setImage(i);
				// Force the label to resize, repaint it, and pack it so it resizes correctly
				outputLabel_.setSize(outputLabel_.getPreferredSize());
				Cloudwatcher.this.repaint();
				Cloudwatcher.this.setMinimumSize(Cloudwatcher.this.getPreferredSize());
				Cloudwatcher.this.pack();
			}
		});
		
		lowerPanel_.add(convertButton_);
		
		// Put the panels together
		panel_.add(upperPanel_, BorderLayout.NORTH);
		panel_.add(lowerPanel_, BorderLayout.SOUTH);
		this.add(panel_);
		
		
		// Set up menu bar
		menuBar_ = new JMenuBar();
		
		menuFile_ = new JMenu("File");
		menuFile_.setMnemonic(KeyEvent.VK_F);
		
		menuFileOpenImage_ = new JMenuItem("Open...");
		menuFileOpenImage_.setMnemonic(KeyEvent.VK_O);
		menuFileOpenImage_.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) { fileOpen(); }
		});
		
		menuFileExit_ = new JMenuItem("Exit");
		menuFileExit_.setMnemonic(KeyEvent.VK_E);
		menuFileExit_.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) { System.exit(0); }
		});
		
		menuFile_.add(menuFileOpenImage_);
		menuFile_.add(new JSeparator());
		menuFile_.add(menuFileExit_);
		menuBar_.add(menuFile_);
		this.setJMenuBar(menuBar_);
		
		
		// Set up the file chooser
		openImageChooser_ = new JFileChooser("D:/Dropbox/");
		openImageChooser_.setName("Open...");
		
		
		// Set up keyboard shortcuts
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_PRESSED && 
					e.getKeyCode() == KeyEvent.VK_O &&
					e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK) {
					fileOpen();
					return true;
				}
				return false;
			}
		});
		
		
		// Put some placeholder images in
		inputIcon_.setImage(outputImage_);
		outputIcon_.setImage(outputImage_);
		
		this.pack();
		this.setMinimumSize(this.getPreferredSize());
		this.setVisible(true);
	}
	
	private void fileOpen() {
		if (openImageChooser_.showDialog(Cloudwatcher.this, "Choose") == JFileChooser.APPROVE_OPTION) {
			File file = openImageChooser_.getSelectedFile();
			try {
				Image image = ImageIO.read(file);
				if (image == null)
					throw new IOException("Not a valid image file (image was null)");
				outputImage_ = image;
			} catch (IOException e) {
				System.out.println("error:" + e.toString());
			}
		}
	}
	
	private Image convert() {
		return outputImage_;
	}

}
