import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import server.Server;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.*;


public class StoreManagerFrame {

	private JFrame frame;
	
	///Initialise text fields
	JTextField textField_1, textField_2, textField_3 = null;
	File f1, f2, f3;
	String f1_path, f2_path, f3_path = null;
	ArrayList<File> files = new ArrayList<File>();
	String[] filePaths;
	boolean empty;
	static boolean invalid;

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

	//Create application
	public StoreManagerFrame() {
		initialize();
	}

	//initialise frame
	private void initialize() {
		
		frame = new JFrame("Store Manager");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
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
		
		//Button 1
		JButton nameButton = new JButton("Browse");
		nameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open file chooser
				JFileChooser jf1 = new JFileChooser();
				//File can only be in image format
				FileNameExtensionFilter filter1 = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				jf1.setFileFilter(filter1);
				
				int aa = jf1.showOpenDialog(null);
				if(aa==JFileChooser.APPROVE_OPTION) {
					char cbuf[]=null;
					f1=jf1.getSelectedFile();
					textField_1.setText(f1.getAbsolutePath());
					String f1_path = textField_1.getText();
				}
				
			}
		});
		nameButton.setBounds(332, 42, 112, 26);
		frame.getContentPane().add(nameButton);
		
		
		
		//Button 2
		JButton coverButton = new JButton("Browse");
		coverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open file chooser
				JFileChooser jf2 = new JFileChooser();
				//File can only be in image format
				FileNameExtensionFilter filter2 = new FileNameExtensionFilter("png","jpg");
				jf2.setFileFilter(filter2);
				
				int aa = jf2.showOpenDialog(null);
				if(aa==JFileChooser.APPROVE_OPTION) {
					char cbuf[]=null;
					f2=jf2.getSelectedFile();
					textField_2.setText(f2.getAbsolutePath());

				}
			}
		});
		coverButton.setBounds(327, 97, 117, 29);
		frame.getContentPane().add(coverButton);
		
		//Button 3
		JButton musicButton = new JButton("Browse");
		musicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open file chooser
				JFileChooser jf3 = new JFileChooser();
				//File can only be in midi format
				FileNameExtensionFilter filter3 = new FileNameExtensionFilter("midi", "mid");
				jf3.setFileFilter(filter3);
				
				int aa = jf3.showOpenDialog(null);
				if(aa==JFileChooser.APPROVE_OPTION) {
					char cbuf[]=null;
					f3=jf3.getSelectedFile();
					textField_3.setText(f3.getAbsolutePath());
				}
			}
		});
		musicButton.setBounds(327, 148, 117, 29);
		frame.getContentPane().add(musicButton);
		
		//Buttons
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(22, 46, 61, 16);
		frame.getContentPane().add(lblTitle);
		
		JLabel lblCover = new JLabel("Cover Art");
		lblCover.setBounds(22, 102, 61, 16);
		frame.getContentPane().add(lblCover);
		
		JLabel lblMusic = new JLabel("Music");
		lblMusic.setBounds(22, 153, 61, 16);
		frame.getContentPane().add(lblMusic);
		
		//On Submit
		JButton btnSave = new JButton("Save");
		
		btnSave.addActionListener(new ActionListener() {	
			
			public void actionPerformed(ActionEvent e) {
				//Create a array holding file paths
				f1_path = textField_1.getText();
				f2_path = textField_2.getText();
				f3_path = textField_3.getText();
				String[] filePaths = {f1_path, f2_path, f3_path};
				
				//1) CHECK FIELDS ARE FULL
				for(int i=0; i< 3; i++){
					if(filePaths[i].length()==0) { 
						System.out.println("field " +(i+1) +" is empty");
						empty = true;
						}
					}
				//break if field is null
				if (empty==true) {
					System.out.println("This application has closed. Please next time ensure all fields submit a file");
					frame.dispose(); 
					return;
				}	
				
				//2) CHECK PATHS ARE VALID
				checkF1(f1_path);
				checkF2(f2_path);
				checkF3(f3_path);
				
				//Break if paths are invalid
				if (invalid==true) {
					System.out.println("This application has closed. Please next time ensure all fields submit a VALID file.");
					frame.dispose(); 
					return;
				}	
				
				//Add valid files to array list
				for(int i=0; i< 3; i++){
					files.add(new File(filePaths[i]));  
					}
				
				//Close frame
				frame.dispose(); 
				
				//Create a Song object
				Client song = new Client();
				song.filesSong = files;
				//Run method within Client
				try {
					Client.run();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
				
			});
		
		btnSave.setBounds(149, 212, 117, 29);
		frame.getContentPane().add(btnSave);

	}
	
	public static void checkF1(String s) {
		File f = new File(s);
		//check file exists 
		if(f.exists() && !f.isDirectory()) { 
		    //check suffix
			if (!s.endsWith(".txt")) {
				System.out.println("The first file must be of .txt format.");
				invalid = true;
			}
		}
		
		else{
			System.out.println("File 1 does not exist");
			invalid = true;
		}
		
	}
	
	public static void checkF2(String s) {
		File f = new File(s);
		//check file exists 
		if(f.exists() && !f.isDirectory()) { 
		    //check suffix
			if (!s.endsWith(".png") && !s.endsWith(".jpg")) {
				System.out.println("The second file must be of .png or of .jpg format.");
				invalid = true;
			}
		}
		
		else{
			System.out.println("File 2 does not exist");
			invalid = true;
		}
		
	}

	public static void checkF3(String s) {
		File f = new File(s);
		//check file exists 
		if(f.exists() && !f.isDirectory()) { 
		    //check suffix
			if (!s.endsWith(".mid")) {
				System.out.println("The first file must be of .mid format.");
				invalid = true;
			}
		}
		
		else{
			System.out.println("File 3 does not exist");
			invalid = true;
		}
		
	}
	
	
}
