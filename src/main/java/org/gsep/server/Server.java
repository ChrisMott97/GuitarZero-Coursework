package org.gsep.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Niha Gummakonda
 * @version 1.0 07/03/2019
 */
public class Server extends Thread {
	
    public static final int PORT = 3335;
 
    public static void main(String[] args) throws IOException {
        ServerSocket s1 = new ServerSocket(PORT);
        while(true) {
            Socket soc = s1.accept();
            Worker wkr =new Worker(soc);
            wkr.run();
        }
    }
}  