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
    public static final int PORT_NUMBER=5408;

    /**
     * Method that calls the methods readFile() and zipFile(). The purpose of run() is to invoke the client side methods all at once.
     * @throws Exception
     */
    public static void run() throws Exception {

        Socket soc = new Socket("localhost",PORT_NUMBER);
        DataInputStream dis = new DataInputStream(soc.getInputStream());
        String msg = dis.readUTF();
        System.out.println(msg);
        DataOutputStream dos = new DataOutputStream((soc.getOutputStream()));
        //Create string of song name
        String file = readFile(filesSong.get(0).toString(), StandardCharsets.UTF_8 );
        System.out.println(file);
        
        //Run methods
        //createFolder(file, soc);
        createFile(soc);
        

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

    
    public static void createFolder(String file, Socket soc) throws IOException {
  
    		reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));

        // creating folder
        System.out.println("Creating remote folder");
        writer.write("mkdir"+","+name);
        writer.flush();
    	
    }

    public static void createFile(Socket soc) throws UnknownHostException, IOException {
    		String fileName = "/Users/humzahmalik/Bohemian Rhapsody File/title.txt";
    	 
        File file = new File(fileName);
       
        ObjectInputStream ois = new ObjectInputStream(soc.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(soc.getOutputStream());
 
        oos.writeObject(file.getName());
 
        FileInputStream fis = new FileInputStream(file);
        byte [] buffer = new byte[BUFFER_SIZE];
        Integer bytesRead = 0;
 
        while ((bytesRead = fis.read(buffer)) > 0) {
            oos.writeObject(bytesRead);
            oos.writeObject(Arrays.copyOf(buffer, buffer.length));
        }
 
        oos.close();
        ois.close();   
    }

}