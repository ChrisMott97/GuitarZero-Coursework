package org.gsep.manager;
import org.gsep.midi.guitarMIDI;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;
import java.util.ArrayList;
import java.awt.event.*;

/*
 * StoreManagerFrame
 * 
 * @author Humzah Malik
 * @version 1.0
 * 
 */
public class StoreManagerFrame {

	private JFrame frame;
	private guitarMIDI gM;
	///Declaring fields holding file paths as a String and files as a File object
	JTextField textField_1, textField_2, textField_3 = null;
	File f1, f2, f3;
	String f1_path, f2_path, f3_path = null;
	
	//Initialise ArrayList to hold files and Array to hold file paths
	ArrayList<File> files = new ArrayList<File>();
	String[] filePaths;
	
	//Declare booleans used to validate file entry
	static boolean empty;
	static boolean invalid;
	
	/**
	 * Method calling create(), which in turn invokes the JFrame.
	 */
	//Launch application
	public static void create() {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StoreManagerFrame window = new StoreManagerFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Method that invokes the initializing of the JFrame.
	 */
	public StoreManagerFrame() {
		this.gM = new guitarMIDI();
		initialize();
	}
	
	/**
	 * Method containing information on the structure of the JFrame and its functionality. Method also dynamically calls Client class. 
	 */
	private void initialize() {
		//Setting bounds of JFrame
		frame = new JFrame("Store Manager");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Creating text fields to hold file paths
		textField_1 = new JTextField();
		textField_1.setBounds(96, 41, 219, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(96, 97, 215, 26);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(96, 148, 219, 26);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		//Create first button to browse for .txt file
		JButton nameButton = new JButton("Browse");
		nameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Open file chooser
				JFileChooser jf1 = new JFileChooser();
				//File can only be in text format
				FileNameExtensionFilter filter1 = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				jf1.setFileFilter(filter1);
				
				int aa = jf1.showOpenDialog(null);
				//If chosen file if off allowed format
				if(aa==JFileChooser.APPROVE_OPTION) {
					char cbuf[]=null;
					f1=jf1.getSelectedFile();
					//Set contents of textfield to the path of the file
					textField_1.setText(f1.getAbsolutePath());
				}
				
			}
		});
		nameButton.setBounds(332, 42, 112, 26);
		frame.getContentPane().add(nameButton);
		
		
		
		//Create second button to browse for .txt file
		JButton coverButton = new JButton("Browse");
		coverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Open file chooser
				JFileChooser jf2 = new JFileChooser();
				//File can only be in image format
				FileNameExtensionFilter filter2 = new FileNameExtensionFilter("png","jpg");
				jf2.setFileFilter(filter2);
				
