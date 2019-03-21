package org.gsep.server;

import com.fasterxml.jackson.databind.util.JSONPObject;
import javafx.scene.chart.PieChart;

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

    Worker(Socket soc) {
        this.soc = soc;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {


                System.out.println("Connected!!");
                DataInputStream dis = new DataInputStream(soc.getInputStream());

                String[] part = dis.readUTF().split("-");
                if (part[0].equals("Send")) {
                    System.out.println("2");
                    int size = Integer.parseInt(part[1]);
                    while (size > 0) {
                        System.out.println("3");
                        String[] file = dis.readUTF().split("-");
                        System.out.println(file[0]);
                        storeFile(file[0], Integer.parseInt(file[1]));
                        System.out.println("4");
                        size -= 1;
                    }
                } else if (part[0].equals("Get")) {
                    System.out.println("1");
                    getFile(part[1]);
                } else {
                    System.out.println("WRONG BEGINNING! ");
                }

            } catch (Exception e) {
                System.out.println(e);
            }


        }
    }

    public void storeFile(String fileName, int fileLength) throws IOException {

        String[] extension = fileName.split(".");
        String folder = null;
        byte[] b = new byte[fileLength];

        //Initialise input stream for file
        InputStream is = soc.getInputStream();
        FileOutputStream fos = new FileOutputStream(fileName);
        is.read(b, 0, b.length);
        fos.write(b, 0, b.length);
        System.out.println("File called " + fileName + " has been written");
        System.out.println("File has been created! \n Completed");


    }

    public void addToJSON(String fileName) {

    }

    public void getFile(String fileName) throws IOException {
        ArrayList<String> folders = new ArrayList<>();
        folders.add("img");
        folders.add("notes");
        folders.add("midi");
        String[] extension = {".jpg", ".txt", ".mid"};
        for (int i = 0; i < folders.size(); i++) {
            DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
            File file = new File("src/main/resources/songs/ServerContents/" + folders.get(i) + "/" + fileName + extension[i]);
            FileInputStream fis = new FileInputStream(file);
            dos.writeInt((int) file.length());
            System.out.println((int) file.length());
            byte[] b = new byte[(int) file.length()];
            fis.read(b, 0, b.length);
            OutputStream os = soc.getOutputStream();
            os.write(b, 0, b.length);
            deleteFile(file);
        }
    }


    public void deleteFile(File file) {
        if (file.delete()) {
            System.out.println("File added to Game Contents");
        } else {
            System.out.println("Error: couldn't delete file");
        }
    }
}