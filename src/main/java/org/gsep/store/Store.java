package org.gsep.store;

import java.io.IOException;
import java.net.Socket;

public class Store {

    static String fileName;
    public void run(){

        try {
            Socket soc = new Socket("192.168.56.1", 3332);
            getFile(fileName, soc);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void getFile(String fileName, Socket soc){

    }
}
