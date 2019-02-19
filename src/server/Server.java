package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
 
public class Server {
 
    /**
     * @param args
     */
    public static void start() throws IOException,EOFException {
        // TODO Auto-generated method stub
     FileOutputStream fos;
     BufferedOutputStream bos;
     OutputStream output;
     DataOutputStream dos;
     int len;
     int smblen; 
     InputStream in;
     boolean flag=true;
     DataInputStream clientData;
     BufferedInputStream clientBuff;
  
    ServerSocket serverSocket = new ServerSocket(2104);
    Socket clientSocket = null;
    clientSocket = serverSocket.accept();
   
    while (true){
    //while(true && flag==true){
      while(flag==true){  
           
            in = clientSocket.getInputStream(); //used  
            clientData = new DataInputStream(in); //use 
            clientBuff = new BufferedInputStream(in); //use 
          
            System.out.println("Starting...");  
                
                int fileSize = clientData.read();
                    
                ArrayList<File>files=new ArrayList<File>(fileSize); //store list of filename from client directory
                ArrayList<Integer>sizes = new ArrayList<Integer>(fileSize); //store file size from client
                //Start to accept those filename from server
                for (int count=0;count < fileSize;count ++){
                        File ff=new File(clientData.readUTF());
                        files.add(ff);
                }
                 
                for (int count=0;count < fileSize;count ++){
                     
                        sizes.add(clientData.readInt());
                }
                 
               for (int count =0;count < fileSize ;count ++){  
                    
                   if (fileSize - count == 1){
                       flag =false;
                   }
     
                  len=sizes.get(count);
                            
                System.out.println("File Size ="+len);
                
                output = new FileOutputStream(files.get(count));
                dos=new DataOutputStream(output);
                bos=new BufferedOutputStream(output);
               
                byte[] buffer = new byte[1024];  
                 
                bos.write(buffer, 0, buffer.length); //This line is important
                 
                while (len > 0 && (smblen = clientData.read(buffer)) > 0) { 
                    dos.write(buffer, 0, smblen); 
                      len = len - smblen;
                      dos.flush();
                    }  
                  dos.close();  //It should close to avoid continue deploy by resource under view
               }   
                            
       }
        
          if (flag==false){
             clientSocket = serverSocket.accept();
             flag = true;
          }
       
         } //end of while(true)
        
      } 
}