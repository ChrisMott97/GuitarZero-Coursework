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
            String msg = dis.readUTF();
            System.out.println(msg);

            String filename = dis.readUTF();
            System.out.println("Sending File: "+filename);
            dos.writeUTF(filename);
            FileOutputStream fin=new FileOutputStream("server" + filename);
            byte b[]=new byte [1024];
            dis.read(b,0,b.length);
            fin.write(b,0,b.length);
            System.out.println("File has been created! \n Completed");

            fin.close();
            dos.close();
            dis.close();
            soc.close();
        }catch (Exception e){
            System.out.println(e);
        }


    }

}
