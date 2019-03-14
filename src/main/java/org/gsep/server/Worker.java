package org.gsep.server;

import java.io.*;
import java.net.Socket;

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
            try {


                System.out.println("Connected!!");
                DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
                dos.writeUTF("Welcome to socket! ");

                DataInputStream dis = new DataInputStream(soc.getInputStream());
                String filename = dis.readUTF();

                String[] part = filename.split("-");
                if(part[0].equals("Send")){
                    storeFile(part[1], dis);
                }else if (part[0].equals("Get")){
                   getFile(part[1],dis);
                }
                else{
                    System.out.println("WRONG BEGINNING! ");
                }

            } catch (Exception e) {
                System.out.println(e);
            }



    }

    public void storeFile(String filename, DataInputStream dis) throws IOException {

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("server" + filename));
            long fileSize = dis.readLong();
            System.out.println(fileSize);
            System.out.println("Sending File: " + filename);
            int n;
            byte[] buf = new byte[1024];
            while (fileSize > 0
                    && (n = dis.read(buf, 0, (int) Math.min(buf.length, fileSize))) != -1) {
                bos.write(buf, 0, n);
                System.out.println(fileSize);
                fileSize -= 1;
            }

            System.out.println("File has been created! \n Completed");



    }

    public void getFile(String fileName, DataInputStream dis){

    }


}
