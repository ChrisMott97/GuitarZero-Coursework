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
	JTextField textField_1=null;
	JTextField textField_2=null;
	JTextField textField_3=null;
	File f1;
	File f2;
	File f3;
	ArrayList<String> files = new ArrayList<String>();
	static boolean valid=false;
	
	static Socket socket;
    static BufferedWriter writer;
    static BufferedReader reader;
	
	
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
					//Add to file path array
					files.add(f1_path);
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
					String f2_path = textField_2.getText();
					//Add to file path array
					files.add(f2_path);
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
					String f3_path = textField_3.getText();
					//Add to file path array
					files.add(f3_path);

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
				
			createSocket();
			try {
				sendName(readFile(textField_1.getText(), Charset.defaultCharset()));
				createDir();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			closePort();
			frame.dispose();	
				
			}
				
			});
		
		btnSave.setBounds(149, 212, 117, 29);
		frame.getContentPane().add(btnSave);

	}
	
	//Establish connection
	public static void createSocket() {
		try {
			socket = new Socket("localhost", 1500);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Create directory
	public static void createDir() throws IOException {
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		try
        {
            System.out.println("Connecting to the server");
       
            // creating folder
            writer.write("mkdir");
            writer.flush();

        }
        catch(IOException err)
        {
            System.out.println(err.getMessage());
        }
	}
	
	//Send name to directory
	public static void sendName(String name) {
		try
        {
            System.out.println("Sending file name");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
       
            // send name of file
            System.out.println("writing file name");
            writer.write("FileName= " + name);
            writer.flush();
            System.out.println("file name written");

        }
        catch(IOException err)
        {
            System.out.println(err.getMessage());
        }
	}
	
	//find name from .txt file
	public static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	
	//Send files
	public static void send(File file) throws Exception {
        
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
 
        oos.writeObject(file.getName());
 
        FileInputStream fis = new FileInputStream(file);
        byte [] buffer = new byte[2002];
        Integer bytesRead = 0;
 
        while ((bytesRead = fis.read(buffer)) > 0) {
            oos.writeObject(bytesRead);
            oos.writeObject(Arrays.copyOf(buffer, buffer.length));
        }
 
        oos.close();
        ois.close();

    }
	
	//Exit program
	public static void closePort() {
		try
        {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
       
            // send name of file
            writer.write("exit");
            writer.flush();

        }
        catch(IOException err)
        {
            System.out.println(err.getMessage());
        }
	}
	
}
