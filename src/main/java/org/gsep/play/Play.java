package org.gsep.play;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Play {
    private final int CANVASWIDTH = 950;
    private final int CANVASHEIGHT = 700;
    private Scene scene;
    private NoteHighwayModel model;
    private NoteHighwayView view;
    private NoteHighwayController controller;
    private File midiFile;
    private Map<Integer, Note[]> songSequence;

    /**
     * @author Örs Barkanyi
     * Constructor for Play Mode
     *
     * @param root the root object in the play mode scene
     * @param noteFilePath the path to the selected note file
     * @param midiFilePath the path to the selected midi file
     */
    public Play(String noteFilePath, String midiFilePath){
        Canvas canvas = new Canvas(CANVASWIDTH, CANVASHEIGHT);

        //initialise scene
        Group root = new Group();
        this.scene = new Scene(root);

        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(mouseEvent.getSceneY());
                System.out.println(mouseEvent.getSceneX());
            }
        });

        root.getChildren().add(createBackground());
        root.getChildren().add(canvas);

        //set up mvc
        this.model = new NoteHighwayModel();
        this.view = new NoteHighwayView(canvas);
        this.controller = new NoteHighwayController(model, view);

        //find files
        try{
            this.songSequence = readFile(getClass().getResource(noteFilePath).getFile());
        } catch (Exception e) {
            System.out.println("Note file not found");
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
        controller.play(songSequence,midiFile);
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
     * @return
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
}
