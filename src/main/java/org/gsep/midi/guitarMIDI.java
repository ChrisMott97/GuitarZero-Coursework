package org.gsep.midi;

import java.io.*;
import javax.sound.midi.*;

import java.util.ArrayList;
import java.util.List;


/**
 * This class holds all code for converting a MIDI file into a form readable by the game during play mode.
 *
 * @author Abigail Lilley
 * @author Niha Gummakonda
 * @version 1.0, February 21st 2019
 */
public class guitarMIDI {

    /**
     * Returns the name of the synthesizer's nth instrument
     * @param n     Position of the instrument in the synthesizer's array of instruments
     * @return      The name of the instrument
     */
    private static String instrumentName( int n ) {
        try {

            final Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            final Instrument[] instrs = synth.getAvailableInstruments();
            synth.close();
            return instrs[ n ].getName();

        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 ); return "";
        }
    }


    /**
     * Converts a given track of a MIDI file into an ArrayList containing the information used in play mode.
     * A note's lane is decided by it's relative position within the range of notes (i.e. highest third in lane one,
     * lowest in lane three).
     * A note's colour is decided by it's relative position within the range on it's lane (i.e. higher half black,
     * lower half white). 0 corresponds to black, and 1 to white.
     *
     * Future development:
     * @param track     Track from a MIDI file
     * @return          ArrayList of strings in the form "timing, lane, colour"
     *                      n.b. lane refers to the path the note will take down the fret board in play mode
     *                           colour refers to whether the note is black or white
     */
    //TODO Change note colour logic so sharp and flat notes are black, all others are white (like a piano)

    public static ArrayList <String> getTrackNotes( Track track ) {


        ArrayList<Integer> guitarChan = new ArrayList<>();
        List<List<String>> currentArray = new ArrayList<>();
        for ( int i = 0; i < track.size(); i = i +1 ) {
            MidiEvent   evt  = track.get( i );
            MidiMessage msg = evt.getMessage();

            if ( msg instanceof ShortMessage ) {

                final long         tick = evt.getTick();
                final ShortMessage smsg = (ShortMessage) msg;
                final int          chan = smsg.getChannel();
                final int          cmd  = smsg.getCommand();
                final int          dat1 = smsg.getData1();

                switch (cmd) {
                    case ShortMessage.PROGRAM_CHANGE:
                        if(instrumentName(dat1).toLowerCase().contains("guitar")
                                || instrumentName(dat1).toLowerCase().contains("gt")
                                || instrumentName(dat1).toLowerCase().contains("string")) {
                            guitarChan.add(chan);
                        }
                        break;
                    case ShortMessage.NOTE_ON:
                        for (int j = 0; j < guitarChan.size(); j++) {
                            currentArray.add(new ArrayList<>());
                            if (guitarChan.get(j) == chan) {

                                switch (dat1 % 6) {
                                    case 0:
                                        currentArray.get(j).add(tick + " 1 0");
                                        break;
                                    case 1:
                                        currentArray.get(j).add(tick + " 1 1");
                                        break;
                                    case 2:
                                        currentArray.get(j).add(tick + " 2 0");
                                        break;
                                    case 3:
                                        currentArray.get(j).add(tick + " 2 1");
                                        break;
                                    case 4:
                                        currentArray.get(j).add(tick + " 3 0");
                                        break;
                                    case 5:
                                        currentArray.get(j).add(tick + " 3 1");
                                        break;
                                }
                                break;
                            }
                        }
                        break;
                    default:
                        /* ignore other commands */
                        break;
                }

            }
        }
        ArrayList<String> longestArray = new ArrayList<>();
        int arrayLen = 0;

        for (List<String> aCurrentArray : currentArray) {

            if (arrayLen < aCurrentArray.size()) {
                arrayLen = aCurrentArray.size();
                longestArray = (ArrayList<String>) aCurrentArray;
            }
        }
        return longestArray;
    }


    /**
     * Takes a string array as input and writes each element to a new line in a .txt file called 'noteFile.txt'
     * @param arr   An ArrayList. Each element hold a string on information about a note in the order they're played
     * @return      A .txt file called 'noteFile.txt' where each line is an element of arr
     */
    public static File writeToFile ( ArrayList <String> arr ) {

        BufferedWriter bw = null;
        try {

            File file = new File("noteFile.txt");

            //CHECK THIS
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);

            for (String noteInfo: arr) {
                bw.write(noteInfo);
                bw.newLine();
            }
            return file;

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (Exception ex) {
                System.out.println("Error in closing the BufferedWriter" + ex);
            }
        }
        return null;
    }

    public static File xyz(ArrayList arr){
        for (int i =0; i <arr.size(); i++){
            if (i < arr.size() - 1) {
                String[] j = arr.get(i).toString().split("\\s+");
                String[] k = arr.get(i + 1).toString().split("\\s+");
                int j1 = Integer.parseInt(j[0]);
                int k1 = Integer.parseInt(k[0]);
                if (k1 < j1 + 5 && k1 != j1) {
                    arr.remove(i + 1);
                }
            }
        }


        return writeToFile(arr);
    }

    /**
     * Takes the name of a MIDI file and returns a .txt file where information for each note is written on a new line.
     * A line looks as follows: timing, lane, colour
     *      Timing:         when the note is played in the song
     *      Lane (1-3):     the path the note takes in play mode
     *      Colour (0/1):   which button for the lane corresponds to that note (0 for black, 1 for white)
     * @param MIDIFileName      Name of the MIDI file you wish to convert to a play mode compatible .txt file
     * @return                  A .txt file, each line has information needed in play mode for a given note
     */
    public static File convertMIDI ( String MIDIFileName ) {
        //TODO make sure it handles when a) file not found b) input file in not a MIDI file
        try {

            Sequence seq = MidiSystem.getSequence( new File(MIDIFileName));
            Sequencer seqr = MidiSystem.getSequencer();
            seqr.setSequence(seq);
//            long tickspermicrosec = seq.getMicrosecondLength()/seq.getTickLength();
//            System.out.println(tickspermicrosec + " " + seq.getTickLength() + " " + seq.getMicrosecondLength());
            Track[] trks = seq.getTracks();
            int longestLen = 0;
            ArrayList <String> trackArray = new ArrayList<>();
            System.out.println(seqr.getTempoInBPM());
            for (Track trk : trks) {

                ArrayList<String> currentTrack = getTrackNotes(trk);
                int trackSize = currentTrack.size();

                if (trackSize > longestLen) {
                    longestLen = trackSize;
                    trackArray = currentTrack;
                }
            }

            return xyz(trackArray);
        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
        }
        return null;
    }

    public static void main (String[] args) {                       //To test file is written and passed back correctly
        //In real implementation, convertMIDI will be
        File noteFile = convertMIDI("MamaDo.mid");      //called from externally
//        ArrayList<String> arr = new ArrayList<>();

//        arr.add("12345 567 677");
//        arr.add("12345 567 677");
//        arr.add("12345 567 677");
//        arr.add("12345 567 677");
//        arr.add("12347 434 674");
//        arr.add("17282 574 267");
//        arr.add("17292 574 267");
//        arr.add("17305 363 992");
//        arr.add("17309 574 267");
//        arr.add("19827 363 992");
//        arr.add("19923 574 267");
//        arr.add("19927 363 992");
//        arr.add("20123 363 992");
//        xyz(arr);
//        for(int i = 0; i < arr.size(); i ++){
//            System.out.println(arr.get(i));
//        }
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