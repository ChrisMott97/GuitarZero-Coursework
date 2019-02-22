 import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/*
 * Client
 * 
 * @author Humzah Malik
 * @version 1.0
 * 
 */
public class Client{
	
	static ArrayList<File> filesSong = new ArrayList<File>();
	static String name;
	static Socket socket;
	
	/**
	 * Method that calls the methods readFile() and zipFile(). The purpose of run() is to invoke the client side methods all at once.
	 * @throws Exception
	 */
	public static void run() throws Exception {
		
		//Retrieve name of song from text file.
		readFile(filesSong.get(0).toString(), StandardCharsets.UTF_8 );
		
		//Take files and make them into zip
		zipFile(filesSong, name);
	}
	
	/**
	 * Method that reads a file and returns its contents in a string.
	 * @param path: Path of the file
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
				
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  //hold contents of file in name
			  name= new String(encoded, encoding);
			  return name;
			}
	
	/**
	 * Method that zips an array of files, with the zip file assigned a given name.
	 * @param files: List of files to zip
	 * @param name: String to name the Zip file.
	 * @throws IOException
	 */
	public static void zipFile(ArrayList<File> files, String name) throws IOException {
		//METHOD NOT COMPLETED OR INVOKED YET
	    try {
	    		
	    	  FileOutputStream   fos = new FileOutputStream(name+".zip");
	      ZipOutputStream zos = new ZipOutputStream(fos);
	      byte[] buffer = new byte[128];
	      
	      for (int i = 0; i < files.size(); i++) {
	        File currentFile = files.get(i);
	        
	        if (!currentFile.isDirectory()) {
	          ZipEntry entry = new ZipEntry(currentFile.getName());
	          FileInputStream fis = new FileInputStream(currentFile);
	          zos.putNextEntry(entry);
	          int read = 0;
	          while ((read = fis.read(buffer)) != -1) {
	            zos.write(buffer, 0, read);
	          }
	          zos.closeEntry();
	          fis.close();
	        }
	      }
	      zos.close();
	      fos.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("File not found : " + e);
	    }

	  }
	

	/**
	 * This method has not yet been fully completed. Its purpose will be to establish a connection to the server, and send the zip file over the network.
	 * @param file: File to send to server.
	 * @throws Exception
	 */
	public static void sendZip(File file) throws Exception {
	         
			//connect to server
	        socket = new Socket("localhost", 3332);
	        
	        
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
	        System.exit(0);    
		

    }
	
	/**
	 * This method handles exceptions within the transfering of the zip file.
	 * @param message
	 * @throws Exception
	 */
	public static void throwException(String message) throws Exception {
		//METHOD NOT INVOKED YET
        throw new Exception(message);
    }
	
	
	
}