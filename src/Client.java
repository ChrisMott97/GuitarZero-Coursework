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

public class Client{
	
	static ArrayList<File> filesSong = new ArrayList<File>();
	static String name;
	static Socket socket;
	
	//RUN METHOD
	public static void run() throws IOException {
		
		//GET NAME OF SONG
		readFile(filesSong.get(0).toString(), StandardCharsets.UTF_8 );
		
		//CONVERT FILES TO ZIP
		zipFile(filesSong, name);
		
		//SEND ZIPPED FILE TO SERVER AND UNZIP
		sendZip();
	}
	
	//Get name of song
	public static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  name= new String(encoded, encoding);
			  return name;
			}
	
	//Zip File
	public static void zipFile(ArrayList<File> files, String name) throws IOException {
		System.out.println(files.size());
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
	
	
	//Send zip file over socket
	public static void sendZip() throws IOException {
		socket = new Socket("localhost", 1510);
		
		//file to transfer
		File f = new File("/Users/humzahmalik/Bohemian Rhapsody File/title.txt");
		
		//streams to transfer files
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        oos.writeObject(f.getName());
 
        FileInputStream fis = new FileInputStream(f);
        byte [] buffer = new byte[2002];
        Integer bytesRead = 0;
 
        while ((bytesRead = fis.read(buffer)) > 0) {
            oos.writeObject(bytesRead);
            oos.writeObject(Arrays.copyOf(buffer, buffer.length));
        }
 
        oos.close();
        ois.close();

    }
	
	
	
}