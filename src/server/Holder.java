import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTextField;

public class Client {
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
	    
	    //Establish connection
		public static void createSocket() {
			try {
				socket = new Socket("localhost", 1510);

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
		
		//Send file notification
		public static void sendFileNotification() throws IOException {
	        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	   
	        // send name of file
	        writer.write("FileNotification");
	        writer.flush();
		}
		
		//Send files
		public static void send(File file) throws Exception {
	       
	        //streams to transfer files
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
