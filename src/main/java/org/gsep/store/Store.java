package org.gsep.store;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.chart.PieChart;
import org.gsep.manager.Song;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Store {

    private static int id;
    private static Socket soc;
    private final String BASE_PATH = "src/main/resources/songs/GameContents/";

    Store(int id) {
        Store.id = id;
        try {
            soc = new Socket("192.168.56.1", 3335);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getFile() throws IOException {
        DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
        dos.writeUTF("Get-" + id);
        ArrayList<String> folders = new ArrayList<>();
        folders.add("img");
        folders.add("notes");
        folders.add("midi");
        String[] extension = {".jpg",".txt",".mid"};
        for(int i = 0; i < extension.length; i++) {
            DataInputStream dis = new DataInputStream(soc.getInputStream());
            InputStream in = soc.getInputStream();
            FileOutputStream fis = new FileOutputStream("src/main/resources/songs/GameContents/"+folders.get(i)+"/" + id + extension[i]);
            int fileLen = dis.readInt();
            byte[] b = new byte[fileLen];
            in.read(b, 0, b.length);
            fis.write(b, 0, b.length);
        }
    }

    public List<Song> getSongs(){
        List<Song> songs = new ArrayList<>();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(BASE_PATH + "index.json");
            songs = objectMapper.readValue(file, new TypeReference<List<Song>>(){});

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return songs;

    }
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
