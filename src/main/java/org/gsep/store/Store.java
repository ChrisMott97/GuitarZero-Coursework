package org.gsep.store;

import javafx.scene.chart.PieChart;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Store {

    private static int id;
    private static Socket soc;

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

}
