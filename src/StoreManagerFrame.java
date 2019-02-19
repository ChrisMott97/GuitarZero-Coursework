import java.awt.*;
import javax.swing.*;

import server.Server;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.awt.event.*;


public class StoreManagerFrame {

	private JFrame frame;

	static public File f1;
	static public File f2;
	static public File f3;

	
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
		
		///Initialise text fields
		JTextField textField_1;
		JTextField textField_2;
		JTextField textField_3;
		
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
				JFileChooser jf = new JFileChooser();
				int aa = jf.showOpenDialog(null);
				System.out.println(aa);
				if(aa==JFileChooser.APPROVE_OPTION) {
					char cbuf[]=null;
					f1=jf.getSelectedFile();
					textField_1.setText(f1.getAbsolutePath());
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
				JFileChooser jf = new JFileChooser();
				int aa = jf.showOpenDialog(null);
				System.out.println(aa);
				if(aa==JFileChooser.APPROVE_OPTION) {
					char cbuf[]=null;
					f2=jf.getSelectedFile();
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
				JFileChooser jf = new JFileChooser();
				int aa = jf.showOpenDialog(null);
				System.out.println(aa);
				if(aa==JFileChooser.APPROVE_OPTION) {
					char cbuf[]=null;
					f3=jf.getSelectedFile();
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
				File[] myArray = {f1, f2, f3};
				//transfer these files over
				try {
					tranfer(myArray);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.dispose();
				
			}
		});
		btnSave.setBounds(149, 212, 117, 29);
		frame.getContentPane().add(btnSave);
		
		
	}

	//Save files on server
	 public static void tranfer(File[] fList) throws IOException {
	        // TODO Auto-generated method stub
	     
	            Socket sock = new Socket("localhost", 2104);  //replace with your remote host static IP address.
	            System.out.println("Connecting.........");
	             
	            File[] Files = fList;
	              
	            OutputStream os = sock.getOutputStream();  
	            DataOutputStream dos = new DataOutputStream(os); 
	            
	            dos.writeInt(Files.length);
	             
	            for (int count=0;count<Files.length;count ++){
	                  dos.writeUTF(Files[count].getName());
	                   
	            }
	            for (int count=0;count<Files.length;count ++){
	                   
	                  int filesize = (int) Files[count].length();
	                  dos.writeInt(filesize);
	            }
	             
	            for (int count=0;count<Files.length;count ++){
	             
	            int filesize = (int) Files[count].length();
	            byte [] buffer = new byte [filesize];
	                 
	            //FileInputStream fis = new FileInputStream(myFile);  
	            FileInputStream fis = new FileInputStream(Files[count].toString());  
	            BufferedInputStream bis = new BufferedInputStream(fis);  
	         
	            //Sending file name and file size to the server  
	            bis.read(buffer, 0, buffer.length); //This line is important
	             
	            dos.write(buffer, 0, buffer.length);   
	            dos.flush(); 
	            //dos.close();
	            }  
	             
	            //Closing socket  
	            //dos.close();
	            sock.close();
	 
	    }  
	
	 //Create a folder on server
	 
}
