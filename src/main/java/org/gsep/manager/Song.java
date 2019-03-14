package org.gsep.manager;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/*
 * Client
 *
 * @author Humzah Malik
 * @version 1.0
 *
 */
public class Song{

    static ArrayList<File> filesSong = new ArrayList<File>();
    static String name;
    static BufferedWriter writer;
    static BufferedReader reader;
    public static final int BUFFER_SIZE = 3000;
    public static final int PORT_NUMBER=5469;
    static DataOutputStream dos;

    /**
     * Method that calls the methods readFile() and zipFile(). The purpose of run() is to invoke the client side methods all at once.
     * @throws Exception
     */
    public static void run() {

        Socket soc;
        
		try {
			//Create socket
			soc = new Socket("localhost",PORT_NUMBER);
			
	        //Run methods
			sendName(soc, "/Users/humzahmalik/Bohemian Rhapsody File/title.txt");
	        sendFile(soc, "/Users/humzahmalik/Bohemian Rhapsody File/coverArt.jpg");
	        
	        //sendFile(soc, "/Users/humzahmalik/Bohemian Rhapsody File/title.txt");
	        
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void sendName(Socket soc, String title) {
    		DataOutputStream dos;
    		
			try {
				dos = new DataOutputStream(soc.getOutputStream());
				//Send alert that file name is coming
				dos.writeUTF("FileName,"+ readFile(title, StandardCharsets.UTF_8));
				dos.flush();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

				
    }
    
    public static void sendFile(Socket soc, String file) throws IOException {
    	
    		//Send over file extension
    		String ext = getFileExtension(new File(file));
    		
    		DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
    		dos.writeUTF("fileSend,"+ext);
    		dos.flush();
    		
    		// Create output stream
        OutputStream os = soc.getOutputStream();
        BufferedOutputStream out = new BufferedOutputStream(os, 1024);
        
        //Read file
        FileInputStream in = new FileInputStream(file);
        
        //Initialise byte variables
        int i = 0;
        int bytecount = 1024;
        byte[] buf = new byte[BUFFER_SIZE];
        
        //Read and send file over out stream
        while ((i = in.read(buf, 0, 1024)) != -1) {
            bytecount = bytecount + 1024;
            out.write(buf, 0, i);
          }
        out.flush();
        
        System.out.println("Bytes Sent :" + bytecount);

}
    
    /**
     * @author humzahmalik
     * @param file
     * @return
     */
	private static String getFileExtension(File file) {
	    String name = file.getName();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndexOf);
    }
    
    /**
     * @author humzahmalik
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

    


}