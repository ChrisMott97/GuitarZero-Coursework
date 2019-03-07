package org.gsep.play;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.image.ImageView; 
import javafx.scene.layout.*;

public class Play extends Application{
    private final int CANVASWIDTH = 950;
    private final int CANVASHEIGHT = 700;
    private ArrayList<Note[]> songSequenceHolder;
    private String f= getClass().getResource("/noteFile.txt").getFile();
    private Note[] notesHolder; 
    private ArrayList<String[]> songNotes;
    private ArrayList<Note[]> songSequence;
    private LinkedHashMap mapA;

    public static void main(String[] args) {
        launch(args);
    }
    

    public void start(Stage stage) throws IOException{
    	/*
        stage.setTitle("Play");

        //initialise scene
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        Canvas canvas = new Canvas(CANVASWIDTH, CANVASHEIGHT);

        root.getChildren().add(createBackground());
        root.getChildren().add(canvas);

        //temporary to find point values
        scene.setOnMouseClicked(e -> {
            System.out.println(e.getSceneX());
            System.out.println(e.getSceneY());
        });

        NoteHighwayModel model = new NoteHighwayModel();
        NoteHighwayView view = new NoteHighwayView(canvas);
        NoteHighwayController controller = new NoteHighwayController(model, view);
        controller.arraySetter(songToGameNotes(readFile(f)));

        Map<Integer, Note[]> song = new HashMap<>();
        song.put(0, new Note[] {Note.OPEN, Note.BLACK, Note.WHITE});

        stage.show();
        view.startRender();
        controller.play(song, 100);
    		mapA=readFile(f);
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
    public  LinkedHashMap readFile(String f) throws IOException {
    			
			BufferedReader in = new BufferedReader(new FileReader(f));
			String str;
			
			//Create ArrayList to hold the lists of notes
		    songNotes = new ArrayList<String[]>();
		    //Create dictionary
		    mapA = new LinkedHashMap();
		  
		    
		    in.readLine(); 
			
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
				mapA.put(split[0], dictValue);

				
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