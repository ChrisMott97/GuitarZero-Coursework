package org.gsep.server;

import org.gsep.manager.StoreManagerFrame;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Niha Gummakonda
 * @version 1.0 07/03/2019
 */
public class Server extends Thread {
	
    public static final int PORT = 3332;
    public static final int BUFFER_SIZE = 2002;
 
    public static void main(String[] args) throws IOException {
        ServerSocket s1 = new ServerSocket(PORT);
        while(true) {
            Socket soc = s1.accept();
            Worker wkr =new Worker(soc);
            wkr.run();
        }
    }
}  