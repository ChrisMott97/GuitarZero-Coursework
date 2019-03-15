package org.gsep.server;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Niha Gummakonda
 * @version 1.0 07/03/2019
 */
public class Worker implements Runnable {

    private Socket soc;
    private String songName;

    Worker(Socket soc){
        this.soc = soc;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {


                System.out.println("Connected!!");
                
               
                
                DataInputStream dis = new DataInputStream(soc.getInputStream());
                
                //Read message sent from client
                String[] part = dis.readUTF().split(",");
                //Number of files to send over
                int size = Integer.parseInt(part[1])-1;
                
                //If client is about to send files over
                if (part[0].equals("Send")) {
                		//Create directories if they dont exist
                    createDir("Images");
                    createDir("Music");
                    createDir("Notes");
                    
                		//Retrieve name of file
                		songName=part[2];
                		
                		//Send file to method that stores it
                    while (size > 0) {
                        String[] file = dis.readUTF().split(",");
                        storeFile(file[0], Integer.parseInt(file[1]));
                        size -= 1;
                    }
                    
                //If the client is requesting to get the file
                } else if (part[0].equals("Get")) {
                    getFile(part[1], dis);
                } else {
                    System.out.println("WRONG BEGINNING! ");
                }

            } catch (Exception e) {
                System.out.println(e);
            }


        }
    }

    public void storeFile(String fileName, int fileLength) throws IOException {

    		String extension = getFileExtension(new File(fileName));
        System.out.println(extension);
        String folder = null;
        
        if (extension.equals(".txt")) {
        		folder = "Notes";
        }
        
        if (extension.equals(".mid")) {
    			folder = "Music";
        }
        
        if (extension.equals(".png")|| extension.equals(".jpg")) {
    			folder = "Images";
        }
        
        
        byte[] b = new byte[fileLength];

      
        //Initialise input stream for file
        InputStream is = soc.getInputStream();
        FileOutputStream fos = new FileOutputStream(folder+"/"+songName+extension);
        is.read(b,0,b.length);
        fos.write(b,0,b.length);
        System.out.println("File called " +songName+" has been written to " + folder);
    }

    public void getFile(String fileName, DataInputStream dis){

    }

    /**
     * @author humzahmalik
     * @param file
     * @return
     */
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }
    
    /**
     * @author humzahmalik
     */
    private static void createDir(String dirName) {
    		File directory = new File(dirName);
    		//If directory doesn't exist, create it
    		if (! directory.exists()){
    	        directory.mkdir();
    	    }
    		
    }
    
    
    
    
}
