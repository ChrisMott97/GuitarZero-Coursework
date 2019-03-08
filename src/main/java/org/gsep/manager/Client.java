package org.gsep.manager;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author Niha Gummakonda
 * @version 1.0 07/03/2019
 */
public class Client {


    public static void main(String[] args){
        
        try {
            Socket soc = new Socket("localhost",3332);
            DataInputStream dis = new DataInputStream(soc.getInputStream());
            String msg = dis.readUTF();
            System.out.println(msg);
            DataOutputStream dos = new DataOutputStream((soc.getOutputStream()));
            String file = "Hello.zip";
            dos.writeUTF("File has been created of length: " + file.length());
            sendFile(file,soc);
        }catch(Exception e){
            System.out.println(e);
        }
    }

//    public static File createFile() throws IOException {
//        File file = new File("hello.txt");
//        file.createNewFile();
//
//        String fileContent = "Hello World!!! How are youuuu?? ";
//
//        FileWriter fileWriter = new FileWriter("hello.txt");
//        fileWriter.write(fileContent);
//        fileWriter.close();
//
//        return file;
//    }

    public static void sendFile(String file, Socket soc) throws IOException {

        DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
        System.out.println("Receving file..");
        FileInputStream fos =new FileInputStream(file);
        dos.writeUTF(file);
        byte[] b = new byte[1024];
        fos.read(b,0,b.length);
        dos.write(b,0,b.length);
        System.out.println("Comleted");

    }
}
