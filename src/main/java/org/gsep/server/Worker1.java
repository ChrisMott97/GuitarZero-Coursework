package org.gsep.server;

import java.io.*;
import java.net.Socket;

public class Worker1 implements Runnable{
    /**
     * @author Niha Gummakonda
     * @version 1.0 07/03/2019
  */

        public static final int BUFFER_SIZE = 3000;
        private Socket soc;
        private String name;


        Worker1(Socket soc){
            this.soc = soc;
        }

        @Override
        public void run() {
            String extension = null;
            String request = null;
            String folder = null;


            try {

                //Recieve file extension
                DataInputStream dis = new DataInputStream(soc.getInputStream());

                request = dis.readUTF();
                if (request.contains("FileName")) {
                    String[] values = request.split(",");
                    System.out.println("Recieved file name "+values[1]);
                    name = values[1];
                }

                request = dis.readUTF();
                if (request.contains("fileSend")) {

                    //Get extension
                    String[] values = request.split(",");
                    System.out.println("Recieved extension "+values[1]);
                    extension = values[1];

                    //Initialise byte variables
                    byte[] b = new byte[BUFFER_SIZE];
                    int len = 0;
                    int bytcount = 1024;

                    if(extension.equals(".png")||extension.equals(".jpg")) {
                        folder = "Images";
                    }

                    if(extension.equals(".mid")) {
                        folder = "Music";
                    }

                    if(extension.equals(".txt")) {
                        folder = "Notes";
                    }

                    //Create file to be written to
                    FileOutputStream inFile = new FileOutputStream(folder+"/"+name+extension);

                    //Initialise input stream for file
                    InputStream is = soc.getInputStream();
                    BufferedInputStream in2 = new BufferedInputStream(is, 1024);

                    //Read file and write to created file
                    while ((len = in2.read(b, 0, 1024)) != -1) {
                        bytcount = bytcount + 1024;
                        inFile.write(b, 0, len);
                    }
                    System.out.println("File called " +name+extension+" has been written" );
                }


            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
