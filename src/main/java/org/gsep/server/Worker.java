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

import org.gsep.manager.JsonSong;
import org.gsep.manager.MapperClass;
import org.json.*;
import org.json.simple.parser.JSONParser;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
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
                System.out.println("1");
                
                //Read message sent from client
                String[] part = dis.readUTF().split(",");
                System.out.println("2");
                //Number of files to send over
                int size = Integer.parseInt(part[1])-1;
                System.out.println("3");
                
                //If client is about to send files over
                if (part[0].equals("Send")) {
                		//Create directories and JSON file if they dont exist
                    createDir("img");
                    createDir("midi");
                    createDir("notes");
                    createJSON();
                    
                    //Check how many songs exist atm
                    getNumSongs();
                    System.out.println("4");
                   
                    //Read JSON
                    //readJSON();
                    
                		//Retrieve name of file
                		String songName = part[2].trim();
                		System.out.println(songName);

                		
                		//Write to JSON file                		
                     writeJSON(songName);
                		

                		
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
     * This method write and object to JSON file.
     * @author humzahmalik
     * @throws IOException 
     * @throws JSONException 
     * @throws org.json.simple.parser.ParseException 
     */
    private static void writeJSON(String dirName){

            
    	try {
    		
    			

		    File file = new File(jsonPath);
		    FileWriter fileWriter = new FileWriter(file, true);

		    ObjectMapper mapper = new ObjectMapper();

		    SequenceWriter seqWriter = mapper.writer().writeValuesAsArray(fileWriter);
		    //seqWriter.write(new JsonSong(index, dirName));
		    seqWriter.write(new JsonSong(numSongs, dirName));
		    seqWriter.close();
		    
		    
		} catch (IOException e) {
		    e.printStackTrace();
		}
    }
    
	
		/**
		 * @author humzahmalik
		 * Method that reads JSON file and prints most recent object. 
		 * @return 
		 * @throws org.json.simple.parser.ParseException 
		 * @throws IOException 
		 * @throws FileNotFoundException 
		 * @throws JSONException 
		 */
    private static String readJSON() throws IOException  {

    		byte[] encoded = Files.readAllBytes(Paths.get(jsonPath));
    	  String s= new String(encoded, StandardCharsets.UTF_8);
    	  String strNew = s.replace("[", "");
    	  String strNew2 = strNew.replace("]", "");// strNew is 'bcdDCBA123'

    	  return strNew2;
	    	

    }
    
}
