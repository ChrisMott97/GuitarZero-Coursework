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

    Worker(Socket soc){
        this.soc = soc;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {


                System.out.println("Connected!!");
                DataInputStream dis = new DataInputStream(soc.getInputStream());

                String[] part = dis.readUTF().split("-");
                int size = Integer.parseInt(part[1]);
                if (part[0].equals("Send")) {
                    System.out.println("2");
                    while (size > 0) {
                        System.out.println("3");
                        String[] file = dis.readUTF().split("-");
                        System.out.println(file[0]);
                        storeFile(file[0], Integer.parseInt(file[1]));
                        System.out.println("4");
                        size -= 1;
                    }
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

        String[] extension = fileName.split(".");
        String folder = null;
        byte[] b = new byte[fileLength];

        if(extension[1].equals(".png")||extension[1].equals(".jpg")) {
            folder = "Images";
        }

        if(extension[1].equals(".mid")) {
            folder = "Music";
        }

        if(extension[1].equals(".txt")) {
            folder = "Notes";
        }
        //Initialise input stream for file
        InputStream is = soc.getInputStream();
        FileOutputStream fos = new FileOutputStream(folder+"/"+fileName);
        is.read(b,0,b.length);
        fos.write(b,0,b.length);
        System.out.println("File called " +fileName+" has been written" );
        System.out.println("File has been created! \n Completed");


    }

    public void getFile(String fileName, DataInputStream dis){

    }


}
