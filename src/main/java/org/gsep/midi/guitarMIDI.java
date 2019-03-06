package org.gsep.midi;

import java.io.*;
import javax.sound.midi.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
            final Instrument[] availableInstruments = synth.getAvailableInstruments();
            synth.close();

            return availableInstruments[n].getName();
        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
            return "";
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
        ArrayList<Integer> guitarChannnel = new ArrayList<>();
        List<List<String>> currentArray = new ArrayList<>();

        for ( int i = 0; i < track.size(); i = i +1 ) {
            MidiEvent midiEvent = track.get( i );
            MidiMessage midiMessage = midiEvent.getMessage();

            if ( midiMessage instanceof ShortMessage ) {
                final long tick = midiEvent.getTick();
                final ShortMessage smsg = (ShortMessage) midiMessage;
                final int chan = smsg.getChannel();
                final int cmd = smsg.getCommand();
                final int dat1 = smsg.getData1();

                switch (cmd) {
                    case ShortMessage.PROGRAM_CHANGE:
                        if(instrumentName(dat1).toLowerCase().contains("guitar")
                                || instrumentName(dat1).toLowerCase().contains("gt")
                                || instrumentName(dat1).toLowerCase().contains("string")) {
                            guitarChannnel.add(chan);
                        }
                        break;
                    case ShortMessage.NOTE_ON:
                        for (int j = 0; j < guitarChannnel.size(); j++) {
                            currentArray.add(new ArrayList<>());
                            if (guitarChannnel.get(j) == chan) {
                                currentArray.get(j).add(tick + " " + dat1);
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

    public static File addNotes(ArrayList arr, double perTick){
        ArrayList<String> newArr = new ArrayList<>();
        newArr.add(perTick + " ms/tick");
        Random rand = new Random();

        for(int i = 0; i < arr.size(); i++){
            int count=0;
            String[] iSplit = arr.get(i).toString().split("\\s+");
            int iTick = Integer.parseInt(iSplit[0]);

            for (Object anArr : arr) {
                String[] jSplit = anArr.toString().split("\\s+");
                int jTick = Integer.parseInt(jSplit[0]);
                if (iTick == jTick) {
                    count = count + 1;
                }
            }

            if(count == 1){
                int row = rand.nextInt(3) + 1;
                if (row == 0){
                    int colr = rand.nextInt(2) + 1;
                    newArr.add(iTick + "," + colr + ",0,0");
                }else if (row == 1) {
                    int colr = rand.nextInt(2) + 1;
                    newArr.add(iTick + ",0," + colr + ",0");
                }else if(row == 2){
                    int colr = rand.nextInt(2) + 1;
                    newArr.add(iTick + ",0,0," + colr);
                }
            }else if(count == 2){
                int row = rand.nextInt(3) + 1;
                int row2;
                do{
                    row2 = rand.nextInt(3) + 1;
                }while (row2 == row);
                if (row == 0 && row2 == 1 || row == 1 && row2 == 0){
                    int colr = rand.nextInt(2) + 1;
                    int colr1 = rand.nextInt(2) + 1;
                    newArr.add(iTick + "," + colr + "," + colr1 + ",0");
                }else if (row == 1 && row2 == 2 || row == 2 && row2 == 1){
                    int colr = rand.nextInt(2) + 1;
                    int colr1 = rand.nextInt(2) + 1;
                    newArr.add(iTick + ",0," + colr + "," + colr1);
                }else if(row == 2 && row2 == 0 || row == 0 && row2 == 2){
                    int colr = rand.nextInt(2) + 1;
                    int colr1 = rand.nextInt(2) + 1;
                    newArr.add(iTick + "," + colr + ",0," + colr1);
                }
            }else if (count > 2){
                int colr = rand.nextInt(2) + 1;
                int colr1 = rand.nextInt(2) + 1;
                int colr2 = rand.nextInt(2) + 1;
                newArr.add(iTick + "," + colr + "," + colr1 + "," + colr2 );
            }
            i = i + count - 1;
        }

        return writeToFile(newArr);
        //It has nothing to do with notes, is that okay????
    }

    public static File concatArr(ArrayList arr, double perTick){
        for (int i =0; i <arr.size(); i++){
            if (i < arr.size() - 1) {
                String[] iSplit = arr.get(i).toString().split("\\s+");
                String[] nextSplit = arr.get(i + 1).toString().split("\\s+");
                int iTick = Integer.parseInt(iSplit[0]);
                int nextTick = Integer.parseInt(nextSplit[0]);
                if (nextTick < iTick + 100 && nextTick != iTick) {
                    arr.remove(i + 1);
                    i = i - 2;
                }
            }
        }
        return addNotes(arr,perTick);
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
    public File convertMIDI ( String MIDIFileName ) {
        //TODO make sure it handles when a) file not found b) input file in not a MIDI file
        try {

            Sequence seq = MidiSystem.getSequence(new File(getClass().getResource(MIDIFileName).getFile()));
            Sequencer seqr = MidiSystem.getSequencer();
            seqr.setSequence(seq);
            Track[] trks = seq.getTracks();
            int longestLen = 0;
            ArrayList <String> trackArray = new ArrayList<>();
            double perTick = 60000/(seqr.getTempoInBPM()*seq.getResolution());
            for (Track trk : trks) {

                ArrayList<String> currentTrack = getTrackNotes(trk);
                int trackSize = currentTrack.size();

                if (trackSize > longestLen) {
                    longestLen = trackSize;
                    trackArray = currentTrack;
                }
            }
            return concatArr(trackArray, perTick);

        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
        }
        return null;
    }

    public static void main (String[] args) {                       //To test file is written and passed back correctly
        //In real implementation, convertMIDI will be
        guitarMIDI boi = new guitarMIDI();

        File noteFile = boi.convertMIDI("/queen.mid");      //called from externally
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