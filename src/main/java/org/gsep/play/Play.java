package org.gsep.play;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private String noteFilePath;
    private String midiFilePath;
    private LinkedHashMap mapA;

    public Play(Group root, String noteFilePath, String midiFilePath){
        Canvas canvas = new Canvas(CANVASWIDTH, CANVASHEIGHT);

        this.scene = new Scene(root);
        this.noteFilePath = noteFilePath;
        this.midiFilePath = midiFilePath;

        root.getChildren().add(createBackground());
        root.getChildren().add(canvas);

        this.model = new NoteHighwayModel();
        this.view = new NoteHighwayView(canvas);
        this.controller = new NoteHighwayController(model, view);

        String f = getClass().getResource(noteFilePath).getFile();
        Map<Integer, Note[]> song = readFile(f);
    }

    public void play(){
        view.startRender();
        controller.play(song);
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
        mapA = new LinkedHashMap();

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
