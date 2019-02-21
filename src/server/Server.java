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

	//CODE TO RECIEVE FILES FROM CLIENT
	public static void recieveZip() throws ClassNotFoundException, IOException {
		
		serverSocket = new ServerSocket(1510);
		socket = serverSocket.accept();
		System.out.println("Server Created");
			 
		//File transfer stuff
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		FileOutputStream fos = null;
		byte [] buffer = new byte[200];
		  
		  // 1. Read file name.
		  Object o = ois.readObject();
		
		  if (o instanceof String) {
		  		fos = new FileOutputStream(new File(o.toString()));
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