package org.gsep.server;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
 
/*
 * Server - THIS CLASS HAS NOT YET BEEN IMPLEMENTED
 * 
 * @author Humzah Malik
 * @version 1.0
 * 
 */
public class Server extends Thread {
	
    public static final int PORT = 3332;
    public static final int BUFFER_SIZE = 2002;
 
    /**
     * This method is not yet being used. It will be used to invoke the saveFile() method that will accept the stream from the client.
     */
    @Override
    public void run() {
        try {
        		//Start server on socket of number 'PORT'
            ServerSocket serverSocket = new ServerSocket(PORT);
            //While connection exists.
            while (true) {
                Socket s = serverSocket.accept();
                saveFile(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method has not yet been completed or implemented. It eventually will establish a connection to the client, and accept its stream of files.
     * @param socket 
     * @throws Exception
     */
    private void saveFile(Socket socket) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        FileOutputStream fos = null;
        byte [] buffer = new byte[BUFFER_SIZE];
 
        // Read the name of the file
        Object o = ois.readObject();
 
        if (o instanceof String) {
            fos = new FileOutputStream(o.toString());
        } else {
            throwException("Something is wrong");
        }
 
        // Read the file, byte by byte, until the end
        Integer bytesRead = 0;
 
        do {
            o = ois.readObject();
 
            if (!(o instanceof Integer)) {
                throwException("Something is wrong");
            }
 
            bytesRead = (Integer)o;
 
            o = ois.readObject();
 
            if (!(o instanceof byte[])) {
                throwException("Something is wrong");
            }
 
            buffer = (byte[])o;
 
            //Write the data to an output file
            fos.write(buffer, 0, bytesRead);
           
        } while (bytesRead == BUFFER_SIZE);
         
        System.out.println("File transfer success");
         
        fos.close();
 
        ois.close();
        oos.close();
    }
 
    /**
     * This method handles exceptions within the transfering of the zip file.
     * @param message
     * @throws Exception
     */
    public static void throwException(String message) throws Exception {
        throw new Exception(message);
    }
 

}  