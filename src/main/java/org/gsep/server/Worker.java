package org.gsep.server;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import org.json.*;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Niha Gummakonda
 * @version 1.0 07/03/2019
 */
public class Worker implements Runnable {

    private Socket soc;
    private String songName;
    private int songIndex;

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
                    createDir("img");
                    createDir("midi");
                    createDir("notes");
                    //createJSON();
                   
                    //Read JSON
                    
                		//Retrieve name of file
                		songName=part[2];
                		
                		//Write to JSON file
                     writeJSON(4, songName);
                		
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
                    System.out.println("This is not a valid input");
                }

            } catch (Exception e) {
                System.out.println(e);
            }


        }
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
        FileOutputStream fos = new FileOutputStream("./src/main/resources/songs/"+folder+"/"+songName+extension);
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
    		File file = new File("./src/main/resources/songs/index.json");
    		
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
     * This method writes to JSON file
     * @author humzahmalik
     */
    private static void writeJSON(int num, String song) {
    		JSONObject obj = new JSONObject();

		try {
			obj.put("id", num);
			obj.put("name", song);
			
			String jsonString = obj.toString();

			
	        // Writing to a file   
	        try(FileWriter file = new FileWriter("./src/main/resources/songs/index.json", true)){
	        		file.write(obj.toString());
	        		file.flush();
	        }
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
		/**
		 * @author humzahmalik
		 * Method that reads JSON file and finds ID
		 * @throws org.json.simple.parser.ParseException 
		 * @throws IOException 
		 * @throws FileNotFoundException 
		 * @throws JSONException 
		 */
    private static void readJSON() throws FileNotFoundException, IOException, org.json.simple.parser.ParseException, JSONException {
    	 	
    		JSONParser parser = new JSONParser();
    		
    	 	Object obj = parser.parse(new FileReader("./src/main/resources/songs/index.json"));

        JSONObject jsonObject = (JSONObject) obj;

        String name = (String) jsonObject.get("name");
        System.out.println(name);

        long age = (Long) jsonObject.get("age");
        System.out.println(age);

    }
    
}
