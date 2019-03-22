package org.gsep.store;

import com.fasterxml.jackson.core.JsonGenerationException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.gsep.manager.Song;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Niha Gummakonda
 * @version 2.0 22 March 2019
 */
public class Store {


    private static Socket soc;
    private final String BASE_PATH = "src/main/resources/songs/";

    Store() {
        try {
            soc = new Socket("localhost", 3335);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the images that are available on the server and stores them
     * in a cache folder.
     * @throws IOException
     */
    public void getImages() throws IOException {
        // Sends message to server about what it wants to do
        DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
        dos.writeUTF("Images");
        DataInputStream dis = new DataInputStream(soc.getInputStream());
        // Gets number of files
        int count = dis.readInt();
        File[] files = new File[count];

        for(int i = 0; i < files.length; i++){
        InputStream inputStream = soc.getInputStream();
        // Gets name and size
        String name = dis.readUTF();
        byte[] sizeAr = new byte[4];
        inputStream.read(sizeAr);
        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

        byte[] imageAr = new byte[size];
        inputStream.read(imageAr);

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
        // writes the image
        files[i] = new File("src/main/resources/cache/img/"+name);
        ImageIO.write(image, "jpg", files[i]);
        }

    }

    /**
     * Gets the index.json file from the server and stores in cache folder
     * @throws IOException
     */
    public void getJSON() throws IOException {
        // Tells server what it wants to do
        DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
        dos.writeUTF("JSON");
        DataInputStream dis = new DataInputStream(soc.getInputStream());
        // Reads and writes bytes
        int fileLen = dis.readInt();
        byte[] b = new byte[fileLen];
        FileOutputStream fos = new FileOutputStream("src/main/resources/cache/index.json");
        InputStream in = soc.getInputStream();
        in.read(b,0,b.length);
        fos.write(b,0,b.length);

    }

    /**
     * When user selects an an image in store, it gets the respective
     * files for that image (midi and notes)
     * @param id - of the image selected
     * @throws IOException
     */
    public void getFile(int id) throws IOException {
        DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
        dos.writeUTF("Get," + id);
        // Creating folders and extensions
        ArrayList<String> folders = new ArrayList<>();
        folders.add("img");
        folders.add("notes");
        folders.add("midi");
        String[] extension = {".jpg",".txt",".mid"};
        File[] files = new File("src/main/resources/songs/img").listFiles();
        int count = files.length;
        // Gets file from each folder
        for(int i = 0; i < extension.length; i++) {
            DataInputStream dis = new DataInputStream(soc.getInputStream());
            InputStream in = soc.getInputStream();
            FileOutputStream fos = new FileOutputStream(BASE_PATH+folders.get(i)+"/" + (count-1) + extension[i]);
            int fileLen = dis.readInt();
            byte[] b = new byte[fileLen];
            in.read(b, 0, b.length);
            fos.write(b, 0, b.length);
        }
    }

    /**
     * Gets the songs available in select
     * @return arraylist of songs
     */
    public List<Song> getSongs(){
        List<Song> songs = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File(BASE_PATH+"index.json");
        try{
            songs = objectMapper.readValue(file, new TypeReference<List<Song>>(){});
        }catch(Exception e){
            System.out.println("Error" + e.getMessage());
        }
        return songs;
    }

    /**
     * Adds the new song to the JSON file
     * @param fileName name of file chosen
     */
    public void updateJSON(String fileName){

        List<Song> songs = getSongs();

        int lastID = songs.get(songs.size()-1).getId();
        int newID = lastID + 1;
        songs.add(new Song(newID,fileName));

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(BASE_PATH+"index.json");
            objectMapper.writeValue(file, songs);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
