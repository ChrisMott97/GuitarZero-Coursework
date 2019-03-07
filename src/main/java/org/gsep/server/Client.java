package org.gsep.server;

import java.io.*;
import java.net.Socket;

public class Client {



    public static void main(String[] args){

        try {
             Socket soc = new Socket("192.168.56.1",3332);
            DataInputStream dis = new DataInputStream(soc.getInputStream());
            String msg = dis.readUTF();
            System.out.println(msg);
            DataOutputStream dos = new DataOutputStream((soc.getOutputStream()));
            File file = createFile();
            dos.writeUTF("File has been created of length: " + file.length());
            sendFile(file,soc);

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static File createFile() throws IOException {
        File file = new File("hello.txt");
        file.createNewFile();

        String fileContent = "Hello Learner !! Welcome to howtodoinjava.com.";

        FileWriter fileWriter = new FileWriter("hello.txt");
        fileWriter.write(fileContent);
        fileWriter.close();

        return file;
    }

    public static void sendFile(File file, Socket soc) throws IOException {

        DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
        DataInputStream dis = new DataInputStream(soc.getInputStream());

        byte b[]=new byte [1024];
        System.out.println("Receving file..");
        FileOutputStream fos=new FileOutputStream(file,true);
        long bytesRead;
        do
        {
            bytesRead = dis.read(b, 0, b.length);
            fos.write(b,0,b.length);
        }while(!(bytesRead<1024));
        System.out.println("Comleted");
        fos.close();
        dos.close();
        soc.close();
    }
}
