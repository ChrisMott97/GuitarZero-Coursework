package org.gsep.server;

import java.io.*;
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
                        String fileName = dis.readUTF();
                        System.out.println(fileName);
                        storeFile(fileName);
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

    public void storeFile(String fileName) throws IOException {

        FileOutputStream inFile = new FileOutputStream(fileName);

        byte[] b = new byte[3000];
        int len = 0;
        int bytcount = 1024;
        //Initialise input stream for file
        InputStream is = soc.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is, 1024);

        //Read file and write to created file
        while ((len = bis.read(b, 0, 1024)) != -1) {
            bytcount = bytcount + 1024;
            inFile.write(b, 0, len);
        }
        System.out.println("File called " +fileName+" has been written" );
            System.out.println("File has been created! \n Completed");


    }

    public void getFile(String fileName, DataInputStream dis){

    }


}
