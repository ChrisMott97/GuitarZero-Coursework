package org.gsep;

import java.io.*;
import static org.gsep.guitarMIDI.convertMIDI;

public class Main {


    public static void main (String[] args) {                       //To test file is written and passed back correctly
        //In real implementation, convertMIDI will be
        File noteFile = convertMIDI("queen.mid");      //called from externally

        try {
            FileReader fr = new FileReader(noteFile);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                //process the line
                System.out.println(line);
            }
        } catch (FileNotFoundException i ) {
            System.out.println("File not found lol");
        } catch (IOException e) {
            System.out.println("IO Error");
        }
    }
}
