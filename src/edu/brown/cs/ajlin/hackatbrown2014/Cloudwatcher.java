package edu.brown.cs.ajlin.hackatbrown2014;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;

public class Cloudwatcher extends JFrame {
	
	private static final int STAGE_NO_INPUT = 0;
	private static final int STAGE_NO_OUTPUT = 1;
	private static final int STAGE_DONE = 2;
	
	private MatlabInterface matlab_;

	private int state_ = STAGE_NO_INPUT;
	private String imagePath_;
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
		this.setResizable(false);
		
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
		inputTextLabel_ = new JLabel("Clouds", JLabel.CENTER);
		outputTextLabel_ = new JLabel("Looks like...", JLabel.CENTER);
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
		convertButton_ = new JButton();
		convertButton_.setMnemonic(KeyEvent.VK_I);
		convertButton_.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Image i = Cloudwatcher.this.convert();
				Cloudwatcher.this.outputImage_ = i;
				Cloudwatcher.this.state_ = Cloudwatcher.STAGE_DONE;
				Cloudwatcher.this.redrawAndPackProperly();
			}
		});
		
		lowerPanel_.add(convertButton_);
		
		// Put the panels together
		panel_.add(upperPanel_, BorderLayout.CENTER);
		panel_.add(lowerPanel_, BorderLayout.SOUTH);
		this.add(panel_);
		
		
		// Set up menu bar
		menuBar_ = new JMenuBar();
		
		menuFile_ = new JMenu("File");
		menuFile_.setMnemonic(KeyEvent.VK_F);
		
		menuFileOpenImage_ = new JMenuItem("Open...");
		menuFileOpenImage_.setMnemonic(KeyEvent.VK_O);
		menuFileOpenImage_.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuFileOpenImage_.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				Image i = Cloudwatcher.this.openImageFile();
				if (i != null) {
					Cloudwatcher.this.inputImage_ = i;
					Cloudwatcher.this.state_ = Cloudwatcher.STAGE_NO_OUTPUT;
					redrawAndPackProperly();
				}
			}
		});
		
		menuFileExit_ = new JMenuItem("Exit");
		menuFileExit_.setMnemonic(KeyEvent.VK_E);
		menuFileExit_.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		menuFileExit_.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) { System.exit(0); }
		});
		
		menuFile_.add(menuFileOpenImage_);
		menuFile_.add(new JSeparator());
		menuFile_.add(menuFileExit_);
		menuBar_.add(menuFile_);
		this.setJMenuBar(menuBar_);
		
		
		// Set up the file chooser
		openImageChooser_ = new JFileChooser("cloudImages");
		openImageChooser_.setName("Open...");
		openImageChooser_.setFileFilter(new FileFilter() {
			@Override public String getDescription() { return "Images"; }

			@Override
			public boolean accept(File file) {
				if (file.isDirectory())
					return true;
				
				String name = file.getName();
				int extPoint = name.indexOf('.');
				if (extPoint >= 0 && extPoint < name.length() - 1) {
					String ext = name.substring(extPoint+1).toLowerCase();
					return ext.equals("bmp") ||
						   ext.equals("gif") ||
						   ext.equals("jpeg") ||
						   ext.equals("jpg")||
						   ext.equals("bmp");
				} else
					return false;
			}
		});
		
		
		// Run matlab
		try {
			matlab_ = new MatlabInterface();
		} catch (MatlabConnectionException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		
		
		// GO!
		redrawAndPackProperly();
		this.setVisible(true);
	}
	
	private void redrawAndPackProperly() {
		Dimension dim1;
		Dimension dim2;
		dim1 = dim2 = new Dimension(160, 100);

		switch (state_) {
			case STAGE_NO_INPUT:
				inputLabel_.setIcon(null);
				inputLabel_.setText("Select an image");
				outputLabel_.setIcon(null);
				outputLabel_.setText("Waiting for input");
				convertButton_.setText("Waiting for input");
				convertButton_.setEnabled(false);
				break;
				
			case STAGE_NO_OUTPUT:
				inputIcon_.setImage(inputImage_);
				inputLabel_.setText("");
				inputLabel_.setIcon(inputIcon_);
				outputLabel_.setIcon(null);
				outputLabel_.setText("Prepare to IMAGINE");
				dim1 = inputLabel_.getMinimumSize();
				convertButton_.setText("Imagine");
				convertButton_.setEnabled(true);
				break;
				
			case STAGE_DONE:
				inputIcon_.setImage(inputImage_);
				inputLabel_.setText("");
				inputLabel_.setIcon(inputIcon_);
				outputIcon_.setImage(outputImage_);
				outputLabel_.setText("");
				outputLabel_.setIcon(outputIcon_);
				dim1 = inputLabel_.getMinimumSize();
				dim2 = outputLabel_.getMinimumSize();
				convertButton_.setText("Done!");
				convertButton_.setEnabled(false);
				break;

			default:
				break;
		}

		Dimension maxDim = new Dimension(Math.max(dim1.width, dim2.width), Math.max(dim1.height, dim2.height));
		inputLabel_.setPreferredSize(maxDim);
		outputLabel_.setPreferredSize(maxDim);

		Cloudwatcher.this.repaint();
		Cloudwatcher.this.setMinimumSize(Cloudwatcher.this.getPreferredSize());
		Cloudwatcher.this.pack();
	}
	
	private Image openImageFile() {
		if (openImageChooser_.showDialog(Cloudwatcher.this, "Open") == JFileChooser.APPROVE_OPTION) {
			File file = openImageChooser_.getSelectedFile();
			try {
				Image image = ImageIO.read(file);
				if (image == null)
					throw new IOException("The selected file was not a valid image file.");
				imagePath_ = file.getAbsolutePath();
				return image;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return null;
	}
	
	private Image convert() {
		try {
			String path = matlab_.cloudwatch(imagePath_);
			Image image = ImageIO.read(new File(path));
			return image;
		} catch (MatlabInvocationException | IOException e) {
			e.printStackTrace();
		}
		return outputImage_;
	}

}
