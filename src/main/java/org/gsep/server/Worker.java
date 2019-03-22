package org.gsep.server;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.gsep.manager.Song;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
/*
 * Worker class
 * 
 * This class handles incoming requests to the server. 
 *
 * @author  Niha and Humzah Malik
 * @version 2.00, March 2019.
 */
public class Worker implements Runnable {

    private Socket soc;
    private String[] extension = {".jpg", ".txt", ".mid"};
    private ArrayList<String> folders = new ArrayList<>();
    private String songName;
    private int songIndex;
    private final static String JSONPATH = "./src/main/resources/songs/ServerContents/index.json";
    private final static String MIDIPATH = "./src/main/resources/songs/ServerContents/midi";
    private final static String SERVERPATH = "./src/main/resources/songs/ServerContents/";
    private static int numSongs;


    Worker(Socket soc) {
        this.soc = soc;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {


                System.out.println("You are connected to the Guitar Zero Lite server!");
                
                DataInputStream dis = new DataInputStream(soc.getInputStream());
                
                //Read message sent from client
                String[] part = dis.readUTF().split(",");

                //Number of files to send over
                int size = Integer.parseInt(part[1])-1;
                
                //If client is about to send files over
                if (part[0].equals("Send")) {
                		//Create directories and JSON file if they dont exist
                    createDir("img");
                    createDir("midi");
                    createDir("notes");
                    createJSON();
                    
                    //Check how many songs exist
                    getNumSongs();
                    
                		//Retrieve name of file
                		String songName = part[2].trim();

                		//CHRIS STUFF      		
            	        addSong(numSongs, songName);

                		//Send file to method that stores it
                    while (size > 0) {
                        String[] file = dis.readUTF().split(",");
                        storeFile(file[0], Integer.parseInt(file[1]));
                        size -= 1;
                    }
                    
                //If the client is requesting to get the file
                } else if (part[0].equals("Get")) {
                    
                    System.out.println("1");
                    sendFile(part[1]);
                } else if(part[0].equals("Images")) {
                    sendImages();
                }else if(part[0].equals("JSON")){
                    sendJSON();
                }else{
                    System.out.println("WRONG BEGINNING! ");
                }

            } catch (Exception e) {
                System.out.println("There's been an error in adding the files. Please try again.");
            }


        }
    }
    
    
    /**
     * Sets number of songs currently on server, to a field n. This is used when indexing new songs.
     * @author humzahmalik
     */
    private void getNumSongs() {
		int n = new File(MIDIPATH).list().length;
		this.numSongs=n;
		
    }
    
    
    /**
     * Stores the file in the correct directory, with correct extension and name.
     * @author humzahmalik and Niha
     * @param fileName Path of file
     * @param fileLength Length of file, used in buffered reader.
     * @throws IOException
     */

    public void storeFile(String fileName, int fileLength) throws IOException {
    		//Get extension of file transfered
    		String extension = getFileExtension(new File(fileName));
        String folder = null;
        
        //Specify the folder for the file to go into, regarding its extension
        if (extension.equals(".txt")) {
        		folder = "notes";
        }
        
        if (extension.equals(".mid")) {
    			folder = "midi";
        }
        
        if (extension.equals(".png")|| extension.equals(".jpg")) {
    			folder = "img";
        }
        
        
        byte[] b = new byte[fileLength];
      
        //Initialise input stream for file
        InputStream is = soc.getInputStream();
        
        FileOutputStream fos = new FileOutputStream(SERVERPATH+folder+"/"+numSongs+extension);
        is.read(b,0,b.length);
        fos.write(b,0,b.length);
    }


    /**
     * This method returns the extension of a file.
     * @author humzahmalik
     * @param file The file to check for its extension
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
     * Creates a directory if it doesn't already exist.
     * 
     * The reason this method exists is to preserve the file system. If the event occurs that the directories holding the files are deleted
     * store manager mode will still be able to continue to function.
     * 
     * @param dirName The name of the directory to create
     * @author humzahmalik
     */
    private static void createDir(String dirName) {
    
    		File directory = new File(SERVERPATH+dirName);
    		//If directory doesn't exist, create it
    		if (!directory.exists()){
    	        directory.mkdir();
    	    }
    		
    }
    
    /**
     * This method checks if the index.json file exists and if not, creates it.
     * @author humzahmalik
     */
    private static void createJSON() {
        File file = new File(JSONPATH);

        //If JSON file doesn't exist, create it
        try {
            if (file.createNewFile()) {
            } else {
                return;
            }
        } catch (IOException e) {
            System.out.println("There has been an issue accessing the store file system. Please try again");
        }
    }

    /**
     * Reads JSON index file and stores the contents in a list of Song objects which is returns.
     * @author Chris Mott
     * @return List of Song objects
     */
    public List<Song> getSongs(){
        List<Song> songs = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File(JSONPATH);
        try{
            songs = objectMapper.readValue(file, new TypeReference<List<Song>>(){});
        }catch(Exception e){
            System.out.println("Error");
        }
        return songs;
    }

    public void sendImages() throws IOException {

        DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
        File[] files = new File("src/main/resources/ServerContents/img").listFiles();
        dos.writeInt(files.length);
        for(File file: files){
            OutputStream os = soc.getOutputStream();
            BufferedImage image = ImageIO.read(file);
            dos.writeUTF(file.getName());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);

            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            os.write(size);
            os.write(byteArrayOutputStream.toByteArray());
            os.flush();
            System.out.println("Flushed: " + System.currentTimeMillis());

            System.out.println("Closing: " + System.currentTimeMillis());
        }

    }
    public void sendJSON() throws IOException {
        DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
        dos.writeUTF("JSON");
        System.out.println("Receving file..");
        File file = new File("src/main/resources/ServerContents/index.json");
        FileInputStream fis = new FileInputStream(file);
        dos.writeInt((int) file.length());
        byte[] b = new byte[(int) file.length()];
        fis.read(b, 0, b.length);
        OutputStream os = soc.getOutputStream();
        os.write(b,0,b.length);
    }
    /**
     * @author niha 
     * @param fileName
     * @throws IOException
     */
    public void sendFile(String fileName) throws IOException {
        ArrayList<String> folders = new ArrayList<>();
        folders.add("img");
        folders.add("notes");
        folders.add("midi");
        String[] extension = {".jpg", ".txt", ".mid"};
        for (int i = 0; i < folders.size(); i++) {
            DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
            File file = new File("src/main/resources/ServerContents/" + folders.get(i) + "/" + fileName + extension[i]);
            FileInputStream fis = new FileInputStream(file);
            dos.writeInt((int) file.length());
            byte[] b = new byte[(int) file.length()];
            fis.read(b, 0, b.length);
            OutputStream os = soc.getOutputStream();
            os.write(b, 0, b.length);
        }
    }
    
    /**
     * @author Chris Mot & Humzah Malik
     * 
     * Adds a Song object to the current array of Song objects, updating the midi file. 
     * The reason we chose a JSON format to store the file names is because of the ability to work with JSON objects.
     * JSON objects can hold name/value pairs, allowing us to associate an index with each song. 
     * This means we can iterate through our file system looking for specific index values, making the process more efficient.   
     * 
     * @param id The index of the song
     * @param name The name of the song
     */
    public void addSong(int id, String name){

    		//Get list of current songs
        List<Song> songs = getSongs();
        //Create a new song object of correct id and name
        Song song = new Song(id, name);

        songs.add(song);
        ObjectMapper objectMapper = new ObjectMapper();

        try{
        		//Write updated list to index file
            objectMapper.writeValue(new FileOutputStream(JSONPATH), songs);
            System.out.println("You have just added the song called - " + (name) + " - to your server, congratulations! This is game number " + (id+1)+ " in the store.");
            
        }catch(Exception e){
            System.out.println("There's been an error updating the store. Please try again later.");

        }
    }

//    public void deleteFile(File file) {
//        if (file.delete()) {
//            System.out.println("File added to Game Contents");
//        } else {
//            System.out.println("Error: couldn't delete file");
//        }
//        songs.add(song);
//        ObjectMapper objectMapper = new ObjectMapper();
//
//    }
    
    
	
    
}