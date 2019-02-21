package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
 
public class Server {
 
	static ServerSocket   serverSocket;
    static Socket         socket;
    static BufferedReader reader;
	static BufferedWriter writer;
	static String name;
    
	public static void run()
    {
		try
        {
            System.out.println("Hola mundo");
            serverSocket = new ServerSocket(1500);
            socket = serverSocket.accept();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while(true)
            {
                String command = reader.readLine();
                if(command.contains("mkdir"))
                {
                		new File(name).mkdirs();
                }
                
                if(command.equals("stop"))
                {
                	socket.close();
                }
                
                if(command.contains("FileName")) 
                {
                	 	System.out.println("FileName command Recieved");
	                	name=command;
			    		String[] words=name.split("\\s");
			    		name=words[1];
                }
            }
        }
        catch(Exception err)
        {

        }
    }


//exception handling
public static void throwException(String message) throws Exception {
    throw new Exception(message);
}


}