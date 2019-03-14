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
 * @version 2.0, March 7th 2019
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

    public static String noteName( int n ) {
        final String[] NAMES =
                { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
        final int octave = (n / 12) - 1;
        final int note   = n % 12;
        return NAMES[ note ] + octave;
    }

    /**
     * Converts a given track of a MIDI file into an ArrayList containing the ticks and notes of
     * an instrument that contains strings
     *
     * Future development:
     * @param track     Track from a MIDI file
     * @return          ArrayList of strings in the form "ticks note" which is the longest for that track
     *
     */

    public static ArrayList <String> getTrackNotes( Track track ) {
        ArrayList<Integer> guitarChannnel = new ArrayList<>();
        List<List<String>> currentArray = new ArrayList<>();
        //Goes through tracks
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
        //Finds longest instrument in the track
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

    /**
     * It randomly assigns where the note will be coming down in play mode. Its in the format ticks,
     * row1,row2,row3. It puts either 0,1 or 2 in each row. 0 means the row is empty/open. 1 means
     * the black button should be pressed on that row and 2 means white.
     * @param arr the longest array after its been concatenated
     * @return the new array created is going into writeToFile method
     */

    //TODO could change it so that what button is pressed is determined by how high/low the note is
    public static File addNotes(ArrayList arr){
        ArrayList<String> newArr = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < arr.size(); i++){
            int count=0;
            //Gets the ticks
            String[] iSplit = arr.get(i).toString().split("\\s+");
            int iTick = Integer.parseInt(iSplit[0]);
            System.out.println(iSplit[1]);
            int note = Integer.parseInt(iSplit[1]);
            int min = 200, max = 0;
            for (Object anArr : arr) {
                String[] jSplit = anArr.toString().split("\\s+");
                int jTick = Integer.parseInt(jSplit[0]);
                int jNote = Integer.parseInt(jSplit[1]);
                //Compares the ticks with themselves
                if (iTick == jTick) {
                    count = count + 1;
                }

                if (i == 0){
                    if(max < jNote){
                        max = jNote;
                    }else if(min > jNote){
                        min = jNote;
                    }
                }
            }
            //All randomly assigned
            //If only 1 note is played at that tick
            if(count == 1){
                int row = rand.nextInt(3) ;
                int colr = rand.nextInt(2) + 1;
                if (row == 0){
                    newArr.add(iTick + "," + colr + ",0,0");
                }else if (row == 1) {
                    newArr.add(iTick + ",0," + colr + ",0");
                }else if(row == 2){
                    newArr.add(iTick + ",0,0," + colr);
                }
                //If 2 notes are played at that tick
            }else if(count == 2){
                int row = rand.nextInt(3);
                int row2;
                do{
                    row2 = rand.nextInt(3);
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
                //If more than 2 notes are played at that tick
            }else if (count > 2){
                int colr = rand.nextInt(2) + 1;
                int colr1 = rand.nextInt(2) + 1;
                int colr2 = rand.nextInt(2) + 1;
                newArr.add(iTick + "," + colr + "," + colr1 + "," + colr2 );
            }
            i = i + count - 1;
        }

        return writeToFile(newArr);

    }

    /**
     * If the notes are too close together that it is impossible for someone to play
     * the game, this method restricts the notes so that there is a minimum distance
     * berween the ticks
     * @param arr The longest instrument notes array in whole midi file
     * @return calls the addNotes function with the concatenated array
     */
    public static File concatArr(ArrayList arr){
        for (int i =0; i <arr.size(); i++){
            //Checks current tick with next tick
            if (i < arr.size() - 1) {
                String[] iSplit = arr.get(i).toString().split("\\s+");
                String[] nextSplit = arr.get(i + 1).toString().split("\\s+");
                int iTick = Integer.parseInt(iSplit[0]);
                int nextTick = Integer.parseInt(nextSplit[0]);
                if (nextTick < iTick + 100 && nextTick != iTick) {
                    //Removes it if the ticks are too close
                    arr.remove(i + 1);
                    i = i - 2;
                }
            }
        }
        return addNotes(arr);
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

            Sequence seq = MidiSystem.getSequence(new File (MIDIFileName));
            Sequencer seqr = MidiSystem.getSequencer();
            seqr.setSequence(seq);
            Track[] trks = seq.getTracks();
            int longestLen = 0;
            ArrayList <String> trackArray = new ArrayList<>();
//            double perTick = 60000/(seqr.getTempoInBPM()*seq.getResolution());
//            double bperms = seqr.getTempoInBPM()*1/60000;
//            double tickperbeat = 1/(bperms*perTick);
            for (Track trk : trks) {

                ArrayList<String> currentTrack = getTrackNotes(trk);
                int trackSize = currentTrack.size();

                if (trackSize > longestLen) {
                    longestLen = trackSize;
                    trackArray = currentTrack;
                }
            }
            return concatArr(trackArray);

        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
        }
        return null;
    }

    public static void main (String[] args) {                       //To test file is written and passed back correctly
        //In real implementation, convertMIDI will be
        guitarMIDI gm = new guitarMIDI();

        File noteFile = gm.convertMIDI("/queen.mid");      //called from externally
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