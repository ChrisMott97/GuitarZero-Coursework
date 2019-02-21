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
			createConnection();
            System.out.println("Hola mundo");
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

//create connection
public static void createConnection() {
	try {
		serverSocket = new ServerSocket(1500);
		socket = serverSocket.accept();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
}

//CODE TO RECIEVE FILES FROM CLIENT
public static void recieveFiles() throws ClassNotFoundException, IOException {
	createConnection();
	//File transfer stuff
	ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
	ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
	  FileOutputStream fos = null;
	  byte [] buffer = new byte[200];
	  
	  // 1. Read file name.
	  Object o = ois.readObject();
	
	  if (o instanceof String) {
	  		fos = new FileOutputStream(new File(name));
	  } else {
	
	  }
	
	  // 2. Read file to the end.
	  Integer bytesRead = 0;
	
	  do {
	      o = ois.readObject();
	
	      if (!(o instanceof Integer)) {
	         ;
	      }
	
	      bytesRead = (Integer)o;
	
	      o = ois.readObject();
	
	      if (!(o instanceof byte[])) {
	         ;
	      }
	
	      buffer = (byte[])o;
	
	      // 3. Write data to output file.
	      fos.write(buffer, 0, bytesRead);
	     
	  } while (bytesRead == 2002);
	   
	  System.out.println("File transfer success");
	   
	  fos.close();
	  ois.close();
	
	}

}