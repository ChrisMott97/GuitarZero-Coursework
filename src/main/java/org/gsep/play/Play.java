package org.gsep.play;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.gsep.controller.*;

public class Play {
    static final int CANVASWIDTH = 950;
    static final int CANVASHEIGHT = 700;
    private Scene scene;
    private NoteHighwayModel model;
    private NoteHighwayView view;
    private NoteHighwayController controller;
    private File midiFile;
    private Map<Integer, Note[]> songSequence;

//    private final static String[] BUTTONNAMES = { 	"fret1_white",
//                                                    "fret1_black",
//                                                    "fret2_white",
//                                                    "fret2_black",
//                                                    "fret3_white",
//                                                    "fret3_black",
//                                                    "zeroPower",
//                                                    "strumBar",
//                                                    "escape",
//                                                    "power",
//                                                    "bender",
//                                                    "whammy"	    	};
    //int[] buttonNums = { 0, 1, 4, 2, 5, 3, 8, 16, 10, 12, 13, 14};


    //private final static int[] BUTTONNUMS = { 0, 1, 4, 2, 5, 3, 8, 15, 10, 12, 13, 17};
    /* Index of the component in the component array. Order corresponds to the names in BUTTONNAMES */

    /**
     * @author Örs Barkanyi
     * @author Abigail Lilley
     * Constructor for Play Mode
     *
     * @param noteFilePath the path to the selected note file
     * @param midiFilePath the path to the selected midi file
     */
    public Play(String noteFilePath, String midiFilePath){
        //initialise scene
        Group root = new Group();
        this.scene = new Scene(root);

//        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                System.out.println(mouseEvent.getSceneY());
//                System.out.println(mouseEvent.getSceneX());
//            }
//        });

        root.getChildren().add(createBackground());

        //set up mvc
        this.model = new NoteHighwayModel();
        this.view = new NoteHighwayView(root);
        this.controller = new NoteHighwayController(model, view);

        //find files
        try{
            this.songSequence = readFile(getClass().getResource(noteFilePath).getFile());
        } catch (Exception e) {
            System.out.println("Note file not found or invalid");
            e.printStackTrace();
            System.exit(1);
        }

        try{
            this.midiFile = new File(getClass().getResource(midiFilePath).getFile());
        } catch (Exception e){
            System.out.println("MIDI file Not found");
            e.printStackTrace();
            System.exit(1);
        }
    }


    public Scene getScene() {
        return scene;
    }

    /**
     * @author Örs Barkanyi
     * Invokes the start play mode
     */
    public void play(){
        view.startRender();
        try{
            controller.play(songSequence,midiFile);
        } catch (Exception e){
            System.out.println("Couldn't play MIDI file");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * @author humzahmalik
     * Setting bakckground image as the fret board
     */
    public ImageView createBackground(){
        File file = new File(getClass().getResource("/play/highway.png").getFile());
        Image image = new Image(file.toURI().toString());

        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(CANVASWIDTH);
        imageView.setPreserveRatio(true);

        return imageView;
    }

    /**
     * @author humzahmalik
     * Method that reads notes file into an array
     *
     * @throws IOException
     *
     */
    public LinkedHashMap readFile(String f) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(f));
        String str;

        //Create ArrayList to hold the lists of notes
        //Create dictionary
        LinkedHashMap mapA = new LinkedHashMap();

        //While there is a line, add it to the list
        while((str = in.readLine()) != null){

            //Create dictionary value list
            Note[] dictValue;

            //split string
            String[] split = str.split(",");
            //Create dictioanry value list
            dictValue = new Note[] {null, null, null};
            dictValue[0]=checkNote(Integer.parseInt(split[1]));

            dictValue[1]=checkNote(Integer.parseInt(split[2]));

            dictValue[2]=checkNote(Integer.parseInt(split[3]));

            //add to dictionary
            mapA.put(Integer.parseInt(split[0]), dictValue);
        }
        return mapA;
    }

    /**
     * @author humzahmalik
     * Method checking whether number corresponds to black, white or empty note
     * @return Note value
     */
    public Note checkNote(int num) {
        Note type = null;

        if(num==0) {
            type= Note.OPEN;
        }
        if(num==1) {
            type= Note.BLACK;
        }
        if(num==2) {
            type= Note.WHITE;
        }

        return type;

    }

//    private void linkGuitar() {
//        ControllerEnvironment cenv = ControllerEnvironment.getDefaultEnvironment();
//        Controller[] ctrls = cenv.getControllers();
//        GuitarEventHandler guitarEventHandler = new GuitarEventHandler(controller);
//        int[] buttonNums;
//        final int[] windowsButtonNums = { 0, 1, 4, 2, 5, 3, 8, 16, 10, 12, 13, 14};
//        final int[] unixButtonNums    = { 0, 1, 4, 2, 5, 3, 8, 16, 10, 12, 13, 14};;
//        final int[] macButtonNums     = { 0, 1, 4, 2, 5, 3, 8, 15, 10, 12, 13, 17};
//
//
//        try {
//            String osName = System.getProperty("os.name");
//            if (osName == null) {
//                throw new IOException("os.name not found");
//            }
//            osName = osName.toLowerCase(Locale.ENGLISH);
//            if (osName.contains("windows")) {
//                buttonNums = windowsButtonNums;
//            } else if (osName.contains("linux")
//                    || osName.contains("mpe/ix")
//                    || osName.contains("freebsd")
//                    || osName.contains("irix")
//                    || osName.contains("digital unix")
//                    || osName.contains("unix")) {
//                buttonNums = unixButtonNums;
//            } else if (osName.contains("mac os")) {
//                buttonNums = macButtonNums;
//                System.out.println("THIS IS A MAC");
//            } else {
//                throw new IOException("os.name not supported");
//            }
//
//            Button[] buttons = new Button[ BUTTONNAMES.length ];
//            for ( int i = 0; i < buttons.length; i = i + 1 ) {
//                buttons[ i ] = new Button( BUTTONNAMES[i], buttonNums[i]);
//                buttons[ i ].addButtonListener( guitarEventHandler );			/* Adding listeners to Buttons depending on the mode */
//                Thread buttonThread = new Thread(buttons[ i ]);
//                buttonThread.start();								/* Starting a thread for each Button */
//            }
//
//        } catch (IOException ex) {
//            System.out.println("OS not identified, can't run game");
//            ex.getMessage();
//            ex.printStackTrace();
//            //TODO terminate game
//        }
//
//
//    }
}