				int aa = jf2.showOpenDialog(null);
				//If chosen file if off allowed format
				if(aa==JFileChooser.APPROVE_OPTION) {
					char cbuf[]=null;
					f2=jf2.getSelectedFile();
					//Set contents of textfield to the path of the file
					textField_2.setText(f2.getAbsolutePath());

				}
			}
		});
		coverButton.setBounds(327, 97, 117, 29);
		frame.getContentPane().add(coverButton);
		
		//Create third button to browse for .txt file
		JButton musicButton = new JButton("Browse");
		musicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Open file chooser
				JFileChooser jf3 = new JFileChooser();
				//File can only be in midi format
				FileNameExtensionFilter filter3 = new FileNameExtensionFilter("midi", "mid");
				jf3.setFileFilter(filter3);
				
				int aa = jf3.showOpenDialog(null);
				//If chosen file if off allowed format
				if(aa==JFileChooser.APPROVE_OPTION) {
					char cbuf[]=null;
					f3=jf3.getSelectedFile();
					//Set contents of textfield to the path of the file
					textField_3.setText(f3.getAbsolutePath());
				}
			}
		});
		musicButton.setBounds(327, 148, 117, 29);
		frame.getContentPane().add(musicButton);
		
		//Create labels for text fields
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(22, 46, 61, 16);
		frame.getContentPane().add(lblTitle);
		
		JLabel lblCover = new JLabel("Cover Art");
		lblCover.setBounds(22, 102, 61, 16);
		frame.getContentPane().add(lblCover);
		
		JLabel lblMusic = new JLabel("Music");
		lblMusic.setBounds(22, 153, 61, 16);
		frame.getContentPane().add(lblMusic);
		
		//Create submit button
		JButton btnSave = new JButton("Save");
		
		btnSave.addActionListener(new ActionListener() {	
			//On submit
			public void actionPerformed(ActionEvent e) {
				//Create a array holding file paths
				f1_path = textField_1.getText();
				f2_path = textField_2.getText();
				f3_path = textField_3.getText();
				String[] filePaths = {f1_path, f2_path, f3_path};
				
				//Validation 1- Check fields contain a non-empty string
				for(int i=0; i< 3; i++){
					if(filePaths[i].length()==0) { 
						System.out.println("Text field number " +(i+1) +" has been left empty");
						empty = true;
						}
					}
				//If field does contain an empty string, close application and give warning.
				if (empty==true) {
					System.out.println("This application has closed. Please next time ensure ALL fields contain a file");
					System.exit(0);
					return;
				}
				System.out.println("1");
				//Validation 2- Ensure files both exist and are of the required format
				checkF(f1_path, 1);
				checkF(f2_path, 2);
				checkF(f3_path, 3);
				
				//If files are invalid, break.
				if (invalid==true) {
					System.out.println("This application has closed. Please next time ensure all fields submit a VALID file.");
					frame.dispose(); 
					return;
				}
				System.out.println("2");
				//Add valid files to array list
				for(int i=0; i< 3; i++){
					files.add(new File(filePaths[i]));  
					}
				
				//Create proprietry file from midi file and add that to list of files
				 File noteFile = gM.convertMIDI(files.get(2).getAbsolutePath());
				 files.add(noteFile);  
				 
				//Close frame
				frame.dispose();
				System.out.println("3");
				//Create a Song object
				Song song = new Song();
				Song.filesSong = files;
				//Run method within Client
				try {
					song.run();
				} catch (Exception e1) {
					System.out.println("HELLO");
					e1.printStackTrace();
				}
			
				}
			}});
		
		btnSave.setBounds(149, 212, 117, 29);
		frame.getContentPane().add(btnSave);

	}
			
	/*
	 * Method validating the file submitted, within the first field, from the StoreManager application. 
	 * Ensures it is in the correct format
	 * @param s: The file path to be checked
	 * @return 
	 * @return 
	 * @return 
	 */
	public static String checkF(String s, int Case, int Case2) {
	String initial ="valid";
	String format="falseFormat";
	String valid="falseString";
	File f = new File(s);
	
	//Switch statement
	switch(Case) {
	
	case 1:
		
		switch(Case2) {
			
			case 1: if(!f.exists() || f.isDirectory()) {
				System.out.println("The first file does not exist");
				return valid;
				}
			
				
			case 2:
				if (!s.endsWith(".txt")){
					System.out.println("The first file must be of .txt format.");
					return format;
				}
				break;
		}
		break;
				
	case 2:
		
		switch(Case2) {
			case 1: if(!f.exists() || f.isDirectory()) {
				System.out.println("The second file does not exist");
				return valid;
			}
        break;
	case 2:
		//Check file exists and is not a directory
			if(f.exists() && !f.isDirectory()) { 
			    //Ensure suffix is of correct notation
				if (!(s.endsWith(".png") || s.endsWith(".jpg"))) {
					System.out.println("The second submitted file must be of .png or of .jpg format.");
					invalid = true;
					return;
				}
				break;
			
			}
			break;
		
		
	case 3:
		switch(Case2) {
			
			case 1: 
			
			if(!f.exists() || f.isDirectory()) {
				System.out.println("The third file does not exist");
				return valid;
				}
				break;
				
			case 2:
				if (!s.endsWith(".mid")){
					System.out.println("The third file must be of .mid format.");
					return format;
				}
				break;
			}
		
		else{
			System.out.println("The third submitted file does not exist");
			invalid = true;
			return;
		}
        break;
	}
	}
	
	
	

}
