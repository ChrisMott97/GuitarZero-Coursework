package org.gsep.server;

import java.io.*;
import java.net.Socket;

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


        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void getFile(){

    }
}
