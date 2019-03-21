package org.gsep.server;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.gsep.manager.MapperClass;
import org.gsep.manager.Song;
import org.json.*;
import org.json.simple.parser.JSONParser;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Niha Gummakonda
 * @version 1.0 07/03/2019
 */
public class Worker implements Runnable {

    private Socket soc;
    private String songName;
    private int songIndex;
    private static String jsonPath = "./src/main/resources/songs/index.json";
    private static String midiPath = "./src/main/resources/songs/midi";
    private static int numSongs;

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
                    
                } else {
                    System.out.println("This is not a valid input");
                }

            } catch (Exception e) {
                System.out.println(e);
            }


        }
    }
    
    
    /**
     * @author humzahmalik
     *  This method gets the number of songs seeing how many songs exist currently.
     */
    private void getNumSongs() {
		int n = new File(midiPath).list().length;
		this.numSongs=n;
		
    }
    
    
    /**
     * This method stores the file in the correct directory
     * @author humzahmalik and Niha
     * @param fileName
     * @param fileLength
     * @throws IOException
     */

    public void storeFile(String fileName, int fileLength) throws IOException {

    		String extension = getFileExtension(new File(fileName));
        String folder = null;
        
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
        System.out.println(folder);
        FileOutputStream fos = new FileOutputStream("./src/main/resources/songs/"+folder+"/"+numSongs+extension);
        is.read(b,0,b.length);
        fos.write(b,0,b.length);
        System.out.println("File called " +songName+" has been written to " + "./src/main/resources/songs/"+folder+"/"+songName+extension);
    }


    /**
     * This method returns the extension of a file
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
     * This method creates a directory if it doesn't already exist
     * @param dirName
     * @author humzahmalik
     */
    private static void createDir(String dirName) {
    
    		File directory = new File("./src/main/resources/songs/"+dirName);
    		//If directory doesn't exist, create it
    		if (!directory.exists()){
    	        directory.mkdir();
    	        System.out.println("Created");
    	    }
    		
    }
    
    /**
     * This method checks if the index.json file exists and if not, creates it.
     * @author humzahmalik
     */
    private static void createJSON() {
    		File file = new File(jsonPath);
    		
    		//If file doesn't exist, create it

    		try {
				if (file.createNewFile())
				{
				    System.out.println("Json file is created!");
				} else {
				    System.out.println("File already exists.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
    }
    
    /**
     * @author Chris Mott and Humzah Malik
     * @return
     */
    public List<Song> getSongs(){
        List<Song> songs = new ArrayList<Song>();

        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File(jsonPath);
        try{
            songs = objectMapper.readValue(file, new TypeReference<List<Song>>(){});
        }catch(Exception e){
            e.printStackTrace();
        }
        return songs;
    }

    /**
     * @author Chris Mot humzahmalik
     * Method that adds a song to the current array of song objects, updating the midi file
     * @param id
     * @param name
     */
    public void addSong(int id, String name){
        List<Song> songs = getSongs();
        Song song = new Song(id, name);

        songs.add(song);
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            objectMapper.writeValue(
                new FileOutputStream(jsonPath), songs);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
	
    
}
