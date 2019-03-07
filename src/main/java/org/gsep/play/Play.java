package org.gsep.play;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Play extends Application{
    private final int CANVASWIDTH = 950;
    private final int CANVASHEIGHT = 700;
    private String f= getClass().getResource("/noteFile.txt").getFile();
    private LinkedHashMap mapA;

    public static void main(String[] args) {
        launch(args);
    }
    

    public void start(Stage stage) throws IOException{
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

        Map<Integer, Note[]> song = readFile(f);


//        Map<Integer, Note[]> song = new HashMap<>();
//        song.put(1234, new Note[]{Note.OPEN, Note.OPEN, Note.OPEN});
//        File file = new File(getClass().getResource("/queen.mid").getFile());
//        try{
//            Sequence midiSequence = MidiSystem.getSequence(file);
//
//            Sequencer midiSequencer = MidiSystem.getSequencer();
//
//            midiSequencer.open();
//            midiSequencer.setSequence(midiSequence);
//            midiSequencer.start();
//        } catch (Exception exn) {
//            System.out.println(exn); System.exit(1);
//        }

        stage.show();
        view.startRender();
        controller.play(song, 1.9054689407348633);
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