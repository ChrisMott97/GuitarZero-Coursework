import java.io.*;
import javax.sound.midi.*;

import java.util.ArrayList;
import java.util.List;


/**
 * This class holds all code for converting a MIDI file into a form readable by the game during play mode.
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
     * Converts
     * @param track     Track from a MIDI file
     * @return          ArrayList of strings in the form "timing, note"
     */
    private static ArrayList <String> displayTrack( Track track, int resol ) {


        ArrayList<Integer> guitarChan = new ArrayList<>();
        List<List<String>> currentArray = new ArrayList<>();
        float mspertick = 0;
        for ( int i = 0; i < track.size(); i = i +1 ) {
            MidiEvent   evt  = track.get( i );
            MidiMessage msg = evt.getMessage();
//            if (msg instanceof MetaMessage && i == 0) {
//
//                MetaMessage mm = (MetaMessage) msg;
//                if(mm.getType()== SET_TEMPO){
//                    byte[] data = mm.getData();
//                    int tempo = (data[0] & 0xff) << 16 | (data[1] & 0xff) << 8 | (data[2] & 0xff);
//                    int bpm = 60000000 / tempo;
//                }
//            }
            if ( msg instanceof ShortMessage ) {
                final long         tick = evt.getTick();
                final ShortMessage smsg = (ShortMessage) msg;
                final int          chan = smsg.getChannel();
                final int          cmd  = smsg.getCommand();
                final int          dat1 = smsg.getData1();

                switch (cmd) {
                    case ShortMessage.PROGRAM_CHANGE:
                        if(instrumentName(dat1).toLowerCase().contains("string")
                                || instrumentName(dat1).toLowerCase().contains("gt")
                                || instrumentName(dat1).toLowerCase().contains("guitar")) {
                            guitarChan.add(chan);
                        }
                        break;
                    case ShortMessage.NOTE_ON:
                        for(int j = 0; j < guitarChan.size(); j++) {

                            currentArray.add(new ArrayList<>());
                            if (guitarChan.get(j) == chan) {
                                currentArray.get(j).add(tick + ", " + dat1 + " Channel : " + chan);
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
        int arrayLen = 0;
        ArrayList<String> longestArray = new ArrayList<>();
        for (List<String> aCurrentArray : currentArray) {

            if (arrayLen < aCurrentArray.size()) {
                arrayLen = aCurrentArray.size();
                longestArray = (ArrayList<String>) aCurrentArray;
            }
        }
        return longestArray;
    }


    private static File writeToFile ( ArrayList <String> arr ) {

        BufferedWriter bw = null;
        try {

            //Specify the file name and path here
            File file = new File("noteFile.txt");

            /* This logic will make sure that the file
             * gets created if it is not present at the
             * specified location*/
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

    public static File convertMIDI ( String MIDIFile ) {

        try {
            Sequence seq = MidiSystem.getSequence( new File( MIDIFile ) );
            int resol = seq.getResolution();
            Track[] trks = seq.getTracks();

            int longestLen = 0;
            ArrayList <String> trackArray = new ArrayList<>();

            for (Track trk : trks) {
                ArrayList<String> currentTrack = displayTrack(trk,resol);
                int trackSize = currentTrack.size();
                if (trackSize > longestLen) {
                    longestLen = trackSize;
                    trackArray = currentTrack;
                }
            }
            return writeToFile(trackArray);
        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
        }
        return null;
    }

    public static void main (String[] args) {
        File noteFile = convertMIDI("queen.mid");


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