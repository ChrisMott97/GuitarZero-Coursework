import java.io.File;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import java.util.ArrayList;
import java.util.List;


public class guitartry {
    final static String FILE = "MamaDo.mid";
    static List<List<String>> currentArray = new ArrayList<>();


    public static String instrumentName( int n ) {
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


    public static void getInstrument(long tick, int dat1, int count){

        currentArray.get(count).add("@" + tick + ", " + dat1);
//        int currLen = currentArray.size();
//        System.out.println(currentArray.get(currLen - 1));

    }
    public static void displayTrack( Track track ) {

        ArrayList<String> longestArray = new ArrayList<>();
        ArrayList<Integer> guitarchan = new ArrayList<>();

        for ( int i = 0; i < track.size(); i = i + 1 ) {
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
                        if(instrumentName(dat1).toLowerCase().contains("string") || instrumentName(dat1).toLowerCase().contains("gt") || instrumentName(dat1).toLowerCase().contains("guitar")) {
                            guitarchan.add(chan);
                            System.out.print("@" + tick + ", ");
                            System.out.println("Program change: " + instrumentName(dat1));
                        }
                        break;
                    case ShortMessage.NOTE_ON:
                        for(int j = 0; j < guitarchan.size(); j++) {
                            currentArray.add(new ArrayList<>());
                            if (guitarchan.get(j) == chan) {
                                getInstrument(tick, dat1,j);
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

        for (List<String> aCurrentArray : currentArray) {
            if (arrayLen < aCurrentArray.size()) {
                arrayLen = aCurrentArray.size();
                longestArray = (ArrayList<String>) aCurrentArray;
            }
        }
        for(int j = 0; j < longestArray.size(); j++){
            System.out.println(longestArray.get(j));
        }
    }
    public static void displaySequence( Sequence seq ) {
        Track[] trks = seq.getTracks();

        for ( int i = 0; i < trks.length; i++ ) {
            System.out.println( "Track " + i );
            displayTrack( trks[ i ] );
        }
    }


//    public static void writeToFile (ArrayList< String > arr) {
//        try {
//            FileWriter writer = new FileWriter("output.txt");
//        for (String int : arr) {
//            writer.write(int);
//        }
//        writer.close();
//        } catch ( IOException e ) {
//            //TODO handle this
//        }
//    }

    public static void main( String[] argv ) {
        try {
            Sequence seq = MidiSystem.getSequence( new File( FILE ) );
            displaySequence( seq );
        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
        }
    }
}
